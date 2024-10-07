package com.httpserver.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Represents a worker thread for handling HTTP connections.
 * This class extends Thread to manage the communication
 * between the server and a client over a given socket.
 */
public class HttpConnectionWorkerThread extends Thread {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpConnectionWorkerThread.class);

    private final Socket socket;

    /**
     * Constructs an HttpConnectionWorkerThread with the specified socket.
     *
     * @param socket the socket connected to the client
     */
    public HttpConnectionWorkerThread(Socket socket) {
        this.socket = socket;
        LOGGER.debug("HttpConnectionWorkerThread created for socket: {}", socket);
    }

    /**
     * Runs the worker thread, handling incoming requests and sending responses.
     * <p>
     * This method reads data from the input stream of the socket,
     * processes the HTTP request, and writes a simple HTML response
     * back to the client. It logs the completion of the connection
     * and handles any IOExceptions that may occur during communication.
     * </p>
     */
    @Override
    public void run() {
        LOGGER.debug("Handling connection from client: {}", socket.getInetAddress());

        try (InputStream inputStream = socket.getInputStream();
             OutputStream outputStream = socket.getOutputStream()) {

            // Log that the input and output streams have been successfully obtained
            LOGGER.debug("Input and output streams obtained for socket: {}", socket);

            // Reading the HTTP request (not fully implemented)
            StringBuilder requestBuilder = new StringBuilder();
            int byteRead;
            while ((byteRead = inputStream.read()) != -1) {
                requestBuilder.append((char) byteRead);
            }
            String request = requestBuilder.toString();
            LOGGER.debug("Received request: {}", request);

            // Simple HTML response
            String html = "<html><head><title>Simple Java HTTP Server</title></head><body>This page was served using Java</body></html>";

            // CRLF = Carriage Return (\r) and Line Feed (\n)
            final String CRLF = "\r\n";

            // Constructing the HTTP response
            String response =
                    "HTTP/1.1 200 OK" + CRLF // HTTP_VERSION RESPONSE_CODE RESPONSE_MESSAGE
                            + "Content-Length: " + html.getBytes().length + CRLF // HEADER
                            + CRLF + html + CRLF;

            // Sending the response to the client
            outputStream.write(response.getBytes());
            LOGGER.debug("Sent response to client: {}", socket.getInetAddress());

            LOGGER.info("Connection completed with client: {}", socket.getInetAddress());
        } catch (IOException e) {
            LOGGER.error("IOException occurred while handling connection with client: {}", socket.getInetAddress(), e);
        } finally {
            try {
                socket.close();
                LOGGER.debug("Socket closed for client: {}", socket.getInetAddress());
            } catch (IOException e) {
                LOGGER.error("Failed to close socket for client: {}", socket.getInetAddress(), e);
            }
        }
    }
}
