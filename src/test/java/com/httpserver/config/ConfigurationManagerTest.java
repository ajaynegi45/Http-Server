package com.httpserver.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class ConfigurationManagerTest {

    private ConfigurationManager configManager;

    @BeforeEach
    void setUp() {
        configManager = ConfigurationManager.getInstance();
    }

    @Test
    void testSingletonInstance() {
        ConfigurationManager instance1 = ConfigurationManager.getInstance();
        ConfigurationManager instance2 = ConfigurationManager.getInstance();

        assertAll("Singleton Instance",
                () -> assertSame(instance1, instance2, "Instances should be the same, Singleton pattern is not followed.")
        );
    }

    @Test
    void testLoadConfigurationSuccess(@TempDir Path tempDir) throws Exception {
        Path configFile = tempDir.resolve("config.json");
        String jsonContent = "{ \"httpPort\": 8080, \"httpsPort\": 8443, \"webroot\": \"/var/www\" }";
        Files.write(configFile, jsonContent.getBytes());

        assertDoesNotThrow(() -> configManager.loadConfiguration(configFile.toString(), HttpServerConfiguration.class),
                "The configuration should load without throwing an exception.");

        HttpServerConfiguration loadedConfig = configManager.getConfiguration(HttpServerConfiguration.class);

        assertAll("Valid Configuration",
                () -> assertEquals(8080, loadedConfig.getHttpPort(), "HttpPort should match the expected value."),
                () -> assertEquals(8443, loadedConfig.getHttpsPort(), "HttpsPort should match the expected value."),
                () -> assertEquals("/var/www", loadedConfig.getWebroot(), "Webroot should match the expected value.")
        );
    }

    @Test
    void testLoadConfigurationFileNotFound() {
        String invalidPath = "nonexistent-file.json";

        Exception exception = assertThrows(HttpConfigurationException.class,
                () -> configManager.loadConfiguration(invalidPath, HttpServerConfiguration.class),
                "Loading a non-existent configuration should throw an HttpConfigurationException.");

        assertAll("File Not Found Exception",
                () -> assertTrue(exception.getMessage().contains("Error reading the configuration file"),
                        "Exception message should indicate file reading issue.")
        );
    }

    @Test
    void testLoadConfigurationInvalidJson(@TempDir Path tempDir) throws IOException {
        Path configFile = tempDir.resolve("invalid-config.json");
        String invalidJsonContent = "{ invalid_json }";
        Files.write(configFile, invalidJsonContent.getBytes());

        Exception exception = assertThrows(HttpConfigurationException.class,
                () -> configManager.loadConfiguration(configFile.toString(), HttpServerConfiguration.class),
                "Invalid JSON content should throw HttpConfigurationException.");

        assertAll("Invalid JSON Exception",
                () -> assertTrue(exception.getMessage().contains("Error parsing the configuration JSON"),
                        "Exception message should indicate JSON parsing issue.")
        );
    }

}
