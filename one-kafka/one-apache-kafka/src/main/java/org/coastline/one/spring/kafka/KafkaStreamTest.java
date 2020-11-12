package org.coastline.one.spring.kafka;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.KStream;

import java.util.Properties;
import java.util.concurrent.CountDownLatch;

public class KafkaStreamTest {

    public static void main(String[] args) throws Exception {
        Properties config = new Properties();

        config.put(StreamsConfig.APPLICATION_ID_CONFIG, "stream-pipe");
        // specify a list of host/port pairs to connect to the kakfa cluster
        config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.1.121:9092, 192.168.1.122:9092,192.168.1.123:9092");
        config.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        config.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        final StreamsBuilder builder = new StreamsBuilder();
        // a source stream from a kakfa topic named streams-plaintext-inpput
        KStream<String,String> source = builder.stream("streams-plaintext-input");

        // write to streams-pipe-output topic
        source.to("streams-pipe-output");

        // concatenate the above two lines into a single line:
        // builder.stream("streams-plaintext-input").to("streams-pipe-output");

        // inpsect what kind of topology is created from this builder
        final Topology topology = builder.build();
        System.out.println(topology.describe());

        final KafkaStreams streams = new KafkaStreams(topology, config);

        final CountDownLatch latch = new CountDownLatch(1);
        Runtime.getRuntime().addShutdownHook(new Thread("streams-shutdonw-hook") {
            @Override
            public void run() {
                streams.close();
                latch.countDown();
            }
        });

        try {
            streams.start();
            latch.await();
        }catch(Throwable e) {
            System.exit(1);
        }
    }
}