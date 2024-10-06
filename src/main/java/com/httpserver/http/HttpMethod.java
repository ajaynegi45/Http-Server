package com.httpserver.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Enum representing standard HTTP methods used in requests.
 */
public enum HttpMethod {
    /** The GET method requests data from a specified resource. */
    GET,

    /** The HEAD method requests the headers of a specified resource without the body. */
    HEAD,

    /** The POST method submits data to be processed to a specified resource. */
    POST,

    /** The PUT method updates a current resource with new data. */
    PUT,

    /** The DELETE method deletes a specified resource. */
    DELETE,

    /** The CONNECT method establishes a tunnel to a server identified by a given URI. */
    CONNECT,

    /** The OPTIONS method describes the communication options for the target resource. */
    OPTIONS,

    /** The TRACE method performs a message loop-back test along the path to the target resource. */
    TRACE,

    /** The PATCH method applies partial modifications to a resource. */
    PATCH;

    private static final Logger logger = LoggerFactory.getLogger(HttpMethod.class);

    /** The maximum length of the HTTP method names. */
    public static final int MAX_LENGTH;

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
}
