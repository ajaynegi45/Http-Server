package com.httpserver.exception;

import com.httpserver.http.HttpStatusCode;

public class ExceptionHandler {

    public static String handleException(Exception e) {
        if (e instanceof HttpParsingException ex) {
            return formatErrorResponse(ex.getStatusCode());
        } else if (e instanceof BadHttpVersionException) {
            return formatErrorResponse(HttpStatusCode.SERVER_ERROR_505_HTTP_VERSION_NOT_SUPPORTED);
        } else {
            // Generic handler for other exceptions
            return formatErrorResponse(HttpStatusCode.SERVER_ERROR_500_INTERNAL_SERVER_ERROR);
        }
    }

    private static String formatErrorResponse(HttpStatusCode statusCode) {
        return String.format("HTTP/1.1 %d %s", statusCode.STATUS_CODE, statusCode.MESSAGE);
    }
}

