package com.httpserver.config;

/**
 * Custom exception class to handle configuration-related errors in the HTTP server.
 * This class extends {@link RuntimeException} to provide specific error handling
 * for configuration issues.
 */
public class HttpConfigurationException extends RuntimeException {

    /**
     * Constructs a new HttpConfigurationException with {@code null} as its detail message.
     */
    public HttpConfigurationException() {
    }

    /**
     * Constructs a new HttpConfigurationException with the specified detail message.
     *
     * @param message the detail message, which is saved for later retrieval by the {@link #getMessage()} method
     */
    public HttpConfigurationException(String message) {
        super(message);
    }

    /**
     * Constructs a new HttpConfigurationException with the specified detail message and cause.
     *
     * @param message the detail message, which is saved for later retrieval by the {@link #getMessage()} method
     * @param cause   the cause (which is saved for later retrieval by the {@link #getCause()} method)
     */
    public HttpConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new HttpConfigurationException with the specified cause.
     *
     * @param cause the cause (which is saved for later retrieval by the {@link #getCause()} method)
     */
    public HttpConfigurationException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new HttpConfigurationException with the specified detail message, cause, suppression enabled or disabled,
     * and writable stack trace enabled or disabled.
     *
     * @param message            the detail message, which is saved for later retrieval by the {@link #getMessage()} method
     * @param cause              the cause (which is saved for later retrieval by the {@link #getCause()} method)
     * @param enableSuppression  whether or not suppression is enabled or disabled
     * @param writableStackTrace whether or not the stack trace should be writable
     */
    public HttpConfigurationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
