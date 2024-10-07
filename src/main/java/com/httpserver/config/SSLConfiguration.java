package com.httpserver.config;

/**
 * Represents the configuration settings for the SSL (Secure Socket Layer) connection
 * in the HTTP server. This class holds the path to the keystore and the passwords 
 * required for SSL certificate management.
 */
public class SSLConfiguration {
    private String keystorePath;
    private String keystorePassword;
    private String keyPassword;

    /**
     * Default constructor for creating an SSLConfiguration object
     * with default values.
     */
    public SSLConfiguration() {
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
        super();
        this.keystorePath = keystorePath;
        this.keystorePassword = keystorePassword;
        this.keyPassword = keyPassword;
    }

    /**
     * Returns the file path to the keystore.
     *
     * @return the keystore path
     */
    public String getKeystorePath() {
        return keystorePath;
    }

    /**
     * Sets the file path to the keystore.
     *
     * @param keystorePath the keystore path to set
     */
    public void setKeystorePath(String keystorePath) {
        this.keystorePath = keystorePath;
    }

    /**
     * Returns the password for accessing the keystore.
     *
     * @return the keystore password
     */
    public String getKeystorePassword() {
        return keystorePassword;
    }

    /**
     * Sets the password for accessing the keystore.
     *
     * @param keystorePassword the keystore password to set
     */
    public void setKeystorePassword(String keystorePassword) {
        this.keystorePassword = keystorePassword;
    }

    /**
     * Returns the password for the key entry in the keystore.
     *
     * @return the key password
     */
    public String getKeyPassword() {
        return keyPassword;
    }

    /**
     * Sets the password for the key entry in the keystore.
     *
     * @param keyPassword the key password to set
     */
    public void setKeyPassword(String keyPassword) {
        this.keyPassword = keyPassword;
    }
}
