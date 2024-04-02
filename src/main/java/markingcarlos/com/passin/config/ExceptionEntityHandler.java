package markingcarlos.com.passin.config;

import markingcarlos.com.passin.domain.events.exceptions.EventNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionEntityHandler {
   @ExceptionHandler(EventNotFoundException.class)
    public ResponseEntity handleEventNotFound(EventNotFoundException exception){
        return ResponseEntity.notFound().build();
    }
}
