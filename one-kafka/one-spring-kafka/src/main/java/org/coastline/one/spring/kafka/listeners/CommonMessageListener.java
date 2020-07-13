package org.coastline.one.spring.kafka.listeners;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @author Jay.H.Zou
 * @date 2020/7/13
 */
@Component
public class CommonMessageListener {

    @KafkaListener(topics = {""})
    public void listenerCommonMessage() {

    }

}
