package com.httpserver.exception;

/**
 * Custom exception class for handling errors related to invalid HTTP versions.
 * This exception is thrown when the HTTP version in a request is not supported
 * or is incorrectly formatted.
 */
public class BadHttpVersionException extends RuntimeException {

    /**
     * Constructs a new BadHttpVersionException with the specified detail message.
     *
     * @param message the detail message that explains the reason for the exception.
     *                This message can be retrieved later using the {@link Throwable#getMessage()} method.
     */
    public BadHttpVersionException(String message) {
        super(message); // Call the constructor of the superclass (RuntimeException) with the message
    }

    /**
     * Constructs a new BadHttpVersionException with the specified detail message and cause.
     *
     * @param message the detail message that explains the reason for the exception.
     * @param cause the underlying reason for the exception, which can be another throwable.
     *              This can help to trace back the original cause of the error.
     */
    public BadHttpVersionException(String message, Throwable cause) {
        super(message, cause); // Call the constructor of the superclass with both the message and cause
    }

    /**
     * Constructs a new BadHttpVersionException with the specified cause.
     *
     * @param cause the underlying reason for the exception, which can be another throwable.
     *              This is useful for capturing the original error that led to this exception being thrown.
     */
    public BadHttpVersionException(Throwable cause) {
        super(cause); // Call the constructor of the superclass with the cause
    }
}
