package project.tenis.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when trying to create reservation with invalid arguments
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidReservationException extends RuntimeException {

    public InvalidReservationException() { }

    public InvalidReservationException(String expression) {
        super(expression);
    }

    public InvalidReservationException(String expression, Throwable cause) {
        super(expression, cause);
    }
}
