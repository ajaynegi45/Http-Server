package com.httpserver.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents the configuration settings for the SSL (Secure Socket Layer) connection
 * in the HTTP server. This class holds the path to the keystore and the passwords 
 * required for SSL certificate management.
 */
public class SSLConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(SSLConfiguration.class); // SLF4J logger instance

    private String keystorePath;
    private String keystorePassword;
    private String keyPassword;

    /**
     * Default constructor for creating an SSLConfiguration object
     * with default values.
     */
    public SSLConfiguration() {
        logger.info("Created a new SSLConfiguration object with default values.");
        logger.trace("Default SSLConfiguration constructor invoked.");
    }

    /**
     * Constructs an SSLConfiguration object with the specified keystore path,
     * keystore password, and key password.
     *
     * @param keystorePath     the file path to the keystore containing the SSL certificate
     * @param keystorePassword  the password to access the keystore
     * @param keyPassword      the password for the key entry in the keystore
     */
    public SSLConfiguration(String keystorePath, String keystorePassword, String keyPassword) {
        this.keystorePath = keystorePath;
        this.keystorePassword = keystorePassword;
        this.keyPassword = keyPassword;
        logger.info("Created a new SSLConfiguration object with keystore path: {}, keystore password: [PROTECTED], and key password: [PROTECTED]", keystorePath);
        logger.trace("SSLConfiguration initialized with keystore path: {}, keystore password: [PROTECTED], and key password: [PROTECTED]", keystorePath);
    }

    /**
     * Returns the file path to the keystore.
     *
     * @return the keystore path
     */
    public String getKeystorePath() {
        logger.debug("Retrieved keystore path: {}", keystorePath);
        return keystorePath;
    }

    /**
     * Sets the file path to the keystore.
     *
     * @param keystorePath the keystore path to set
     */
    public void setKeystorePath(String keystorePath) {
        logger.info("Setting keystore path to: {}", keystorePath);
        this.keystorePath = keystorePath;
    }

    /**
     * Returns the password for accessing the keystore.
     *
     * @return the keystore password
     */
    public String getKeystorePassword() {
        logger.debug("Retrieved keystore password: [PROTECTED]");
        return keystorePassword;
    }

    /**
     * Sets the password for accessing the keystore.
     *
     * @param keystorePassword the keystore password to set
     */
    public void setKeystorePassword(String keystorePassword) {
        logger.info("Setting keystore password.");
        this.keystorePassword = keystorePassword;
    }

    /**
     * Returns the password for the key entry in the keystore.
     *
     * @return the key password
     */
    public String getKeyPassword() {
        logger.debug("Retrieved key password: [PROTECTED]");
        return keyPassword;
    }

    /**
     * Sets the password for the key entry in the keystore.
     *
     * @param keyPassword the key password to set
     */
    public void setKeyPassword(String keyPassword) {
        logger.info("Setting key password.");
        this.keyPassword = keyPassword;
    }

    /**
     * Provides a string representation of the SSL configuration settings.
     *
     * @return a string describing the SSL configuration
     */
    @Override
    public String toString() {
        return "SSLConfiguration{" +
                "keystorePath='" + keystorePath + '\'' +
                ", keystorePassword=[PROTECTED]" +
                ", keyPassword=[PROTECTED]" +
                '}';
    }
}
