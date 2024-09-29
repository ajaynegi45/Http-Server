package com.httpserver;

import org.slf4j.Logger;
import java.io.IOException;
import org.slf4j.LoggerFactory;
import com.httpserver.config.Configuration;
import com.httpserver.core.ServerListenerThread;
import com.httpserver.config.ConfigurationManager;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HttpServerApplication {

	private final static Logger LOGGER = LoggerFactory.getLogger(HttpServerApplication.class);

	public static void main(String[] args) {

		if (args.length != 1) {
			LOGGER.error("No configuration file provided.");
			LOGGER.error("Syntax:  java -jar simplehttpserver-1.0-SNAPSHOT.jar  <config.json>");
			return;
		}

		LOGGER.info("Starting server...");

		ConfigurationManager.getInstance().loadConfigurationFile("src/main/resources/http.json");
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
