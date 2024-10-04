package com.httpserver.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.httpserver.utils.Json;

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

    private static ConfigurationManager configurationManager;
    private static Configuration myCurrentConfiguration;

    /**
     * Private constructor to prevent instantiation from outside the class.
     */
    private ConfigurationManager() {
    }

    /**
     * Retrieves the single instance of the ConfigurationManager.
     *
     * @return the singleton instance of ConfigurationManager
     */
    public static ConfigurationManager getInstance() {
        if (configurationManager == null) {
            configurationManager = new ConfigurationManager();
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
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(filePath);
        } catch (FileNotFoundException e) {
            throw new HttpConfigurationException(e);
        }
        StringBuffer sb = new StringBuffer();

        int i;
        try {
            while ((i = fileReader.read()) != -1) {
                sb.append((char) i);
            }
        } catch (IOException e) {
            throw new HttpConfigurationException(e);
        }

        JsonNode config = null;
        try {
            config = Json.parse(sb.toString());
        } catch (IOException e) {
            throw new HttpConfigurationException("Error parsing the Configuration file", e);
        }

        try {
            myCurrentConfiguration = Json.fromJson(config, Configuration.class);
        } catch (IOException e) {
            throw new HttpConfigurationException("Error parsing the Configuration file INTERNAL", e);
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
            throw new HttpConfigurationException("No Current Configuration Set.");
        }
        return myCurrentConfiguration;
    }
}
