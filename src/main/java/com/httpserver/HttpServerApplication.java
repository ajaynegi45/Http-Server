package com.httpserver;

import org.slf4j.Logger;
import java.io.IOException;
import org.slf4j.LoggerFactory;
import com.httpserver.config.Configuration;
import com.httpserver.core.ServerListenerThread;
import com.httpserver.config.ConfigurationManager;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application class for the HTTP server.
 * This class initializes the server by loading configuration settings
 * and starting the server listener thread.
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

		if (args.length != 1) {
			LOGGER.error("No configuration file provided.");
			LOGGER.error("Syntax:  java -jar simplehttpserver-1.0-SNAPSHOT.jar  <config.json>");
			return;
		}

		LOGGER.info("Starting server...");

		// Load the configuration from the provided JSON file.
		ConfigurationManager.getInstance().loadConfigurationFile(args[0]);
		Configuration config = ConfigurationManager.getInstance().getCurrentConfiguration();

		LOGGER.info("Using Port: {}", config.getPort());
		LOGGER.info("Using Webroot: {}", config.getWebroot());

		try {
			System.out.println("Thread Starting...");
			ServerListenerThread serverListenerThread = new ServerListenerThread(config.getPort(), config.getWebroot());
			serverListenerThread.start();

			System.out.println("Thread Ended");
		} catch (IOException e) {
			// TODO Handle Later
			e.printStackTrace();
		}
	}
}
