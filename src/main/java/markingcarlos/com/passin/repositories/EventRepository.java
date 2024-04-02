package markingcarlos.com.passin.repositories;

import markingcarlos.com.passin.domain.events.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, String> {
}
