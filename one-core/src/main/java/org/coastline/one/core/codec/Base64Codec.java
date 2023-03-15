package org.coastline.one.core.codec;

import java.util.Base64;

/**
 * @author Jay.H.Zou
 * @date 2023/3/3
 */
public class Base64Codec implements ICodec<byte[]> {

    private static final Base64.Encoder ENCODER = Base64.getEncoder();

    private static final Base64.Decoder DECODER = Base64.getDecoder();

    @Override
    public byte[] encode(byte[] data) {
        return ENCODER.encode(data);
    }

    @Override
    public byte[] decode(byte[] data) {
        return DECODER.decode(data);
    }
}
