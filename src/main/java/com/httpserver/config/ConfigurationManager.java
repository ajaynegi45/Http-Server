package com.httpserver.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.httpserver.utils.Json;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Manages the configuration settings for various components of the HTTP server.
 * <p>
 * This class implements the Singleton pattern to ensure only one instance of 
 * ConfigurationManager is used throughout the server, providing centralized management
 * of configurations. It allows for loading, storing, and retrieving configuration data.
 * </p>
 * <p>
 * The configuration files are expected to be in JSON format, and they are mapped 
 * to appropriate configuration objects.
 * </p>
 */
public class ConfigurationManager {
	private static final Logger logger = LoggerFactory.getLogger(ConfigurationManager.class); // SLF4J logger instance

    private static ConfigurationManager instance;
    private final Map<Class<?>, Object> configurations = new HashMap<>();

    /**
     * Private constructor to prevent instantiation from outside the class.
     * <p>
     * The constructor initializes the logger and prevents the creation of multiple
     * instances, ensuring the Singleton pattern is followed.
     * </p>
     */
    private ConfigurationManager() {
    	logger.trace("ConfigurationManager constructor called.");
    }

    /**
     * Retrieves the singleton instance of the ConfigurationManager.
     * <p>
     * If the instance does not already exist, it is created. Otherwise, the 
     * existing instance is returned.
     * </p>
     *
     * @return the singleton instance of ConfigurationManager
     */
    public static ConfigurationManager getInstance() {
        if (instance == null) {
            instance = new ConfigurationManager();
            logger.info("Created new instance of ConfigurationManager.");
        } else {
            logger.trace("Returning existing instance of ConfigurationManager.");
        }
        return instance;
    }

    /**
     * Loads a configuration file and maps it to a specified configuration class.
     * <p>
     * This method reads a configuration file from the provided file path, parses the file
     * as JSON, and maps it to the specified configuration class. The resulting configuration
     * object is stored internally for later retrieval.
     * </p>
     *
     * @param filePath the path to the configuration file to be loaded
     * @param configClass the class type to map the configuration to
     * @param <T> the type of the configuration class
     * @throws HttpConfigurationException if there is an error reading or parsing the configuration file
     */
    public <T> void loadConfiguration(String filePath, Class<T> configClass) {
    	logger.info("Attempting to load configuration file from path: {}", filePath);

        String jsonString = readFile(filePath);
        JsonNode configJson = parseJson(jsonString);
        
        try {
            T configInstance = Json.fromJson(configJson, configClass);
            configurations.put(configClass, configInstance);
            
            logger.info("Configuration successfully loaded.");
            logger.trace("Loaded configuration: {}", configInstance.toString());
        } catch (IOException e) {
        	logger.error("Error converting JSON to Configuration object", e);
            throw new HttpConfigurationException("Error parsing the Configuration file internally", e);
        }
    }

    /**
     * Reads the content of a file into a String.
     * <p>
     * This method reads the specified file from the file system and returns its 
     * contents as a String. If an error occurs during the reading process, 
     * an exception is thrown.
     * </p>
     *
     * @param filePath the path to the file to be read
     * @return the content of the file as a String
     * @throws HttpConfigurationException if an error occurs while reading the file
     */
    private String readFile(String filePath) {
        StringBuilder sb = new StringBuilder();
        try (FileReader fileReader = new FileReader(filePath)) {
            int i;
            while ((i = fileReader.read()) != -1) {
                sb.append((char) i);
            }
            logger.trace("Configuration file read successfully, content length: {}", sb.length());
        } catch (IOException e) {
        	logger.error("Error reading the configuration file", e);
            throw new HttpConfigurationException("Error reading the configuration file: " + filePath, e);
        } 
        return sb.toString();
    }

    /**
     * Parses a JSON string into a JsonNode.
     * <p>
     * This method takes a JSON string and parses it into a {@link JsonNode} object.
     * If parsing fails, it throws an exception.
     * </p>
     *
     * @param jsonString the JSON string to be parsed
     * @return the parsed JsonNode representing the configuration
     * @throws HttpConfigurationException if there is an error while parsing the JSON
     */
    private JsonNode parseJson(String jsonString) {
        try {
            JsonNode config = Json.parse(jsonString);
            logger.info("Successfully parsed the configuration file into JSON.");
            logger.trace("Parsed JSON content: {}", jsonString);
            return config;
        } catch (IOException e) {
        	logger.error("Error parsing the configuration file into JSON", e);
            throw new HttpConfigurationException("Error parsing the configuration JSON", e);
        }
    }

    /**
     * Retrieves the configuration object for the specified class type.
     * <p>
     * This method returns the loaded configuration object for the given class type. 
     * If no configuration has been loaded for the specified class, an exception is thrown.
     * </p>
     *
     * @param configClass the class type of the configuration to retrieve
     * @param <T> the type of the configuration class
     * @return the loaded configuration object
     * @throws HttpConfigurationException if no configuration is loaded for the specified class
     */
    @SuppressWarnings("unchecked")
    public <T> T getConfiguration(Class<T> configClass) {
        T configInstance = (T) configurations.get(configClass);
        if (configInstance == null) {
            logger.warn("Attempted to get configuration, but none is loaded.");
            throw new HttpConfigurationException("No configuration set for: " + configClass.getSimpleName());
        }
        logger.info("Returning current configuration.");
        logger.trace("Current configuration: {}", configInstance.toString());
        return configInstance;
    }
}
