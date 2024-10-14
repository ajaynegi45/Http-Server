package com.httpserver.http;

/**
 * Enum representing standard HTTP status codes and their associated messages.
 */
public enum HttpStatusCode {

    /* --- SUCCESS CODES --- */
    SUCCESS_200_OK(200, "OK"),
    SUCCESS_201_CREATED(201, "Created"),
    SUCCESS_204_NO_CONTENT(204, "No Content"),

    /* --- CLIENT ERRORS --- */
    CLIENT_ERROR_400_BAD_REQUEST(400, "Bad Request"),
    CLIENT_ERROR_401_UNAUTHORIZED(401, "Unauthorized"),
    CLIENT_ERROR_403_FORBIDDEN(403, "Forbidden"),
    CLIENT_ERROR_404_NOT_FOUND(404, "Not Found"),
    CLIENT_ERROR_405_METHOD_NOT_ALLOWED(405, "Method Not Allowed"),
    CLIENT_ERROR_408_REQUEST_TIMEOUT(408, "Request Timeout"),
    CLIENT_ERROR_413_PAYLOAD_TOO_LARGE(413, "Payload Too Large"),
    CLIENT_ERROR_415_UNSUPPORTED_MEDIA_TYPE(415, "Unsupported Media Type"),
    CLIENT_ERROR_429_TOO_MANY_REQUESTS(429, "Too Many Requests"),

    /* --- SERVER ERRORS --- */
    SERVER_ERROR_500_INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
    SERVER_ERROR_501_NOT_IMPLEMENTED(501, "Not Implemented"),
    SERVER_ERROR_502_BAD_GATEWAY(502, "Bad Gateway"),
    SERVER_ERROR_503_SERVICE_UNAVAILABLE(503, "Service Unavailable"),
    SERVER_ERROR_504_GATEWAY_TIMEOUT(504, "Gateway Timeout"),
    SERVER_ERROR_505_HTTP_VERSION_NOT_SUPPORTED(505, "HTTP Version Not Supported"),
	
	/* --- REDIRECTION CODES --- */
	REDIRECTION_301_MOVED_PERMANENTLY(301, "Moved Permanently");

    /** The numeric status code. */
    public final int STATUS_CODE;

    /** The message associated with the status code. */
    public final String MESSAGE;

    /**
     * Constructor to create an instance of HttpStatusCode with a status code and message.
     *
     * @param STATUS_CODE the numeric status code.
     * @param MESSAGE the message associated with the status code.
     */
    HttpStatusCode(int STATUS_CODE, String MESSAGE) {
        this.STATUS_CODE = STATUS_CODE;
        this.MESSAGE = MESSAGE;
    }
}
