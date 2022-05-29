package org.coastline.one.okhttp;

import okhttp3.*;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.coastline.one.core.HttpClientTool;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Jay.H.Zou
 * @date 2022/5/29
 */
public class OneApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(OneApplication.class);

    // kafka
    private static final CountDownLatch countDownLatch = new CountDownLatch(1);
    private static final String KAFKA_BROKERS = "";
    private static final String KAFKA_GROUP_ID = "";
    private static final List<String> KAFKA_TOPICS = new ArrayList<String>() {{
        add("");
    }};

    // http
    public static final MediaType TEXT_PLAIN = MediaType.parse("text/plain;charset=utf-8");
    private static final String URL = "";


    private static final int CORE = Runtime.getRuntime().availableProcessors();
    private static final List<KafkaConsumer<byte[], byte[]>> CONSUMERS = new ArrayList<>();
    private static final ThreadPoolExecutor THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(4, 4, 2, TimeUnit.MINUTES, new SynchronousQueue<>());

    private static KafkaConsumer<byte[], byte[]> initKafkaClient() {
        Properties properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_BROKERS);
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, KAFKA_GROUP_ID);
        // serialize
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, ByteArrayDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ByteArrayDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.FETCH_MAX_BYTES_CONFIG, String.valueOf(256 * 1024 * 1024));
        properties.setProperty(ConsumerConfig.MAX_PARTITION_FETCH_BYTES_CONFIG, String.valueOf(256 * 1024 * 1024));
        properties.setProperty(ConsumerConfig.REQUEST_TIMEOUT_MS_CONFIG, "60000");
        properties.setProperty(ConsumerConfig.FETCH_MAX_WAIT_MS_CONFIG, "20000");
        properties.setProperty(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "60000");
        properties.setProperty(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG, "20000");
        KafkaConsumer<byte[], byte[]> consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(KAFKA_TOPICS, new ConsumerRebalanceListener() {
            @Override
            public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
                LOGGER.error("onPartitionsRevoked");
            }

            @Override
            public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
                LOGGER.error("onPartitionsAssigned");
            }
        });
        return consumer;
    }

    public static void main(String[] args) throws InterruptedException {
        initShutdownHook();
        for (int i = 0; i < CORE; i++) {
            THREAD_POOL_EXECUTOR.execute(new DataProcessor());
        }
        countDownLatch.await();
    }

    private static void initShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                THREAD_POOL_EXECUTOR.shutdown();
                for (KafkaConsumer<byte[], byte[]> consumer : CONSUMERS) {
                    consumer.close();
                }
            } catch (Exception e) {
                LOGGER.error("close error", e);
            }
            countDownLatch.countDown();
        }));
    }

    private static class DataProcessor implements Runnable {
        private final KafkaConsumer<byte[], byte[]> consumer;
        private final OkHttpClient httpClient;

        public DataProcessor() {
            consumer = initKafkaClient();
            httpClient = HttpClientTool.getClientDefault();
            CONSUMERS.add(consumer);
        }

        @Override
        public void run() {
            ConsumerRecords<byte[], byte[]> records = consumer.poll(Duration.ofSeconds(1));
            for (ConsumerRecord<byte[], byte[]> record : records) {
                Request request = new Request.Builder()
                        .url(URL)
                        .post(RequestBody.create(record.value(), TEXT_PLAIN))
                        .build();
                httpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        LOGGER.error("onFailure: write data into victoria error", e);
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        if (!response.isSuccessful()) {
                            LOGGER.error("onResponse: write data into victoria error, response = {}", response);
                        }
                        response.close();
                    }
                });
            }
        }
    }

}
