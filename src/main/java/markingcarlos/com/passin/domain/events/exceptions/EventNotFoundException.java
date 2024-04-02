package markingcarlos.com.passin.domain.events.exceptions;

public class EventNotFoundException extends RuntimeException{
    public  EventNotFoundException(String mensagem){
        super(mensagem);

    }
}
