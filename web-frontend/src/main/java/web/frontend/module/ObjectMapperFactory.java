package web.frontend.module;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectMapperFactory {
    private static ObjectMapper objectMapper;

    public static synchronized ObjectMapper getObjectMapper() {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
        }
        return objectMapper;
    }
}
