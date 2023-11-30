package org.gross.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;

public abstract class JsonSerializerBase {

    private static final SimpleDateFormat SDF = new SimpleDateFormat("dd-MM-yyyy");
    private final ObjectMapper mapper;

    public JsonSerializerBase() {
        mapper = new ObjectMapper();
        mapper.setDateFormat(SDF);
    }

    public String defaultSerialize(Object o) {
        try {
            return mapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Cannot serialize object!", e);
        }
    }

    public <T> T defaultDeserialize(File file) {
        try {
            return mapper.readValue(file, new TypeReference<>(){});
        } catch (IOException e) {
            throw new RuntimeException("Cannot deserialize object!", e);
        }
    }

}
