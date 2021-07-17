// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: opentelemetry/proto/trace/v1/trace.proto

package io.opentelemetry.proto.trace.v1;

public interface InstrumentationLibrarySpansOrBuilder extends
        // @@protoc_insertion_point(interface_extends:opentelemetry.proto.trace.v1.InstrumentationLibrarySpans)
        com.google.protobuf.MessageOrBuilder {

    /**
     * <pre>
     * The instrumentation library information for the spans in this message.
     * Semantically when InstrumentationLibrary isn't set, it is equivalent with
     * an empty instrumentation library name (unknown).
     * </pre>
     *
     * <code>.opentelemetry.proto.common.v1.InstrumentationLibrary instrumentation_library = 1;</code>
     *
     * @return Whether the instrumentationLibrary field is set.
     */
    boolean hasInstrumentationLibrary();

    /**
     * <pre>
     * The instrumentation library information for the spans in this message.
     * Semantically when InstrumentationLibrary isn't set, it is equivalent with
     * an empty instrumentation library name (unknown).
     * </pre>
     *
     * <code>.opentelemetry.proto.common.v1.InstrumentationLibrary instrumentation_library = 1;</code>
     *
     * @return The instrumentationLibrary.
     */
    io.opentelemetry.proto.common.v1.InstrumentationLibrary getInstrumentationLibrary();

    /**
     * <pre>
     * The instrumentation library information for the spans in this message.
     * Semantically when InstrumentationLibrary isn't set, it is equivalent with
     * an empty instrumentation library name (unknown).
     * </pre>
     *
     * <code>.opentelemetry.proto.common.v1.InstrumentationLibrary instrumentation_library = 1;</code>
     */
    io.opentelemetry.proto.common.v1.InstrumentationLibraryOrBuilder getInstrumentationLibraryOrBuilder();

    /**
     * <pre>
     * A list of Spans that originate from an instrumentation library.
     * </pre>
     *
     * <code>repeated .opentelemetry.proto.trace.v1.Span spans = 2;</code>
     */
    java.util.List<Span>
    getSpansList();

    /**
     * <pre>
     * A list of Spans that originate from an instrumentation library.
     * </pre>
     *
     * <code>repeated .opentelemetry.proto.trace.v1.Span spans = 2;</code>
     */
    Span getSpans(int index);

    /**
     * <pre>
     * A list of Spans that originate from an instrumentation library.
     * </pre>
     *
     * <code>repeated .opentelemetry.proto.trace.v1.Span spans = 2;</code>
     */
    int getSpansCount();

    /**
     * <pre>
     * A list of Spans that originate from an instrumentation library.
     * </pre>
     *
     * <code>repeated .opentelemetry.proto.trace.v1.Span spans = 2;</code>
     */
    java.util.List<? extends SpanOrBuilder>
    getSpansOrBuilderList();

    /**
     * <pre>
     * A list of Spans that originate from an instrumentation library.
     * </pre>
     *
     * <code>repeated .opentelemetry.proto.trace.v1.Span spans = 2;</code>
     */
    SpanOrBuilder getSpansOrBuilder(
            int index);

    /**
     * <pre>
     * This schema_url applies to all spans and span events in the "spans" field.
     * </pre>
     *
     * <code>string schema_url = 3;</code>
     *
     * @return The schemaUrl.
     */
    String getSchemaUrl();

    /**
     * <pre>
     * This schema_url applies to all spans and span events in the "spans" field.
     * </pre>
     *
     * <code>string schema_url = 3;</code>
     *
     * @return The bytes for schemaUrl.
     */
    com.google.protobuf.ByteString
    getSchemaUrlBytes();
}
