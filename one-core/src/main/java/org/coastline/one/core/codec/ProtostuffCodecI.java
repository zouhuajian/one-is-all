package org.coastline.one.core.codec;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;

/**
 * @author Jay.H.Zou
 * @date 2021/7/31
 */
public class ProtostuffCodecI<T> implements ICodec<T> {

    private final Schema<T> schema;

    private ProtostuffCodecI(Class<T> clazz) {
        this.schema = RuntimeSchema.getSchema(clazz);
    }

    public static <T> ProtostuffCodecI<T> create(Class<T> clazz) {
        return new ProtostuffCodecI<>(clazz);
    }

    @Override
    public byte[] encode(T data) {
        // Re-use (manage) this buffer to avoid allocating on every serialization
        LinkedBuffer buffer = LinkedBuffer.allocate(512);
        final byte[] bytes;
        try {
            bytes = ProtostuffIOUtil.toByteArray(data, schema, buffer);
        } finally {
            buffer.clear();
        }
        return bytes;
    }

    @Override
    public T decode(byte[] data) {
        T result = schema.newMessage();
        ProtostuffIOUtil.mergeFrom(data, result, schema);
        return result;
    }

}
