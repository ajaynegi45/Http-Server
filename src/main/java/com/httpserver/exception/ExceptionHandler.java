package com.httpserver.exception;

import com.httpserver.exception.BadHttpVersionException;
import com.httpserver.exception.HttpParsingException;
import com.httpserver.http.HttpStatusCode;

/**
 * ExceptionHandler is responsible for handling exceptions that occur within the HTTP server.
 * It provides methods to generate appropriate HTTP error responses based on the type of exception.
 */
public class ExceptionHandler {

    /**
     * Handles the provided exception and returns a formatted error response as a string.
     * This method checks the type of the exception and generates the appropriate HTTP error response.
     *
     * @param e the exception that occurred during the processing of an HTTP request.
     * @return a formatted error response string that can be sent to the client.
     */
    public static String handleException(Exception e) {
        // Check if the exception is an instance of HttpParsingException
        if (e instanceof HttpParsingException) {
            HttpParsingException ex = (HttpParsingException) e; // Cast to specific exception type
            return formatErrorResponse(ex.getStatusCode()); // Generate error response with specific status code
        } 
        // Check if the exception is an instance of BadHttpVersionException
        else if (e instanceof BadHttpVersionException) {
            return formatErrorResponse(HttpStatusCode.SERVER_ERROR_505_HTTP_VERSION_NOT_SUPPORTED); // Handle HTTP version errors
        } 
        // Handle any other exceptions generically
        else {
            return formatErrorResponse(HttpStatusCode.SERVER_ERROR_500_INTERNAL_SERVER_ERROR); // Return a generic internal server error response
        }
    }

    /**
     * Formats an HTTP error response based on the provided status code.
     *
     * @param statusCode the HTTP status code to include in the response.
     * @return a formatted error response string that includes the HTTP version, status code, and message.
     */
    private static String formatErrorResponse(HttpStatusCode statusCode) {
        // Format the error response according to the HTTP/1.1 specification
        return String.format("HTTP/1.1 %d %s", statusCode.STATUS_CODE, statusCode.MESSAGE);
    }
}
