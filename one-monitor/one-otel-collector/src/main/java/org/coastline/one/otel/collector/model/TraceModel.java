package org.coastline.one.otel.collector.model;



import com.google.common.collect.Lists;

import java.util.List;
import java.util.Map;

/**
 * @author Jay.H.Zou
 * @date 2021/7/21
 */
public class TraceModel {

    private TraceModel() {
    }

    public static TraceModel create() {
        return new TraceModel();
    }

    private ResourceData resourceData =  ResourceData.create();

    private List<InstrumentationLibrarySpansData> instrumentationLibrarySpansDataList = Lists.newArrayList();

    public ResourceData getResourceData() {
        return resourceData;
    }

    public TraceModel setResourceData(ResourceData resourceData) {
        this.resourceData = resourceData;
        return this;
    }

    public List<InstrumentationLibrarySpansData> getInstrumentationLibrarySpansDataList() {
        return instrumentationLibrarySpansDataList;
    }

    public TraceModel setInstrumentationLibrarySpansDataList(List<InstrumentationLibrarySpansData> instrumentationLibrarySpansDataList) {
        this.instrumentationLibrarySpansDataList = instrumentationLibrarySpansDataList;
        return this;
    }

    /********************************* Resource AttributesList & InstrumentationLibrary *********************************/
    public static class ResourceData {
        private ResourceData(){}

        public static ResourceData create() {
            return new ResourceData();
        }

        /**
         * service.name = one-spring-demo
         * service.zone = LOCAL
         */
        private Map<String, String> attributesMap;

        public Map<String, String> getAttributesMap() {
            return attributesMap;
        }

        public ResourceData setAttributesMap(Map<String, String> attributesMap) {
            this.attributesMap = attributesMap;
            return this;
        }
    }

    public static class InstrumentationLibrarySpansData {
        private InstrumentationLibrarySpansData() {}

        public static InstrumentationLibrarySpansData create() {
            return new InstrumentationLibrarySpansData();
        }

        private InstrumentationLibraryData instrumentationLibraryData;

        private List<SpanData> spanDataList;

        public InstrumentationLibraryData getInstrumentationLibraryData() {
            return instrumentationLibraryData;
        }

        public InstrumentationLibrarySpansData setInstrumentationLibraryData(InstrumentationLibraryData instrumentationLibraryData) {
            this.instrumentationLibraryData = instrumentationLibraryData;
            return this;
        }

        public List<SpanData> getSpanDataList() {
            return spanDataList;
        }

        public InstrumentationLibrarySpansData setSpanDataList(List<SpanData> spanDataList) {
            this.spanDataList = spanDataList;
            return this;
        }
    }

    public static class InstrumentationLibraryData {

        private InstrumentationLibraryData() {}

        public static InstrumentationLibraryData create() {
            return new InstrumentationLibraryData();
        }

        private String name;

        private String version;

        public String getName() {
            return name;
        }

        public InstrumentationLibraryData setName(String name) {
            this.name = name;
            return this;
        }

        public String getVersion() {
            return version;
        }

        public InstrumentationLibraryData setVersion(String version) {
            this.version = version;
            return this;
        }
    }

    /********************************* Span *********************************/
    public static class SpanData {

        private SpanData() {}

        public static SpanData create() {
            return new SpanData();
        }
        private String traceId;

        private String spanId;

        private String name;

        private String kind;

        private long startTimeNano;

        private long endTimeNano;

        private Map<String, String> attributesMap;

        private List<EventData> eventDataList;

        private List<LinkData> linkDataList;

        private String status;

        public String getTraceId() {
            return traceId;
        }

        public SpanData setTraceId(String traceId) {
            this.traceId = traceId;
            return this;
        }

        public String getSpanId() {
            return spanId;
        }

        public SpanData setSpanId(String spanId) {
            this.spanId = spanId;
            return this;
        }

        public String getName() {
            return name;
        }

        public SpanData setName(String name) {
            this.name = name;
            return this;
        }

        public String getKind() {
            return kind;
        }

        public SpanData setKind(String kind) {
            this.kind = kind;
            return this;
        }

        public long getStartTimeNano() {
            return startTimeNano;
        }

        public SpanData setStartTimeNano(long startTimeNano) {
            this.startTimeNano = startTimeNano;
            return this;
        }

        public long getEndTimeNano() {
            return endTimeNano;
        }

        public SpanData setEndTimeNano(long endTimeNano) {
            this.endTimeNano = endTimeNano;
            return this;
        }

        public Map<String, String> getAttributesMap() {
            return attributesMap;
        }

        public SpanData setAttributesMap(Map<String, String> attributesMap) {
            this.attributesMap = attributesMap;
            return this;
        }

        public List<EventData> getEventDataList() {
            return eventDataList;
        }

        public SpanData setEventDataList(List<EventData> eventDataList) {
            this.eventDataList = eventDataList;
            return this;
        }

        public List<LinkData> getLinkDataList() {
            return linkDataList;
        }

        public SpanData setLinkDataList(List<LinkData> linkDataList) {
            this.linkDataList = linkDataList;
            return this;
        }

        public String getStatus() {
            return status;
        }

        public SpanData setStatus(String status) {
            this.status = status;
            return this;
        }
    }

    public static class EventData {

        private EventData() {}

        public static EventData create() {
            return new EventData();
        }

        private long timeNano;

        private Map<String, String> attributesMap;

        public long getTimeNano() {
            return timeNano;
        }

        public EventData setTimeNano(long timeNano) {
            this.timeNano = timeNano;
            return this;
        }

        public Map<String, String> getAttributesMap() {
            return attributesMap;
        }

        public EventData setAttributesMap(Map<String, String> attributesMap) {
            this.attributesMap = attributesMap;
            return this;
        }
    }

    public static class LinkData {

        private LinkData() {}

        public static LinkData create() {
            return new LinkData();
        }

        private String traceId;

        private String spanId;

        private Map<String, String> attributesMap;

        private String status;

        public String getTraceId() {
            return traceId;
        }

        public LinkData setTraceId(String traceId) {
            this.traceId = traceId;
            return this;
        }

        public String getSpanId() {
            return spanId;
        }

        public LinkData setSpanId(String spanId) {
            this.spanId = spanId;
            return this;
        }

        public Map<String, String> getAttributesMap() {
            return attributesMap;
        }

        public void setAttributesMap(Map<String, String> attributesMap) {
            this.attributesMap = attributesMap;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
