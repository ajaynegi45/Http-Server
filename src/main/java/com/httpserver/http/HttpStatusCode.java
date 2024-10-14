package com.httpserver.http;

/**
 * Enum representing standard HTTP status codes and their associated messages.
 * Each status code corresponds to a specific condition in HTTP responses.
 */
public enum HttpStatusCode {

    /* --- SUCCESS CODES --- */
    SUCCESS_200_OK(200, "OK"), // Indicates that the request has succeeded
    SUCCESS_201_CREATED(201, "Created"), // Indicates that a resource has been successfully created
    SUCCESS_204_NO_CONTENT(204, "No Content"), // Indicates that the server successfully processed the request, but is not returning any content

    /* --- CLIENT ERRORS --- */
    CLIENT_ERROR_400_BAD_REQUEST(400, "Bad Request"), // Indicates that the server could not understand the request due to invalid syntax
    CLIENT_ERROR_401_UNAUTHORIZED(401, "Unauthorized"), // Indicates that the request requires user authentication
    CLIENT_ERROR_403_FORBIDDEN(403, "Forbidden"), // Indicates that the server understood the request, but refuses to authorize it
    CLIENT_ERROR_404_NOT_FOUND(404, "Not Found"), // Indicates that the server can't find the requested resource
    CLIENT_ERROR_405_METHOD_NOT_ALLOWED(405, "Method Not Allowed"), // Indicates that the request method is not allowed for the specified resource
    CLIENT_ERROR_408_REQUEST_TIMEOUT(408, "Request Timeout"), // Indicates that the server timed out waiting for the request
    CLIENT_ERROR_413_PAYLOAD_TOO_LARGE(413, "Payload Too Large"), // Indicates that the request is larger than the server is willing or able to process
    CLIENT_ERROR_415_UNSUPPORTED_MEDIA_TYPE(415, "Unsupported Media Type"), // Indicates that the request entity has a media type that the server or resource does not support
    CLIENT_ERROR_429_TOO_MANY_REQUESTS(429, "Too Many Requests"), // Indicates that the user has sent too many requests in a given amount of time

    /* --- SERVER ERRORS --- */
    SERVER_ERROR_500_INTERNAL_SERVER_ERROR(500, "Internal Server Error"), // Indicates that the server encountered an unexpected condition
    SERVER_ERROR_501_NOT_IMPLEMENTED(501, "Not Implemented"), // Indicates that the server does not support the functionality required to fulfill the request
    SERVER_ERROR_502_BAD_GATEWAY(502, "Bad Gateway"), // Indicates that the server received an invalid response from an upstream server while trying to fulfill the request
    SERVER_ERROR_503_SERVICE_UNAVAILABLE(503, "Service Unavailable"), // Indicates that the server is currently unable to handle the request due to temporary overloading or maintenance of the server
    SERVER_ERROR_504_GATEWAY_TIMEOUT(504, "Gateway Timeout"), // Indicates that the server, while acting as a gateway, did not receive a timely response from the upstream server
    SERVER_ERROR_505_HTTP_VERSION_NOT_SUPPORTED(505, "HTTP Version Not Supported"); // Indicates that the server does not support the HTTP protocol version that was used in the request

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
        this.STATUS_CODE = STATUS_CODE; // Initialize the numeric status code
        this.MESSAGE = MESSAGE; // Initialize the associated message
    }
}
