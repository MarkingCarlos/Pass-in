package markingcarlos.com.passin.repositories;

import markingcarlos.com.passin.domain.attendee.Attendee;
import markingcarlos.com.passin.domain.events.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, String> {

}
