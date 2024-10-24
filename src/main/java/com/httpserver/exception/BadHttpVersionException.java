package com.httpserver.exception;

/**
 * Custom exception for Bad HTTP Version Errors.
 */
public class BadHttpVersionException extends RuntimeException {

    /**
     * Constructs a new BadHttpVersionException with the specified detail message.
     *
     * @param message the detail message
     */
    public BadHttpVersionException(String message) {
        super(message);
    }

    /**
     * Constructs a new BadHttpVersionException with the specified detail message and cause.
     *
     * @param message the detail message
     * @param cause   the cause of the exception
     */
    public BadHttpVersionException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new BadHttpVersionException with the specified cause.
     *
     * @param cause the cause of the exception
     */
    public BadHttpVersionException(Throwable cause) {
        super(cause);
    }
}
