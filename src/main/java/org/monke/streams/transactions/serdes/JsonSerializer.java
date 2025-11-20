package org.monke.streams.transactions.serdes;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.kafka.common.serialization.Serializer;
import org.monke.streams.transactions.util.JsonUtils;

import java.nio.charset.StandardCharsets;

public class JsonSerializer<T> implements Serializer<T> {

    @Override
    public byte[] serialize(String topic, T data) {
        try {
            return JsonUtils.getMapper().writeValueAsString(data).getBytes(StandardCharsets.UTF_8);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(String.format("Error serializing to %s : %s", data.getClass().getSimpleName(), e.getMessage()), e);
        }
    }
}
