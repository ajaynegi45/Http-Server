package com.httpserver.core.http;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class HttpConnectionWorkerThreadTest {
    private Socket socket;
    private OutputStream outputStream;
    private ByteArrayOutputStream byteArrayOutputStream;
    private HttpConnectionWorkerThread workerThread;

    @BeforeEach
    public void setUp() throws IOException {
        socket = Mockito.mock(Socket.class);
        byteArrayOutputStream = new ByteArrayOutputStream();
        outputStream = byteArrayOutputStream;

        when(socket.getOutputStream()).thenReturn(outputStream);
        when(socket.getInetAddress()).thenReturn(InetAddress.getByName("127.0.0.1"));

        workerThread = new HttpConnectionWorkerThread(socket);
    }

    @Test
    public void successfulResponse() throws IOException {
        workerThread.start();
        try {
            workerThread.join();
            String response = byteArrayOutputStream.toString(StandardCharsets.UTF_8);

            assertTrue(response.startsWith("HTTP/1.1 301 Moved Permanently"));
            assertTrue(response.contains("Location: https://127.0.0.1:8043/"));
            assertTrue(response.contains("Connection: close"));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            fail("Thread was interrupted");
        }
    }

    @Test
    public void handlingIOException() throws IOException {
        when(socket.getOutputStream()).thenThrow(new IOException("Output stream error"));
        workerThread.start();
        try {
            workerThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            fail("Thread was interrupted");
        }
        verify(socket, times(1)).close();
    }
}
