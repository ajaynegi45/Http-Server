package com.httpserver.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;

import java.io.IOException;

/**
 * Utility class for handling JSON operations using Jackson's ObjectMapper.
 * This class provides methods for parsing, converting, and generating JSON.
 */
public class Json {

    private static ObjectMapper mapper = defaultObjectMapper(); // Default ObjectMapper instance for JSON operations

    /**
     * Creates a default ObjectMapper with specific configuration.
     *
     * @return a configured ObjectMapper instance
     */
    private static ObjectMapper defaultObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        // Configure the mapper to ignore unknown properties during deserialization
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper;
    }

    /**
     * Parses the given JSON string into a JsonNode.
     *
     * @param jsonSrc the JSON string to be parsed
     * @return a JsonNode representing the parsed JSON
     * @throws JsonProcessingException if there is a problem processing the JSON
     * @throws IOException if an I/O error occurs while reading the JSON
     */
    public static JsonNode parse(String jsonSrc) throws JsonProcessingException, IOException {
        return mapper.readTree(jsonSrc); // Parse the JSON string into a JsonNode
    }

    /**
     * Converts a JsonNode to an object of the specified class.
     *
     * @param node  the JsonNode to convert
     * @param clazz the class of the object to convert to
     * @param <A>   the type of the object to return
     * @return an object of type A represented by the JsonNode
     * @throws JsonProcessingException if there is a problem processing the JSON
     * @throws IOException if an I/O error occurs during conversion
     */
    public static <A> A fromJson(JsonNode node, Class<A> clazz) throws JsonProcessingException, IOException {
        return mapper.convertValue(node, clazz); // Convert the JsonNode to the specified object type
    }

    /**
     * Converts an object to a JsonNode.
     *
     * @param obj the object to convert
     * @return a JsonNode representing the object
     */
    public static JsonNode toJson(Object obj) {
        return mapper.valueToTree(obj); // Convert the object to a JsonNode
    }

    /**
     * Converts a JsonNode to a JSON string.
     *
     * @param node the JsonNode to convert
     * @return a JSON string representation of the JsonNode
     * @throws JsonProcessingException if there is a problem processing the JSON
     */
    private static String stringify(JsonNode node) throws JsonProcessingException {
        return generateJson(node, false); // Generate a JSON string without pretty-printing
    }

    /**
     * Converts a JsonNode to a pretty-printed JSON string.
     *
     * @param node the JsonNode to convert
     * @return a pretty-printed JSON string representation of the JsonNode
     * @throws JsonProcessingException if there is a problem processing the JSON
     */
    private static String stringifyPretty(JsonNode node) throws JsonProcessingException {
        return generateJson(node, true); // Generate a JSON string with pretty-printing
    }

    /**
     * Generates a JSON string from an object.
     *
     * @param obj    the object to convert to JSON
     * @param pretty whether to pretty-print the JSON output
     * @return a JSON string representation of the object
     * @throws JsonProcessingException if there is a problem processing the JSON
     */
    private static String generateJson(Object obj, boolean pretty) throws JsonProcessingException {
        ObjectWriter writer = mapper.writer(); // Create a writer for the ObjectMapper

        // Configure the writer to pretty-print the output if specified
        if (pretty) {
            writer = writer.with(SerializationFeature.INDENT_OUTPUT);
        }
        return writer.writeValueAsString(obj); // Convert the object to a JSON string
    }
}
