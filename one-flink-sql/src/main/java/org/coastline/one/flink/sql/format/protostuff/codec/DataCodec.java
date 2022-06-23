package org.coastline.one.flink.sql.format.protostuff.codec;

import java.io.Serializable;

/**
 * @author Jay.H.Zou
 * @date 2021/7/31
 */
public interface DataCodec<T> extends Serializable {

    byte[] encode(T data);

    T decode(byte[] data);
}
