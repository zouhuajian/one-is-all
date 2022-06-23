package org.coastline.one.flink.sql.format.protostuff;

import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.configuration.ConfigOption;
import org.apache.flink.configuration.ReadableConfig;
import org.apache.flink.formats.common.TimestampFormat;
import org.apache.flink.formats.json.JsonFormatOptionsUtil;
import org.apache.flink.table.connector.ChangelogMode;
import org.apache.flink.table.connector.Projection;
import org.apache.flink.table.connector.format.DecodingFormat;
import org.apache.flink.table.connector.format.ProjectableDecodingFormat;
import org.apache.flink.table.connector.source.DynamicTableSource;
import org.apache.flink.table.data.RowData;
import org.apache.flink.table.factories.DeserializationFormatFactory;
import org.apache.flink.table.factories.DynamicTableFactory;
import org.apache.flink.table.factories.FactoryUtil;
import org.apache.flink.table.types.DataType;
import org.apache.flink.table.types.logical.RowType;

import java.util.HashSet;
import java.util.Set;

import static org.apache.flink.formats.json.JsonFormatOptions.*;
import static org.coastline.one.flink.sql.format.protostuff.ProtostuffFormatOptions.PROTOSTUFF_SCHEMA_CLASS;

/**
 * Table format factory for providing configured instances of Protostuff to RowData.
 * {@link DeserializationSchema}.
 *
 * @author Jay.H.Zou
 * @date 2022/6/17
 */
public class ProtostuffMetricsFormatFactory implements DeserializationFormatFactory {

    // Factory 的唯一标识
    public static final String IDENTIFIER = "protostuff";

    @Override
    public DecodingFormat<DeserializationSchema<RowData>> createDecodingFormat(DynamicTableFactory.Context context, ReadableConfig formatOptions) {
        FactoryUtil.validateFactoryOptions(this, formatOptions);
        JsonFormatOptionsUtil.validateDecodingFormatOptions(formatOptions);
        final boolean failOnMissingField = formatOptions.get(FAIL_ON_MISSING_FIELD);
        final boolean ignoreParseErrors = formatOptions.get(IGNORE_PARSE_ERRORS);
        TimestampFormat timestampOption = JsonFormatOptionsUtil.getTimestampFormat(formatOptions);
        String protostuffSchemaClassName = formatOptions.get(PROTOSTUFF_SCHEMA_CLASS);
        return new ProjectableDecodingFormat<DeserializationSchema<RowData>>() {
            @Override
            public DeserializationSchema<RowData> createRuntimeDecoder(DynamicTableSource.Context context, DataType physicalDataType, int[][] projections) {
                final DataType producedDataType = Projection.of(projections).project(physicalDataType);
                final RowType rowType = (RowType) producedDataType.getLogicalType();
                final TypeInformation<RowData> rowDataTypeInfo = context.createTypeInformation(producedDataType);
                return new ProtostuffRowDataDeserializationSchema(
                        rowType,
                        rowDataTypeInfo,
                        failOnMissingField,
                        ignoreParseErrors,
                        timestampOption,
                        protostuffSchemaClassName);
            }

            @Override
            public ChangelogMode getChangelogMode() {
                return ChangelogMode.insertOnly();
            }
        };
    }

    @Override
    public String factoryIdentifier() {
        return IDENTIFIER;
    }

    /**
     * User must provide protostuff.schema-class
     * @return
     */
    @Override
    public Set<ConfigOption<?>> requiredOptions() {
        Set<ConfigOption<?>> options = new HashSet<>();
        options.add(PROTOSTUFF_SCHEMA_CLASS);
        return options;
    }

    @Override
    public Set<ConfigOption<?>> optionalOptions() {
        Set<ConfigOption<?>> options = new HashSet<>();
        options.add(FAIL_ON_MISSING_FIELD);
        options.add(IGNORE_PARSE_ERRORS);
        options.add(TIMESTAMP_FORMAT);
        options.add(MAP_NULL_KEY_MODE);
        options.add(MAP_NULL_KEY_LITERAL);
        options.add(ENCODE_DECIMAL_AS_PLAIN_NUMBER);
        return options;
    }

    @Override
    public Set<ConfigOption<?>> forwardOptions() {
        Set<ConfigOption<?>> options = new HashSet<>();
        options.add(TIMESTAMP_FORMAT);
        options.add(MAP_NULL_KEY_MODE);
        options.add(MAP_NULL_KEY_LITERAL);
        options.add(ENCODE_DECIMAL_AS_PLAIN_NUMBER);
        return options;
    }
}
