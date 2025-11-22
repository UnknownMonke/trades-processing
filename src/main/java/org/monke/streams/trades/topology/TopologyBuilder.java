package org.monke.streams.trades.topology;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.*;
import org.monke.streams.generic.serdes.GenericSerdes;
import org.monke.streams.trades.model.Trade;
import org.monke.streams.trades.util.JsonUtils;

public class TopologyBuilder {

    public static Topology buildTopology() {
        StreamsBuilder streamsBuilder = new StreamsBuilder();

        KTable<String, Long> transactionCountTable = streamsBuilder.stream(
                "trades",
                Consumed.with(Serdes.String(), GenericSerdes.with(JsonUtils.getMapper()).from(Trade.class))
            )
            .groupBy((key, value) -> value.ticker())
            .count(Named.as("transaction-count-store"));

        transactionCountTable
            .toStream()
            .print(Printed.<String, Long>toSysOut().withLabel("trade-transactions-count"));

        transactionCountTable.toStream()
            .to("trade-transactions-count", Produced.with(Serdes.String(), Serdes.Long()));

        return streamsBuilder.build();
    }
}
