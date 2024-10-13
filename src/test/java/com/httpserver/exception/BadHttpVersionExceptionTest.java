package com.httpserver.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BadHttpVersionExceptionTest {

    @Test
    public void testConstructorWithMessage() {
        String message = "Invalid HTTP version";
        BadHttpVersionException exception = new BadHttpVersionException(message);
        assertEquals(message, exception.getMessage());
    }

    @Test
    public void testConstructorWithMessageAndCause() {
        String message = "Invalid HTTP version";
        Throwable cause = new Throwable("Cause of the exception");
        BadHttpVersionException exception = new BadHttpVersionException(message, cause);
        assertAll(
                () -> assertEquals(message, exception.getMessage()),
                () -> assertEquals(cause, exception.getCause())
        );
    }

    @Test
    public void testConstructorWithCause() {
        Throwable cause = new Throwable("Cause of the exception");
        BadHttpVersionException exception = new BadHttpVersionException(cause);
        assertEquals(cause, exception.getCause());
    }
}
