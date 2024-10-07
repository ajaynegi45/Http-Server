package com.httpserver.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.httpserver.utils.Json;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Manages the configuration settings for the HTTP server.
 * This class follows the Singleton design pattern to ensure that
 * only one instance of the configuration manager exists throughout
 * the application.
 */
public class ConfigurationManager {

    private static final Logger logger = LoggerFactory.getLogger(ConfigurationManager.class); // SLF4J logger instance

    private static ConfigurationManager configurationManager;
    private static Configuration myCurrentConfiguration;

    /**
     * Private constructor to prevent instantiation from outside the class.
     */
    private ConfigurationManager() {
        logger.trace("ConfigurationManager constructor called.");
    }

    /**
     * Retrieves the single instance of the ConfigurationManager.
     *
     * @return the singleton instance of ConfigurationManager
     */
    public static ConfigurationManager getInstance() {
        if (configurationManager == null) {
            configurationManager = new ConfigurationManager();
            logger.info("Created new instance of ConfigurationManager.");
        } else {
            logger.trace("Returning existing instance of ConfigurationManager.");
        }
        return configurationManager;
    }

    /**
     * Loads a configuration file from the specified file path.
     * <p>
     * This method reads the contents of the configuration file, parses
     * it into a JSON structure, and populates the current configuration
     * object. If the file is not found or there are issues parsing the
     * configuration, it throws an {@link HttpConfigurationException}.
     * </p>
     *
     * @param filePath the path to the configuration file to be loaded
     * @throws HttpConfigurationException if the configuration file is not found
     *                                     or if an error occurs while parsing
     */
    public void loadConfigurationFile(String filePath) {
        logger.info("Attempting to load configuration file from path: {}", filePath);

        FileReader fileReader;
        try {
            fileReader = new FileReader(filePath);
        } catch (FileNotFoundException e) {
            logger.error("Configuration file not found at path: {}", filePath, e);
            throw new HttpConfigurationException(e);
        }

        StringBuilder sb = new StringBuilder();
        int i;
        try {
            while ((i = fileReader.read()) != -1) {
                sb.append((char) i);
            }
            logger.trace("Configuration file read successfully, content length: {}", sb.length());
        } catch (IOException e) {
            logger.error("Error reading the configuration file", e);
            throw new HttpConfigurationException(e);
        }

        JsonNode config;
        try {
            config = Json.parse(sb.toString());
            logger.info("Successfully parsed the configuration file into JSON.");
            logger.trace("Parsed JSON content: {}", sb.toString());
        } catch (IOException e) {
            logger.error("Error parsing the configuration file into JSON", e);
            throw new HttpConfigurationException("Error parsing the Configuration file", e);
        }

        try {
            myCurrentConfiguration = Json.fromJson(config, Configuration.class);
            logger.info("Configuration successfully loaded into the current configuration.");
            logger.trace("Loaded configuration: {}", myCurrentConfiguration.toString());
        } catch (IOException e) {
            logger.error("Error converting JSON to Configuration object", e);
            throw new HttpConfigurationException("Error parsing the Configuration file internally", e);
        }
    }

    /**
     * Returns the current loaded configuration.
     *
     * @return the current Configuration object
     * @throws HttpConfigurationException if no current configuration is set
     */
    public Configuration getCurrentConfiguration() {
        if (myCurrentConfiguration == null) {
            logger.warn("Attempted to get current configuration, but no configuration is loaded.");
            throw new HttpConfigurationException("No Current Configuration Set.");
        }
        logger.info("Returning current configuration.");
        logger.trace("Current configuration: {}", myCurrentConfiguration.toString());
        return myCurrentConfiguration;
    }
}
