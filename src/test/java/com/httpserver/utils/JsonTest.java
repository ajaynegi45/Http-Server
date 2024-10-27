package com.httpserver.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

class JsonTest {

    private static JsonNode validNode;
    private static JsonNode invalidNode;
    private static String jsonSrc;

    @BeforeAll
    static void setUp() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        jsonSrc = "{\"name\":\"Max\",\"age\":18}";
        validNode = Json.parse(jsonSrc);
        invalidNode = mapper.createObjectNode()
                .put("name", "Max")
                .put("age", "eighteen");
    }

    @Test
    void testParseValidJson() throws IOException {
        JsonNode result = Json.parse(jsonSrc);
        assertAll("Valid JSON Parsing",
                () -> assertEquals("Max", result.get("name").asText(), "Name should be Max"),
                () -> assertEquals(18, result.get("age").asInt(), "Age should be 18")
        );
    }

    @Test
    void testParseInvalidJson() {
        assertThrows(JsonProcessingException.class, () -> Json.parse("invalid json"), "Should throw JsonProcessingException for invalid JSON");
    }

    @Test
    void testFromJsonValidNode() throws IOException {
        Person person = Json.fromJson(validNode, Person.class);
        assertAll("Deserializing Valid Node",
                () -> assertEquals("Max", person.getName(), "Name should match"),
                () -> assertEquals(18, person.getAge(), "Age should match")
        );
    }

    @Test
    void testFromJsonInvalidNode() {
        assertThrows(IllegalArgumentException.class, () -> Json.fromJson(invalidNode, InvalidPerson.class), "Should throw IllegalArgumentException for invalid node");
    }

    @Test
    void testToJson() {
        Person person = new Person("Max", 18);
        JsonNode jsonNode = Json.toJson(person);
        assertAll("Serialization to JSON",
                () -> assertEquals("Max", jsonNode.get("name").asText(), "Name should be Max"),
                () -> assertEquals(18, jsonNode.get("age").asInt(), "Age should be 18")
        );
    }

    @Test
    void testStringify() throws Exception {
        Method method = Json.class.getDeclaredMethod("stringify", JsonNode.class);
        method.setAccessible(true);
        String jsonString = (String) method.invoke(null, validNode);
        assertEquals(jsonSrc, jsonString, "STRINGIFIED JSON should match the original");
    }

    @Test
    void testStringifyPretty() throws Exception {
        Method method = Json.class.getDeclaredMethod("stringifyPretty", JsonNode.class);
        method.setAccessible(true);

        String prettyJsonString = (String) method.invoke(null, validNode);
        String expectedPrettyJson = "{\n  \"name\" : \"Max\",\n  \"age\" : 18\n}";
        assertEquals(expectedPrettyJson, prettyJsonString, "Pretty JSON should match the expected format");
    }

    static class Person {
        private String name;
        private int age;

        public Person() {
        }

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }
    }

    static class InvalidPerson {
        private final String name;
        private final String age;

        public InvalidPerson(String name, String age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public String getAge() {
            return age;
        }
    }
}
