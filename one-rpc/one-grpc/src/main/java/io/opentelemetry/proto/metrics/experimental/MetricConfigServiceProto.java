// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: opentelemetry/proto/metrics/experimental/metrics_config_service.proto

package io.opentelemetry.proto.metrics.experimental;

public final class MetricConfigServiceProto {
    private MetricConfigServiceProto() {
    }

    public static void registerAllExtensions(
            com.google.protobuf.ExtensionRegistryLite registry) {
    }

    public static void registerAllExtensions(
            com.google.protobuf.ExtensionRegistry registry) {
        registerAllExtensions(
                (com.google.protobuf.ExtensionRegistryLite) registry);
    }

    static final com.google.protobuf.Descriptors.Descriptor
            internal_static_opentelemetry_proto_metrics_experimental_MetricConfigRequest_descriptor;
    static final
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
            internal_static_opentelemetry_proto_metrics_experimental_MetricConfigRequest_fieldAccessorTable;
    static final com.google.protobuf.Descriptors.Descriptor
            internal_static_opentelemetry_proto_metrics_experimental_MetricConfigResponse_descriptor;
    static final
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
            internal_static_opentelemetry_proto_metrics_experimental_MetricConfigResponse_fieldAccessorTable;
    static final com.google.protobuf.Descriptors.Descriptor
            internal_static_opentelemetry_proto_metrics_experimental_MetricConfigResponse_Schedule_descriptor;
    static final
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
            internal_static_opentelemetry_proto_metrics_experimental_MetricConfigResponse_Schedule_fieldAccessorTable;
    static final com.google.protobuf.Descriptors.Descriptor
            internal_static_opentelemetry_proto_metrics_experimental_MetricConfigResponse_Schedule_Pattern_descriptor;
    static final
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
            internal_static_opentelemetry_proto_metrics_experimental_MetricConfigResponse_Schedule_Pattern_fieldAccessorTable;

    public static com.google.protobuf.Descriptors.FileDescriptor
    getDescriptor() {
        return descriptor;
    }

    private static com.google.protobuf.Descriptors.FileDescriptor
            descriptor;

    static {
        String[] descriptorData = {
                "\nEopentelemetry/proto/metrics/experiment" +
                        "al/metrics_config_service.proto\022(opentel" +
                        "emetry.proto.metrics.experimental\032.opent" +
                        "elemetry/proto/resource/v1/resource.prot" +
                        "o\"r\n\023MetricConfigRequest\022;\n\010resource\030\001 \001" +
                        "(\0132).opentelemetry.proto.resource.v1.Res" +
                        "ource\022\036\n\026last_known_fingerprint\030\002 \001(\014\"\340\003" +
                        "\n\024MetricConfigResponse\022\023\n\013fingerprint\030\001 " +
                        "\001(\014\022Z\n\tschedules\030\002 \003(\0132G.opentelemetry.p" +
                        "roto.metrics.experimental.MetricConfigRe" +
                        "sponse.Schedule\022\037\n\027suggested_wait_time_s" +
                        "ec\030\003 \001(\005\032\265\002\n\010Schedule\022k\n\022exclusion_patte" +
                        "rns\030\001 \003(\0132O.opentelemetry.proto.metrics." +
                        "experimental.MetricConfigResponse.Schedu" +
                        "le.Pattern\022k\n\022inclusion_patterns\030\002 \003(\0132O" +
                        ".opentelemetry.proto.metrics.experimenta" +
                        "l.MetricConfigResponse.Schedule.Pattern\022" +
                        "\022\n\nperiod_sec\030\003 \001(\005\032;\n\007Pattern\022\020\n\006equals" +
                        "\030\001 \001(\tH\000\022\025\n\013starts_with\030\002 \001(\tH\000B\007\n\005match" +
                        "2\241\001\n\014MetricConfig\022\220\001\n\017GetMetricConfig\022=." +
                        "opentelemetry.proto.metrics.experimental" +
                        ".MetricConfigRequest\032>.opentelemetry.pro" +
                        "to.metrics.experimental.MetricConfigResp" +
                        "onseB\224\001\n+io.opentelemetry.proto.metrics." +
                        "experimentalB\030MetricConfigServiceProtoP\001" +
                        "ZIgithub.com/open-telemetry/opentelemetr" +
                        "y-proto/gen/go/metrics/experimentalb\006pro" +
                        "to3"
        };
        descriptor = com.google.protobuf.Descriptors.FileDescriptor
                .internalBuildGeneratedFileFrom(descriptorData,
                        new com.google.protobuf.Descriptors.FileDescriptor[]{
                                io.opentelemetry.proto.resource.v1.ResourceProto.getDescriptor(),
                        });
        internal_static_opentelemetry_proto_metrics_experimental_MetricConfigRequest_descriptor =
                getDescriptor().getMessageTypes().get(0);
        internal_static_opentelemetry_proto_metrics_experimental_MetricConfigRequest_fieldAccessorTable = new
                com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
                internal_static_opentelemetry_proto_metrics_experimental_MetricConfigRequest_descriptor,
                new String[]{"Resource", "LastKnownFingerprint",});
        internal_static_opentelemetry_proto_metrics_experimental_MetricConfigResponse_descriptor =
                getDescriptor().getMessageTypes().get(1);
        internal_static_opentelemetry_proto_metrics_experimental_MetricConfigResponse_fieldAccessorTable = new
                com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
                internal_static_opentelemetry_proto_metrics_experimental_MetricConfigResponse_descriptor,
                new String[]{"Fingerprint", "Schedules", "SuggestedWaitTimeSec",});
        internal_static_opentelemetry_proto_metrics_experimental_MetricConfigResponse_Schedule_descriptor =
                internal_static_opentelemetry_proto_metrics_experimental_MetricConfigResponse_descriptor.getNestedTypes().get(0);
        internal_static_opentelemetry_proto_metrics_experimental_MetricConfigResponse_Schedule_fieldAccessorTable = new
                com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
                internal_static_opentelemetry_proto_metrics_experimental_MetricConfigResponse_Schedule_descriptor,
                new String[]{"ExclusionPatterns", "InclusionPatterns", "PeriodSec",});
        internal_static_opentelemetry_proto_metrics_experimental_MetricConfigResponse_Schedule_Pattern_descriptor =
                internal_static_opentelemetry_proto_metrics_experimental_MetricConfigResponse_Schedule_descriptor.getNestedTypes().get(0);
        internal_static_opentelemetry_proto_metrics_experimental_MetricConfigResponse_Schedule_Pattern_fieldAccessorTable = new
                com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
                internal_static_opentelemetry_proto_metrics_experimental_MetricConfigResponse_Schedule_Pattern_descriptor,
                new String[]{"Equals", "StartsWith", "Match",});
        io.opentelemetry.proto.resource.v1.ResourceProto.getDescriptor();
    }

    // @@protoc_insertion_point(outer_class_scope)
}
