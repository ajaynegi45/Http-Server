package com.httpserver.http;

/**
 * Enum representing standard HTTP status codes and their associated messages.
 */
public enum HttpStatusCode {

    /* --- INFORMATIONAL RESPONSES --- */
    INFORMATIONAL_100_CONTINUE(100, "Continue"),
    INFORMATIONAL_101_SWITCHING_PROTOCOLS(101, "Switching Protocols"),
    INFORMATIONAL_102_PROCESSING(102, "Processing"),

    /* --- SUCCESS CODES --- */
    SUCCESS_200_OK(200, "OK"),
    SUCCESS_201_CREATED(201, "Created"),
    SUCCESS_202_ACCEPTED(202, "Accepted"),
    SUCCESS_203_NON_AUTHORITATIVE_INFORMATION(203, "Non-Authoritative Information"),
    SUCCESS_204_NO_CONTENT(204, "No Content"),
    SUCCESS_205_RESET_CONTENT(205, "Reset Content"),
    SUCCESS_206_PARTIAL_CONTENT(206, "Partial Content"),
    SUCCESS_207_MULTI_STATUS(207, "Multi-Status"),
    SUCCESS_208_ALREADY_REPORTED(208, "Already Reported"),

    /* --- REDIRECTION CODES --- */
    REDIRECTION_300_MULTIPLE_CHOICES(300, "Multiple Choices"),
    REDIRECTION_301_MOVED_PERMANENTLY(301, "Moved Permanently"),
    REDIRECTION_302_FOUND(302, "Found"),
    REDIRECTION_303_SEE_OTHER(303, "See Other"),
    REDIRECTION_304_NOT_MODIFIED(304, "Not Modified"),
    REDIRECTION_307_TEMPORARY_REDIRECT(307, "Temporary Redirect"),
    REDIRECTION_308_PERMANENT_REDIRECT(308, "Permanent Redirect"),

    /* --- CLIENT ERRORS --- */
    CLIENT_ERROR_400_BAD_REQUEST(400, "Bad Request"),
    CLIENT_ERROR_401_UNAUTHORIZED(401, "Unauthorized"),
    CLIENT_ERROR_402_PAYMENT_REQUIRED(402, "Payment Required"),
    CLIENT_ERROR_403_FORBIDDEN(403, "Forbidden"),
    CLIENT_ERROR_404_NOT_FOUND(404, "Not Found"),
    CLIENT_ERROR_405_METHOD_NOT_ALLOWED(405, "Method Not Allowed"),
    CLIENT_ERROR_406_NOT_ACCEPTABLE(406, "Not Acceptable"),
    CLIENT_ERROR_407_PROXY_AUTHENTICATION_REQUIRED(407, "Proxy Authentication Required"),
    CLIENT_ERROR_408_REQUEST_TIMEOUT(408, "Request Timeout"),
    CLIENT_ERROR_409_CONFLICT(409, "Conflict"),
    CLIENT_ERROR_410_GONE(410, "Gone"),
    CLIENT_ERROR_411_LENGTH_REQUIRED(411, "Length Required"),
    CLIENT_ERROR_412_PRECONDITION_FAILED(412, "Precondition Failed"),
    CLIENT_ERROR_413_PAYLOAD_TOO_LARGE(413, "Payload Too Large"),
    CLIENT_ERROR_414_URI_TOO_LONG(414, "URI Too Long"),
    CLIENT_ERROR_415_UNSUPPORTED_MEDIA_TYPE(415, "Unsupported Media Type"),
    CLIENT_ERROR_416_RANGE_NOT_SATISFIABLE(416, "Range Not Satisfiable"),
    CLIENT_ERROR_417_EXPECTATION_FAILED(417, "Expectation Failed"),
    CLIENT_ERROR_418_IM_A_TEAPOT(418, "I'm a teapot"),
    CLIENT_ERROR_421_MISDIRECTED_REQUEST(421, "Misdirected Request"),
    CLIENT_ERROR_422_UNPROCESSABLE_ENTITY(422, "Unprocessable Entity"),
    CLIENT_ERROR_424_FAILED_DEPENDENCY(424, "Failed Dependency"),
    CLIENT_ERROR_425_TOO_EARLY(425, "Too Early"),
    CLIENT_ERROR_426_UPGRADE_REQUIRED(426, "Upgrade Required"),
    CLIENT_ERROR_428_PRECONDITION_REQUIRED(428, "Precondition Required"),
    CLIENT_ERROR_429_TOO_MANY_REQUESTS(429, "Too Many Requests"),
    CLIENT_ERROR_431_REQUEST_HEADER_FIELDS_TOO_LARGE(431, "Request Header Fields Too Large"),
    CLIENT_ERROR_451_UNAVAILABLE_FOR_LEGAL_REASONS(451, "Unavailable For Legal Reasons"),

    /* --- SERVER ERRORS --- */
    SERVER_ERROR_500_INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
    SERVER_ERROR_501_NOT_IMPLEMENTED(501, "Not Implemented"),
    SERVER_ERROR_502_BAD_GATEWAY(502, "Bad Gateway"),
    SERVER_ERROR_503_SERVICE_UNAVAILABLE(503, "Service Unavailable"),
    SERVER_ERROR_504_GATEWAY_TIMEOUT(504, "Gateway Timeout"),
    SERVER_ERROR_505_HTTP_VERSION_NOT_SUPPORTED(505, "HTTP Version Not Supported"),
    SERVER_ERROR_506_VARIANT_ALSO_NEGOTIATES(506, "Variant Also Negotiates"),
    SERVER_ERROR_507_INSUFFICIENT_STORAGE(507, "Insufficient Storage"),
    SERVER_ERROR_508_LOOP_DETECTED(508, "Loop Detected"),
    SERVER_ERROR_510_NOT_EXTENDED(510, "Not Extended"),
    SERVER_ERROR_511_NETWORK_AUTHENTICATION_REQUIRED(511, "Network Authentication Required");

    /**
     * The numeric status code.
     */
    public final int STATUS_CODE;

    /**
     * The message associated with the status code.
     */
    public final String MESSAGE;

    /**
     * Constructor to create an instance of HttpStatusCode with a status code and message.
     *
     * @param STATUS_CODE the numeric status code.
     * @param MESSAGE     the message associated with the status code.
     */
    HttpStatusCode(int STATUS_CODE, String MESSAGE) {
        this.STATUS_CODE = STATUS_CODE;
        this.MESSAGE = MESSAGE;
    }
}
