package com.httpserver.core.https;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.security.KeyStore;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.httpserver.config.ConfigurationManager;
import com.httpserver.config.SSLConfiguration;

/**
 * Represents a server listener thread that accepts incoming HTTPS connections.
 * This class extends Thread to manage the lifecycle of the SSL server socket
 * and handle client requests by spawning worker threads for secure communication.
 */
public class HttpsServerListenerThread extends Thread {
    private final static Logger LOGGER = LoggerFactory.getLogger(HttpsServerListenerThread.class);

    private final int port;
    private String webroot;
    private final SSLServerSocket serverSocket;

    /**
     * Constructs an HttpsServerListenerThread with the specified port and web root.
     *
     * @param port the port on which the server will listen for incoming HTTPS connections
     * @param webroot the root directory for serving web content
     * @throws Exception if an error occurs while initializing the server socket or loading the keystore
     */
    public HttpsServerListenerThread(int port, String webroot) throws Exception {
        this.port = port;
        this.webroot = webroot;
        this.serverSocket = createSSLServerSocket();
    }

    /**
     * Creates and initializes the SSLServerSocket based on the SSL configuration.
     *
     * @return an initialized SSLServerSocket
     * @throws Exception if an error occurs while loading the keystore or initializing the socket
     */
    private SSLServerSocket createSSLServerSocket() throws Exception {
        SSLConfiguration sslConfig = ConfigurationManager.getInstance().getConfiguration(SSLConfiguration.class);

        String keyStorePath = sslConfig.getKeystorePath();
        String keyStorePassword = sslConfig.getKeystorePassword();
        String keyPassword = sslConfig.getKeystorePassword();

        KeyStore keyStore = KeyStore.getInstance("JKS");
        try (FileInputStream keyStoreStream = new FileInputStream(keyStorePath)) {
            keyStore.load(keyStoreStream, keyStorePassword.toCharArray());
        }

        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(keyStore, keyPassword.toCharArray());

        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(keyManagerFactory.getKeyManagers(), null, null);

        SSLServerSocketFactory factory = sslContext.getServerSocketFactory();
        return (SSLServerSocket) factory.createServerSocket(this.port);
    }

    /**
     * Runs the server listener thread, accepting incoming HTTPS connections and
     * spawning worker threads to handle them.
     * 
     * This method loops indefinitely until the server socket is closed, 
     * accepting client connections and creating an instance of 
     * {@link HttpsConnectionWorkerThread} for each accepted socket connection. 
     * It logs connection information, including the port and the client's IP address, 
     * and handles any IOExceptions that may occur during socket operations.
     *
     * @throws IOException if an I/O error occurs while waiting for a connection
     */
    @Override
    public void run() {
        try {
            while (serverSocket.isBound() && !serverSocket.isClosed()) {
                Socket socket = serverSocket.accept(); // Waits here until a connection is accepted.

                LOGGER.info("* Connection accepted on port {}", this.port);
                LOGGER.info("* Connection accepted from: {}", socket.getInetAddress());

                HttpsConnectionWorkerThread workerThread = new HttpsConnectionWorkerThread(socket);
                workerThread.start();
            }
        } catch (IOException e) {
            LOGGER.error("Problem with setting up socket", e);
        } finally {
            try {
                serverSocket.close();
            } catch (IOException e) {
                LOGGER.error("Error closing SSLServerSocket in HttpsServerListenerThread", e);
            }
        }
    }
}
