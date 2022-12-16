package aoc.helpers;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonObjectMapper {
    private static JsonObjectMapper instance;
    private ObjectMapper mapper;

    private JsonObjectMapper() {
        mapper = new ObjectMapper();
    }

    public static final JsonObjectMapper instance() {
        if (instance == null) {
            instance = new JsonObjectMapper();
        }
        return instance;
    }

    public ObjectMapper mapper() {
        return mapper;
    }

}
