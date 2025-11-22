package org.monke.streams.generic.serdes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.apache.kafka.common.serialization.Serializer;

@AllArgsConstructor
public class JsonSerializer<T> implements Serializer<T> {

    private ObjectMapper mapper;

    @Override
    public byte[] serialize(String topic, T data) {
        try {
            return mapper.writeValueAsBytes(data);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(String.format("Error serializing to %s : %s", data.getClass().getSimpleName(), e.getMessage()), e);
        }
    }
}
