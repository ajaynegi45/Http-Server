package com.httpserver;

import org.slf4j.Logger;
import java.io.IOException;
import org.slf4j.LoggerFactory;
import com.sun.tools.javac.Main;
import com.httpserver.config.Configuration;
import com.httpserver.core.ServerListenerThread;
import com.httpserver.config.ConfigurationManager;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HttpServerApplication {

	private final static Logger LOGGER = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) {
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
