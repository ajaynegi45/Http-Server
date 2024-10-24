package com.httpserver.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Enum representing standard HTTP methods used in requests.
 */
public enum HttpMethod {
    /**
     * The GET method requests data from a specified resource.
     */
    GET,

    /**
     * The HEAD method requests the headers of a specified resource without the body.
     */
    HEAD,

    /**
     * The POST method submits data to be processed to a specified resource.
     */
    POST,

    /**
     * The PUT method updates a current resource with new data.
     */
    PUT,

    /**
     * The DELETE method deletes a specified resource.
     */
    DELETE,

    /**
     * The CONNECT method establishes a tunnel to a server identified by a given URI.
     */
    CONNECT,

    /**
     * The OPTIONS method describes the communication options for the target resource.
     */
    OPTIONS,

    /**
     * The TRACE method performs a message loop-back test along the path to the target resource.
     */
    TRACE,

    /**
     * The PATCH method applies partial modifications to a resource.
     */
    PATCH;

    /**
     * The maximum length of the HTTP method names.
     */
    public static final int MAX_LENGTH;
    private static final Logger logger = LoggerFactory.getLogger(HttpMethod.class);

    static {
        logger.debug("Initializing HttpMethod enum and calculating MAX_LENGTH.");
        int tempMaxLength = -1;
        for (HttpMethod method : values()) {
            int length = method.name().length();
            logger.debug("Method: {}, Length: {}", method, length);
            if (length > tempMaxLength) {
                tempMaxLength = length;
            }
        }
        MAX_LENGTH = tempMaxLength;
        logger.info("HttpMethod enum initialized. MAX_LENGTH set to {}", MAX_LENGTH);
    }

    /**
     * Gets the default error information for the specified HTTP method.
     *
     * @return a string containing the default error message.
     */
    public String getDefaultErrorInfo() {
        switch (this) {
            case GET:
                return "Error occurred while fetching data with GET method.";
            case HEAD:
                return "Error occurred while fetching headers with HEAD method.";
            case POST:
                return "Error occurred while submitting data with POST method.";
            case PUT:
                return "Error occurred while updating data with PUT method.";
            case DELETE:
                return "Error occurred while deleting data with DELETE method.";
            case CONNECT:
                return "Error occurred while establishing a connection with CONNECT method.";
            case OPTIONS:
                return "Error occurred while retrieving options with OPTIONS method.";
            case TRACE:
                return "Error occurred while tracing the request with TRACE method.";
            case PATCH:
                return "Error occurred while partially modifying data with PATCH method.";
            default:
                return "Unknown HTTP method error.";
        }
    }
}
