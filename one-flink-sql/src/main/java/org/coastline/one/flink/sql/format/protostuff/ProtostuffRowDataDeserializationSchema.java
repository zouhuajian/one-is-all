/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.coastline.one.flink.sql.format.protostuff;

import org.apache.flink.annotation.Internal;
import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.formats.common.TimestampFormat;
import org.apache.flink.formats.json.JsonToRowDataConverters;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.core.json.JsonReadFeature;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.DeserializationFeature;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.JsonNode;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.flink.table.data.RowData;
import org.apache.flink.table.types.logical.DecimalType;
import org.apache.flink.table.types.logical.RowType;
import org.apache.flink.table.types.logical.utils.LogicalTypeChecks;
import org.coastline.one.flink.sql.format.protostuff.codec.ProtostuffCodec;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.Objects;

import static java.lang.String.format;
import static org.apache.flink.util.Preconditions.checkNotNull;

/**
 * Deserialization schema from Protostuff to Flink Table/SQL internal data structure {@link RowData}.
 *
 * <p>Deserializes a <code>byte[]</code> message as a JSON object and reads the specified fields.
 *
 * <p>Failures during deserialization are forwarded as wrapped IOExceptions.
 */
@Internal
public class ProtostuffRowDataDeserializationSchema implements DeserializationSchema<RowData> {
    private static final long serialVersionUID = 1L;

    /**
     * Flag indicating whether to fail if a field is missing.
     */
    private final boolean failOnMissingField;

    /**
     * Flag indicating whether to ignore invalid fields/rows (default: throw an exception).
     */
    private final boolean ignoreParseErrors;

    /**
     * TypeInformation of the produced {@link RowData}.
     */
    private final TypeInformation<RowData> resultTypeInfo;

    /**
     * Runtime converter that converts {@link JsonNode}s into objects of Flink SQL internal data
     * structures.
     */
    private final JsonToRowDataConverters.JsonToRowDataConverter runtimeConverter;

    /**
     * Object mapper for parsing the JSON.
     */
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Timestamp format specification which is used to parse timestamp.
     */
    private final TimestampFormat timestampFormat;

    private final String className;
    private ProtostuffCodec<?> protostuffCodec;

    public ProtostuffRowDataDeserializationSchema(
            RowType rowType,
            TypeInformation<RowData> resultTypeInfo,
            boolean failOnMissingField,
            boolean ignoreParseErrors,
            TimestampFormat timestampFormat,
            String className) {
        if (ignoreParseErrors && failOnMissingField) {
            throw new IllegalArgumentException("Protostuff format doesn't support failOnMissingField and ignoreParseErrors are both enabled.");
        }
        this.resultTypeInfo = checkNotNull(resultTypeInfo);
        this.failOnMissingField = failOnMissingField;
        this.ignoreParseErrors = ignoreParseErrors;
        this.runtimeConverter = new JsonToRowDataConverters(failOnMissingField, ignoreParseErrors, timestampFormat)
                .createConverter(checkNotNull(rowType));
        this.timestampFormat = timestampFormat;
        boolean hasDecimalType = LogicalTypeChecks.hasNested(rowType, t -> t instanceof DecimalType);
        if (hasDecimalType) {
            objectMapper.enable(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS);
        }
        objectMapper.configure(JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS.mappedFeature(), true);
        this.className = className;
    }

    @Override
    public void open(InitializationContext context) throws Exception {
        DeserializationSchema.super.open(context);
        try {
            protostuffCodec = ProtostuffCodec.create(Class.forName(className));
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("Protostuff schema class not found, class name: " + className, e);
        }
    }

    @Override
    public RowData deserialize(@Nullable byte[] message) throws IOException {
        if (message == null) {
            return null;
        }
        try {
            JsonNode jsonNode = deserializeToJsonNode(message);
            return convertToRowData(jsonNode);
        } catch (Throwable t) {
            if (ignoreParseErrors) {
                return null;
            }
            throw new IOException(format("Failed to deserialize JSON '%s'.", new String(message)), t);
        }
    }

    public JsonNode deserializeToJsonNode(byte[] message) throws IOException {
        Object decode = protostuffCodec.decode(message);
        return objectMapper.valueToTree(decode);
    }


    public RowData convertToRowData(JsonNode message) {
        return (RowData) runtimeConverter.convert(message);
    }

    @Override
    public boolean isEndOfStream(RowData nextElement) {
        return false;
    }

    @Override
    public TypeInformation<RowData> getProducedType() {
        return resultTypeInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProtostuffRowDataDeserializationSchema that = (ProtostuffRowDataDeserializationSchema) o;
        return failOnMissingField == that.failOnMissingField
                && ignoreParseErrors == that.ignoreParseErrors
                && resultTypeInfo.equals(that.resultTypeInfo)
                && timestampFormat.equals(that.timestampFormat)
                && Objects.equals(className, that.className);
    }

    @Override
    public int hashCode() {
        return Objects.hash(failOnMissingField, ignoreParseErrors, resultTypeInfo, timestampFormat, className);
    }
}
