package main.java.rm.exceptions;

public class NameDoesNotExistException extends Exception {
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
