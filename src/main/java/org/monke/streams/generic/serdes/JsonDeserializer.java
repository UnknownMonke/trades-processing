package org.monke.streams.generic.serdes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.apache.kafka.common.serialization.Deserializer;

import java.nio.charset.StandardCharsets;

@AllArgsConstructor
public class JsonDeserializer<T> implements Deserializer<T> {

    private Class<T> objectClass;
    private ObjectMapper mapper;

    @Override
    public T deserialize(String topic, byte[] data) {
        if (data == null) return null;

        try {
            return mapper.readValue(new String(data, StandardCharsets.UTF_8), objectClass);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(String.format("Error deserializing from %s : %s", objectClass.getSimpleName(), e.getMessage()), e);
        }
    }
}
