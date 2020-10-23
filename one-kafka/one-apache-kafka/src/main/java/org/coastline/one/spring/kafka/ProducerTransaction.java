package org.coastline.one.spring.kafka;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

/**
 * @author Jay.H.Zou
 * @date 2020/10/22
 */
public class ProducerTransaction {

    public static void main(String[] args) {
        Properties props = new Properties();
        // brokers
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "<one or more servers separated by comma>");
        props.put(ProducerConfig.CLIENT_ID_CONFIG, "<group_id>");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        // ... set additional producer properties (optional)
        KafkaProducer<String, String> producer = new KafkaProducer<>(props);
        producer.send(new ProducerRecord<>("topic", "key", "value"), new Callback() {
            @Override
            public void onCompletion(RecordMetadata recordMetadata, Exception e) {

            }
        });
    }
}
