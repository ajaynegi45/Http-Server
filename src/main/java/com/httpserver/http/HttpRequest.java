package com.httpserver.http;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents an HTTP request message, including the method, target, HTTP version,
 * headers, and body.
 */
public class HttpRequest extends HttpMessage {

    private HttpMethod method;
    private String requestTarget;
    private String originalHttpVersion; // literal from the request
    private HttpVersion bestCompatibleHttpVersion;
    private final Map<String, String> headers = new HashMap<>(); // To store headers
    private String body; // To store the body of the request

    /**
     * Default constructor for HttpRequest.
     */
    HttpRequest() {
    }

    /**
     * Gets the HTTP method of the request.
     *
     * @return the HTTP method.
     */
    public HttpMethod getMethod() {
        return method;
    }

    /**
     * Gets the request target.
     *
     * @return the request target.
     */
    public String getRequestTarget() {
        return requestTarget;
    }

    /**
     * Gets the best compatible HTTP version determined for the request.
     *
     * @return the best compatible HTTP version.
     */
    public HttpVersion getBestCompatibleHttpVersion() {
        return bestCompatibleHttpVersion;
    }

    /**
     * Gets the original HTTP version from the request.
     *
     * @return the original HTTP version.
     */
    public String getOriginalHttpVersion() {
        return originalHttpVersion;
    }

    /**
     * Gets all headers included in the request.
     *
     * @return a map of headers, where the key is the header name and the value is the header value.
     */
    public Map<String, String> getHeaders() {
        return headers;
    }

    /**
     * Gets the body of the request.
     *
     * @return the body of the request as a string.
     */
    public String getBody() {
        return body;
    }

    /**
     * Sets the HTTP method for the request.
     *
     * @param method the HTTP method to set.
     */
    void setMethod(HttpMethod method) {
        this.method = method;
    }

    /**
     * Sets the request target.
     *
     * @param requestTarget the target to set.
     * @throws HttpParsingException if the request target is null or empty.
     */
    void setRequestTarget(String requestTarget) throws HttpParsingException {
        if (requestTarget == null || requestTarget.isEmpty()) {
            throw new HttpParsingException(HttpStatusCode.SERVER_ERROR_500_INTERNAL_SERVER_ERROR);
        }
        this.requestTarget = requestTarget;
    }

    /**
     * Sets the original HTTP version for the request and determines the best compatible version.
     *
     * @param originalHttpVersion the original HTTP version to set.
     * @throws BadHttpVersionException if the provided HTTP version is invalid.
     * @throws HttpParsingException if the HTTP version is not supported.
     */
    void setHttpVersion(String originalHttpVersion) throws BadHttpVersionException, HttpParsingException {
        this.originalHttpVersion = originalHttpVersion;
        this.bestCompatibleHttpVersion = HttpVersion.getBestCompatibleVersion(originalHttpVersion);
        if (this.bestCompatibleHttpVersion == null) {
            throw new HttpParsingException(HttpStatusCode.SERVER_ERROR_505_HTTP_VERSION_NOT_SUPPORTED);
        }
    }

    /**
     * Adds a header to the request.
     *
     * @param name the name of the header.
     * @param value the value of the header.
     * @throws IllegalArgumentException if the header name or value is null or empty.
     */
    public void addHeader(String name, String value) {
        if (name == null || name.isEmpty() || value == null) {
            throw new IllegalArgumentException("Header name and value must not be null or empty");
        }
        headers.put(name, value);
    }

    /**
     * Sets the body of the HTTP request.
     *
     * @param body the body to set.
     */
    public void setBody(String body) {
        this.body = body;
    }

    /**
     * Returns a string representation of the HttpRequest.
     *
     * @return a string containing the details of the HTTP request.
     */
    @Override
    public String toString() {
        return "HttpRequest{" +
                "method=" + method +
                ", requestTarget='" + requestTarget + '\'' +
                ", originalHttpVersion='" + originalHttpVersion + '\'' +
                ", bestCompatibleHttpVersion=" + bestCompatibleHttpVersion +
                ", headers=" + headers +
                ", body='" + body + '\'' +
                '}';
    }
}
