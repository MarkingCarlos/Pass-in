package markingcarlos.com.passin.domain.attendee.exceptions;

public class AttendeeAlreadyExisteException extends  RuntimeException {
    public AttendeeAlreadyExisteException(String message){
        super(message);
    }

}
