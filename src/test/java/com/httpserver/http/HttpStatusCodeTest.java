package com.httpserver.http;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HttpStatusCodeTest {

    @Test
    public void testHttpStatusCodeValues() {

        // Test SUCCESS CODES
        assertEquals(200, HttpStatusCode.SUCCESS_200_OK.STATUS_CODE);
        assertEquals("OK", HttpStatusCode.SUCCESS_200_OK.MESSAGE);

        assertEquals(201, HttpStatusCode.SUCCESS_201_CREATED.STATUS_CODE);
        assertEquals("Created", HttpStatusCode.SUCCESS_201_CREATED.MESSAGE);

        assertEquals(204, HttpStatusCode.SUCCESS_204_NO_CONTENT.STATUS_CODE);
        assertEquals("No Content", HttpStatusCode.SUCCESS_204_NO_CONTENT.MESSAGE);

        // Test CLIENT ERRORS
        assertEquals(400, HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST.STATUS_CODE);
        assertEquals("Bad Request", HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST.MESSAGE);

        assertEquals(401, HttpStatusCode.CLIENT_ERROR_401_UNAUTHORIZED.STATUS_CODE);
        assertEquals("Unauthorized", HttpStatusCode.CLIENT_ERROR_401_UNAUTHORIZED.MESSAGE);

        assertEquals(403, HttpStatusCode.CLIENT_ERROR_403_FORBIDDEN.STATUS_CODE);
        assertEquals("Forbidden", HttpStatusCode.CLIENT_ERROR_403_FORBIDDEN.MESSAGE);

        assertEquals(404, HttpStatusCode.CLIENT_ERROR_404_NOT_FOUND.STATUS_CODE);
        assertEquals("Not Found", HttpStatusCode.CLIENT_ERROR_404_NOT_FOUND.MESSAGE);

        assertEquals(405, HttpStatusCode.CLIENT_ERROR_405_METHOD_NOT_ALLOWED.STATUS_CODE);
        assertEquals("Method Not Allowed", HttpStatusCode.CLIENT_ERROR_405_METHOD_NOT_ALLOWED.MESSAGE);

        assertEquals(408, HttpStatusCode.CLIENT_ERROR_408_REQUEST_TIMEOUT.STATUS_CODE);
        assertEquals("Request Timeout", HttpStatusCode.CLIENT_ERROR_408_REQUEST_TIMEOUT.MESSAGE);

        assertEquals(413, HttpStatusCode.CLIENT_ERROR_413_PAYLOAD_TOO_LARGE.STATUS_CODE);
        assertEquals("Payload Too Large", HttpStatusCode.CLIENT_ERROR_413_PAYLOAD_TOO_LARGE.MESSAGE);

        assertEquals(415, HttpStatusCode.CLIENT_ERROR_415_UNSUPPORTED_MEDIA_TYPE.STATUS_CODE);
        assertEquals("Unsupported Media Type", HttpStatusCode.CLIENT_ERROR_415_UNSUPPORTED_MEDIA_TYPE.MESSAGE);

        assertEquals(429, HttpStatusCode.CLIENT_ERROR_429_TOO_MANY_REQUESTS.STATUS_CODE);
        assertEquals("Too Many Requests", HttpStatusCode.CLIENT_ERROR_429_TOO_MANY_REQUESTS.MESSAGE);

        // Test SERVER ERRORS
        assertEquals(500, HttpStatusCode.SERVER_ERROR_500_INTERNAL_SERVER_ERROR.STATUS_CODE);
        assertEquals("Internal Server Error", HttpStatusCode.SERVER_ERROR_500_INTERNAL_SERVER_ERROR.MESSAGE);

        assertEquals(501, HttpStatusCode.SERVER_ERROR_501_NOT_IMPLEMENTED.STATUS_CODE);
        assertEquals("Not Implemented", HttpStatusCode.SERVER_ERROR_501_NOT_IMPLEMENTED.MESSAGE);

        assertEquals(502, HttpStatusCode.SERVER_ERROR_502_BAD_GATEWAY.STATUS_CODE);
        assertEquals("Bad Gateway", HttpStatusCode.SERVER_ERROR_502_BAD_GATEWAY.MESSAGE);

        assertEquals(503, HttpStatusCode.SERVER_ERROR_503_SERVICE_UNAVAILABLE.STATUS_CODE);
        assertEquals("Service Unavailable", HttpStatusCode.SERVER_ERROR_503_SERVICE_UNAVAILABLE.MESSAGE);

        assertEquals(504, HttpStatusCode.SERVER_ERROR_504_GATEWAY_TIMEOUT.STATUS_CODE);
        assertEquals("Gateway Timeout", HttpStatusCode.SERVER_ERROR_504_GATEWAY_TIMEOUT.MESSAGE);

        assertEquals(505, HttpStatusCode.SERVER_ERROR_505_HTTP_VERSION_NOT_SUPPORTED.STATUS_CODE);
        assertEquals("HTTP Version Not Supported", HttpStatusCode.SERVER_ERROR_505_HTTP_VERSION_NOT_SUPPORTED.MESSAGE);
    }
}
