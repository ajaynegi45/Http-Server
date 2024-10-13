package com.httpserver.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SSLConfigurationTest {

    private SSLConfiguration sslConfig;

    @BeforeEach
    void setUp() {
        sslConfig = new SSLConfiguration();
    }

    @Test
    void testDefaultConstructor() {
        assertAll("Testing default constructor",
                () -> assertNull(sslConfig.getKeystorePath(), "Keystore path should be null"),
                () -> assertNull(sslConfig.getKeystorePassword(), "Keystore password should be null"),
                () -> assertNull(sslConfig.getKeyPassword(), "Key password should be null")
        );
    }

    @Test
    void testParameterizedConstructor() {
        SSLConfiguration config = new SSLConfiguration("path/to/keystore", "keystorePass", "keyPass");

        assertAll("Testing parameterized constructor",
                () -> assertEquals("path/to/keystore", config.getKeystorePath(), "Keystore path should match"),
                () -> assertEquals("keystorePass", config.getKeystorePassword(), "Keystore password should match"),
                () -> assertEquals("keyPass", config.getKeyPassword(), "Key password should match")
        );
    }

    @Test
    void testSetKeystorePath() {
        sslConfig.setKeystorePath("new/path/to/keystore");
        assertEquals("new/path/to/keystore", sslConfig.getKeystorePath(), "Keystore path should be updated");
    }

    @Test
    void testSetKeystorePassword() {
        sslConfig.setKeystorePassword("newKeystorePass");
        assertEquals("newKeystorePass", sslConfig.getKeystorePassword(), "Keystore password should be updated");
    }

    @Test
    void testSetKeyPassword() {
        sslConfig.setKeyPassword("newKeyPass");
        assertEquals("newKeyPass", sslConfig.getKeyPassword(), "Key password should be updated");
    }

    @Test
    void testToString() {
        sslConfig.setKeystorePath("path/to/keystore");
        sslConfig.setKeystorePassword("keystorePass");
        sslConfig.setKeyPassword("keyPass");

        String expectedString = "SSLConfiguration{keystorePath='path/to/keystore', keystorePassword=[PROTECTED], keyPassword=[PROTECTED]}";
        assertEquals(expectedString, sslConfig.toString(), "toString should match the expected format");
    }

    @Test
    void testSetNullKeystorePath() {
        sslConfig.setKeystorePath(null);
        assertNull(sslConfig.getKeystorePath(), "Keystore path should be null after setting to null");
    }

    @Test
    void testSetNullKeystorePassword() {
        sslConfig.setKeystorePassword(null);
        assertNull(sslConfig.getKeystorePassword(), "Keystore password should be null after setting to null");
    }

    @Test
    void testSetNullKeyPassword() {
        sslConfig.setKeyPassword(null);
        assertNull(sslConfig.getKeyPassword(), "Key password should be null after setting to null");
    }

    @Test
    void testSetEmptyKeystorePath() {
        sslConfig.setKeystorePath("");
        assertEquals("", sslConfig.getKeystorePath(), "Keystore path should be empty");
    }

    @Test
    void testSetEmptyKeystorePassword() {
        sslConfig.setKeystorePassword("");
        assertEquals("", sslConfig.getKeystorePassword(), "Keystore password should be empty");
    }

    @Test
    void testSetEmptyKeyPassword() {
        sslConfig.setKeyPassword("");
        assertEquals("", sslConfig.getKeyPassword(), "Key password should be empty");
    }
}
