package org.monke.streams.generic;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.Topology;

import java.util.Properties;
import java.util.concurrent.CountDownLatch;

/**
 * Generic lifecycle class for the Kafka Streams application.
 * <ul>
 *     <li> Creates an instance from given topology and config.
 *     <li> Starts instance.
 *     <li> Handles shutdown though latch.
 * </ul>
 */
@Slf4j
public class ApplicationRunner {

    public static void run(Topology topology, Properties config) {

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
