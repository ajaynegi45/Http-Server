package com.httpserver.exception;

import com.httpserver.http.HttpStatusCode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExceptionHandlerTest {

    @Test
    public void testHandleHttpParsingException() {
        HttpStatusCode statusCode = HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST;
        HttpParsingException exception = new HttpParsingException(statusCode);
        String response = ExceptionHandler.handleException(exception);
        assertEquals("HTTP/1.1 400 Bad Request", response);
    }

    @Test
    public void testHandleBadHttpVersionException() {
        BadHttpVersionException exception = new BadHttpVersionException("Bad HTTP version");
        String response = ExceptionHandler.handleException(exception);
        assertEquals("HTTP/1.1 505 HTTP Version Not Supported", response);
    }

    @Test
    public void testHandleGenericException() {
        Exception exception = new Exception("Generic error");
        String response = ExceptionHandler.handleException(exception);
        assertEquals("HTTP/1.1 500 Internal Server Error", response);
    }
}
