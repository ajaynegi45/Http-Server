package com.httpserver.core.http;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents a server listener thread that accepts incoming HTTP connections.
 * This class extends {@link Thread} to manage the lifecycle of the server socket
 * and handle client requests by spawning worker threads.
 */
public class HttpServerListenerThread extends Thread {
    private final static Logger LOGGER = LoggerFactory.getLogger(HttpServerListenerThread.class);

    private final int port;
    private final String webroot;
    private final ServerSocket serverSocket;

    /**
     * Constructs a {@code HttpServerListenerThread} with the specified port and web root.
     *
     * @param port    the port on which the server will listen for incoming connections
     * @param webroot the root directory for serving web content
     * @throws IOException if an I/O error occurs when opening the server socket
     */
    public HttpServerListenerThread(int port, String webroot) throws IOException {
        this.port = port;
        this.webroot = webroot;
        this.serverSocket = new ServerSocket(this.port);
        LOGGER.debug("HTTP - Server initialized on port: {} with webroot: {}", this.port, this.webroot);
    }

    /**
     * Runs the server listener thread, accepting incoming connections and spawning
     * worker threads to handle them.
     * <p>
     * This method loops indefinitely until the server socket is closed, accepting
     * client connections and creating an instance of
     * {@link HttpConnectionWorkerThread} for each accepted socket connection. It
     * logs connection information, including the port and the client's IP address,
     * and handles any {@link IOException} that may occur during socket operations.
     * </p>
     *
     * @throws IOException if an I/O error occurs while waiting for a connection
     */
    @Override
    public void run() {
        try {
            while (serverSocket.isBound() && !serverSocket.isClosed()) {
                LOGGER.debug("Waiting for a new connection...");
                Socket socket = serverSocket.accept(); // Code waits here until a connection is accepted.

                LOGGER.info("* Connection accepted on port {}", this.port);
                LOGGER.info("* Connection accepted from IP: {}", socket.getInetAddress());

                // Create and start a worker thread to handle the new connection
                HttpConnectionWorkerThread workerThread = new HttpConnectionWorkerThread(socket);
                workerThread.start();
            }
        } catch (IOException e) {
            LOGGER.error("Problem with setting socket: {}", e.getMessage());
            LOGGER.debug("Full stack trace:", e); // Log the stack trace for debugging
        } finally {
            try {
                serverSocket.close();
                LOGGER.debug("Server socket closed successfully.");
            } catch (IOException e) {
                LOGGER.error("Error in closing ServerSocket in HttpServerListenerThread: {}", e.getMessage());
                LOGGER.debug("Full stack trace:", e); // Log the stack trace for debugging
            }
        }
    }
}
