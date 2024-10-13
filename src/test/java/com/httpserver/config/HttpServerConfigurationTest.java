package com.httpserver.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class HttpServerConfigurationTest {

    private HttpServerConfiguration config;

    @BeforeEach
    void setUp() {
        config = new HttpServerConfiguration();
    }

    @Test
    void testDefaultConstructor() {
        assertEquals(0, config.getHttpPort(), "Default HTTP port should be 0");
        assertEquals(0, config.getHttpsPort(), "Default HTTPS port should be 0");
        assertNull(config.getWebroot(), "Default webroot should be null");
    }

    @Test
    void testParameterizedConstructor() {
        config = new HttpServerConfiguration(8080, 8443, "/var/www");
        assertEquals(8080, config.getHttpPort(), "HTTP port should be set to 8080");
        assertEquals(8443, config.getHttpsPort(), "HTTPS port should be set to 8443");
        assertEquals("/var/www", config.getWebroot(), "Webroot should be set to '/var/www'");
    }

    @Test
    void testSetAndGetHttpPort() {
        config.setHttpPort(9090);
        assertEquals(9090, config.getHttpPort(), "HTTP port should be set to 9090");
    }

    @Test
    void testSetAndGetHttpsPort() {
        config.setHttpsPort(9443);
        assertEquals(9443, config.getHttpsPort(), "HTTPS port should be set to 9443");
    }

    @Test
    void testSetAndGetWebroot() {
        config.setWebroot("/home/user");
        assertEquals("/home/user", config.getWebroot(), "Webroot should be set to '/home/user'");
    }

    @Test
    void testSetHttpPortWithNegativeValue() {
        assertThrows(IllegalArgumentException.class, () -> config.setHttpPort(-1),
                "Setting a negative HTTP port should throw IllegalArgumentException");
    }

    @Test
    void testSetHttpsPortWithNegativeValue() {
        assertThrows(IllegalArgumentException.class, () -> config.setHttpsPort(-1),
                "Setting a negative HTTPS port should throw IllegalArgumentException");
    }

    @Test
    void testSetWebrootWithNullValue() {
        config.setWebroot(null);
        assertNull(config.getWebroot(), "Setting webroot to null should be allowed and return null");
    }

    @Test
    void testSetHttpPortBoundaryValues() {
        config.setHttpPort(0);
        assertEquals(0, config.getHttpPort(), "HTTP port should be allowed to be set to 0");

        config.setHttpPort(65535);
        assertEquals(65535, config.getHttpPort(), "HTTP port should be allowed to be set to 65535");
    }

    @Test
    void testSetHttpsPortBoundaryValues() {
        config.setHttpsPort(0);
        assertEquals(0, config.getHttpsPort(), "HTTPS port should be allowed to be set to 0");

        config.setHttpsPort(65535);
        assertEquals(65535, config.getHttpsPort(), "HTTPS port should be allowed to be set to 65535");
    }

    @Test
    void testToStringMethod() {
        config = new HttpServerConfiguration(8080, 8443, "/var/www");
        String expected = "HttpServerConfiguration{httpPort=8080, httpsPort=8443, webroot='/var/www'}";
        assertEquals(expected, config.toString(), "toString method output should match expected string");
    }
}
