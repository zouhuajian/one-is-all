package org.coastline.one.spring.kafka;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * @author Jay.H.Zou
 * @date 2020/6/12
 */
public class OneKafkaProducer {

    public static void main(String[] args) throws InterruptedException {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        // ... set additional producer properties (optional)
        KafkaProducer<String, String> producer = new KafkaProducer<>(props);
        for (int i = 0; i < 100000; i++) {
            TimeUnit.MILLISECONDS.sleep(100);
            producer.send(new ProducerRecord<>("order_service", "one-" + i, "ONE-" + i));
        }

    }

}
