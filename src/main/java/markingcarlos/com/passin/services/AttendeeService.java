package markingcarlos.com.passin.services;

import lombok.RequiredArgsConstructor;
import markingcarlos.com.passin.domain.attendee.Attendee;
import markingcarlos.com.passin.domain.attendee.exceptions.AttendeeAlreadyExisteException;
import markingcarlos.com.passin.domain.attendee.exceptions.AttendeeNotFoundException;
import markingcarlos.com.passin.domain.checkin.Checkin;
import markingcarlos.com.passin.dto.attendee.AttendeeBadgeResponseDTO;
import markingcarlos.com.passin.dto.attendee.AttendeeDetails;
import markingcarlos.com.passin.dto.attendee.AttendeeListDTO;
import markingcarlos.com.passin.dto.attendee.AttendeeBadgeDTO;
import markingcarlos.com.passin.repositories.AttendeeRepository;
import markingcarlos.com.passin.repositories.CheckInRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AttendeeService {
    private  final AttendeeRepository attendeeRepository;
    private final CheckInService checkInService;
    public List<Attendee> getAllAttendee(String eventId){
        return this.attendeeRepository.findByEventId(eventId);

    }

    public AttendeeListDTO getEventsAttendee(String eventId){
        List<Attendee> attendeeList = this.getAllAttendee(eventId);
        List<AttendeeDetails> attendeeDetailsList = attendeeList.stream().map(attendee -> {
           Optional<Checkin> checkin = this.checkInService.getCheckIn(attendee.getId());
            LocalDateTime checkedInAt = checkin.isPresent() ? checkin.get().getCreatedAt() : null;
            return new AttendeeDetails(attendee.getId(), attendee.getName(), attendee.getEmail(), attendee.getCreatedAt(), checkedInAt);
        }).toList();
        return new AttendeeListDTO(attendeeDetailsList);
    }
    public Attendee registerAttendee(Attendee newAttendee){
        this.attendeeRepository.save(newAttendee);
        return newAttendee;
    }

    public void verifyAttendeSubscrition(String Email, String EventId){
        Optional<Attendee> isAttendeeRegister = this.attendeeRepository.findByEventIdAndEmail(EventId,Email);
        if (isAttendeeRegister.isPresent()) throw new AttendeeAlreadyExisteException("Attendee is already register");
    }

    public AttendeeBadgeResponseDTO getAttendeeBadge(String attendeeId, UriComponentsBuilder uriComponentsBuilder){
       Attendee attendee = this.getAttendee(attendeeId);

        var uri = uriComponentsBuilder.path("/attendees/{attendeeId}/check-in").buildAndExpand(attendeeId).toUri().toString();
        AttendeeBadgeDTO attendeeBadgeDTO = new AttendeeBadgeDTO(attendee.getName(),attendee.getEmail(),uri,attendee.getEvent().getId());
       return new AttendeeBadgeResponseDTO(attendeeBadgeDTO);
    }

    public void checkInAttendee(String attendeeId){
        Attendee attendee = this.getAttendee(attendeeId);
        this.checkInService.RegistercheckIn(attendee);

    }

    private Attendee getAttendee(String attendeeId){
        return this.attendeeRepository.findById(attendeeId).orElseThrow(() -> new AttendeeNotFoundException("Participantes não encontrado"));
    }


}
