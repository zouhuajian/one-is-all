// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: opentelemetry/proto/logs/v1/logs.proto

package io.opentelemetry.proto.logs.v1;

public interface InstrumentationLibraryLogsOrBuilder extends
        // @@protoc_insertion_point(interface_extends:opentelemetry.proto.logs.v1.InstrumentationLibraryLogs)
        com.google.protobuf.MessageOrBuilder {

    /**
     * <pre>
     * The instrumentation library information for the logs in this message.
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
     * The instrumentation library information for the logs in this message.
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
     * The instrumentation library information for the logs in this message.
     * Semantically when InstrumentationLibrary isn't set, it is equivalent with
     * an empty instrumentation library name (unknown).
     * </pre>
     *
     * <code>.opentelemetry.proto.common.v1.InstrumentationLibrary instrumentation_library = 1;</code>
     */
    io.opentelemetry.proto.common.v1.InstrumentationLibraryOrBuilder getInstrumentationLibraryOrBuilder();

    /**
     * <pre>
     * A list of log records.
     * </pre>
     *
     * <code>repeated .opentelemetry.proto.logs.v1.LogRecord logs = 2;</code>
     */
    java.util.List<LogRecord>
    getLogsList();

    /**
     * <pre>
     * A list of log records.
     * </pre>
     *
     * <code>repeated .opentelemetry.proto.logs.v1.LogRecord logs = 2;</code>
     */
    LogRecord getLogs(int index);

    /**
     * <pre>
     * A list of log records.
     * </pre>
     *
     * <code>repeated .opentelemetry.proto.logs.v1.LogRecord logs = 2;</code>
     */
    int getLogsCount();

    /**
     * <pre>
     * A list of log records.
     * </pre>
     *
     * <code>repeated .opentelemetry.proto.logs.v1.LogRecord logs = 2;</code>
     */
    java.util.List<? extends LogRecordOrBuilder>
    getLogsOrBuilderList();

    /**
     * <pre>
     * A list of log records.
     * </pre>
     *
     * <code>repeated .opentelemetry.proto.logs.v1.LogRecord logs = 2;</code>
     */
    LogRecordOrBuilder getLogsOrBuilder(
            int index);

    /**
     * <pre>
     * This schema_url applies to all logs in the "logs" field.
     * </pre>
     *
     * <code>string schema_url = 3;</code>
     *
     * @return The schemaUrl.
     */
    String getSchemaUrl();

    /**
     * <pre>
     * This schema_url applies to all logs in the "logs" field.
     * </pre>
     *
     * <code>string schema_url = 3;</code>
     *
     * @return The bytes for schemaUrl.
     */
    com.google.protobuf.ByteString
    getSchemaUrlBytes();
}
