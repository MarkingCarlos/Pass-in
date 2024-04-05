package markingcarlos.com.passin.controller;

import lombok.RequiredArgsConstructor;
import markingcarlos.com.passin.dto.attendee.AttendeRequestDTO;
import markingcarlos.com.passin.dto.attendee.AttendeeIdDto;
import markingcarlos.com.passin.dto.attendee.AttendeeListDTO;
import markingcarlos.com.passin.dto.event.*;
import markingcarlos.com.passin.services.AttendeeService;
import markingcarlos.com.passin.services.EventServices;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {
    private final EventServices eventServices;
    private final AttendeeService attendeeService;
    @GetMapping("/{eventId}")
    public ResponseEntity<EventResponseDTO> getEvent(@PathVariable String eventId){
        EventResponseDTO event =  this.eventServices.getEventDetail(eventId);
        return ResponseEntity.ok(event);
    }
    @PostMapping
    public ResponseEntity<EventIdDTO> createEvent(@RequestBody EventRequestDTO body, UriComponentsBuilder uriComponentsBuilder){
        EventIdDTO event = this.eventServices.createEvent(body);
        var uri = uriComponentsBuilder.path("/events/{id}").buildAndExpand(event.eventId()).toUri();
        return ResponseEntity.created(uri).body(event);
    }

    @GetMapping("/attendees/{id}")
    public ResponseEntity<AttendeeListDTO> getEventAttendee(@PathVariable String id){
        AttendeeListDTO attendeesListResponse = this.attendeeService.getEventsAttendee(id);
        return ResponseEntity.ok(attendeesListResponse);
    }
    @GetMapping("/list")
    public ResponseEntity<EventListDTO> getEvents(){
        EventListDTO eventListDTO = this.eventServices.getEvents();
        return ResponseEntity.ok(eventListDTO);
    }


    @PostMapping("/{eventId}/attendees")
    public ResponseEntity<AttendeeIdDto> RegisterParticipantes(@PathVariable String eventId, @RequestBody AttendeRequestDTO body, UriComponentsBuilder uriComponentsBuilder){
        AttendeeIdDto attendeeIdDto = this.eventServices.registerAttendeeOnEvent(eventId,body);
        var uri = uriComponentsBuilder.path("/attendees//{attendeeid}/badge").buildAndExpand(attendeeIdDto.AttendeeId()).toUri();
        return ResponseEntity.created(uri).body(attendeeIdDto);
    }


}
