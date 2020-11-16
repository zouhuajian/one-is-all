package org.coastline.one.kafka.stream;

import org.coastline.one.spring.kafka.stream.KafkaWindowProcess;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * @author zouhuajian
 * @date 2020/11/16
 */
public class StreamAggregateTest {

    @Test
    public void testWindow() throws InterruptedException {
        KafkaWindowProcess kafkaWindowProcess = new KafkaWindowProcess();
        kafkaWindowProcess.createStream(null);
        kafkaWindowProcess.start();
        TimeUnit.MINUTES.sleep(10);
        kafkaWindowProcess.stop();
    }
}
