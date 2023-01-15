package org.coastline.one.flink.core.functions.source;

import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.streaming.connectors.kafka.KafkaDeserializationSchema;
import org.apache.kafka.clients.consumer.ConsumerRecord;

/**
 * @author Jay.H.Zou
 * @date 2021/8/5
 */
public class MonitorKafkaDeserializationSchema implements KafkaDeserializationSchema<byte[]> {

    private static final long serialVersionUID = 1L;

    @Override
    public boolean isEndOfStream(byte[] bytes) {
        return false;
    }

    @Override
    public byte[] deserialize(ConsumerRecord<byte[], byte[]> record) {
        return record.value();
    }

    @Override
    public TypeInformation<byte[]> getProducedType() {
        return TypeInformation.of(byte[].class);
    }

}
