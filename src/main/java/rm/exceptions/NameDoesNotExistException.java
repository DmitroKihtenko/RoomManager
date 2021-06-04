package rm.exceptions;

/**
 * Exception for class {@link rm.service.Context} that creates if object does not exist some object with specified name
 */
public class NameDoesNotExistException extends RuntimeException {
    public NameDoesNotExistException() {
    }

    public NameDoesNotExistException(String message) {
        super(message);
    }

    public NameDoesNotExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public NameDoesNotExistException(Throwable cause) {
        super(cause);
    }

    public NameDoesNotExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
