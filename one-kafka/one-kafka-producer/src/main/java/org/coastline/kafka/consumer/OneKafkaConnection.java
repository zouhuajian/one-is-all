package org.coastline.kafka.consumer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.KafkaException;
import org.apache.kafka.common.errors.ProducerFencedException;

import java.util.Properties;

/**
 * @author Jay.H.Zou
 * @date 2020/6/12
 */
public class OneKafkaConnection {

    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("client.id", "ProducerTranscationnalExample");
        props.put("bootstrap.servers", "localhost:9092");
        props.put("transactional.id", "test-transactional");
        props.put("acks", "all");
        KafkaProducer<String, String> producer = new KafkaProducer<>(props);
        producer.initTransactions();

        try {
            String msg = "matt test";
            producer.beginTransaction();
            producer.send(new ProducerRecord<>("topic", "0", msg));
            producer.send(new ProducerRecord<>("topic", "1", msg));
            producer.send(new ProducerRecord<>("topic", "2", msg));
            producer.commitTransaction();
        } catch (ProducerFencedException e1) {
            e1.printStackTrace();
            producer.close();
        } catch (KafkaException e2) {
            e2.printStackTrace();
            producer.abortTransaction();
        }
        producer.close();
    }

}
