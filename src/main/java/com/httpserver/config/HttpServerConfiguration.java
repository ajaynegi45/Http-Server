package com.httpserver.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents the configuration settings for the HTTP and HTTPS server. This
 * class holds the server's port numbers for HTTP and HTTPS, and the web root
 * directory for serving files.
 */
public class HttpServerConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(HttpServerConfiguration.class); // SLF4J logger
    // instance

    private int httpPort;
    private int httpsPort;
    private String webroot;

    /**
     * Default constructor for creating a Configuration object with default values.
     */
    public HttpServerConfiguration() {
        logger.info("Created a new HttpServerConfiguration object with default values.");
        logger.trace("Default HttpServerConfiguration constructor invoked.");
    }

    /**
     * Constructs a Configuration object with the specified HTTP and HTTPS port
     * numbers, and the web root directory.
     *
     * @param httpPort  the port number for the HTTP server
     * @param httpsPort the port number for the HTTPS server
     * @param webroot   the web root directory for serving files
     */
    public HttpServerConfiguration(int httpPort, int httpsPort, String webroot) {
        this.httpPort = httpPort;
        this.httpsPort = httpsPort;
        this.webroot = webroot;
        logger.info("Created a new HttpServerConfiguration object with http port: {}, https port {} and webroot: {}",
                httpPort, httpsPort, webroot);
        logger.trace("HttpServerConfiguration object initialized with http port: {}, https port {} and webroot {}",
                httpPort, httpsPort, webroot);
    }

    /**
     * Returns the port number for the HTTP server.
     *
     * @return the HTTP port number
     */
    public int getHttpPort() {
        logger.debug("Retrieved http port: {}", httpPort);
        logger.trace("getHttpPort() called, returning: {}", httpPort);
        return httpPort;
    }

    /**
     * Sets the port number for the HTTP server.
     *
     * @param httpPort the HTTP port number to set
     * @throws IllegalArgumentException if the provided port number is negative
     */
    public void setHttpPort(int httpPort) {
        if (httpPort < 0) {
            logger.error("Attempted to set a negative HTTP port: {}", httpPort);
            throw new IllegalArgumentException("HTTP port number cannot be negative: " + httpPort);
        }
        logger.info("Setting http port to: {}", httpPort);
        this.httpPort = httpPort;
        logger.trace("Http port set to: {}", httpPort);
    }

    /**
     * Returns the port number for the HTTPS server.
     *
     * @return the HTTPS port number
     */
    public int getHttpsPort() {
        logger.debug("Retrieved https port: {}", httpsPort);
        logger.trace("getHttpsPort() called, returning: {}", httpsPort);
        return httpsPort;
    }

    /**
     * Sets the port number for the HTTPS server.
     *
     * @param httpsPort the HTTPS port number to set
     * @throws IllegalArgumentException if the provided port number is negative
     */
    public void setHttpsPort(int httpsPort) {
        if (httpsPort < 0) {
            logger.error("Attempted to set a negative HTTPS port: {}", httpsPort);
            throw new IllegalArgumentException("HTTPS port number cannot be negative: " + httpsPort);
        }
        logger.info("Setting https port to: {}", httpsPort);
        this.httpsPort = httpsPort;
        logger.trace("Https port set to: {}", httpsPort);
    }

    /**
     * Returns the web root directory for serving files.
     *
     * @return the web root directory
     */
    public String getWebroot() {
        logger.debug("Retrieved webroot: {}", webroot);
        logger.trace("getWebroot() called, returning: {}", webroot);
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
        logger.trace("Webroot set to: {}", webroot);
    }

    @Override
    public String toString() {
        return "HttpServerConfiguration{" + "httpPort=" + httpPort + ", httpsPort=" + httpsPort + ", webroot='"
                + webroot + '\'' + '}';
    }
}
