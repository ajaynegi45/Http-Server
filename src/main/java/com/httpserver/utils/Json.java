package com.httpserver.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;

import java.io.IOException;

public class Json {

    private static ObjectMapper mapper = defaultObjectMapper();

    private static ObjectMapper defaultObjectMapper(){
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        return mapper;
    }

    public static JsonNode parse(String jsonSrc) throws JsonProcessingException, IOException {
        return mapper.readTree(jsonSrc);
    }

    public static <A> A fromJson(JsonNode node, Class<A> clazz) throws JsonProcessingException, IOException {
        return mapper.convertValue(node, clazz);
    }

    public static JsonNode toJson(Object obj) {
        return mapper.valueToTree(obj);
    }

    private static String stringify(JsonNode node) throws JsonProcessingException {
        return generateJson(node,false);
    }

    private static String stringifyPretty(JsonNode node) throws JsonProcessingException {
        return generateJson(node,true);
    }

    private static String generateJson(Object obj, boolean pretty) throws JsonProcessingException {
        ObjectWriter writer = mapper.writer();

        if (pretty){
            writer = writer.with(SerializationFeature.INDENT_OUTPUT);
        }
        return writer.writeValueAsString(obj);
    }

}
