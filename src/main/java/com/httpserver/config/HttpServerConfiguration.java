package com.httpserver.config;

/**
 * Represents the configuration settings for the HTTP and HTTPS server.
 * This class holds the server's port numbers for HTTP and HTTPS, and 
 * the web root directory for serving files.
 */
public class HttpServerConfiguration {

    private int httpPort;
    private int httpsPort;
    private String webroot;

    /**
     * Default constructor for creating a Configuration object
     * with default values.
     */
    public HttpServerConfiguration() {
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
    }

    /**
     * Returns the port number for the HTTP server.
     *
     * @return the HTTP port number
     */
    public int getHttpPort() {
        return httpPort;
    }

    /**
     * Sets the port number for the HTTP server.
     *
     * @param httpPort the HTTP port number to set
     */
    public void setHttpPort(int httpPort) {
        this.httpPort = httpPort;
    }

    /**
     * Returns the port number for the HTTPS server.
     *
     * @return the HTTPS port number
     */
    public int getHttpsPort() {
        return httpsPort;
    }

    /**
     * Sets the port number for the HTTPS server.
     *
     * @param httpsPort the HTTPS port number to set
     */
    public void setHttpsPort(int httpsPort) {
        this.httpsPort = httpsPort;
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
