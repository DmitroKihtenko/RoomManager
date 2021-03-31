package main.java.rm.exceptions;

public class ConnectionDoesNotExistException extends Exception {
    public ConnectionDoesNotExistException() {
    }

    public ConnectionDoesNotExistException(String message) {
        super(message);
    }

    public ConnectionDoesNotExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConnectionDoesNotExistException(Throwable cause) {
        super(cause);
    }

    public ConnectionDoesNotExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
