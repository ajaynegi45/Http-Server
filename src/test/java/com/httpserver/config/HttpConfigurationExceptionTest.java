package com.httpserver.config;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class HttpConfigurationExceptionTest {

    @Test
    void testNoArgsConstructor() {
        HttpConfigurationException exception = new HttpConfigurationException();
        assertAll("No-Args Constructor",
                () -> assertNull(exception.getMessage(), "Message should be null for no-arg constructor."),
                () -> assertNull(exception.getCause(), "Cause should be null for no-arg constructor.")
        );
    }

    @Test
    void testMessageConstructor() {
        String errorMessage = "Configuration error occurred.";
        HttpConfigurationException exception = new HttpConfigurationException(errorMessage);

        assertAll("Message Constructor",
                () -> assertEquals(errorMessage, exception.getMessage(), "Message should match the one provided."),
                () -> assertNull(exception.getCause(), "Cause should be null when only message is provided.")
        );
    }

    @Test
    void testMessageAndCauseConstructor() {
        String errorMessage = "Configuration loading failed.";
        Throwable cause = new IOException("File not found");
        HttpConfigurationException exception = new HttpConfigurationException(errorMessage, cause);

        assertAll("Message and Cause Constructor",
                () -> assertEquals(errorMessage, exception.getMessage(), "Message should match the one provided."),
                () -> assertSame(cause, exception.getCause(), "Cause should match the one provided.")
        );
    }

    @Test
    void testCauseConstructor() {
        Throwable cause = new NullPointerException("Null value encountered");
        HttpConfigurationException exception = new HttpConfigurationException(cause);

        assertAll("Cause Constructor",
                () -> assertSame(cause, exception.getCause(), "Cause should match the one provided."),
                () -> assertEquals(cause.toString(), exception.getMessage(), "Message should match cause.toString() when only cause is provided.")
        );
    }

    @Test
    void testFullConstructor() {
        String errorMessage = "Configuration parsing failed.";
        Throwable cause = new IllegalArgumentException("Invalid format");
        HttpConfigurationException exception = new HttpConfigurationException(errorMessage, cause, true, false);

        assertAll("Full Constructor",
                () -> assertEquals(errorMessage, exception.getMessage(), "Message should match the one provided."),
                () -> assertSame(cause, exception.getCause(), "Cause should match the one provided."),
                () -> assertFalse(exception.getSuppressed().length > 0, "Suppressed exceptions should be empty.")
        );
    }
}
