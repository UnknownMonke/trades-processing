package org.monke.streams.transactions;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.Topology;
import org.monke.streams.transactions.config.ApplicationConfig;
import org.monke.streams.transactions.topology.TopologyBuilder;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;

/**
 * <p>Lifecycle class for the Kafka Streams application.</p>
 * <ul>
 *     <li>Creates instance from topology and config.</li>
 *     <li>Starts instance.</li>
 *     <li>Handles shutdown though latch.</li>
 * </ul>
 */
@Slf4j
public class StreamsProcessingGenericClass {

    public static void main(String[] args) {

        Topology topology = TopologyBuilder.buildTopology();
        Properties config = ApplicationConfig.getStreamsConfig();

        log.info("topology : {}", topology.describe().toString());

        try(KafkaStreams streams = new KafkaStreams(topology, config)) {
            streams.cleanUp();
            streams.start();

            final CountDownLatch latch = new CountDownLatch(1);

            Runtime.getRuntime().addShutdownHook(
                new Thread(() -> {
                    streams.close();
                    latch.countDown();
                }));
            latch.await();

        } catch (Exception e) {
            log.error("Error starting the Kafka Streams application: {}", e.getMessage(), e);
            System.exit(1);
        }
        System.exit(0);
    }
}
