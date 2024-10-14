package com.httpserver;

import org.slf4j.Logger;
import java.util.Objects;
import java.io.IOException;
import org.slf4j.LoggerFactory;
import com.httpserver.config.HttpServerConfiguration;
import com.httpserver.core.http.HttpServerListenerThread;
import com.httpserver.core.https.HttpsServerListenerThread;
import com.httpserver.config.ConfigurationManager;
import com.httpserver.config.SSLConfiguration;

import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application class for the HTTP server.
 * This class initializes the server by loading configuration settings
 * and starting the server listener threads for HTTP and HTTPS.
 */
@SpringBootApplication
public class HttpServerApplication {

    private final static Logger LOGGER = LoggerFactory.getLogger(HttpServerApplication.class);

    /**
     * The entry point of the HTTP server application.
     *
     * @param args command-line arguments; expects a single argument specifying the configuration file path.
     */
    public static void main(String[] args) {
        LOGGER.info("Starting HTTP server application...");

        // Uncomment if you want to enforce configuration file path argument
        /*
        if (args.length != 1) {
            LOGGER.error("No configuration file provided.");
            LOGGER.error("Syntax: java -jar simplehttpserver-1.0-SNAPSHOT.jar <config.json>");
            return;
        }
        */
        
        try {
            LOGGER.debug("Loading configuration files from the classpath.");
            loadConfigurations(); // Load server configuration settings
        } catch (Exception e) {
            LOGGER.error("Failed to load configuration file: {}", e.getMessage());
            LOGGER.debug("Exception details: ", e); // Log full exception stack trace for debugging
            return; // Exit if configuration loading fails
        }
        
        // Retrieve the loaded HTTP server configuration
        HttpServerConfiguration config = ConfigurationManager.getInstance().getConfiguration(HttpServerConfiguration.class);

        // Log the loaded configuration values
        LOGGER.info("Using HTTP Port: {}", config.getHttpPort());
        LOGGER.info("Using HTTPS Port: {}", config.getHttpsPort());
        LOGGER.info("Using Webroot: {}", config.getWebroot());

        try {
            LOGGER.info("Starting server listener threads...");
            // Start the HTTPS server listener thread
            HttpsServerListenerThread serverListenerThread = new HttpsServerListenerThread(config.getHttpsPort(), config.getWebroot());
            serverListenerThread.start();
            
            // Start the HTTP server listener thread
            HttpServerListenerThread httpServerListenerThread = new HttpServerListenerThread(config.getHttpPort(), config.getWebroot());
            httpServerListenerThread.start();
            
            LOGGER.info("Server listener threads started successfully.");
        } catch (Exception e) {
            LOGGER.error("Error starting server listener threads: {}", e.getMessage());
            LOGGER.debug("Exception details: ", e); // Log full exception stack trace for debugging
        }
    }
    
    /**
     * Load configurations from JSON files.
     * This method loads the HTTP server and SSL configurations from their respective JSON files.
     * 
     * @throws IOException if configuration files are not found or can't be read.
     */
    private static void loadConfigurations() throws IOException {
        // Load the HTTP server configuration from the specified JSON file
        String httpServerConfigFilePath = Objects.requireNonNull(HttpServerApplication.class.getClassLoader().getResource("http.json")).getFile();
        ConfigurationManager.getInstance().loadConfiguration(
            httpServerConfigFilePath,
            HttpServerConfiguration.class
        );
        LOGGER.debug("Http Server Configuration file loaded from path: {}", httpServerConfigFilePath);

        // Note: The following command can be used to generate an SSL Certificate
        // keytool -genkeypair -alias myserver -keyalg RSA -keysize 2048 -keystore keystore.jks -validity 365

        // Load the SSL configuration from the specified JSON file
        String sslConfigFilePath = Objects.requireNonNull(HttpServerApplication.class.getClassLoader().getResource("ssl-config.json")).getFile();
        ConfigurationManager.getInstance().loadConfiguration(
            sslConfigFilePath,
            SSLConfiguration.class
        );
        LOGGER.debug("SSL Configuration file loaded from path: {}", sslConfigFilePath);
    }
}
