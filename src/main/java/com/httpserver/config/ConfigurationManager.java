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
 * Manages the configuration settings for various types of configurations in the HTTP server.
 * <p>
 * This class follows the Singleton design pattern to ensure that only one instance of the
 * ConfigurationManager exists throughout the application, allowing centralized management
 * of configuration settings.
 * </p>
 */
public class ConfigurationManager {
    private static final Logger logger = LoggerFactory.getLogger(ConfigurationManager.class); // SLF4J logger instance

    private static ConfigurationManager instance;
    private final Map<Class<?>, Object> configurations = new HashMap<>();

    /**
     * Private constructor to prevent instantiation from outside the class.
     * Initializes the ConfigurationManager instance.
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
        if (instance == null) {
            instance = new ConfigurationManager();
            logger.info("Created new instance of ConfigurationManager.");
        } else {
            logger.trace("Returning existing instance of ConfigurationManager.");
        }
        return instance;
    }

    /**
     * Loads a configuration file for a specific configuration type.
     * <p>
     * This method reads the specified configuration file, parses it as JSON,
     * and stores the resulting configuration object in the internal map.
     * </p>
     *
     * @param filePath    the path to the configuration file to be loaded
     * @param configClass the class type of the configuration to be loaded
     * @param <T>         the type of the configuration
     * @throws HttpConfigurationException if there is an error reading or parsing the configuration file
     */
    public <T> void loadConfiguration(String filePath, Class<T> configClass) {
        logger.info("Attempting to load configuration file from path: {}", filePath);

        String jsonString = readFile(filePath);
        JsonNode configJson = parseJson(jsonString);

        try {
            T configInstance = Json.fromJson(configJson, configClass);
            configurations.put(configClass, configInstance);

            logger.info("Configuration successfully loaded into the current configuration.");
            logger.trace("Loaded configuration: {}", configInstance.toString());
        } catch (IOException e) {
            logger.error("Error converting JSON to Configuration object", e);
            throw new HttpConfigurationException("Error parsing the Configuration file internally", e);
        }
    }

    /**
     * Reads the contents of a file and returns it as a String.
     * <p>
     * This method handles file reading and converts the contents of the file
     * into a String. If an error occurs during reading, it throws an
     * HttpConfigurationException.
     * </p>
     *
     * @param filePath the path to the file to be read
     * @return the contents of the file as a String
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
     * Parses a JSON string and returns the corresponding JsonNode.
     * <p>
     * This method takes a JSON string as input and parses it into a JsonNode.
     * If parsing fails, it throws an HttpConfigurationException.
     * </p>
     *
     * @param jsonString the JSON string to be parsed
     * @return the parsed JsonNode
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
     * Retrieves the configuration of the specified type.
     * <p>
     * This method returns the configuration instance of the specified class type.
     * If no configuration has been loaded for the specified type, it throws
     * an HttpConfigurationException.
     * </p>
     *
     * @param configClass the class type of the configuration
     * @param <T>         the type of the configuration
     * @return the configuration instance
     * @throws HttpConfigurationException if no configuration is set for the specified type
     */
    @SuppressWarnings("unchecked")
    public <T> T getConfiguration(Class<T> configClass) {
        T configInstance = (T) configurations.get(configClass);
        if (configInstance == null) {
            logger.warn("Attempted to get current configuration, but no configuration is loaded.");
            throw new HttpConfigurationException("No configuration set for: " + configClass.getSimpleName());
        }
        logger.info("Returning current configuration.");
        logger.trace("Current configuration: {}", configInstance);
        return configInstance;
    }
}
