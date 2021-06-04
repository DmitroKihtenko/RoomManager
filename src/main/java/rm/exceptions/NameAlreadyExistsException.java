package rm.exceptions;

/**
 * Exception for class {@link rm.service.Context} that creates if object already has some object with specified name
 */
public class NameAlreadyExistsException extends RuntimeException {
    public NameAlreadyExistsException() {
    }

    public NameAlreadyExistsException(String message) {
        super(message);
    }

    public NameAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public NameAlreadyExistsException(Throwable cause) {
        super(cause);
    }

    public NameAlreadyExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
