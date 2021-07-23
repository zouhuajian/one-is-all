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
public class OldCollectorConfig {

    @Autowired
    private OldReceiverConfig traceOldReceiverConfig;

    @Autowired
    private OldReceiverConfig metricsOldReceiverConfig;

    @Autowired
    private OldExporterConfig traceOldExporterConfig;

    private OldQueueConfig traceDataQueue;

    @Bean(name = "traceReceiverConfig")
    @ConfigurationProperties(prefix = "receivers.otlp.trace")
    public OldReceiverConfig createTraceReceiverConfig() {
        return new OldReceiverConfig();
    }

    @Bean(name = "metricsReceiverConfig")
    @ConfigurationProperties(prefix = "receivers.otlp.metrics")
    public OldReceiverConfig createMetricsReceiverConfig() {
        return new OldReceiverConfig();
    }

    @Bean(name = "traceExporterConfig")
    @ConfigurationProperties(prefix = "exporters.kafka.trace")
    public OldExporterConfig createTraceExporterConfig() {
        return new OldExporterConfig();
    }



    public OldReceiverConfig getTraceReceiverConfig() {
        return traceOldReceiverConfig;
    }

    public void setTraceReceiverConfig(OldReceiverConfig traceOldReceiverConfig) {
        this.traceOldReceiverConfig = traceOldReceiverConfig;
    }

    public OldReceiverConfig getMetricsReceiverConfig() {
        return metricsOldReceiverConfig;
    }

    public void setMetricsReceiverConfig(OldReceiverConfig metricsOldReceiverConfig) {
        this.metricsOldReceiverConfig = metricsOldReceiverConfig;
    }

    public OldExporterConfig getTraceExportersConfig() {
        return traceOldExporterConfig;
    }

    public void setTraceExportersConfig(OldExporterConfig traceOldExporterConfig) {
        this.traceOldExporterConfig = traceOldExporterConfig;
    }

    public OldQueueConfig getTraceDataQueue() {
        return traceDataQueue;
    }

    public void setTraceDataQueue(OldQueueConfig traceDataQueue) {
        this.traceDataQueue = traceDataQueue;
    }
}
