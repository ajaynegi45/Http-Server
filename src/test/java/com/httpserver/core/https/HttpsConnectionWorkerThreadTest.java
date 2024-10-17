package com.httpserver.core.https;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class HttpsConnectionWorkerThreadTest {

    private Socket socket;
    private InputStream inputStream;
    private OutputStream outputStream;
    private ByteArrayOutputStream outputStreamMock;

    @BeforeEach
    void setUp() throws IOException {
        socket = mock(Socket.class);
        inputStream = mock(InputStream.class);
        outputStreamMock = new ByteArrayOutputStream();
        outputStream = outputStreamMock;

        when(socket.getInputStream()).thenReturn(inputStream);
        when(socket.getOutputStream()).thenReturn(outputStream);
        when(socket.getInetAddress()).thenReturn(mock(java.net.InetAddress.class));
    }

    @Test
    void testRunSendsResponse() throws IOException {
        String simulatedRequest = "GET / HTTP/1.1\r\nHost: localhost\r\n\r\n";
        ByteArrayInputStream requestStream = new ByteArrayInputStream(simulatedRequest.getBytes());
        when(socket.getInputStream()).thenReturn(requestStream);

        HttpsConnectionWorkerThread workerThread = new HttpsConnectionWorkerThread(socket);
        workerThread.start();
        try {
            workerThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        String expectedResponse = "HTTP/1.1 200 OK\r\nContent-Type: text/html\r\nContent-Length: 109\r\nContent-Security-Policy: default-src 'self'; script-src 'self'; style-src 'self'\r\nStrict-Transport-Security: max-age=31536000; includeSubDomains\r\nX-Content-Type-Options: nosniff\r\n\r\n<html><head><title>Simple Java HTTPS Server</title></head><body>This page was served using Java</body></html>\r\n";

        assertEquals(expectedResponse.trim(), outputStreamMock.toString().trim());
    }

    @Test
    void testRunHandlesIOException() throws IOException {
        when(socket.getInputStream()).thenThrow(new IOException("Simulated IOException"));

        HttpsConnectionWorkerThread workerThread = new HttpsConnectionWorkerThread(socket);
        workerThread.start();
        try {
            workerThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        verify(socket).close();
    }

    @Test
    void testRunHandlesSocketCloseException() throws IOException {
        when(socket.getInputStream()).thenReturn(inputStream);
        when(socket.getOutputStream()).thenReturn(outputStream);

        doThrow(new IOException("Simulated IOException on close")).when(socket).close();

        HttpsConnectionWorkerThread workerThread = new HttpsConnectionWorkerThread(socket);
        workerThread.start();
        try {
            workerThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        verify(socket).close();
    }
}
