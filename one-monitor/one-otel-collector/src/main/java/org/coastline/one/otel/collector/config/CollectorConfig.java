package org.coastline.one.otel.collector.config;

import java.util.List;

/**
 * collector base config
 *
 * @author Jay.H.Zou
 * @date 2021/7/18
 */
public class CollectorConfig {

    private ReceiverConfig traceReceiverConfig;

    private QueueConfig queueConfig;

    private KafkaExporterConfig traceKafkaExporterConfig;

    public ReceiverConfig getTraceReceiverConfig() {
        return traceReceiverConfig;
    }

    public void setTraceReceiverConfig(ReceiverConfig traceReceiverConfig) {
        this.traceReceiverConfig = traceReceiverConfig;
    }

    public QueueConfig getQueueConfig() {
        return queueConfig;
    }

    public void setQueueConfig(QueueConfig queueConfig) {
        this.queueConfig = queueConfig;
    }

    public KafkaExporterConfig getTraceKafkaExporterConfig() {
        return traceKafkaExporterConfig;
    }

    public void setTraceKafkaExporterConfig(KafkaExporterConfig traceKafkaExporterConfig) {
        this.traceKafkaExporterConfig = traceKafkaExporterConfig;
    }
}
