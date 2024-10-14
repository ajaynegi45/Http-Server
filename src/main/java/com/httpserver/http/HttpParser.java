package com.httpserver.http;

import com.httpserver.exception.HttpParsingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * Parses HTTP requests from an InputStream.
 * This class handles the parsing of HTTP request lines, headers, and bodies.
 * It ensures that incoming requests adhere to HTTP standards and formats,
 * throwing exceptions when invalid data is encountered.
 */
public class HttpParser {

    private final static Logger LOGGER = LoggerFactory.getLogger(HttpParser.class);

    private static final int SP = 0x20; // Space character (ASCII)
    private static final int CR = 0x0D; // Carriage return (ASCII)
    private static final int LF = 0x0A; // Line feed (ASCII)

    /**
     * Parses an HTTP request from the provided InputStream.
     *
     * @param inputStream the InputStream containing the HTTP request data
     * @return the parsed HttpRequest object
     * @throws HttpParsingException if there is an error during parsing
     */
    public HttpRequest parseHttpRequest(InputStream inputStream) throws HttpParsingException {
        // Create a reader to read the input stream using ASCII encoding
        InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.US_ASCII);
        HttpRequest httpRequest = new HttpRequest(); // Create a new HttpRequest object

        try {
            parseRequestLine(reader, httpRequest); // Parse the request line (method, target, and version)
            parseHeaders(reader, httpRequest);      // Parse headers into the HttpRequest object
            parseBody(reader, httpRequest);         // Parse the body of the request if present
        } catch (IOException e) {
            LOGGER.error("I/O error during parsing: {}", e.getMessage());
            // Throw an HttpParsingException if an I/O error occurs
            throw new HttpParsingException(HttpStatusCode.SERVER_ERROR_500_INTERNAL_SERVER_ERROR);
        }

        return httpRequest; // Return the fully parsed HttpRequest object
    }

    /**
     * Parses the request line of the HTTP request.
     *
     * @param reader  the InputStreamReader to read the request line
     * @param request the HttpRequest object to populate with method and target
     * @throws IOException           if an I/O error occurs
     * @throws HttpParsingException  if the request line is invalid
     */
    private void parseRequestLine(InputStreamReader reader, HttpRequest request) throws IOException, HttpParsingException {
        StringBuilder processingDataBuffer = new StringBuilder(); // Buffer to hold parts of the request line
        boolean methodParsed = false; // Flag to indicate if the method has been parsed
        boolean requestTargetParsed = false; // Flag to indicate if the request target has been parsed

        int _byte;
        while ((_byte = reader.read()) >= 0) { // Read bytes until the end of stream
            if (_byte == CR) { // Check for Carriage Return
                _byte = reader.read(); // Read the next byte
                if (_byte == LF) { // Check for Line Feed
                    if (!methodParsed || !requestTargetParsed) {
                        LOGGER.error("Method or Request Target not parsed properly. MethodParsed: {}, RequestTargetParsed: {}", methodParsed, requestTargetParsed);
                        throw new HttpParsingException(HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
                    }
                    LOGGER.debug("Request line parsed successfully.");
                    return; // Successfully parsed the request line
                } else {
                    LOGGER.error("CRLF expected but got: {}", _byte);
                    throw new HttpParsingException(HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
                }
            }

            if (_byte == SP) { // Check for Space character
                if (!methodParsed) { // Parsing the HTTP method
                    LOGGER.debug("Request Line METHOD to Process: {}", processingDataBuffer.toString());
                    try {
                        request.setMethod(HttpMethod.valueOf(processingDataBuffer.toString())); // Set HTTP method
                        LOGGER.info("Parsed HTTP Method: {}", request.getMethod());
                    } catch (IllegalArgumentException e) {
                        LOGGER.error("Unsupported HTTP Method: {}", processingDataBuffer.toString());
                        throw new HttpParsingException(HttpStatusCode.SERVER_ERROR_501_NOT_IMPLEMENTED);
                    }
                    methodParsed = true; // Mark the method as parsed
                } else if (!requestTargetParsed) { // Parsing the Request Target
                    if (processingDataBuffer.length() == 0) {
                        LOGGER.error("Empty Request Target found.");
                        throw new HttpParsingException(HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
                    }
                    LOGGER.debug("Request Line REQ TARGET to Process: {}", processingDataBuffer.toString());
                    request.setRequestTarget(processingDataBuffer.toString()); // Set the request target
                    requestTargetParsed = true; // Mark the request target as parsed
                } else {
                    LOGGER.error("Extra space detected in request line.");
                    throw new HttpParsingException(HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
                }
                processingDataBuffer.setLength(0); // Clear the buffer for the next part
            } else {
                processingDataBuffer.append((char) _byte); // Append character to the buffer
                if (!methodParsed && processingDataBuffer.length() > HttpMethod.MAX_LENGTH) {
                    LOGGER.error("HTTP Method length exceeded max limit.");
                    throw new HttpParsingException(HttpStatusCode.SERVER_ERROR_501_NOT_IMPLEMENTED);
                }
            }
        }
        LOGGER.warn("Request line not properly terminated. End of stream reached.");
        throw new HttpParsingException(HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST); // End of stream without proper termination
    }

    /**
     * Parses the headers of the HTTP request.
     *
     * @param reader      the InputStreamReader to read the headers
     * @param httpRequest the HttpRequest object to populate with headers
     * @throws IOException           if an I/O error occurs
     * @throws HttpParsingException  if a header is invalid
     */
    private void parseHeaders(InputStreamReader reader, HttpRequest httpRequest) throws IOException, HttpParsingException {
        BufferedReader bufferedReader = new BufferedReader(reader); // BufferedReader for efficient reading
        String line;

        // Read headers until an empty line is encountered (indicating the end of headers)
        while ((line = bufferedReader.readLine()) != null && !line.isEmpty()) {
            String[] header = line.split(": ", 2); // Split header on the first occurrence of ": "
            if (header.length != 2) { // Check if the header format is valid
                LOGGER.error("Invalid header format: {}", line);
                throw new HttpParsingException(HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
            }

            String headerName = header[0]; // Extract header name
            String headerValue = header[1]; // Extract header value
            httpRequest.addHeader(headerName, headerValue); // Add header to the HttpRequest object
            LOGGER.info("Parsed Header: {}: {}", headerName, headerValue); // Log the parsed header
        }
    }

    /**
     * Parses the body of the HTTP request based on the Content-Length header.
     *
     * @param reader      the InputStreamReader to read the body
     * @param httpRequest the HttpRequest object to populate with the body
     * @throws IOException           if an I/O error occurs
     * @throws HttpParsingException  if the body cannot be parsed correctly
     */
    private void parseBody(InputStreamReader reader, HttpRequest httpRequest) throws IOException, HttpParsingException {
        String contentLengthHeader = httpRequest.getHeaders().get("Content-Length"); // Retrieve Content-Length header

        // If no Content-Length header is found, set the body to an empty string
        if (contentLengthHeader == null || contentLengthHeader.isEmpty()) {
            httpRequest.setBody("");
            LOGGER.debug("No Content-Length header found or it is empty. Setting empty body.");
            return;
        }

        int contentLength; // Variable to hold the parsed content length
        try {
            contentLength = Integer.parseInt(contentLengthHeader); // Parse the Content-Length value
            LOGGER.debug("Content-Length found: {}", contentLength);
        } catch (NumberFormatException e) {
            LOGGER.error("Invalid Content-Length value: {}", contentLengthHeader);
            throw new HttpParsingException(HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST); // Invalid number format
        }

        // If the content length is 0 or negative, set the body to an empty string
        if (contentLength <= 0) {
            httpRequest.setBody("");
            LOGGER.debug("Content-Length is 0 or negative. Setting empty body.");
            return;
        }

        char[] buffer = new char[1024]; // Buffer for reading the body
        StringBuilder bodyBuilder = new StringBuilder(contentLength); // StringBuilder to accumulate the body
        int totalBytesRead = 0; // Counter for total bytes read
        int bytesRead; // Variable to track bytes read in each iteration

        LOGGER.debug("Starting to read request body.");
        // Read the body until the total bytes read match the content length
        while (totalBytesRead < contentLength && (bytesRead = reader.read(buffer, 0, Math.min(buffer.length, contentLength - totalBytesRead))) != -1) {
            bodyBuilder.append(buffer, 0, bytesRead); // Append read bytes to the body
            totalBytesRead += bytesRead; // Update total bytes read
            LOGGER.debug("Read {} bytes. Total bytes read so far: {}", bytesRead, totalBytesRead);
        }

        // Check if the total bytes read match the expected content length
        if (totalBytesRead < contentLength) {
            LOGGER.error("Body size does not match Content-Length header. Expected: {}, but read: {}", contentLength, totalBytesRead);
            throw new HttpParsingException(HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST); // Content-Length mismatch
        }

        httpRequest.setBody(bodyBuilder.toString()); // Set the body in the HttpRequest object
        LOGGER.info("Body successfully parsed. Total bytes read: {}", totalBytesRead); // Log success
    }
}
