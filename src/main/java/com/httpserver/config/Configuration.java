package com.httpserver.config;

/**
 * Represents the configuration settings for the HTTP server.
 * This class holds the server's port number and the web root directory.
 */
public class Configuration {

    private int port;
    private String webroot;

    /**
     * Default constructor for creating a Configuration object
     * with default values.
     */
    public Configuration() {
    }

    /**
     * Constructs a Configuration object with the specified port
     * number and web root directory.
     *
     * @param port     the port number for the HTTP server
     * @param webroot  the web root directory for serving files
     */
    public Configuration(int port, String webroot) {
        this.port = port;
        this.webroot = webroot;
    }

    /**
     * Returns the port number for the HTTP server.
     *
     * @return the port number
     */
    public int getPort() {
        return port;
    }

    /**
     * Sets the port number for the HTTP server.
     *
     * @param port the port number to set
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * Returns the web root directory for serving files.
     *
     * @return the web root directory
     */
    public String getWebroot() {
        return webroot;
    }

    /**
     * Sets the web root directory for serving files.
     *
     * @param webroot the web root directory to set
     */
    public void setWebroot(String webroot) {
        this.webroot = webroot;
    }
}
