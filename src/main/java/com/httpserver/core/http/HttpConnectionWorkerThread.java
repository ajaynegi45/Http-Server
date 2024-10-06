package com.httpserver.core.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Represents a worker thread for handling HTTP connections. This class extends
 * Thread to manage communication between the server and a client over a given
 * socket, and it is responsible for handling the HTTP request and providing an
 * appropriate response, such as redirecting the client from HTTP to HTTPS.
 */
public class HttpConnectionWorkerThread extends Thread {
    private final static Logger LOGGER = LoggerFactory.getLogger(HttpConnectionWorkerThread.class);

    private final Socket socket;

    /**
     * Constructs an HttpConnectionWorkerThread with the specified socket.
     *
     * @param socket the socket connected to the client
     */
    public HttpConnectionWorkerThread(Socket socket) {
        this.socket = socket;
    }

    /**
     * Runs the worker thread, handling the incoming HTTP request and sending a 
     * redirection response to the client.
     * <p>
     * This method constructs an HTTP redirection response (301 Moved Permanently)
     * and sends the client to the HTTPS version of the server. The response includes
     * the IP address of the server and the HTTPS port (default 8043). It logs the 
     * successful completion of the connection and handles any IOExceptions that may 
     * occur during communication.
     * </p>
     *
     * @throws IOException if an I/O error occurs while writing to the socket
     */
    @Override
    public void run() {
        try (OutputStream outputStream = socket.getOutputStream()) {

            final int httpsPort = 8043;
            
            // CRLF = Carriage Return (\r) and Line Feed (\n)
            final String CRLF = "\r\n"; // ASCII codes for CR and LF

            // Constructing an HTTP response with a 301 redirect to HTTPS
            String response = "HTTP/1.1 301 Moved Permanently" + CRLF 
                    + "Location: https://" + InetAddress.getLocalHost().getHostAddress() + ":" + httpsPort + "/" + CRLF
                    + "Connection: close" + CRLF + CRLF;

            // Sending the response to the client
            outputStream.write(response.getBytes());
            
            // Log the completion of the connection
            LOGGER.info("Connection completed..");
        } catch (IOException e) {
            // Log and handle communication errors
            LOGGER.error("Problem with Communication", e);
            e.printStackTrace();
        }
    }
}
