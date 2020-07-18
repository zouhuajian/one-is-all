package org.coastline.one.spring.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

/**
 * @author Jay.H.Zou
 * @date 7/17/2020
 */
public class OneKafkaConsumer {

    private static KafkaProducer<String, String> getKafkaProducer() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "xxx");
        props.put(ProducerConfig.CLIENT_ID_CONFIG, "item-base-data");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        return new KafkaProducer<>(props);
    }

    public static void main(String[] args) {

        KafkaProducer<String, String> kafkaProducer = getKafkaProducer();

        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "xx");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "local-test");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList("EC_Hierarchy_ItemBase"));
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(2));
            for (ConsumerRecord<String, String> record : records) {
                kafkaProducer.send(new ProducerRecord<>("bigdata-test", record.key(), record.value()), new Callback() {
                    @Override
                    public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                        if (e != null) {
                            System.out.println(e);
                        }
                    }
                });
            }

        }


    }
}
