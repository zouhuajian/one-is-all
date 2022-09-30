package org.coastline.one.flink.stream.functions.process;

/**
 * 解码
 * @author Jay.H.Zou
 * @date 2021/8/19
 */
/*public class DeserializeFlatMapFunction<T> implements FlatMapFunction<byte[], T> {
    private static final long serialVersionUID = 1L;

    private static final Logger logger = LoggerFactory.getLogger(DeserializeFlatMapFunction.class);

    private ProtostuffCodec<T> codec;

    private DeserializeFlatMapFunction(Class<T> clazz) {
        this.codec = ProtostuffCodec.create(clazz);
    }

    public static <T> DeserializeFlatMapFunction<T> create(Class<T> clazz) {
        return new DeserializeFlatMapFunction<>(clazz);
    }

    @Override
    public void flatMap(byte[] value, Collector<T> out) throws Exception {
        if (value == null) {
            return;
        }
        try {
            T decode = codec.decode(value);
            out.collect(decode);
        } catch (Exception e) {
            logger.error("decode bytes error", e);
        }
    }
}*/
