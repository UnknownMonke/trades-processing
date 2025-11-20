package org.monke.streams.transactions.topology;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.*;
import org.monke.streams.transactions.serdes.SerdesFactory;

public class TopologyBuilder {

    public static Topology buildTopology() {
        StreamsBuilder streamsBuilder = new StreamsBuilder();

        KTable<String, Long> transactionCountTable = streamsBuilder.stream(
                "trades",
                Consumed.with(Serdes.String(), SerdesFactory.tradeSerdes())
            )
            .groupBy((key, trade) -> trade.ticker())
            .count(Materialized.as("transactions-count"));

        transactionCountTable
            .toStream()
            .print(Printed.<String, Long>toSysOut().withLabel("trade-transactions-count"));

        transactionCountTable.toStream().to("trade-transactions-count", Produced.with(Serdes.String(), Serdes.Long()));

        return streamsBuilder.build();
    }
}
