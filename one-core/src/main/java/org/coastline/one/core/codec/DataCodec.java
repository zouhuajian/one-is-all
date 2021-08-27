package org.coastline.one.core.codec;

/**
 * @author Jay.H.Zou
 * @date 2021/7/31
 */
public interface DataCodec<T> {

    byte[] encode(T data);

    T decode(byte[] data);
}
