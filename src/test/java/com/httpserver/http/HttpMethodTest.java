package com.httpserver.http;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HttpMethodTest {

    @Test
    void testEnumValues() {
        HttpMethod[] expectedMethods = {
                HttpMethod.GET, HttpMethod.HEAD, HttpMethod.POST,
                HttpMethod.PUT, HttpMethod.DELETE, HttpMethod.CONNECT,
                HttpMethod.OPTIONS, HttpMethod.TRACE, HttpMethod.PATCH
        };
        assertArrayEquals(expectedMethods, HttpMethod.values());
    }

    @Test
    void testMaxLength() {
        assertEquals(7, HttpMethod.MAX_LENGTH);
    }

    @Test
    void testMethodNameLengths() {
        for (HttpMethod method : HttpMethod.values()) {
            assertTrue(method.name().length() <= HttpMethod.MAX_LENGTH);
        }
    }

    @Test
    void testEnumOrdinal() {
        assertEquals(0, HttpMethod.GET.ordinal());
        assertEquals(1, HttpMethod.HEAD.ordinal());
        assertEquals(2, HttpMethod.POST.ordinal());
        assertEquals(3, HttpMethod.PUT.ordinal());
        assertEquals(4, HttpMethod.DELETE.ordinal());
        assertEquals(5, HttpMethod.CONNECT.ordinal());
        assertEquals(6, HttpMethod.OPTIONS.ordinal());
        assertEquals(7, HttpMethod.TRACE.ordinal());
        assertEquals(8, HttpMethod.PATCH.ordinal());
    }

    @Test
    void testGetDefaultErrorInfo() {
        assertEquals("Error occurred while fetching data with GET method.", HttpMethod.GET.getDefaultErrorInfo());
        assertEquals("Error occurred while fetching headers with HEAD method.", HttpMethod.HEAD.getDefaultErrorInfo());
        assertEquals("Error occurred while submitting data with POST method.", HttpMethod.POST.getDefaultErrorInfo());
        assertEquals("Error occurred while updating data with PUT method.", HttpMethod.PUT.getDefaultErrorInfo());
        assertEquals("Error occurred while deleting data with DELETE method.", HttpMethod.DELETE.getDefaultErrorInfo());
        assertEquals("Error occurred while establishing a connection with CONNECT method.", HttpMethod.CONNECT.getDefaultErrorInfo());
        assertEquals("Error occurred while retrieving options with OPTIONS method.", HttpMethod.OPTIONS.getDefaultErrorInfo());
        assertEquals("Error occurred while tracing the request with TRACE method.", HttpMethod.TRACE.getDefaultErrorInfo());
        assertEquals("Error occurred while partially modifying data with PATCH method.", HttpMethod.PATCH.getDefaultErrorInfo());
    }
}
