package markingcarlos.com.passin.domain.checkin.exceptions;

public class CheckInAlreadyExistsExceptional extends RuntimeException{
    public CheckInAlreadyExistsExceptional(String message){
        super((message));
    }
}
