package org.coastline.one.otel.collector.processor.formatter.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.protobuf.ByteString;
import io.opentelemetry.api.internal.OtelEncodingUtils;
import io.opentelemetry.api.trace.SpanId;
import io.opentelemetry.api.trace.TraceId;
import io.opentelemetry.proto.common.v1.AnyValue;
import io.opentelemetry.proto.common.v1.InstrumentationLibrary;
import io.opentelemetry.proto.common.v1.KeyValue;
import io.opentelemetry.proto.trace.v1.InstrumentationLibrarySpans;
import io.opentelemetry.proto.trace.v1.ResourceSpans;
import io.opentelemetry.proto.trace.v1.Span;
import org.coastline.one.otel.collector.model.TraceModel;
import org.coastline.one.otel.collector.processor.formatter.DataFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * @author Jay.H.Zou
 * @date 2021/7/21
 */
public class DefaultTraceDataFormatter implements DataFormatter<ResourceSpans, TraceModel> {

    private static final Logger logger = LoggerFactory.getLogger(DefaultTraceDataFormatter.class);

    private DefaultTraceDataFormatter() {
    }

    public static DefaultTraceDataFormatter create() {
        return new DefaultTraceDataFormatter();
    }

    @Override
    public TraceModel format(ResourceSpans data) {
        TraceModel traceModel = TraceModel.create();
        // resource attributesList process
        traceModel.getResourceData().setAttributesMap(keyValuesToMap(data.getResource().getAttributesList()));
        List<TraceModel.InstrumentationLibrarySpansData> instrumentationLibrarySpansDataList = traceModel.getInstrumentationLibrarySpansDataList();

        List<InstrumentationLibrarySpans> instrumentationLibrarySpansList = data.getInstrumentationLibrarySpansList();
        for (InstrumentationLibrarySpans instrumentationLibrarySpans : instrumentationLibrarySpansList) {
            InstrumentationLibrary instrumentationLibrary = instrumentationLibrarySpans.getInstrumentationLibrary();
            List<Span> spansList = instrumentationLibrarySpans.getSpansList();

            TraceModel.InstrumentationLibraryData instrumentationLibraryData = TraceModel.InstrumentationLibraryData.create()
                    .setName(instrumentationLibrary.getName())
                    .setVersion(instrumentationLibrary.getVersion());
            List<TraceModel.SpanData> spanDataList = buildSpanList(spansList);

            TraceModel.InstrumentationLibrarySpansData instrumentationLibrarySpansData =
                    TraceModel.InstrumentationLibrarySpansData.create()
                            .setInstrumentationLibraryData(instrumentationLibraryData)
                            .setSpanDataList(spanDataList);
            instrumentationLibrarySpansDataList.add(instrumentationLibrarySpansData);
        }
        Gson gson = new Gson();
        String traceModelJson = gson.toJson(traceModel);
        // logger.info(traceModelJson);
        // logger.info("**************************************************************");
        return traceModel;
    }

    private Map<String, String> keyValuesToMap(List<KeyValue> attributesList) {
        Map<String, String> attributesMap = Maps.newHashMap();
        for (KeyValue keyValue : attributesList) {
            AnyValue value = keyValue.getValue();
            String stringValue = value.getStringValue();
            attributesMap.put(keyValue.getKey(), stringValue);
        }
        return attributesMap;
    }

    private List<TraceModel.SpanData> buildSpanList(List<Span> spansList) {
        List<TraceModel.SpanData> spanDataList = Lists.newArrayList();
        for (Span span : spansList) {
            TraceModel.SpanData spanData = TraceModel.SpanData.create()
                    .setTraceId(decodeTraceId(span.getTraceId()))
                    .setSpanId(decodeSpanId(span.getSpanId()))
                    .setName(span.getName())
                    .setKind(span.getKind().name())
                    .setStartTimeNano(span.getStartTimeUnixNano())
                    .setEndTimeNano(span.getEndTimeUnixNano())
                    .setStatus(span.getStatus().getCode().name());
            // process span attributes
            spanData.setAttributesMap(keyValuesToMap(span.getAttributesList()));

            // process events
            List<Span.Event> eventsList = span.getEventsList();
            ArrayList<TraceModel.EventData> eventDataList = Lists.newArrayList();
            for (Span.Event event : eventsList) {
                TraceModel.EventData eventData = TraceModel.EventData.create()
                        .setTimeNano(event.getTimeUnixNano())
                        .setAttributesMap(keyValuesToMap(event.getAttributesList()));
                eventDataList.add(eventData);
            }
            spanData.setEventDataList(eventDataList);
            List<Span.Link> linksList = span.getLinksList();
            ArrayList<TraceModel.LinkData> linkList = Lists.newArrayList();
            for (Span.Link link : linksList) {
                TraceModel.LinkData.create()
                        .setTraceId(decodeTraceId(span.getTraceId()))
                        .setSpanId(decodeSpanId(span.getSpanId()))
                        .setAttributesMap(keyValuesToMap(link.getAttributesList()));
            }
            spanData.setAttributesMap(keyValuesToMap(span.getAttributesList()))
                    .setEventDataList(eventDataList)
                    .setLinkDataList(linkList);
            spanDataList.add(spanData);
        }
        return spanDataList;
    }

    private String decodeTraceId(ByteString byteString) {
        byte[] bytes = byteString.toByteArray();
        char[] chars = new char[TraceId.getLength()];
        OtelEncodingUtils.bytesToBase16(bytes, chars, bytes.length);
        return new String(chars);
    }

    private String decodeSpanId(ByteString byteString) {
        byte[] bytes = byteString.toByteArray();
        char[] chars = new char[SpanId.getLength()];
        OtelEncodingUtils.bytesToBase16(bytes, chars, bytes.length);
        return new String(chars);
    }

}
