package com.httpserver.core;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerListenerThread extends Thread {
    private final static Logger LOGGER = LoggerFactory.getLogger(ServerListenerThread.class);

    private final int port;
    private String webroot;
    private final ServerSocket serverSocket;

    public ServerListenerThread(int port, String webroot) throws IOException {
        this.port = port;
        this.webroot = webroot;
        this.serverSocket = new ServerSocket(this.port);
    }
    @Override
    public void run() {

        try {
            while (serverSocket.isBound() && !serverSocket.isClosed()) {


                Socket socket = serverSocket.accept(); // Code wait here until the connection is accepted.


                LOGGER.info("* Connection accepted on port {}", this.port);
                LOGGER.info("* Connection accepted: " + serverSocket.getInetAddress());

                HttpConnectionWorkerThread workerThread = new HttpConnectionWorkerThread(socket);
                workerThread.start();

            }
        } catch (IOException e) {
            LOGGER.error("Problem with setting socket", e);
            e.printStackTrace();
        }finally {
            try {
                serverSocket.close();
            } catch (IOException e) {
                LOGGER.error("Error in closing ServerSocket in  serverListenerThread", e);
            }
        }

    }
}
