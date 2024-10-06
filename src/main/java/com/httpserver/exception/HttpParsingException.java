package com.httpserver.exception;

import com.httpserver.http.HttpStatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Custom exception for HTTP Parsing Errors.
 */
public class HttpParsingException extends RuntimeException {

    private final HttpStatusCode statusCode;
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpParsingException.class);

    /**
     * Constructs a new HttpParsingException with the specified HttpStatusCode.
     *
     * @param statusCode the HTTP status code associated with the exception
     */
    public HttpParsingException(HttpStatusCode statusCode) {
        super(statusCode.MESSAGE);
        this.statusCode = statusCode;
        LOGGER.error("HttpParsingException created with status code: {} - {}", statusCode.STATUS_CODE, statusCode.MESSAGE);
        LOGGER.debug("Debug: HttpParsingException initiated with status code: {}", statusCode);
    }

    /**
     * Constructs a new HttpParsingException with a custom detail message.
     *
     * @param message the detail message
     */
    public HttpParsingException(String message) {
        super(message);
        this.statusCode = HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST;  // Defaulting to 400 Bad Request
        LOGGER.error("HttpParsingException created with message: {}", message);
        LOGGER.debug("Debug: HttpParsingException initiated with custom message: {}", message);
    }

    /**
     * Constructs a new HttpParsingException with the specified detail message and cause.
     *
     * @param message the detail message
     * @param cause   the cause of the exception
     */
    public HttpParsingException(String message, Throwable cause) {
        super(message, cause);
        this.statusCode = HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST;  // Defaulting to 400 Bad Request
        LOGGER.error("HttpParsingException created with message: {} and cause: {}", message, cause);
        LOGGER.debug("Debug: HttpParsingException initiated with message: {} and cause: {}", message, cause);
    }

    /**
     * Constructs a new HttpParsingException with the specified cause.
     *
     * @param cause the cause of the exception
     */
    public HttpParsingException(Throwable cause) {
        super(cause);
        this.statusCode = HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST;  // Defaulting to 400 Bad Request
        LOGGER.error("HttpParsingException created with cause: {}", cause);
        LOGGER.debug("Debug: HttpParsingException initiated with cause: {}", cause);
    }

    /**
     * Returns the HTTP status code associated with this exception.
     *
     * @return the HttpStatusCode
     */
    public HttpStatusCode getStatusCode() {
        LOGGER.debug("Retrieving HTTP status code: {}", statusCode);
        return statusCode;
    }
}
