package org.coastline.one.otel.collector.exporter.impl;

import io.opentelemetry.proto.trace.v1.ResourceSpans;
import org.coastline.one.otel.collector.config.ExporterConfig;
import org.coastline.one.otel.collector.exporter.DataExporter;
import org.coastline.one.otel.collector.queue.DataQueue;

import java.util.List;

/**
 * @author Jay.H.Zou
 * @date 2021/7/21
 */
public class TraceKafkaExporter implements DataExporter<ResourceSpans> {

    private ExporterConfig config;

    private DataQueue<ResourceSpans> dataQueue;

    public TraceKafkaExporter(ExporterConfig config, DataQueue<ResourceSpans> dataQueue) {
        this.config = config;
        this.dataQueue = dataQueue;
    }

    public static void create(ExporterConfig config, DataQueue<ResourceSpans> dataQueue) throws Exception {
        TraceKafkaExporter exporter = new TraceKafkaExporter(config, dataQueue);
        exporter.initialize();
    }

    @Override
    public void initialize() throws Exception {

    }

    @Override
    public void close() {

    }

    @Override
    public boolean export(ResourceSpans data) {
        return false;
    }

    @Override
    public boolean export(List<ResourceSpans> dataList) {
        return false;
    }
}
