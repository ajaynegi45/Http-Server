package com.httpserver.http;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Represents an HTTP response, containing status code, headers, and body.
 */
public class HttpResponse {

    private final Map<String, String> headers;
    private HttpStatusCode statusCode;
    private HttpVersion httpVersion;
    private String body;

    /**
     * Constructs an empty HttpResponse with default settings.
     * Initializes headers using a LinkedHashMap to preserve insertion order.
     */
    public HttpResponse() {
        headers = new LinkedHashMap<>();
    }

    /**
     * Retrieves the HTTP status code of the response.
     *
     * @return The status code of the response.
     */
    public HttpStatusCode getStatusCode() {
        return statusCode;
    }

    /**
     * Sets the HTTP status code for the response.
     *
     * @param statusCode The status code to be set.
     */
    public void setStatusCode(HttpStatusCode statusCode) {
        this.statusCode = statusCode;
    }

    /**
     * Retrieves the HTTP version of the response.
     *
     * @return The HTTP version of the response.
     */
    public HttpVersion getHttpVersion() {
        return httpVersion;
    }

    /**
     * Sets the HTTP version for the response.
     *
     * @param httpVersion The HTTP version to be set.
     */
    public void setHttpVersion(HttpVersion httpVersion) {
        this.httpVersion = httpVersion;
    }

    /**
     * Retrieves the body content of the response.
     *
     * @return The body content as a string.
     */
    public String getBody() {
        return body;
    }

    /**
     * Sets the body content of the response.
     *
     * @param body The body content to be set.
     */
    public void setBody(String body) {
        this.body = body;
    }

    /**
     * Adds a header to the HTTP response.
     *
     * @param key   The header name.
     * @param value The header value.
     */
    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    /**
     * Builds the HTTP response by constructing the status line, headers, and body.
     *
     * @return The complete HTTP response as a formatted string.
     */
    public String buildResponse() {

        // CRLF = Carriage Return (\r) and Line Feed (\n)
        String CRLF = "\r\n";

        StringBuilder response = new StringBuilder();

        // Status line
        response.append(httpVersion.LITERAL).append(" ")
                .append(statusCode.STATUS_CODE).append(" ")
                .append(statusCode.MESSAGE).append(CRLF);

        // Headers
        for (Map.Entry<String, String> header : headers.entrySet()) {
            response.append(header.getKey()).append(": ").append(header.getValue()).append(CRLF);
        }

        // Empty line to indicate the end of headers
        response.append(CRLF);

        // Body (if present)
        if (body != null && !body.isEmpty()) {
            response.append(body).append(CRLF);
        }

        return response.toString();
    }
}
