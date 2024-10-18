package com.httpserver.core.https;

import com.httpserver.config.ConfigurationManager;
import com.httpserver.config.SSLConfiguration;
import org.junit.jupiter.api.AfterEach;
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
    private MockedStatic<SSLServerSocketFactory> mockedSSLFactory;
    private MockedStatic<ConfigurationManager> mockedConfigManager;
    private SSLServerSocketFactory sslServerSocketFactoryMock;
    private SSLServerSocket sslServerSocketMock;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        when(sslConfigMock.getKeystorePath()).thenReturn("keystore.jks");
        when(sslConfigMock.getKeystorePassword()).thenReturn("123");
        when(sslConfigMock.getKeyPassword()).thenReturn("123");

        mockedConfigManager = mockStatic(ConfigurationManager.class);
        mockedConfigManager.when(ConfigurationManager::getInstance).thenReturn(configManagerMock);
        when(configManagerMock.getConfiguration(SSLConfiguration.class)).thenReturn(sslConfigMock);

        sslServerSocketFactoryMock = mock(SSLServerSocketFactory.class);
        sslServerSocketMock = mock(SSLServerSocket.class);
        mockedSSLFactory = mockStatic(SSLServerSocketFactory.class);
        mockedSSLFactory.when(SSLServerSocketFactory::getDefault).thenReturn(sslServerSocketFactoryMock);
        when(sslServerSocketFactoryMock.createServerSocket(anyInt())).thenReturn(sslServerSocketMock);

        serverListenerThread = new HttpsServerListenerThread(8443, "/webroot");
    }
    @AfterEach
    public void tearDown() {
        if (mockedSSLFactory != null) {
            mockedSSLFactory.close();
        }
        if (mockedConfigManager != null) {
            mockedConfigManager.close();
        }
    }

    @Test
    public void testSSLConfigurationInitialization() {
        assertAll("SSL Configuration Initialization",
                () -> assertNotNull(serverListenerThread, "Server listener should not be null"),
                () -> assertEquals(8443, serverListenerThread.getPort(), "Port should be 8443"),
                () -> assertEquals("/webroot", serverListenerThread.getWebroot(), "Webroot should be '/webroot'")
        );
    }

    @Test
    public void testSSLServerSocketCreationSuccess() throws Exception {

        try (SSLServerSocket sslServerSocket = serverListenerThread.createSSLServerSocket()) {
            assertAll("SSL Server Socket Creation",
                    () -> assertNotNull(sslServerSocket, "SSL server socket should be created successfully"),
                    () -> verify(sslServerSocketFactoryMock, times(1)).createServerSocket(anyInt())
            );
        }
    }

    @Test
    public void testSSLServerSocketCreationFailure() throws RuntimeException {
        mockedSSLFactory.when(SSLServerSocketFactory::getDefault).thenThrow(new RuntimeException("Failed to create SSL Server Socket"));
        RuntimeException exception = assertThrows(RuntimeException.class, serverListenerThread::createSSLServerSocket);
        assertEquals("Failed to create SSL Server Socket", exception.getMessage());
    }

    @Test
    public void testServerRunMethodStartsSuccessfully() throws Exception {
        when(sslServerSocketMock.accept()).thenThrow(new IOException("Mocked accept termination"));
        Thread thread = new Thread(() -> serverListenerThread.run());
        thread.start();
        Thread.sleep(1000);
        thread.interrupt();

        assertAll("Server Run Method",
                () -> verify(sslServerSocketFactoryMock, times(1)).createServerSocket(anyInt()),
                () -> assertTrue(thread.isAlive(), "Thread should be alive during execution")
        );
    }

    @Test
    public void testStopServerMethodStopsSuccessfully() throws Exception {
        doNothing().when(sslServerSocketMock).close();
        serverListenerThread.interrupt();
        assertAll("Stop Server Method",
                () -> verify(sslServerSocketMock, times(1)).close(),
                () -> assertTrue(sslServerSocketMock.isClosed(), "Server socket should be closed")
        );
    }
}
