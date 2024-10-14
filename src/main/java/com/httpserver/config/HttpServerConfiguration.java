package com.httpserver.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents the configuration settings for the HTTP and HTTPS server.
 * <p>
 * This class holds the server's port numbers for HTTP and HTTPS, as well
 * as the web root directory for serving static files.
 * </p>
 */
public class HttpServerConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(HttpServerConfiguration.class); // SLF4J logger instance
    
    private int httpPort;
    private int httpsPort;
    private String webroot;

    /**
     * Default constructor for creating a configuration object
     * with default values.
     */
    public HttpServerConfiguration() { 
        logger.info("Created a new HttpServerConfiguration object with default values.");
        logger.trace("Default HttpServerConfiguration constructor invoked.");
    }

    /**
     * Constructs a Configuration object with the specified HTTP and HTTPS
     * port numbers, and the web root directory.
     *
     * @param httpPort   the port number for the HTTP server
     * @param httpsPort  the port number for the HTTPS server
     * @param webroot    the web root directory for serving files
     */
    public HttpServerConfiguration(int httpPort, int httpsPort, String webroot) {
        this.httpPort = httpPort;
        this.httpsPort = httpsPort;
        this.webroot = webroot;
        logger.info("Created HttpServerConfiguration with httpPort: {}, httpsPort: {}, and webroot: {}", httpPort, httpsPort, webroot);
    }

    /**
     * Returns the port number for the HTTP server.
     *
     * @return the HTTP port number
     */
    public int getHttpPort() {
        logger.debug("Retrieved HTTP port: {}", httpPort);
        return httpPort;
    }

    /**
     * Sets the port number for the HTTP server.
     *
     * @param httpPort the HTTP port number to set
     */
    public void setHttpPort(int httpPort) { 
        logger.info("Setting HTTP port to: {}", httpPort);    
        this.httpPort = httpPort;
    }

    /**
     * Returns the port number for the HTTPS server.
     *
     * @return the HTTPS port number
     */
    public int getHttpsPort() {
        logger.debug("Retrieved HTTPS port: {}", httpsPort);
        return httpsPort;
    }

    /**
     * Sets the port number for the HTTPS server.
     *
     * @param httpsPort the HTTPS port number to set
     */
    public void setHttpsPort(int httpsPort) {
        logger.info("Setting HTTPS port to: {}", httpsPort);    
        this.httpsPort = httpsPort;
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

    /**
     * Provides a string representation of the configuration settings.
     *
     * @return a string describing the configuration
     */
    @Override
    public String toString() {
        return "HttpServerConfiguration{" +
                "httpPort=" + httpPort +
                ", httpsPort=" + httpsPort +
                ", webroot='" + webroot + '\'' +
                '}';
    }
}
