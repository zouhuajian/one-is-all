package org.coastline.one.otel.collector.processor.formatter.impl;

import io.opentelemetry.proto.common.v1.InstrumentationLibrary;
import io.opentelemetry.proto.common.v1.KeyValue;
import io.opentelemetry.proto.resource.v1.Resource;
import io.opentelemetry.proto.trace.v1.InstrumentationLibrarySpans;
import io.opentelemetry.proto.trace.v1.ResourceSpans;
import io.opentelemetry.proto.trace.v1.Span;
import org.coastline.one.otel.collector.model.TraceModel;
import org.coastline.one.otel.collector.processor.formatter.DataFormatter;

import java.util.List;


/**
 * @author Jay.H.Zou
 * @date 2021/7/21
 */
public class DefaultTraceDataFormatter implements DataFormatter<ResourceSpans, TraceModel> {

    private DefaultTraceDataFormatter() {
    }

    public static DefaultTraceDataFormatter create() {
        return new DefaultTraceDataFormatter();
    }

    @Override
    public TraceModel format(ResourceSpans data) {


        Resource resource = data.getResource();
        List<KeyValue> attributesList = resource.getAttributesList();
        for (KeyValue keyValue : attributesList) {
            System.out.println("**** AttributesList KeyValue ****\n" + keyValue);
        }

        List<InstrumentationLibrarySpans> instrumentationLibrarySpansList = data.getInstrumentationLibrarySpansList();
        for (InstrumentationLibrarySpans instrumentationLibrarySpans : instrumentationLibrarySpansList) {
            InstrumentationLibrary instrumentationLibrary = instrumentationLibrarySpans.getInstrumentationLibrary();
            System.out.println("**** InstrumentationLibrary **** \n" + instrumentationLibrary);
            List<Span> spansList = instrumentationLibrarySpans.getSpansList();
            for (Span span : spansList) {
                System.out.println("**** Span ****\n" + span);
            }
        }

        System.out.println("**************************************************************");
        return TraceModel.create();
    }
}
