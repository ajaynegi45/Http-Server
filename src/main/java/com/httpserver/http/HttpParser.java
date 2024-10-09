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
 */
public class HttpParser {

    private final static Logger LOGGER = LoggerFactory.getLogger(HttpParser.class);

    private static final int SP = 0x20; // Space character
    private static final int CR = 0x0D; // Carriage return
    private static final int LF = 0x0A; // Line feed

    /**
     * Parses an HTTP request from the provided InputStream.
     *
     * @param inputStream the InputStream containing the HTTP request data
     * @return the parsed HttpRequest object
     * @throws HttpParsingException if there is an error during parsing
     */
    public HttpRequest parseHttpRequest(InputStream inputStream) throws HttpParsingException {
        InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.US_ASCII);
        HttpRequest httpRequest = new HttpRequest();

        try {
            parseRequestLine(reader, httpRequest);
            parseHeaders(reader, httpRequest);
            parseBody(reader, httpRequest);
        } catch (IOException e) {
            LOGGER.error("I/O error during parsing: {}", e.getMessage());
            throw new HttpParsingException(HttpStatusCode.SERVER_ERROR_500_INTERNAL_SERVER_ERROR);
        }

        return httpRequest;
    }

    /**
     * Parses the request line of the HTTP request.
     *
     * @param reader  the InputStreamReader to read the request line
     * @param request the HttpRequest object to populate
     * @throws IOException           if an I/O error occurs
     * @throws HttpParsingException  if the request line is invalid
     */
    private void parseRequestLine(InputStreamReader reader, HttpRequest request) throws IOException, HttpParsingException {
        StringBuilder processingDataBuffer = new StringBuilder();
        boolean methodParsed = false;
        boolean requestTargetParsed = false;

        int _byte;
        while ((_byte = reader.read()) >= 0) {
            if (_byte == CR) {
                _byte = reader.read();
                if (_byte == LF) {
                    if (!methodParsed || !requestTargetParsed) {
                        LOGGER.error("Method or Request Target not parsed properly. MethodParsed: {}, RequestTargetParsed: {}", methodParsed, requestTargetParsed);
                        throw new HttpParsingException(HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
                    }
                    LOGGER.debug("Request line parsed successfully.");
                    return;
                } else {
                    LOGGER.error("CRLF expected but got: {}", _byte);
                    throw new HttpParsingException(HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
                }
            }

            if (_byte == SP) {
                if (!methodParsed) {
                    LOGGER.debug("Request Line METHOD to Process: {}", processingDataBuffer.toString());
                    try {
                        request.setMethod(HttpMethod.valueOf(processingDataBuffer.toString()));
                        LOGGER.info("Parsed HTTP Method: {}", request.getMethod());
                    } catch (IllegalArgumentException e) {
                        LOGGER.error("Unsupported HTTP Method: {}", processingDataBuffer.toString());
                        throw new HttpParsingException(HttpStatusCode.SERVER_ERROR_501_NOT_IMPLEMENTED);
                    }
                    methodParsed = true;
                } else if (!requestTargetParsed) {
                    if (processingDataBuffer.length() == 0) {
                        LOGGER.error("Empty Request Target found.");
                        throw new HttpParsingException(HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
                    }
                    LOGGER.debug("Request Line REQ TARGET to Process: {}", processingDataBuffer.toString());
                    request.setRequestTarget(processingDataBuffer.toString());
                    requestTargetParsed = true;
                } else {
                    LOGGER.error("Extra space detected in request line.");
                    throw new HttpParsingException(HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
                }
                processingDataBuffer.setLength(0); // Clear buffer
            } else {
                processingDataBuffer.append((char) _byte);
                if (!methodParsed && processingDataBuffer.length() > HttpMethod.MAX_LENGTH) {
                    LOGGER.error("HTTP Method length exceeded max limit.");
                    throw new HttpParsingException(HttpStatusCode.SERVER_ERROR_501_NOT_IMPLEMENTED);
                }
            }
        }
        LOGGER.warn("Request line not properly terminated. End of stream reached.");
        throw new HttpParsingException(HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
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
        BufferedReader bufferedReader = new BufferedReader(reader);
        String line;

        while ((line = bufferedReader.readLine()) != null && !line.isEmpty()) {
            String[] header = line.split(": ", 2);
            if (header.length != 2) {
                LOGGER.error("Invalid header format: {}", line);
                throw new HttpParsingException(HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
            }

            String headerName = header[0];
            String headerValue = header[1];
            httpRequest.addHeader(headerName, headerValue);
            LOGGER.info("Parsed Header: {}: {}", headerName, headerValue);
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
        String contentLengthHeader = httpRequest.getHeaders().get("Content-Length");

        if (contentLengthHeader == null || contentLengthHeader.isEmpty()) {
            httpRequest.setBody("");
            LOGGER.debug("No Content-Length header found or it is empty. Setting empty body.");
            return;
        }

        int contentLength;
        try {
            contentLength = Integer.parseInt(contentLengthHeader);
            LOGGER.debug("Content-Length found: {}", contentLength);
        } catch (NumberFormatException e) {
            LOGGER.error("Invalid Content-Length value: {}", contentLengthHeader);
            throw new HttpParsingException(HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
        }

        if (contentLength <= 0) {
            httpRequest.setBody("");
            LOGGER.debug("Content-Length is 0 or negative. Setting empty body.");
            return;
        }

        char[] buffer = new char[1024];
        StringBuilder bodyBuilder = new StringBuilder(contentLength);
        int totalBytesRead = 0;
        int bytesRead;

        LOGGER.debug("Starting to read request body.");
        while (totalBytesRead < contentLength && (bytesRead = reader.read(buffer, 0, Math.min(buffer.length, contentLength - totalBytesRead))) != -1) {
            bodyBuilder.append(buffer, 0, bytesRead);
            totalBytesRead += bytesRead;
            LOGGER.debug("Read {} bytes. Total bytes read so far: {}", bytesRead, totalBytesRead);
        }

        if (totalBytesRead < contentLength) {
            LOGGER.error("Body size does not match Content-Length header. Expected: {}, but read: {}", contentLength, totalBytesRead);
            throw new HttpParsingException(HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
        }

        httpRequest.setBody(bodyBuilder.toString());
        LOGGER.info("Body successfully parsed. Total bytes read: {}", totalBytesRead);
    }
}
