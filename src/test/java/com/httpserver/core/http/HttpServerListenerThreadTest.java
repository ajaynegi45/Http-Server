package com.httpserver.core.http;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class HttpServerListenerThreadTest {
    private HttpServerListenerThread serverListener;
    private ServerSocket serverSocket;
    private Socket clientSocket;

    @BeforeEach
    public void setUp() throws IOException {
        serverSocket = mock(ServerSocket.class);
        clientSocket = mock(Socket.class);
        serverListener = new HttpServerListenerThread(0, "/webroot") {
            @Override
            public void run() {
                try {
                    Socket socket = serverSocket.accept();
                    new HttpConnectionWorkerThread(socket).start();
                } catch (IOException e) {
                    Thread.currentThread().interrupt();
                    fail("Thread was interrupted");
                }
            }
        };
    }

    @AfterEach
    public void tearDown() throws IOException, InterruptedException {
        if (serverListener.isAlive()) {
            serverListener.interrupt();
        }
        if (serverSocket != null && !serverSocket.isClosed()) {
            serverSocket.close();
        }
        if (clientSocket != null && !clientSocket.isClosed()) {
            clientSocket.close();
        }
        Thread.sleep(100);
    }

    @Test
    public void acceptConnection() throws IOException {
        when(serverSocket.isBound()).thenReturn(true);
        when(serverSocket.isClosed()).thenReturn(false);
        when(serverSocket.accept()).thenReturn(clientSocket);

        serverListener.start();
        try {
            serverListener.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            fail("Thread was interrupted");
        }

        verify(serverSocket, times(1)).accept();
        verify(clientSocket, times(1)).getInetAddress();
    }

    @Test
    public void handlingIOException() throws IOException {
        when(serverSocket.isBound()).thenReturn(true);
        when(serverSocket.isClosed()).thenReturn(false);
        when(serverSocket.accept()).thenThrow(new IOException("Accept error"));

        assertDoesNotThrow(() -> serverListener.start());
    }

    @Test
    public void serverSocketClosure() throws IOException {
        when(serverSocket.isClosed()).thenReturn(true);

        serverListener.start();
        assertTrue(serverSocket.isClosed());
    }
}
