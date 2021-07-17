// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: opentelemetry/proto/metrics/v1/metrics.proto

package io.opentelemetry.proto.metrics.v1;

/**
 * <pre>
 * Summary metric data are used to convey quantile summaries,
 * a Prometheus (see: https://prometheus.io/docs/concepts/metric_types/#summary)
 * and OpenMetrics (see: https://github.com/OpenObservability/OpenMetrics/blob/4dbf6075567ab43296eed941037c12951faafb92/protos/prometheus.proto#L45)
 * data type. These data points cannot always be merged in a meaningful way.
 * While they can be useful in some applications, histogram data points are
 * recommended for new applications.
 * </pre>
 * <p>
 * Protobuf type {@code opentelemetry.proto.metrics.v1.Summary}
 */
public final class Summary extends
        com.google.protobuf.GeneratedMessageV3 implements
        // @@protoc_insertion_point(message_implements:opentelemetry.proto.metrics.v1.Summary)
        SummaryOrBuilder {
    private static final long serialVersionUID = 0L;

    // Use Summary.newBuilder() to construct.
    private Summary(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
        super(builder);
    }

    private Summary() {
        dataPoints_ = java.util.Collections.emptyList();
    }

    @Override
    @SuppressWarnings({"unused"})
    protected Object newInstance(
            UnusedPrivateParameter unused) {
        return new Summary();
    }

    @Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
        return this.unknownFields;
    }

    private Summary(
            com.google.protobuf.CodedInputStream input,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
        this();
        if (extensionRegistry == null) {
            throw new NullPointerException();
        }
        int mutable_bitField0_ = 0;
        com.google.protobuf.UnknownFieldSet.Builder unknownFields =
                com.google.protobuf.UnknownFieldSet.newBuilder();
        try {
            boolean done = false;
            while (!done) {
                int tag = input.readTag();
                switch (tag) {
                    case 0:
                        done = true;
                        break;
                    case 10: {
                        if (!((mutable_bitField0_ & 0x00000001) != 0)) {
                            dataPoints_ = new java.util.ArrayList<SummaryDataPoint>();
                            mutable_bitField0_ |= 0x00000001;
                        }
                        dataPoints_.add(
                                input.readMessage(SummaryDataPoint.parser(), extensionRegistry));
                        break;
                    }
                    default: {
                        if (!parseUnknownField(
                                input, unknownFields, extensionRegistry, tag)) {
                            done = true;
                        }
                        break;
                    }
                }
            }
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
            throw e.setUnfinishedMessage(this);
        } catch (java.io.IOException e) {
            throw new com.google.protobuf.InvalidProtocolBufferException(
                    e).setUnfinishedMessage(this);
        } finally {
            if (((mutable_bitField0_ & 0x00000001) != 0)) {
                dataPoints_ = java.util.Collections.unmodifiableList(dataPoints_);
            }
            this.unknownFields = unknownFields.build();
            makeExtensionsImmutable();
        }
    }

    public static final com.google.protobuf.Descriptors.Descriptor
    getDescriptor() {
        return MetricsProto.internal_static_opentelemetry_proto_metrics_v1_Summary_descriptor;
    }

    @Override
    protected FieldAccessorTable
    internalGetFieldAccessorTable() {
        return MetricsProto.internal_static_opentelemetry_proto_metrics_v1_Summary_fieldAccessorTable
                .ensureFieldAccessorsInitialized(
                        Summary.class, Builder.class);
    }

    public static final int DATA_POINTS_FIELD_NUMBER = 1;
    private java.util.List<SummaryDataPoint> dataPoints_;

    /**
     * <code>repeated .opentelemetry.proto.metrics.v1.SummaryDataPoint data_points = 1;</code>
     */
    @Override
    public java.util.List<SummaryDataPoint> getDataPointsList() {
        return dataPoints_;
    }

    /**
     * <code>repeated .opentelemetry.proto.metrics.v1.SummaryDataPoint data_points = 1;</code>
     */
    @Override
    public java.util.List<? extends SummaryDataPointOrBuilder>
    getDataPointsOrBuilderList() {
        return dataPoints_;
    }

    /**
     * <code>repeated .opentelemetry.proto.metrics.v1.SummaryDataPoint data_points = 1;</code>
     */
    @Override
    public int getDataPointsCount() {
        return dataPoints_.size();
    }

    /**
     * <code>repeated .opentelemetry.proto.metrics.v1.SummaryDataPoint data_points = 1;</code>
     */
    @Override
    public SummaryDataPoint getDataPoints(int index) {
        return dataPoints_.get(index);
    }

    /**
     * <code>repeated .opentelemetry.proto.metrics.v1.SummaryDataPoint data_points = 1;</code>
     */
    @Override
    public SummaryDataPointOrBuilder getDataPointsOrBuilder(
            int index) {
        return dataPoints_.get(index);
    }

    private byte memoizedIsInitialized = -1;

    @Override
    public final boolean isInitialized() {
        byte isInitialized = memoizedIsInitialized;
        if (isInitialized == 1) return true;
        if (isInitialized == 0) return false;

        memoizedIsInitialized = 1;
        return true;
    }

    @Override
    public void writeTo(com.google.protobuf.CodedOutputStream output)
            throws java.io.IOException {
        for (int i = 0; i < dataPoints_.size(); i++) {
            output.writeMessage(1, dataPoints_.get(i));
        }
        unknownFields.writeTo(output);
    }

    @Override
    public int getSerializedSize() {
        int size = memoizedSize;
        if (size != -1) return size;

        size = 0;
        for (int i = 0; i < dataPoints_.size(); i++) {
            size += com.google.protobuf.CodedOutputStream
                    .computeMessageSize(1, dataPoints_.get(i));
        }
        size += unknownFields.getSerializedSize();
        memoizedSize = size;
        return size;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Summary)) {
            return super.equals(obj);
        }
        Summary other = (Summary) obj;

        if (!getDataPointsList()
                .equals(other.getDataPointsList())) return false;
        if (!unknownFields.equals(other.unknownFields)) return false;
        return true;
    }

    @Override
    public int hashCode() {
        if (memoizedHashCode != 0) {
            return memoizedHashCode;
        }
        int hash = 41;
        hash = (19 * hash) + getDescriptor().hashCode();
        if (getDataPointsCount() > 0) {
            hash = (37 * hash) + DATA_POINTS_FIELD_NUMBER;
            hash = (53 * hash) + getDataPointsList().hashCode();
        }
        hash = (29 * hash) + unknownFields.hashCode();
        memoizedHashCode = hash;
        return hash;
    }

    public static Summary parseFrom(
            java.nio.ByteBuffer data)
            throws com.google.protobuf.InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static Summary parseFrom(
            java.nio.ByteBuffer data,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static Summary parseFrom(
            com.google.protobuf.ByteString data)
            throws com.google.protobuf.InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static Summary parseFrom(
            com.google.protobuf.ByteString data,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static Summary parseFrom(byte[] data)
            throws com.google.protobuf.InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static Summary parseFrom(
            byte[] data,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static Summary parseFrom(java.io.InputStream input)
            throws java.io.IOException {
        return com.google.protobuf.GeneratedMessageV3
                .parseWithIOException(PARSER, input);
    }

    public static Summary parseFrom(
            java.io.InputStream input,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws java.io.IOException {
        return com.google.protobuf.GeneratedMessageV3
                .parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static Summary parseDelimitedFrom(java.io.InputStream input)
            throws java.io.IOException {
        return com.google.protobuf.GeneratedMessageV3
                .parseDelimitedWithIOException(PARSER, input);
    }

    public static Summary parseDelimitedFrom(
            java.io.InputStream input,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws java.io.IOException {
        return com.google.protobuf.GeneratedMessageV3
                .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }

    public static Summary parseFrom(
            com.google.protobuf.CodedInputStream input)
            throws java.io.IOException {
        return com.google.protobuf.GeneratedMessageV3
                .parseWithIOException(PARSER, input);
    }

    public static Summary parseFrom(
            com.google.protobuf.CodedInputStream input,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws java.io.IOException {
        return com.google.protobuf.GeneratedMessageV3
                .parseWithIOException(PARSER, input, extensionRegistry);
    }

    @Override
    public Builder newBuilderForType() {
        return newBuilder();
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(Summary prototype) {
        return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }

    @Override
    public Builder toBuilder() {
        return this == DEFAULT_INSTANCE
                ? new Builder() : new Builder().mergeFrom(this);
    }

    @Override
    protected Builder newBuilderForType(
            BuilderParent parent) {
        Builder builder = new Builder(parent);
        return builder;
    }

    /**
     * <pre>
     * Summary metric data are used to convey quantile summaries,
     * a Prometheus (see: https://prometheus.io/docs/concepts/metric_types/#summary)
     * and OpenMetrics (see: https://github.com/OpenObservability/OpenMetrics/blob/4dbf6075567ab43296eed941037c12951faafb92/protos/prometheus.proto#L45)
     * data type. These data points cannot always be merged in a meaningful way.
     * While they can be useful in some applications, histogram data points are
     * recommended for new applications.
     * </pre>
     * <p>
     * Protobuf type {@code opentelemetry.proto.metrics.v1.Summary}
     */
    public static final class Builder extends
            com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
            // @@protoc_insertion_point(builder_implements:opentelemetry.proto.metrics.v1.Summary)
            SummaryOrBuilder {
        public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
            return MetricsProto.internal_static_opentelemetry_proto_metrics_v1_Summary_descriptor;
        }

        @Override
        protected FieldAccessorTable
        internalGetFieldAccessorTable() {
            return MetricsProto.internal_static_opentelemetry_proto_metrics_v1_Summary_fieldAccessorTable
                    .ensureFieldAccessorsInitialized(
                            Summary.class, Builder.class);
        }

        // Construct using io.opentelemetry.proto.metrics.v1.Summary.newBuilder()
        private Builder() {
            maybeForceBuilderInitialization();
        }

        private Builder(
                BuilderParent parent) {
            super(parent);
            maybeForceBuilderInitialization();
        }

        private void maybeForceBuilderInitialization() {
            if (com.google.protobuf.GeneratedMessageV3
                    .alwaysUseFieldBuilders) {
                getDataPointsFieldBuilder();
            }
        }

        @Override
        public Builder clear() {
            super.clear();
            if (dataPointsBuilder_ == null) {
                dataPoints_ = java.util.Collections.emptyList();
                bitField0_ = (bitField0_ & ~0x00000001);
            } else {
                dataPointsBuilder_.clear();
            }
            return this;
        }

        @Override
        public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
            return MetricsProto.internal_static_opentelemetry_proto_metrics_v1_Summary_descriptor;
        }

        @Override
        public Summary getDefaultInstanceForType() {
            return Summary.getDefaultInstance();
        }

        @Override
        public Summary build() {
            Summary result = buildPartial();
            if (!result.isInitialized()) {
                throw newUninitializedMessageException(result);
            }
            return result;
        }

        @Override
        public Summary buildPartial() {
            Summary result = new Summary(this);
            int from_bitField0_ = bitField0_;
            if (dataPointsBuilder_ == null) {
                if (((bitField0_ & 0x00000001) != 0)) {
                    dataPoints_ = java.util.Collections.unmodifiableList(dataPoints_);
                    bitField0_ = (bitField0_ & ~0x00000001);
                }
                result.dataPoints_ = dataPoints_;
            } else {
                result.dataPoints_ = dataPointsBuilder_.build();
            }
            onBuilt();
            return result;
        }

        @Override
        public Builder clone() {
            return super.clone();
        }

        @Override
        public Builder setField(
                com.google.protobuf.Descriptors.FieldDescriptor field,
                Object value) {
            return super.setField(field, value);
        }

        @Override
        public Builder clearField(
                com.google.protobuf.Descriptors.FieldDescriptor field) {
            return super.clearField(field);
        }

        @Override
        public Builder clearOneof(
                com.google.protobuf.Descriptors.OneofDescriptor oneof) {
            return super.clearOneof(oneof);
        }

        @Override
        public Builder setRepeatedField(
                com.google.protobuf.Descriptors.FieldDescriptor field,
                int index, Object value) {
            return super.setRepeatedField(field, index, value);
        }

        @Override
        public Builder addRepeatedField(
                com.google.protobuf.Descriptors.FieldDescriptor field,
                Object value) {
            return super.addRepeatedField(field, value);
        }

        @Override
        public Builder mergeFrom(com.google.protobuf.Message other) {
            if (other instanceof Summary) {
                return mergeFrom((Summary) other);
            } else {
                super.mergeFrom(other);
                return this;
            }
        }

        public Builder mergeFrom(Summary other) {
            if (other == Summary.getDefaultInstance()) return this;
            if (dataPointsBuilder_ == null) {
                if (!other.dataPoints_.isEmpty()) {
                    if (dataPoints_.isEmpty()) {
                        dataPoints_ = other.dataPoints_;
                        bitField0_ = (bitField0_ & ~0x00000001);
                    } else {
                        ensureDataPointsIsMutable();
                        dataPoints_.addAll(other.dataPoints_);
                    }
                    onChanged();
                }
            } else {
                if (!other.dataPoints_.isEmpty()) {
                    if (dataPointsBuilder_.isEmpty()) {
                        dataPointsBuilder_.dispose();
                        dataPointsBuilder_ = null;
                        dataPoints_ = other.dataPoints_;
                        bitField0_ = (bitField0_ & ~0x00000001);
                        dataPointsBuilder_ =
                                com.google.protobuf.GeneratedMessageV3.alwaysUseFieldBuilders ?
                                        getDataPointsFieldBuilder() : null;
                    } else {
                        dataPointsBuilder_.addAllMessages(other.dataPoints_);
                    }
                }
            }
            this.mergeUnknownFields(other.unknownFields);
            onChanged();
            return this;
        }

        @Override
        public final boolean isInitialized() {
            return true;
        }

        @Override
        public Builder mergeFrom(
                com.google.protobuf.CodedInputStream input,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws java.io.IOException {
            Summary parsedMessage = null;
            try {
                parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
            } catch (com.google.protobuf.InvalidProtocolBufferException e) {
                parsedMessage = (Summary) e.getUnfinishedMessage();
                throw e.unwrapIOException();
            } finally {
                if (parsedMessage != null) {
                    mergeFrom(parsedMessage);
                }
            }
            return this;
        }

        private int bitField0_;

        private java.util.List<SummaryDataPoint> dataPoints_ =
                java.util.Collections.emptyList();

        private void ensureDataPointsIsMutable() {
            if (!((bitField0_ & 0x00000001) != 0)) {
                dataPoints_ = new java.util.ArrayList<SummaryDataPoint>(dataPoints_);
                bitField0_ |= 0x00000001;
            }
        }

        private com.google.protobuf.RepeatedFieldBuilderV3<
                SummaryDataPoint, SummaryDataPoint.Builder, SummaryDataPointOrBuilder> dataPointsBuilder_;

        /**
         * <code>repeated .opentelemetry.proto.metrics.v1.SummaryDataPoint data_points = 1;</code>
         */
        public java.util.List<SummaryDataPoint> getDataPointsList() {
            if (dataPointsBuilder_ == null) {
                return java.util.Collections.unmodifiableList(dataPoints_);
            } else {
                return dataPointsBuilder_.getMessageList();
            }
        }

        /**
         * <code>repeated .opentelemetry.proto.metrics.v1.SummaryDataPoint data_points = 1;</code>
         */
        public int getDataPointsCount() {
            if (dataPointsBuilder_ == null) {
                return dataPoints_.size();
            } else {
                return dataPointsBuilder_.getCount();
            }
        }

        /**
         * <code>repeated .opentelemetry.proto.metrics.v1.SummaryDataPoint data_points = 1;</code>
         */
        public SummaryDataPoint getDataPoints(int index) {
            if (dataPointsBuilder_ == null) {
                return dataPoints_.get(index);
            } else {
                return dataPointsBuilder_.getMessage(index);
            }
        }

        /**
         * <code>repeated .opentelemetry.proto.metrics.v1.SummaryDataPoint data_points = 1;</code>
         */
        public Builder setDataPoints(
                int index, SummaryDataPoint value) {
            if (dataPointsBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                ensureDataPointsIsMutable();
                dataPoints_.set(index, value);
                onChanged();
            } else {
                dataPointsBuilder_.setMessage(index, value);
            }
            return this;
        }

        /**
         * <code>repeated .opentelemetry.proto.metrics.v1.SummaryDataPoint data_points = 1;</code>
         */
        public Builder setDataPoints(
                int index, SummaryDataPoint.Builder builderForValue) {
            if (dataPointsBuilder_ == null) {
                ensureDataPointsIsMutable();
                dataPoints_.set(index, builderForValue.build());
                onChanged();
            } else {
                dataPointsBuilder_.setMessage(index, builderForValue.build());
            }
            return this;
        }

        /**
         * <code>repeated .opentelemetry.proto.metrics.v1.SummaryDataPoint data_points = 1;</code>
         */
        public Builder addDataPoints(SummaryDataPoint value) {
            if (dataPointsBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                ensureDataPointsIsMutable();
                dataPoints_.add(value);
                onChanged();
            } else {
                dataPointsBuilder_.addMessage(value);
            }
            return this;
        }

        /**
         * <code>repeated .opentelemetry.proto.metrics.v1.SummaryDataPoint data_points = 1;</code>
         */
        public Builder addDataPoints(
                int index, SummaryDataPoint value) {
            if (dataPointsBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                ensureDataPointsIsMutable();
                dataPoints_.add(index, value);
                onChanged();
            } else {
                dataPointsBuilder_.addMessage(index, value);
            }
            return this;
        }

        /**
         * <code>repeated .opentelemetry.proto.metrics.v1.SummaryDataPoint data_points = 1;</code>
         */
        public Builder addDataPoints(
                SummaryDataPoint.Builder builderForValue) {
            if (dataPointsBuilder_ == null) {
                ensureDataPointsIsMutable();
                dataPoints_.add(builderForValue.build());
                onChanged();
            } else {
                dataPointsBuilder_.addMessage(builderForValue.build());
            }
            return this;
        }

        /**
         * <code>repeated .opentelemetry.proto.metrics.v1.SummaryDataPoint data_points = 1;</code>
         */
        public Builder addDataPoints(
                int index, SummaryDataPoint.Builder builderForValue) {
            if (dataPointsBuilder_ == null) {
                ensureDataPointsIsMutable();
                dataPoints_.add(index, builderForValue.build());
                onChanged();
            } else {
                dataPointsBuilder_.addMessage(index, builderForValue.build());
            }
            return this;
        }

        /**
         * <code>repeated .opentelemetry.proto.metrics.v1.SummaryDataPoint data_points = 1;</code>
         */
        public Builder addAllDataPoints(
                Iterable<? extends SummaryDataPoint> values) {
            if (dataPointsBuilder_ == null) {
                ensureDataPointsIsMutable();
                com.google.protobuf.AbstractMessageLite.Builder.addAll(
                        values, dataPoints_);
                onChanged();
            } else {
                dataPointsBuilder_.addAllMessages(values);
            }
            return this;
        }

        /**
         * <code>repeated .opentelemetry.proto.metrics.v1.SummaryDataPoint data_points = 1;</code>
         */
        public Builder clearDataPoints() {
            if (dataPointsBuilder_ == null) {
                dataPoints_ = java.util.Collections.emptyList();
                bitField0_ = (bitField0_ & ~0x00000001);
                onChanged();
            } else {
                dataPointsBuilder_.clear();
            }
            return this;
        }

        /**
         * <code>repeated .opentelemetry.proto.metrics.v1.SummaryDataPoint data_points = 1;</code>
         */
        public Builder removeDataPoints(int index) {
            if (dataPointsBuilder_ == null) {
                ensureDataPointsIsMutable();
                dataPoints_.remove(index);
                onChanged();
            } else {
                dataPointsBuilder_.remove(index);
            }
            return this;
        }

        /**
         * <code>repeated .opentelemetry.proto.metrics.v1.SummaryDataPoint data_points = 1;</code>
         */
        public SummaryDataPoint.Builder getDataPointsBuilder(
                int index) {
            return getDataPointsFieldBuilder().getBuilder(index);
        }

        /**
         * <code>repeated .opentelemetry.proto.metrics.v1.SummaryDataPoint data_points = 1;</code>
         */
        public SummaryDataPointOrBuilder getDataPointsOrBuilder(
                int index) {
            if (dataPointsBuilder_ == null) {
                return dataPoints_.get(index);
            } else {
                return dataPointsBuilder_.getMessageOrBuilder(index);
            }
        }

        /**
         * <code>repeated .opentelemetry.proto.metrics.v1.SummaryDataPoint data_points = 1;</code>
         */
        public java.util.List<? extends SummaryDataPointOrBuilder>
        getDataPointsOrBuilderList() {
            if (dataPointsBuilder_ != null) {
                return dataPointsBuilder_.getMessageOrBuilderList();
            } else {
                return java.util.Collections.unmodifiableList(dataPoints_);
            }
        }

        /**
         * <code>repeated .opentelemetry.proto.metrics.v1.SummaryDataPoint data_points = 1;</code>
         */
        public SummaryDataPoint.Builder addDataPointsBuilder() {
            return getDataPointsFieldBuilder().addBuilder(
                    SummaryDataPoint.getDefaultInstance());
        }

        /**
         * <code>repeated .opentelemetry.proto.metrics.v1.SummaryDataPoint data_points = 1;</code>
         */
        public SummaryDataPoint.Builder addDataPointsBuilder(
                int index) {
            return getDataPointsFieldBuilder().addBuilder(
                    index, SummaryDataPoint.getDefaultInstance());
        }

        /**
         * <code>repeated .opentelemetry.proto.metrics.v1.SummaryDataPoint data_points = 1;</code>
         */
        public java.util.List<SummaryDataPoint.Builder>
        getDataPointsBuilderList() {
            return getDataPointsFieldBuilder().getBuilderList();
        }

        private com.google.protobuf.RepeatedFieldBuilderV3<
                SummaryDataPoint, SummaryDataPoint.Builder, SummaryDataPointOrBuilder>
        getDataPointsFieldBuilder() {
            if (dataPointsBuilder_ == null) {
                dataPointsBuilder_ = new com.google.protobuf.RepeatedFieldBuilderV3<
                        SummaryDataPoint, SummaryDataPoint.Builder, SummaryDataPointOrBuilder>(
                        dataPoints_,
                        ((bitField0_ & 0x00000001) != 0),
                        getParentForChildren(),
                        isClean());
                dataPoints_ = null;
            }
            return dataPointsBuilder_;
        }

        @Override
        public final Builder setUnknownFields(
                final com.google.protobuf.UnknownFieldSet unknownFields) {
            return super.setUnknownFields(unknownFields);
        }

        @Override
        public final Builder mergeUnknownFields(
                final com.google.protobuf.UnknownFieldSet unknownFields) {
            return super.mergeUnknownFields(unknownFields);
        }


        // @@protoc_insertion_point(builder_scope:opentelemetry.proto.metrics.v1.Summary)
    }

    // @@protoc_insertion_point(class_scope:opentelemetry.proto.metrics.v1.Summary)
    private static final Summary DEFAULT_INSTANCE;

    static {
        DEFAULT_INSTANCE = new Summary();
    }

    public static Summary getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<Summary>
            PARSER = new com.google.protobuf.AbstractParser<Summary>() {
        @Override
        public Summary parsePartialFrom(
                com.google.protobuf.CodedInputStream input,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return new Summary(input, extensionRegistry);
        }
    };

    public static com.google.protobuf.Parser<Summary> parser() {
        return PARSER;
    }

    @Override
    public com.google.protobuf.Parser<Summary> getParserForType() {
        return PARSER;
    }

    @Override
    public Summary getDefaultInstanceForType() {
        return DEFAULT_INSTANCE;
    }

}

