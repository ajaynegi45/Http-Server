package com.httpserver.http;

import com.httpserver.exception.HttpParsingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HttpRequestTest {

    private HttpRequest httpRequest;

    @BeforeEach
    void setUp() {
        httpRequest = new HttpRequest();
    }

    @Test
    void testSetMethod_GET() {
        HttpMethod method = HttpMethod.GET;
        httpRequest.setMethod(method);
        assertEquals(method, httpRequest.getMethod());
    }

    @Test
    void testSetMethod_POST() {
        HttpMethod method = HttpMethod.POST;
        httpRequest.setMethod(method);
        assertEquals(method, httpRequest.getMethod());
    }

    @Test
    void testSetMethod_PUT() {
        HttpMethod method = HttpMethod.PUT;
        httpRequest.setMethod(method);
        assertEquals(method, httpRequest.getMethod());
    }

    @Test
    void testSetMethod_DELETE() {
        HttpMethod method = HttpMethod.DELETE;
        httpRequest.setMethod(method);
        assertEquals(method, httpRequest.getMethod());
    }

    @Test
    void testSetRequestTarget_Valid() throws HttpParsingException {
        String validTarget = "/api/resource";
        httpRequest.setRequestTarget(validTarget);
        assertEquals(validTarget, httpRequest.getRequestTarget());
    }

    @Test
    void testSetRequestTarget_EmptyTarget() {
        assertThrows(HttpParsingException.class, () -> {
            httpRequest.setRequestTarget("");
        });
    }

    @Test
    void testSetRequestTarget_NullTarget() {
        assertThrows(HttpParsingException.class, () -> {
            httpRequest.setRequestTarget(null);
        });
    }

    @Test
    void testSetHttpVersion_Valid() throws BadHttpVersionException, HttpParsingException {
        String validHttpVersion = "HTTP/1.1";
        httpRequest.setHttpVersion(validHttpVersion);
        assertEquals(validHttpVersion, httpRequest.getOriginalHttpVersion());
        assertEquals(HttpVersion.HTTP_1_1, httpRequest.getBestCompatibleHttpVersion());
    }

    @Test
    void testSetHttpVersion_Invalid() {
        assertThrows(BadHttpVersionException.class, () -> {
            httpRequest.setHttpVersion("INVALID_VERSION");
        });
    }

    @Test
    void testSetHttpVersion_Unsupported() {
        assertThrows(HttpParsingException.class, () -> {
            httpRequest.setHttpVersion("HTTP/2.0");
        });
    }

    @Test
    void testAddHeader() {
        httpRequest.addHeader("Content-Type", "application/json");
        assertEquals("application/json", httpRequest.getHeaders().get("Content-Type"));
    }

    @Test
    void testSetBody() {
        String bodyContent = "This is a request body";
        httpRequest.setBody(bodyContent);
        assertEquals(bodyContent, httpRequest.getBody());
    }


    @Test
    void testIsRequestAllowed_WithinLimit() {
        String clientId = "client1";
        for (int i = 0; i < 100; i++) {
            assertTrue(httpRequest.isRequestAllowed(clientId), "Request should be allowed");
        }
    }

    @Test
    void testIsRequestAllowed_ExceedsLimit() {
        String clientId = "client2";
        for (int i = 0; i < 100; i++) {
            assertTrue(httpRequest.isRequestAllowed(clientId), "Request should be allowed");
        }
        assertFalse(httpRequest.isRequestAllowed(clientId), "Request should be denied after exceeding limit");
    }

    @Test
    void testGetTraceId() {
        assertNotNull(httpRequest.getTraceId(), "Trace ID should not be null");
    }

    @Test
    void testGetRequestId() {
        assertNotNull(httpRequest.getRequestId(), "Request ID should not be null");
    }

    @Test
    void testAddHeaderInvalidHeader() {
        assertThrows(IllegalArgumentException.class, () -> {
            httpRequest.addHeader(null, "application/json");
        }, "Header name must not be null");

        assertThrows(IllegalArgumentException.class, () -> {
            httpRequest.addHeader("Content-Type", null);
        }, "Header value must not be null");

        assertThrows(IllegalArgumentException.class, () -> {
            httpRequest.addHeader("", "application/json");
        }, "Header name must not be empty");
    }
}
