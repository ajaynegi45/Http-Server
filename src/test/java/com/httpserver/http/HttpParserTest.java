package com.httpserver.http;

import com.httpserver.exception.HttpParsingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

class HttpParserTest {

    private HttpParser httpParser;

    @BeforeEach
    void setUp() {
        httpParser = new HttpParser();
    }

    @Test
    void testParseHttpRequest_ValidRequest() throws HttpParsingException {
        String requestString = "GET /index.html HTTP/1.1\r\n" +
                "Host: example.com\r\n" +
                "Content-Length: 0\r\n\r\n";
        InputStream inputStream = new ByteArrayInputStream(requestString.getBytes());

        HttpRequest httpRequest = httpParser.parseHttpRequest(inputStream);

        assertNotNull(httpRequest);
        assertEquals(HttpMethod.GET, httpRequest.getMethod());
        assertEquals("/index.html", httpRequest.getRequestTarget());
        assertEquals("example.com", httpRequest.getHeaders().get("Host"));
        assertEquals(0, httpRequest.getBody().length());
    }

    @Test
    void testParseHttpRequest_InvalidMethod() {
        String requestString = "INVALID_METHOD /index.html HTTP/1.1\r\n\r\n";
        InputStream inputStream = new ByteArrayInputStream(requestString.getBytes());

        HttpParsingException exception = assertThrows(HttpParsingException.class, () -> {
            httpParser.parseHttpRequest(inputStream);
        });

        assertEquals(HttpStatusCode.SERVER_ERROR_501_NOT_IMPLEMENTED, exception.getStatusCode());
    }

    @Test
    void testParseHttpRequest_MissingRequestTarget() {
        String requestString = "GET  HTTP/1.1\r\n\r\n";
        InputStream inputStream = new ByteArrayInputStream(requestString.getBytes());

        HttpParsingException exception = assertThrows(HttpParsingException.class, () -> {
            httpParser.parseHttpRequest(inputStream);
        });

        assertEquals(HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST, exception.getStatusCode());
    }

    @Test
    void testParseHttpRequest_InvalidHeader() {
        String requestString = "GET /index.html HTTP/1.1\r\n" +
                "InvalidHeader\r\n\r\n";
        InputStream inputStream = new ByteArrayInputStream(requestString.getBytes());

        HttpParsingException exception = assertThrows(HttpParsingException.class, () -> {
            httpParser.parseHttpRequest(inputStream);
        });

        assertEquals(HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST, exception.getStatusCode());
    }


    @Test
    void testParseHttpRequest_BodyWithZeroContentLength() throws HttpParsingException {
        String requestString = "POST /submit HTTP/1.1\r\n" +
                "Content-Length: 0\r\n\r\n";
        InputStream inputStream = new ByteArrayInputStream(requestString.getBytes());

        HttpRequest httpRequest = httpParser.parseHttpRequest(inputStream);

        assertEquals("", httpRequest.getBody());
    }

    @Test
    void testParseHttpRequest_MissingContentLength() throws HttpParsingException {
        String requestString = "POST /submit HTTP/1.1\r\n\r\n";
        InputStream inputStream = new ByteArrayInputStream(requestString.getBytes());

        HttpRequest httpRequest = httpParser.parseHttpRequest(inputStream);

        assertEquals("", httpRequest.getBody());
    }
}
