package org.coastline.one.otel.collector.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * collector base config
 *
 * @author Jay.H.Zou
 * @date 2021/7/18
 */
@Configuration
public class CollectorConfig {

    @Autowired
    private ReceiverConfig traceReceiverConfig;

    @Autowired
    private ReceiverConfig metricsReceiverConfig;

    @Autowired
    private ExporterConfig traceExporterConfig;

    private QueueConfig traceDataQueue;

    @Bean(name = "traceReceiverConfig")
    @ConfigurationProperties(prefix = "receivers.otlp.trace")
    public ReceiverConfig createTraceReceiverConfig() {
        return new ReceiverConfig();
    }

    @Bean(name = "metricsReceiverConfig")
    @ConfigurationProperties(prefix = "receivers.otlp.metrics")
    public ReceiverConfig createMetricsReceiverConfig() {
        return new ReceiverConfig();
    }

    @Bean(name = "traceExporterConfig")
    @ConfigurationProperties(prefix = "receivers.kafka.trace")
    public ExporterConfig createTraceExporterConfig() {
        return new ExporterConfig();
    }



    public ReceiverConfig getTraceReceiverConfig() {
        return traceReceiverConfig;
    }

    public void setTraceReceiverConfig(ReceiverConfig traceReceiverConfig) {
        this.traceReceiverConfig = traceReceiverConfig;
    }

    public ReceiverConfig getMetricsReceiverConfig() {
        return metricsReceiverConfig;
    }

    public void setMetricsReceiverConfig(ReceiverConfig metricsReceiverConfig) {
        this.metricsReceiverConfig = metricsReceiverConfig;
    }

    public ExporterConfig getTraceExportersConfig() {
        return traceExporterConfig;
    }

    public void setTraceExportersConfig(ExporterConfig traceExporterConfig) {
        this.traceExporterConfig = traceExporterConfig;
    }

    public QueueConfig getTraceDataQueue() {
        return traceDataQueue;
    }

    public void setTraceDataQueue(QueueConfig traceDataQueue) {
        this.traceDataQueue = traceDataQueue;
    }
}
