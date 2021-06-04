package rm.exceptions;

/**
 * Exception for class {@link rm.model.ConnectionsList} that creates if object does not contain some connection
 */
public class ConnectionDoesNotExistException extends RuntimeException {
    public ConnectionDoesNotExistException() {
    }

    public ConnectionDoesNotExistException(String message) {
        super(message);
    }

    public ConnectionDoesNotExistException(String message,
                                           Throwable cause) {
        super(message, cause);
    }

    public ConnectionDoesNotExistException(Throwable cause) {
        super(cause);
    }

    public ConnectionDoesNotExistException(String message,
                                           Throwable cause,
                                           boolean enableSuppression,
                                           boolean writableStackTrace
    ) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
