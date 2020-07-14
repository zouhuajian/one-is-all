package org.coastline.one.spring.kafka.listeners;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @author Jay.H.Zou
 * @date 2020/7/13
 */
@Component
public class CommonMessageListener {

    @KafkaListener(id = "common-consumer", topics = {""}, concurrency = "1", clientIdPrefix = "my-consumer")
    public void listenerCommonMessage(ConsumerRecord<String, String> record) {

    }

}
