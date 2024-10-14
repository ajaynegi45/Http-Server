package com.httpserver.exception;

import com.httpserver.http.HttpStatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Custom exception class for handling HTTP parsing errors.
 * This exception is thrown when there are issues in parsing HTTP requests.
 */
public class HttpParsingException extends RuntimeException {

    private final HttpStatusCode statusCode; // HTTP status code associated with the exception
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpParsingException.class); // Logger for error tracking

    /**
     * Constructs a new HttpParsingException with the specified HttpStatusCode.
     * This constructor is used when the exception is directly related to an HTTP status.
     *
     * @param statusCode the HTTP status code associated with the exception
     */
    public HttpParsingException(HttpStatusCode statusCode) {
        super(statusCode.MESSAGE); // Call to the superclass with the message from the status code
        this.statusCode = statusCode; // Set the status code for this exception
        LOGGER.error("HttpParsingException created with status code: {} - {}", statusCode.STATUS_CODE, statusCode.MESSAGE); // Log error message
        LOGGER.debug("Debug: HttpParsingException initiated with status code: {}", statusCode); // Log debug information
    }

    /**
     * Constructs a new HttpParsingException with a custom detail message.
     * This constructor is useful when you want to provide a specific error message.
     *
     * @param message the detail message describing the error
     */
    public HttpParsingException(String message) {
        super(message); // Call to the superclass with the custom message
        this.statusCode = HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST;  // Default to 400 Bad Request
        LOGGER.error("HttpParsingException created with message: {}", message); // Log error message
        LOGGER.debug("Debug: HttpParsingException initiated with custom message: {}", message); // Log debug information
    }

    /**
     * Constructs a new HttpParsingException with the specified detail message and cause.
     * This constructor is used to provide a custom message and the underlying cause of the exception.
     *
     * @param message the detail message describing the error
     * @param cause   the cause of the exception (another throwable)
     */
    public HttpParsingException(String message, Throwable cause) {
        super(message, cause); // Call to the superclass with the custom message and cause
        this.statusCode = HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST;  // Default to 400 Bad Request
        LOGGER.error("HttpParsingException created with message: {} and cause: {}", message, cause); // Log error message
        LOGGER.debug("Debug: HttpParsingException initiated with message: {} and cause: {}", message, cause); // Log debug information
    }

    /**
     * Constructs a new HttpParsingException with the specified cause.
     * This constructor is used when the cause of the exception is known but no specific message is provided.
     *
     * @param cause the cause of the exception (another throwable)
     */
    public HttpParsingException(Throwable cause) {
        super(cause); // Call to the superclass with the cause of the exception
        this.statusCode = HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST;  // Default to 400 Bad Request
        LOGGER.error("HttpParsingException created with cause: {}", cause); // Log error message
        LOGGER.debug("Debug: HttpParsingException initiated with cause: {}", cause); // Log debug information
    }

    /**
     * Returns the HTTP status code associated with this exception.
     * This method allows other components to retrieve the status code when handling the exception.
     *
     * @return the HttpStatusCode associated with this exception
     */
    public HttpStatusCode getStatusCode() {
        LOGGER.debug("Retrieving HTTP status code: {}", statusCode); // Log the retrieval of the status code
        return statusCode; // Return the status code
    }
}
