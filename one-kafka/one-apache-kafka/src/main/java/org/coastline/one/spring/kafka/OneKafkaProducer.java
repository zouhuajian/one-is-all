package org.coastline.one.spring.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

/**
 * @author Jay.H.Zou
 * @date 2020/6/12
 */
public class OneKafkaProducer {

    public static void main(String[] args) {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "<one or more servers separated by comma>");
        props.put(ProducerConfig.CLIENT_ID_CONFIG, "<group_id>");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        // ... set additional producer properties (optional)
        KafkaProducer<Integer, String> producer = new KafkaProducer<>(props);
    }

}
