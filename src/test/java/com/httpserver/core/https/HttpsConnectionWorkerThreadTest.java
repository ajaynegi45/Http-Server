package com.httpserver.core.https;

import com.httpserver.core.https.HttpsConnectionWorkerThread;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
        when(inputStream.read(any(byte[].class), anyInt(), anyInt())).thenAnswer(invocation -> {
            byte[] buffer = invocation.getArgument(0);
            int len = Math.min(buffer.length, simulatedRequest.length());
            System.arraycopy(simulatedRequest.getBytes(), 0, buffer, 0, len);
            return len;
        });

        HttpsConnectionWorkerThread workerThread = new HttpsConnectionWorkerThread(socket);
        workerThread.start();
        try {
            workerThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        String expectedResponse = """
                HTTP/1.1 200 OK\r
                Content-Length: 109\r
                \r
                <html><head><title>Simple Java HTTPS Server</title></head><body>This page was served using Java</body></html>\r
                \r
                """;

        assertEquals(expectedResponse, outputStreamMock.toString());
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
