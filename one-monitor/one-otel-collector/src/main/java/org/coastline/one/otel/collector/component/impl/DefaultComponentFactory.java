package org.coastline.one.otel.collector.component.impl;

import com.google.common.collect.Lists;
import io.opentelemetry.proto.trace.v1.ResourceSpans;
import org.coastline.one.otel.collector.component.ComponentFactory;
import org.coastline.one.otel.collector.config.CollectorConfig;
import org.coastline.one.otel.collector.exporter.impl.TraceKafkaExporter;
import org.coastline.one.otel.collector.processor.DataProcessor;
import org.coastline.one.otel.collector.processor.filter.impl.DefaultTraceFilter;
import org.coastline.one.otel.collector.processor.format.impl.DefaultTraceDataFormat;
import org.coastline.one.otel.collector.queue.DataQueue;
import org.coastline.one.otel.collector.queue.impl.MemTraceQueue;
import org.coastline.one.otel.collector.receiver.impl.TraceReceiver;

import java.util.List;

/**
 * @author Jay.H.Zou
 * @date 2021/7/21
 */
public class DefaultComponentFactory implements ComponentFactory {

    public static final DefaultComponentFactory DEFAULT_INSTANT = new DefaultComponentFactory();

    public static void createTraceComponents(CollectorConfig config) throws Exception {
        DEFAULT_INSTANT.buildTraceComponents(config);
    }

    public static void createMetricsComponents(CollectorConfig config) throws Exception {
        DEFAULT_INSTANT.buildMetricsComponents(config);
    }

    @Override
    public void buildTraceComponents(CollectorConfig config) throws Exception {

        // processors
        List<DataProcessor<ResourceSpans>> processors = Lists.newArrayList();
        processors.add(new DefaultTraceFilter());
        processors.add(new DefaultTraceDataFormat());
        // queue
        DataQueue<ResourceSpans> dataQueue = new MemTraceQueue();
        TraceReceiver.create(config.getTraceReceiverConfig(), processors, dataQueue);
        TraceKafkaExporter.create(config.getTraceExportersConfig(), dataQueue);
    }

    @Override
    public void buildMetricsComponents(CollectorConfig config) throws Exception {

    }

    @Override
    public void buildExtensionComponents(CollectorConfig config) throws Exception {

    }
}
