package com.httpserver.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents the configuration settings for the HTTP server.
 * This class holds the server's port number and the web root directory.
 */
public class Configuration {

    private static final Logger logger = LoggerFactory.getLogger(Configuration.class); // SLF4J logger instance

    private int port;
    private String webroot;

    /**
     * Default constructor for creating a Configuration object
     * with default values.
     */
    public Configuration() {
        logger.info("Created a new Configuration object with default values.");
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
        logger.info("Created a new Configuration object with port: {} and webroot: {}", port, webroot);
    }

    /**
     * Returns the port number for the HTTP server.
     *
     * @return the port number
     */
    public int getPort() {
        logger.debug("Retrieved port: {}", port);
        return port;
    }

    /**
     * Sets the port number for the HTTP server.
     *
     * @param port the port number to set
     */
    public void setPort(int port) {
        logger.info("Setting port to: {}", port);
        this.port = port;
    }

    /**
     * Returns the web root directory for serving files.
     *
     * @return the web root directory
     */
    public String getWebroot() {
        logger.debug("Retrieved webroot: {}", webroot);
        return webroot;
    }

    /**
     * Sets the web root directory for serving files.
     *
     * @param webroot the web root directory to set
     */
    public void setWebroot(String webroot) {
        logger.info("Setting webroot to: {}", webroot);
        this.webroot = webroot;
    }
}
