package markingcarlos.com.passin.services;

import lombok.RequiredArgsConstructor;
import markingcarlos.com.passin.domain.attendee.Attendee;
import markingcarlos.com.passin.domain.events.Event;
import markingcarlos.com.passin.domain.events.exceptions.EventFullException;
import markingcarlos.com.passin.domain.events.exceptions.EventNotFoundException;
import markingcarlos.com.passin.dto.attendee.AttendeRequestDTO;
import markingcarlos.com.passin.dto.attendee.AttendeeIdDto;
import markingcarlos.com.passin.dto.event.EventIdDTO;
import markingcarlos.com.passin.dto.event.EventRequestDTO;
import markingcarlos.com.passin.dto.event.EventResponseDTO;
import markingcarlos.com.passin.repositories.EventRepository;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventServices {
    private  final EventRepository eventRepository;
    private  final AttendeeService attendeeService;

    public EventResponseDTO getEventDetail(String eventId){
        Event event = this.getEventById(eventId);
        List<Attendee> attendeeList = this.attendeeService.getAllAttendee(eventId);
        return new EventResponseDTO(event, attendeeList.size());
    }
    public EventIdDTO createEvent(EventRequestDTO eventDTO){
        Event event = new Event();
        event.setTitle(eventDTO.title());
        event.setDetails(eventDTO.details());
        event.setMaximumAttendees(eventDTO.maximunAttendees());
        event.setSlug(this.createSlug(eventDTO.title()));
        this.eventRepository.save(event);
        return new EventIdDTO(event.getId());
    }

    private String createSlug(String text){
        String normalized = Normalizer.normalize(text, Normalizer.Form.NFD);
        return normalized.replaceAll("[\\p{InCOMBINING_DIACRITICAL_MARKS}]", "")
                .replaceAll("[^\\w\\s]", "")
                .replaceAll("\\s+", "-")
                .toLowerCase();
    }

    public AttendeeIdDto registerAttendeeOnEvent(String eventId, AttendeRequestDTO attendeRequestDTO){
        this.attendeeService.verifyAttendeSubscrition(attendeRequestDTO.email(),eventId);

        Event event = this.getEventById(eventId);
        List<Attendee> attendeeList = this.attendeeService.getAllAttendee(eventId);

        if (event.getMaximumAttendees() <= attendeeList.size()){
            throw  new EventFullException("Evento Lotado");
        }else{
            Attendee newAttendee = new Attendee();
            newAttendee.setName(attendeRequestDTO.name());
            newAttendee.setEmail(attendeRequestDTO.email());
            newAttendee.setEvent(event);
            newAttendee.setCreatedAt(LocalDateTime.now());
            this.attendeeService.registerAttendee((newAttendee));
            return new AttendeeIdDto(newAttendee.getId());

        }

    }
    private Event getEventById(String eventId){
        return this.eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException("Event not found with id:"+ eventId));

    }



}
