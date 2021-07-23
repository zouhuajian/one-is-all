package org.coastline.one.otel.collector.processor.filter.impl;

import org.coastline.one.otel.collector.model.TraceModel;
import org.coastline.one.otel.collector.processor.filter.DataFilter;

/**
 * @author Jay.H.Zou
 * @date 2021/7/21
 */
public class DefaultTraceFilter implements DataFilter<TraceModel> {

    private DefaultTraceFilter() {
    }

    public static DefaultTraceFilter create() {
        return new DefaultTraceFilter();
    }

    @Override
    public boolean filter(TraceModel data) {
        return true;
    }
}
