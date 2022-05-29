package org.coastline.one.okhttp;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;

import java.util.List;
import java.util.Properties;

/**
 * @author Jay.H.Zou
 * @date 2022/5/29
 */
public class KafkaClientTool {

    private KafkaClientTool() {
    }

    public static KafkaConsumer<byte[], byte[]> createKafkaClient(String brokers, String groupId, List<String> topics) {
        Properties properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, brokers);
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        // serialize
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, ByteArrayDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ByteArrayDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.FETCH_MAX_BYTES_CONFIG, String.valueOf(256 * 1024 * 1024));
        properties.setProperty(ConsumerConfig.MAX_PARTITION_FETCH_BYTES_CONFIG, String.valueOf(256 * 1024 * 1024));
        properties.setProperty(ConsumerConfig.REQUEST_TIMEOUT_MS_CONFIG, "60000");
        properties.setProperty(ConsumerConfig.FETCH_MAX_WAIT_MS_CONFIG, "20000");
        properties.setProperty(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "60000");
        properties.setProperty(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG, "20000");
        return new KafkaConsumer<>(properties);
    }


}
