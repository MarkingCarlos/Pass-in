package markingcarlos.com.passin.config;

import markingcarlos.com.passin.domain.attendee.exceptions.AttendeeAlreadyExisteException;
import markingcarlos.com.passin.domain.attendee.exceptions.AttendeeNotFoundException;
import markingcarlos.com.passin.domain.checkin.exceptions.CheckInAlreadyExistsExceptional;
import markingcarlos.com.passin.domain.events.exceptions.EventFullException;
import markingcarlos.com.passin.domain.events.exceptions.EventNotFoundException;
import markingcarlos.com.passin.dto.general.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionEntityHandler {
   @ExceptionHandler(EventNotFoundException.class)
    public ResponseEntity handleEventNotFound(EventNotFoundException exception){
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(AttendeeNotFoundException.class)
    public ResponseEntity handleAttendeeNotFound(AttendeeNotFoundException exception){
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(AttendeeAlreadyExisteException.class)
    public ResponseEntity handleAttendeeAlreadyExiste(AttendeeAlreadyExisteException exception){
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @ExceptionHandler(CheckInAlreadyExistsExceptional.class)
    public ResponseEntity handleCheckInAlreadyExiste(CheckInAlreadyExistsExceptional exception){
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
    @ExceptionHandler(EventFullException.class)
    public ResponseEntity<ErrorResponseDTO> handleEventFull(EventFullException exception){
        return ResponseEntity.badRequest().body(new ErrorResponseDTO(exception.getMessage()));
    }

}
