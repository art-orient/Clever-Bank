package com.art.clever.exception;

public class CleverDatabaseException extends Exception {
    public CleverDatabaseException() {
        super();
    }

    public CleverDatabaseException(String message) {
        super(message);
    }

    public CleverDatabaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public CleverDatabaseException(Throwable cause) {
        super(cause);
    }
}
