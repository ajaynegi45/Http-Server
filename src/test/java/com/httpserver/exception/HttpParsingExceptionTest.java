package com.httpserver.exception;

import com.httpserver.http.HttpStatusCode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HttpParsingExceptionTest {

    @Test
    public void testConstructorWithStatusCode() {
        HttpStatusCode statusCode = HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST;
        HttpParsingException exception = new HttpParsingException(statusCode);
        assertAll(
                () -> assertEquals(statusCode.MESSAGE, exception.getMessage()),
                () -> assertEquals(statusCode, exception.getStatusCode())
        );
    }

    @Test
    public void testConstructorWithCustomMessage() {
        String message = "Malformed request";
        HttpParsingException exception = new HttpParsingException(message);
        assertAll(
                () -> assertEquals(message, exception.getMessage()),
                () -> assertEquals(HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST, exception.getStatusCode())
        );
    }

    @Test
    public void testConstructorWithMessageAndCause() {
        String message = "Malformed request";
        Throwable cause = new Throwable("Cause of the exception");
        HttpParsingException exception = new HttpParsingException(message, cause);
        assertAll(
                () -> assertEquals(message, exception.getMessage()),
                () -> assertEquals(HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST, exception.getStatusCode()),
                () -> assertEquals(cause, exception.getCause())
        );
    }

    @Test
    public void testConstructorWithCause() {
        Throwable cause = new Throwable("Cause of the exception");
        HttpParsingException exception = new HttpParsingException(cause);
        assertAll(
                () -> assertEquals(cause.getMessage(), exception.getCause().getMessage()),
                () -> assertEquals(HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST, exception.getStatusCode())
        );
    }

    @Test
    public void testGetStatusCode() {
        HttpStatusCode statusCode = HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST;
        HttpParsingException exception = new HttpParsingException(statusCode);
        assertEquals(statusCode, exception.getStatusCode());
    }
}
