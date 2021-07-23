package org.coastline.one.otel.collector.exporter.impl;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.google.gson.Gson;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.coastline.one.otel.collector.config.ExporterConfig;
import org.coastline.one.otel.collector.exporter.DataExporter;
import org.coastline.one.otel.collector.model.TraceModel;
import org.coastline.one.otel.collector.queue.DataQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Jay.H.Zou
 * @date 2021/7/21
 */
public class KafkaTraceExporter implements DataExporter<TraceModel> {

    private static final Logger logger = LoggerFactory.getLogger(KafkaTraceExporter.class);

    private ExporterConfig config;

    private DataQueue<TraceModel> dataQueue;

    private boolean flag = true;

    private KafkaProducer<byte[], byte[]> producer;

    private ExecutorService executorService = new ThreadPoolExecutor(1, 1,
            1, TimeUnit.HOURS,
            new SynchronousQueue<>(),
            new ThreadFactoryBuilder().setDaemon(true).setNameFormat("consume-queue-%d").build(),
            new ThreadPoolExecutor.CallerRunsPolicy());

    private KafkaTraceExporter(ExporterConfig config, DataQueue<TraceModel> dataQueue) {
        this.config = config;
        this.dataQueue = dataQueue;
    }

    public static KafkaTraceExporter create(ExporterConfig config, DataQueue<TraceModel> dataQueue) throws Exception {
        KafkaTraceExporter exporter = new KafkaTraceExporter(config, dataQueue);
        exporter.start();
        return exporter;
    }

    @Override
    public void initialize() throws Exception {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, config.getBrokers());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, ByteArraySerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ByteArraySerializer.class.getName());
        producer = new KafkaProducer<>(props);
        executorService.submit(new PollData());
    }

    @Override
    public void close() {
        this.flag = false;
        this.executorService.shutdown();
    }

    @Override
    public boolean export(TraceModel data) {
        Gson gson = new Gson();
        String dataJson = gson.toJson(data);
        ProducerRecord<byte[], byte[]> record = new ProducerRecord<>(config.getTopic(), dataJson.getBytes(StandardCharsets.UTF_8));
        producer.send(record);
        logger.info(gson.toJson(data));
        return true;
    }

    @Override
    public boolean export(List<TraceModel> dataList) {
        return false;
    }

    class PollData implements Runnable {

        @Override
        public void run() {
            while (flag) {
                try {
                    TraceModel traceModel = dataQueue.take();
                    export(traceModel);
                } catch (Exception e) {
                    logger.error("take data from queue error", e);
                }
            }
        }
    }
}
