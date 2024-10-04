package com.httpserver.core;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents a server listener thread that accepts incoming HTTP connections.
 * This class extends Thread to manage the lifecycle of the server socket
 * and handle client requests by spawning worker threads.
 */
public class ServerListenerThread extends Thread {
    private final static Logger LOGGER = LoggerFactory.getLogger(ServerListenerThread.class);

    private final int port;
    private String webroot;
    private final ServerSocket serverSocket;

    /**
     * Constructs a ServerListenerThread with the specified port and web root.
     *
     * @param port the port on which the server will listen for incoming connections
     * @param webroot the root directory for serving web content
     * @throws IOException if an I/O error occurs when opening the server socket
     */
    public ServerListenerThread(int port, String webroot) throws IOException {
        this.port = port;
        this.webroot = webroot;
        this.serverSocket = new ServerSocket(this.port);
    }

    /**
     * Runs the server listener thread, accepting incoming connections and
     * spawning worker threads to handle them.
     * <p>
     * This method loops until the server socket is closed, accepting
     * connections and creating an instance of {@link HttpConnectionWorkerThread}
     * for each accepted socket connection. It logs connection information
     * and handles any IOExceptions that may occur during socket operations.
     * </p>
     *
     * @throws IOException if an I/O error occurs while waiting for a connection
     */
    @Override
    public void run() {
        try {
            while (serverSocket.isBound() && !serverSocket.isClosed()) {
                Socket socket = serverSocket.accept(); // Code waits here until the connection is accepted.

                LOGGER.info("* Connection accepted on port {}", this.port);
                LOGGER.info("* Connection accepted: " + serverSocket.getInetAddress());

                HttpConnectionWorkerThread workerThread = new HttpConnectionWorkerThread(socket);
                workerThread.start();
            }
        } catch (IOException e) {
            LOGGER.error("Problem with setting socket", e);
            e.printStackTrace();
        } finally {
            try {
                serverSocket.close();
            } catch (IOException e) {
                LOGGER.error("Error in closing ServerSocket in ServerListenerThread", e);
            }
        }
    }
}
