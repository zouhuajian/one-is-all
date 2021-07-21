package org.coastline.one.otel.collector.model;


import com.google.common.collect.Lists;

import java.util.List;
import java.util.Map;

/**
 * @author Jay.H.Zou
 * @date 2021/7/21
 */
public class TraceModel {

    private TraceModel(){}

    public static TraceModel create() {
        return new TraceModel();
    }

    private Resource resource = new Resource();

    private InstrumentationLibrary instrumentationLibrary = new InstrumentationLibrary();

    private List<Span> spans = Lists.newArrayList();

    public Resource getResource() {
        return resource;
    }

    public TraceModel setResource(Resource resource) {
        this.resource = resource;
        return this;
    }

    public InstrumentationLibrary getInstrumentationLibrary() {
        return instrumentationLibrary;
    }

    public TraceModel setInstrumentationLibrary(InstrumentationLibrary instrumentationLibrary) {
        this.instrumentationLibrary = instrumentationLibrary;
        return this;
    }

    public List<Span> getSpans() {
        return spans;
    }

    public TraceModel setSpans(List<Span> spans) {
        this.spans = spans;
        return this;
    }

    /********************************* Resource AttributesList *********************************/
    public static class Resource {
        /**
         * service.name = one-spring-demo
         * service.zone = LOCAL
         */
        private Map<String, String> attributes;

        public Map<String, String> getAttributes() {
            return attributes;
        }

        public void setAttributes(Map<String, String> attributes) {
            this.attributes = attributes;
        }
    }

    /********************************* InstrumentationLibrary *********************************/
    public static class InstrumentationLibrary {
        private String name;

        private String version;

        public String getName() {
            return name;
        }

        public InstrumentationLibrary setName(String name) {
            this.name = name;
            return this;
        }

        public String getVersion() {
            return version;
        }

        public InstrumentationLibrary setVersion(String version) {
            this.version = version;
            return this;
        }
    }

    /********************************* Span *********************************/
    public static class Span {
        private String traceId;

        private String spanId;

        private String name;

        private String kind;

        private long startTimeNano;

        private long endTimeNano;

        private Map<String, String> attributes;

        private List<Event> events;

        private Status status;

        public String getTraceId() {
            return traceId;
        }

        public void setTraceId(String traceId) {
            this.traceId = traceId;
        }

        public String getSpanId() {
            return spanId;
        }

        public void setSpanId(String spanId) {
            this.spanId = spanId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getKind() {
            return kind;
        }

        public void setKind(String kind) {
            this.kind = kind;
        }

        public long getStartTimeNano() {
            return startTimeNano;
        }

        public void setStartTimeNano(long startTimeNano) {
            this.startTimeNano = startTimeNano;
        }

        public long getEndTimeNano() {
            return endTimeNano;
        }

        public void setEndTimeNano(long endTimeNano) {
            this.endTimeNano = endTimeNano;
        }

        public Map<String, String> getAttributes() {
            return attributes;
        }

        public void setAttributes(Map<String, String> attributes) {
            this.attributes = attributes;
        }

        public List<Event> getEvents() {
            return events;
        }

        public void setEvents(List<Event> events) {
            this.events = events;
        }

        public Status getStatus() {
            return status;
        }

        public void setStatus(Status status) {
            this.status = status;
        }
    }

    public static class Event {
        private long timeNano;

        private Map<String, String> attributes;

        public long getTimeNano() {
            return timeNano;
        }

        public void setTimeNano(long timeNano) {
            this.timeNano = timeNano;
        }

        public Map<String, String> getAttributes() {
            return attributes;
        }

        public void setAttributes(Map<String, String> attributes) {
            this.attributes = attributes;
        }
    }

    private static class Status {
        private String code;

        private String message;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

}
