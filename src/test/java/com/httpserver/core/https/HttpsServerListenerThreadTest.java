package com.httpserver.core.https;

import com.httpserver.config.ConfigurationManager;
import com.httpserver.config.SSLConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class HttpsServerListenerThreadTest {

    @Mock
    private SSLConfiguration sslConfigMock;

    @Mock
    private ConfigurationManager configManagerMock;

    private HttpsServerListenerThread serverListenerThread;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        when(sslConfigMock.getKeystorePath()).thenReturn("path/to/keystore.jks");
        when(sslConfigMock.getKeystorePassword()).thenReturn("keystorePassword");
        when(sslConfigMock.getKeyPassword()).thenReturn("keyPassword");

        try (MockedStatic<ConfigurationManager> mockedConfigManager = mockStatic(ConfigurationManager.class)) {
            mockedConfigManager.when(ConfigurationManager::getInstance).thenReturn(configManagerMock);
            when(configManagerMock.getConfiguration(SSLConfiguration.class)).thenReturn(sslConfigMock);
        }

        int port = 8443;
        String webroot = "/webroot";
        serverListenerThread = new HttpsServerListenerThread(port, webroot);
    }

    @Test
    public void testSSLConfigurationInitialization() {
            //TODO Add getters to HttpsServerListenerThread
        /*assertAll("SSL Configuration Initialization",
                () -> assertNotNull(serverListenerThread, "Server listener should not be null"),
                () -> assertEquals(8443, serverListenerThread.getPort(), "Port should be 8443"),
                () -> assertEquals("/webroot", serverListenerThread.getWebroot(), "Webroot should be '/webroot'")
        );*/
    }

    @Test
    public void testSSLServerSocketCreationSuccess() throws Exception {
        //TODO Do something about the access modifier of the createSSLServerSocket()
        /*SSLServerSocketFactory sslServerSocketFactory = mock(SSLServerSocketFactory.class);
        SSLServerSocket sslServerSocketMock = mock(SSLServerSocket.class);

        when(sslServerSocketFactory.createServerSocket(anyInt())).thenReturn(sslServerSocketMock);

        try (MockedStatic<SSLServerSocketFactory> mockedFactory = mockStatic(SSLServerSocketFactory.class)) {
            mockedFactory.when(SSLServerSocketFactory::getDefault).thenReturn(sslServerSocketFactory);

            SSLServerSocket sslServerSocket = serverListenerThread.createSSLServerSocket();

            assertAll("SSL Server Socket Creation",
                    () -> assertNotNull(sslServerSocket, "SSL server socket should be created successfully"),
                    () -> verify(sslServerSocketFactory, times(1)).createServerSocket(anyInt()),
                    () -> verifyNoMoreInteractions(sslServerSocketFactory)
            );
        }*/
    }

    @Test
    public void testSSLServerSocketCreationFailure() {
        //TODO Do something about the access modifier of the createSSLServerSocket()
        /*try (MockedStatic<SSLServerSocketFactory> mockedFactory = mockStatic(SSLServerSocketFactory.class)) {
            mockedFactory.when(SSLServerSocketFactory::getDefault).thenThrow(new IOException("Failed to create SSL Server Socket"));

            IOException exception = assertThrows(IOException.class, serverListenerThread::createSSLServerSocket);

            assertAll("SSL Server Socket Creation Failure",
                    () -> assertEquals("Failed to create SSL Server Socket", exception.getMessage(), "Exception message should match"),
                    () -> assertTrue(exception != null, "Exception should be of type IOException")
            );
        }*/
    }

    @Test
    public void testServerRunMethodStartsSuccessfully() throws Exception {
        SSLServerSocket sslServerSocketMock = mock(SSLServerSocket.class);
        when(sslServerSocketMock.accept()).thenThrow(new IOException("Mocked accept termination"));

        try (MockedStatic<SSLServerSocketFactory> mockedFactory = mockStatic(SSLServerSocketFactory.class)) {
            SSLServerSocketFactory sslServerSocketFactoryMock = mock(SSLServerSocketFactory.class);
            mockedFactory.when(SSLServerSocketFactory::getDefault).thenReturn(sslServerSocketFactoryMock);
            when(sslServerSocketFactoryMock.createServerSocket(anyInt())).thenReturn(sslServerSocketMock);

            Thread thread = new Thread(() -> {
                try {
                    serverListenerThread.run();
                } catch (Exception ignored) {
                }
            });
            thread.start();
            Thread.sleep(1000);
            thread.interrupt();

            assertAll("Server Run Method",
                    () -> verify(sslServerSocketFactoryMock, times(1)).createServerSocket(anyInt()),
                    () -> assertTrue(thread.isAlive(), "Thread should be alive during execution")
            );
        }
    }

    @Test
    public void testStopServerMethodStopsSuccessfully() throws Exception {
        //TODO Add methods to close serverListenerThread

        /*SSLServerSocket sslServerSocketMock = mock(SSLServerSocket.class);
        when(sslServerSocketMock.isClosed()).thenReturn(false).thenReturn(true);

        serverListenerThread.setServerSocket(sslServerSocketMock);
        serverListenerThread.interrupt();

        assertAll("Stop Server Method",
                () -> verify(sslServerSocketMock, times(1)).close(), // Ensures close was called
                () -> assertTrue(sslServerSocketMock.isClosed(), "Server socket should be closed")
        );*/
    }
}
