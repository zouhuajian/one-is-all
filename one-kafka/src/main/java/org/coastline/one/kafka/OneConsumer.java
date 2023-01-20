package org.coastline.one.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

/**
 * @author Jay.H.Zou
 * @date 2022/9/22
 */
public class OneConsumer {

    public static void main(String[] args) {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "<one or more servers separated by comma>");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "<group_id>");
        // props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, ByteArrayDeserializer.class.getName());
        // props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ByteArrayDeserializer.class.getName());
        // ... set additional consumer properties (optional)
        KafkaConsumer<byte[], byte[]> consumer = new KafkaConsumer<>(props, new ByteArrayDeserializer(), new ByteArrayDeserializer());
        consumer.subscribe(Arrays.asList("foo", "bar"));

        /* 读取数据，读取超时时间为100ms */
        while (true) {
            ConsumerRecords<byte[], byte[]> records = consumer.poll(Duration.ofSeconds(1));
            for (ConsumerRecord<byte[], byte[]> record : records)
                System.out.printf("offset = %d, key = %s, value = %s", record.offset(), Arrays.toString(record.key()), Arrays.toString(record.value()));
        }
    }
}
