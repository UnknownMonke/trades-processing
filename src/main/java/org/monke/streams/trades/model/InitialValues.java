package org.monke.streams.trades.model;

import com.fasterxml.jackson.core.type.TypeReference;
import org.monke.streams.trades.util.JsonUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Holds a single static method {@link #table()} to deserialize initial share values array into a map.
 */
public final class InitialValues {

    private static final InputStream inputStream = InitialValues.class.getResourceAsStream("/initial-values.json");

    public static Map<String, ShareValue> table() {
        try {
            List<ShareValue> values = JsonUtils.getMapper().readValue(inputStream, new TypeReference<>() {});

            return values
                .stream()
                .collect(Collectors.toMap(ShareValue::ticker, Function.identity()));

        } catch (IOException e) {
            throw new RuntimeException("Could not deserialize initial values array", e);
        }
    }
}
