package org.monke.streams.generic.serdes;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;

/**
 * Generic class to serde from entities through Jackson.
 *
 * <p> Custom {@link ObjectMapper} can be supplied through {@link #with} static method.
 * Otherwise, instantiates with a default mapper.
 */
public class GenericSerdes {

    private final ObjectMapper mapper;

    private GenericSerdes(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public static GenericSerdes withDefault() {
        return new GenericSerdes(new ObjectMapper());
    }

    public static GenericSerdes with(ObjectMapper mapper) {
        return new GenericSerdes(mapper);
    }

    public <T> Serde<T> from(Class<T> entity) {

        JsonSerializer<T> jsonSerializer = new JsonSerializer<>(mapper);
        JsonDeserializer<T> jsonDeserializer = new JsonDeserializer<>(entity, mapper);

        return Serdes.serdeFrom(jsonSerializer, jsonDeserializer);
    }
}
