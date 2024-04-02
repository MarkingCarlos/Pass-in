package markingcarlos.com.passin.dto.event;

import lombok.Getter;
import markingcarlos.com.passin.domain.events.Event;

@Getter
public class EventResponseDTO {
    EventDetailDTO event;
    public  EventResponseDTO(Event event, Integer numberOfAttendees){
        this.event = new EventDetailDTO(
                event.getId(),
                event.getTitle(),
                event.getDetails(),
                event.getSlug(),
                event.getMaximumAttendees(),
                numberOfAttendees
        );
    }
}
