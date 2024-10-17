package com.httpserver.http;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HttpVersionTest {

    @Test
    void testValidHttpVersion() throws BadHttpVersionException {
        HttpVersion version = HttpVersion.getBestCompatibleVersion("HTTP/1.1");
        assertNotNull(version);
        assertEquals(HttpVersion.HTTP_1_1, version);
    }

    @Test
    void testInvalidHttpVersionFormat() {
        assertThrows(BadHttpVersionException.class, () -> {
            HttpVersion.getBestCompatibleVersion("HTTP/2");
        });
    }

    @Test
    void testUnsupportedHttpVersion() throws BadHttpVersionException {
        HttpVersion version = HttpVersion.getBestCompatibleVersion("HTTP/1.0");
        assertNull(version);
    }

    @Test
    void testMatchingMajorVersion() throws BadHttpVersionException {
        HttpVersion version = HttpVersion.getBestCompatibleVersion("HTTP/1.0");
        assertNull(version); // Expect null since 1.0 is not in the enum
    }

    @Test
    void testInvalidVersionPattern() {
        assertThrows(BadHttpVersionException.class, () -> {
            HttpVersion.getBestCompatibleVersion("INVALID/1.1");
        });
    }
}
