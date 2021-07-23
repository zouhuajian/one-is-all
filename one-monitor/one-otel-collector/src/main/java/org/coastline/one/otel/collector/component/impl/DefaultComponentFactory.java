package org.coastline.one.otel.collector.component.impl;

import com.google.common.collect.Lists;
import org.coastline.one.otel.collector.component.ComponentFactory;
import org.coastline.one.otel.collector.config.CollectorConfig;
import org.coastline.one.otel.collector.exporter.impl.KafkaTraceExporter;
import org.coastline.one.otel.collector.model.TraceModel;
import org.coastline.one.otel.collector.processor.filter.DataFilter;
import org.coastline.one.otel.collector.processor.filter.impl.DefaultTraceFilter;
import org.coastline.one.otel.collector.processor.formatter.impl.DefaultTraceDataFormatter;
import org.coastline.one.otel.collector.queue.DataQueue;
import org.coastline.one.otel.collector.queue.impl.DefaultTraceQueue;
import org.coastline.one.otel.collector.receiver.impl.TraceReceiver;

import java.util.List;

/**
 * 构建整体处理流程
 *
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
        DefaultTraceDataFormatter formatter = DefaultTraceDataFormatter.create();
        List<DataFilter<TraceModel>> filters = Lists.newArrayList(DefaultTraceFilter.create());
        // queue
        DataQueue<TraceModel> dataQueue = DefaultTraceQueue.create();
        TraceReceiver.create(config.getTraceReceiverConfig(), formatter, filters, dataQueue);
        KafkaTraceExporter.create(config.getTraceExportersConfig(), dataQueue);
    }

    @Override
    public void buildMetricsComponents(CollectorConfig config) throws Exception {

    }
}
