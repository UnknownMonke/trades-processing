package org.monke.streams.transactions.serdes;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.monke.streams.transactions.model.Trade;

public class SerdesFactory {

    public static Serde<Trade> tradeSerdes() {

        JsonSerializer<Trade> jsonSerializer = new JsonSerializer<>();
        JsonDeserializer<Trade> jsonDeserializer = new JsonDeserializer<>(Trade.class);

        return Serdes.serdeFrom(jsonSerializer, jsonDeserializer);
    }
}
