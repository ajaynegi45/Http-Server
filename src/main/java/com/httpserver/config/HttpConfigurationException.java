package com.httpserver.config;

/**
 * A custom exception class to handle errors related to configuration in the HTTP server.
 * <p>
 * This exception is thrown when there are issues during configuration operations, such as 
 * reading or parsing configuration files. It extends {@link RuntimeException}, allowing it 
 * to be used without requiring explicit handling at compile time.
 * </p>
 */
public class HttpConfigurationException extends RuntimeException {

    /**
     * Constructs a new {@code HttpConfigurationException} with no detail message.
     * <p>
     * This constructor creates an exception without a message, suitable for generic 
     * configuration errors where a detailed message is not necessary.
     * </p>
     */
    public HttpConfigurationException() {
        super();
    }

    /**
     * Constructs a new {@code HttpConfigurationException} with the specified detail message.
     * <p>
     * The message provides specific information about the configuration error.
     * </p>
     *
     * @param message the detail message, saved for later retrieval by the {@link #getMessage()} method
     */
    public HttpConfigurationException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code HttpConfigurationException} with the specified detail message and cause.
     * <p>
     * This constructor allows both a message and a cause (another throwable) to be provided.
     * The cause helps in tracing the origin of the error, especially in cases where the 
     * configuration error results from an underlying issue.
     * </p>
     *
     * @param message the detail message, saved for later retrieval by the {@link #getMessage()} method
     * @param cause   the cause (saved for later retrieval by the {@link #getCause()} method), can be null
     */
    public HttpConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new {@code HttpConfigurationException} with the specified cause.
     * <p>
     * This constructor is useful when an error occurs, and the cause (a lower-level 
     * exception) needs to be propagated as part of the configuration error handling.
     * </p>
     *
     * @param cause the cause of the exception (saved for later retrieval by the {@link #getCause()} method)
     */
    public HttpConfigurationException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new {@code HttpConfigurationException} with the specified detail message, cause, 
     * suppression flag, and writable stack trace flag.
     * <p>
     * This constructor allows full control over the exception behavior, such as whether 
     * suppression is enabled or whether the stack trace should be writable. It's typically 
     * used in advanced scenarios where these options are necessary.
     * </p>
     *
     * @param message            the detail message (saved for later retrieval by the {@link #getMessage()} method)
     * @param cause              the cause (saved for later retrieval by the {@link #getCause()} method), can be null
     * @param enableSuppression  whether or not suppression is enabled
     * @param writableStackTrace whether or not the stack trace should be writable
     */
    public HttpConfigurationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
