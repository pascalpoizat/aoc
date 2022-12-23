package aoc.helpers;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonObjectMapper {
    private static JsonObjectMapper instance;
    private final ObjectMapper mapper;

    private JsonObjectMapper() {
        mapper = new ObjectMapper();
    }

    public static JsonObjectMapper instance() {
        if (instance == null) {
            instance = new JsonObjectMapper();
        }
        return instance;
    }

    public ObjectMapper mapper() {
        return mapper;
    }

}
