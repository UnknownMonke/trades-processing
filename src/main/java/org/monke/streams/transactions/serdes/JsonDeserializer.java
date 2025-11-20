package org.monke.streams.transactions.serdes;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import org.apache.kafka.common.serialization.Deserializer;
import org.monke.streams.transactions.util.JsonUtils;

import java.nio.charset.StandardCharsets;

@AllArgsConstructor
public class JsonDeserializer<T> implements Deserializer<T> {

    private Class<T> objectClass;

    @Override
    public T deserialize(String topic, byte[] data) {
        if (data == null) return null;

        try {
            return JsonUtils.getMapper().readValue(new String(data, StandardCharsets.UTF_8), objectClass);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(String.format("Error deserializing from %s : %s", objectClass.getSimpleName(), e.getMessage()), e);
        }
    }
}
