package org.monke.streams.trades;

import lombok.extern.slf4j.Slf4j;
import org.monke.streams.generic.ApplicationRunner;
import org.monke.streams.trades.config.ApplicationConfig;
import org.monke.streams.trades.topology.TopologyBuilder;

@Slf4j
public class StreamsApplication {

    public static void main(String[] args) {
        ApplicationRunner.run(TopologyBuilder.buildTopology(), ApplicationConfig.getStreamsConfig());
    }
}
