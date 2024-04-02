package markingcarlos.com.passin.services;

import lombok.RequiredArgsConstructor;
import markingcarlos.com.passin.domain.attendee.Attendee;
import markingcarlos.com.passin.domain.checkin.Checkin;
import markingcarlos.com.passin.dto.attendee.AttendeeDetails;
import markingcarlos.com.passin.dto.attendee.AttendeeListDTO;
import markingcarlos.com.passin.repositories.AttendeeRepository;
import markingcarlos.com.passin.repositories.CheckInRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AttendeeService {
    private  final AttendeeRepository attendee;
    private final CheckInRepository checkinRepository;
    public List<Attendee> getAllAttendee(String eventId){
        return this.attendee.findByEventId(eventId);

    }

    public AttendeeListDTO getEventsAttendee(String eventId){
        List<Attendee> attendeeList = this.getAllAttendee(eventId);
        List<AttendeeDetails> attendeeDetailsList = attendeeList.stream().map(attendee -> {
           Optional<Checkin> checkin = this.checkinRepository.findByAttendeeId(attendee.getId());
            LocalDateTime checkedInAt = checkin.isPresent() ? checkin.get().getCreatedAt() : null;
            return new AttendeeDetails(attendee.getId(), attendee.getName(), attendee.getEmail(), attendee.getCreatedAt(), checkedInAt);
        }).toList();
        return new AttendeeListDTO(attendeeDetailsList);
    }
}
