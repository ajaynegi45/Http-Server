package com.httpserver.http;

/**
 * Custom exception class for handling bad HTTP version errors.
 * This exception is thrown when the HTTP version specified in a request
 * is not supported by the server.
 */
public class BadHttpVersionException extends Exception {

    /**
     * Constructs a new BadHttpVersionException with no detail message.
     * This constructor is useful when the exception is thrown without
     * a specific message.
     */
    public BadHttpVersionException() {
        super(); // Call to the superclass constructor
    }

    /**
     * Constructs a new BadHttpVersionException with the specified detail message.
     * This constructor allows you to specify a message that describes the error.
     *
     * @param message the detail message explaining the reason for the exception
     */
    public BadHttpVersionException(String message) {
        super(message); // Call to the superclass constructor with the message
    }

    /**
     * Constructs a new BadHttpVersionException with the specified detail message and cause.
     * This constructor allows you to provide both a message and the underlying cause of the exception.
     *
     * @param message the detail message explaining the reason for the exception
     * @param cause the underlying cause of the exception (another throwable)
     */
    public BadHttpVersionException(String message, Throwable cause) {
        super(message, cause); // Call to the superclass constructor with the message and cause
    }

    /**
     * Constructs a new BadHttpVersionException with the specified cause.
     * This constructor is used when the cause of the exception is known,
     * but no specific message is provided.
     *
     * @param cause the underlying cause of the exception (another throwable)
     */
    public BadHttpVersionException(Throwable cause) {
        super(cause); // Call to the superclass constructor with the cause
    }
}
