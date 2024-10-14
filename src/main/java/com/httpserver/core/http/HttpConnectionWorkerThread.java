package com.httpserver.core.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Represents a worker thread responsible for handling HTTP connections.
 * This class extends {@link Thread} to manage communication between the
 * server and a client over a specified socket.
 * <p>
 * The worker thread processes incoming HTTP requests and sends
 * appropriate responses, such as redirecting the client from HTTP to HTTPS.
 * </p>
 */
public class HttpConnectionWorkerThread extends Thread {
    private final static Logger LOGGER = LoggerFactory.getLogger(HttpConnectionWorkerThread.class);

    private final Socket socket;

    /**
     * Constructs an {@code HttpConnectionWorkerThread} with the specified socket.
     *
     * @param socket the socket connected to the client
     */
    public HttpConnectionWorkerThread(Socket socket) {
        this.socket = socket;
        LOGGER.debug("HttpConnectionWorkerThread created for socket: {}", socket);
    }

    /**
     * Executes the worker thread, handling the incoming HTTP request and sending a 
     * redirection response to the client.
     * <p>
     * This method constructs an HTTP redirection response (301 Moved Permanently)
     * and instructs the client to access the HTTPS version of the server. The
     * response includes the server's IP address and the default HTTPS port (8043).
     * It logs the successful completion of the connection and manages any 
     * {@link IOException} that may occur during communication.
     * </p>
     *
     * @throws IOException if an I/O error occurs while writing to the socket
     */
    @Override
    public void run() {
        LOGGER.debug("Handling connection from client: {}", socket.getInetAddress());

        try (OutputStream outputStream = socket.getOutputStream()) {
            // Log that the output stream has been successfully obtained
            LOGGER.debug("Output stream obtained for socket: {}", socket);

            final int httpsPort = 8043;

            // CRLF = Carriage Return (\r) and Line Feed (\n)
            final String CRLF = "\r\n";

            // Constructing an HTTP response with a 301 redirect to HTTPS
            String response = "HTTP/1.1 301 Moved Permanently" + CRLF 
                    + "Location: https://" + InetAddress.getLocalHost().getHostAddress() + ":" + httpsPort + "/" + CRLF
                    + "Connection: close" + CRLF + CRLF;

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
