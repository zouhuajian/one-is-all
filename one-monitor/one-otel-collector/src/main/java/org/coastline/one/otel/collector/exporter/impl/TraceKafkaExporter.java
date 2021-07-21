package org.coastline.one.otel.collector.exporter.impl;

import io.opentelemetry.proto.trace.v1.ResourceSpans;
import org.coastline.one.otel.collector.config.ExporterConfig;
import org.coastline.one.otel.collector.exporter.DataExporter;
import org.coastline.one.otel.collector.model.TraceModel;
import org.coastline.one.otel.collector.queue.DataQueue;

import java.util.List;

/**
 * @author Jay.H.Zou
 * @date 2021/7/21
 */
public class TraceKafkaExporter implements DataExporter<TraceModel> {

    private ExporterConfig config;

    private DataQueue<TraceModel> dataQueue;

    private TraceKafkaExporter(ExporterConfig config, DataQueue<TraceModel> dataQueue) {
        this.config = config;
        this.dataQueue = dataQueue;
    }

    public static TraceKafkaExporter create(ExporterConfig config, DataQueue<TraceModel> dataQueue) throws Exception {
        TraceKafkaExporter exporter = new TraceKafkaExporter(config, dataQueue);
        exporter.initialize();
        return exporter;
    }

    @Override
    public void initialize() throws Exception {

    }

    @Override
    public void close() {

    }

    @Override
    public boolean export(TraceModel data) {
        return false;
    }

    @Override
    public boolean export(List<TraceModel> dataList) {
        return false;
    }
}
