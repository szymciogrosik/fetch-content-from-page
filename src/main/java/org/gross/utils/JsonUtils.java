package org.gross.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.text.SimpleDateFormat;

public class JsonUtils {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final SimpleDateFormat SDF = new SimpleDateFormat("dd-MM-yyyy");

    static {
        MAPPER.setDateFormat(SDF);
    }

    private JsonUtils() {
    }

    public static String parse(Object o) {
        try {
            return MAPPER.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Cannot pare object!", e);
        }
    }

}
