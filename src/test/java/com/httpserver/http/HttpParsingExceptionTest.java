package com.httpserver.http;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class HttpParsingExceptionTest {

    @Test
    void testHttpParsingExceptionMessage() {
        HttpStatusCode statusCode = HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST;
        HttpParsingException exception = new HttpParsingException(statusCode);
        assertEquals(statusCode.MESSAGE, exception.getMessage());
    }

    @Test
    void testHttpParsingExceptionStatusCode() {
        HttpStatusCode statusCode = HttpStatusCode.CLIENT_ERROR_404_NOT_FOUND;
        HttpParsingException exception = new HttpParsingException(statusCode);
        assertEquals(statusCode, exception.getStatusCode());
    }
}
