package markingcarlos.com.passin.repositories;

import markingcarlos.com.passin.domain.attendee.Attendee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AttendeeRepository extends JpaRepository<Attendee, String> {
     List<Attendee> findByEventId(String eventId);

     Optional<Attendee> findByEventIdAndEmail(String EventId, String Email);


}
