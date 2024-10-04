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
}