package com.httpserver.http;

public class HttpParsingException extends Exception {

    private final HttpStatusCode statusCode;

    /**
     * Constructs a new HttpParsingException with the specified HttpStatusCode.
     *
     * @param statusCode the HTTP status code associated with the exception
     */
    public HttpParsingException(HttpStatusCode statusCode) {
        super(statusCode.MESSAGE);
        this.statusCode = statusCode;
    }

    /**
     * Returns the HTTP status code associated with this exception.
     *
     * @return the HttpStatusCode
     */
    public HttpStatusCode getStatusCode() {
        return statusCode;
    }
}
