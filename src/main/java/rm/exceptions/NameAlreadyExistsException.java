package main.java.rm.exceptions;

public class NameAlreadyExistsException extends Exception {
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
