package project.tenis.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when trying to access court which is not in the system
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class CourtNotFoundException extends RuntimeException {

    public CourtNotFoundException() { }

    public CourtNotFoundException(String expression) {
        super(expression);
    }

    public CourtNotFoundException(String expression, Throwable cause) {
        super(expression, cause);
    }


}
