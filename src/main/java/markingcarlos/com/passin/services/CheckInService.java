package markingcarlos.com.passin.services;

import lombok.RequiredArgsConstructor;
import markingcarlos.com.passin.domain.attendee.Attendee;
import markingcarlos.com.passin.domain.checkin.Checkin;
import markingcarlos.com.passin.domain.checkin.exceptions.CheckInAlreadyExistsExceptional;
import markingcarlos.com.passin.repositories.CheckInRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CheckInService {

    private final CheckInRepository checkInRepository;

    public void RegistercheckIn(Attendee attendee){
        this.verifyCheInExists(attendee.getId());
        Checkin newCheIn = new Checkin();
        newCheIn.setAttendee(attendee);
        newCheIn.setCreatedAt(LocalDateTime.now());
        this.checkInRepository.save(newCheIn);
    }

    private void verifyCheInExists(String attendeeId){
        Optional<Checkin> IscheckIn = this.getCheckIn(attendeeId);
        if (IscheckIn.isPresent()) throw new CheckInAlreadyExistsExceptional(("Attendee already checkIn"));

    }

    public Optional<Checkin> getCheckIn(String attendeeId){
        return this.checkInRepository.findByAttendeeId(attendeeId);
    }

}
