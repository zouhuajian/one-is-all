// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: opentelemetry/proto/metrics/v1/metrics.proto

package io.opentelemetry.proto.metrics.v1;

@Deprecated
public interface IntExemplarOrBuilder extends
        // @@protoc_insertion_point(interface_extends:opentelemetry.proto.metrics.v1.IntExemplar)
        com.google.protobuf.MessageOrBuilder {

    /**
     * <pre>
     * The set of labels that were filtered out by the aggregator, but recorded
     * alongside the original measurement. Only labels that were filtered out
     * by the aggregator should be included
     * </pre>
     *
     * <code>repeated .opentelemetry.proto.common.v1.StringKeyValue filtered_labels = 1;</code>
     */
    java.util.List<io.opentelemetry.proto.common.v1.StringKeyValue>
    getFilteredLabelsList();

    /**
     * <pre>
     * The set of labels that were filtered out by the aggregator, but recorded
     * alongside the original measurement. Only labels that were filtered out
     * by the aggregator should be included
     * </pre>
     *
     * <code>repeated .opentelemetry.proto.common.v1.StringKeyValue filtered_labels = 1;</code>
     */
    io.opentelemetry.proto.common.v1.StringKeyValue getFilteredLabels(int index);

    /**
     * <pre>
     * The set of labels that were filtered out by the aggregator, but recorded
     * alongside the original measurement. Only labels that were filtered out
     * by the aggregator should be included
     * </pre>
     *
     * <code>repeated .opentelemetry.proto.common.v1.StringKeyValue filtered_labels = 1;</code>
     */
    int getFilteredLabelsCount();

    /**
     * <pre>
     * The set of labels that were filtered out by the aggregator, but recorded
     * alongside the original measurement. Only labels that were filtered out
     * by the aggregator should be included
     * </pre>
     *
     * <code>repeated .opentelemetry.proto.common.v1.StringKeyValue filtered_labels = 1;</code>
     */
    java.util.List<? extends io.opentelemetry.proto.common.v1.StringKeyValueOrBuilder>
    getFilteredLabelsOrBuilderList();

    /**
     * <pre>
     * The set of labels that were filtered out by the aggregator, but recorded
     * alongside the original measurement. Only labels that were filtered out
     * by the aggregator should be included
     * </pre>
     *
     * <code>repeated .opentelemetry.proto.common.v1.StringKeyValue filtered_labels = 1;</code>
     */
    io.opentelemetry.proto.common.v1.StringKeyValueOrBuilder getFilteredLabelsOrBuilder(
            int index);

    /**
     * <pre>
     * time_unix_nano is the exact time when this exemplar was recorded
     * Value is UNIX Epoch time in nanoseconds since 00:00:00 UTC on 1 January
     * 1970.
     * </pre>
     *
     * <code>fixed64 time_unix_nano = 2;</code>
     *
     * @return The timeUnixNano.
     */
    long getTimeUnixNano();

    /**
     * <pre>
     * Numerical int value of the measurement that was recorded.
     * </pre>
     *
     * <code>sfixed64 value = 3;</code>
     *
     * @return The value.
     */
    long getValue();

    /**
     * <pre>
     * (Optional) Span ID of the exemplar trace.
     * span_id may be missing if the measurement is not recorded inside a trace
     * or if the trace is not sampled.
     * </pre>
     *
     * <code>bytes span_id = 4;</code>
     *
     * @return The spanId.
     */
    com.google.protobuf.ByteString getSpanId();

    /**
     * <pre>
     * (Optional) Trace ID of the exemplar trace.
     * trace_id may be missing if the measurement is not recorded inside a trace
     * or if the trace is not sampled.
     * </pre>
     *
     * <code>bytes trace_id = 5;</code>
     *
     * @return The traceId.
     */
    com.google.protobuf.ByteString getTraceId();
}
