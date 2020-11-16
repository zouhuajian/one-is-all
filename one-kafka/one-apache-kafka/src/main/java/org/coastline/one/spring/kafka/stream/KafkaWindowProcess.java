package org.coastline.one.spring.kafka.stream;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.state.WindowStore;
import org.coastline.one.spring.kafka.model.StreamParam;

import java.time.Duration;
import java.util.Properties;

/**
 * @author zouhuajian
 * @date 2020/11/16
 */
public class KafkaWindowProcess {

    private KafkaStreams streams = null;

    private String TOPIC_NAME = "order_service";

    public Properties initConfig() {
        // 配置信息
        Properties props = new Properties();
        //Streams应用Id
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "order_service_id");
        //Kafka集群地址
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        //指定序列化和反序列化类型
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        return props;
    }

    /**
     * applicationId
     * brokerServers
     * keySerde
     * valueSerde
     * topic
     * windowDuration
     *
     * @param param
     */
    public void createStream(StreamParam param) {

        Properties properties = initConfig();
        //创建一个topology构建器，在kakfa中计算逻辑被定义为连接的处理器节点的拓扑。
        StreamsBuilder builder = new StreamsBuilder();
        //使用topology构建器创建一个源流，指定源topic
        KStream<String, String> source = builder.stream(TOPIC_NAME);

        // 构建topology

        KStream<Windowed<String>, Double> windowStream = source
                .mapValues((value) -> {
                    System.out.println(value);
                    return Double.valueOf(value);
                })
                .groupByKey()

                .windowedBy(TimeWindows.of(Duration.ofSeconds(5L)))
                .aggregate(() -> 0D,
                        (aggKey, newValue, value) -> newValue + value,
                        Materialized.<String, Double,
                                WindowStore<Bytes, byte[]>>as("window-aggregate-statistics").withValueSerde((Serdes.Double())
                        ))
                .toStream();
        // 计算平均值
        // .mapValues((v) -> {v});


        //将stream写回到Kafka
        windowStream.foreach((key, value) -> System.out.println(key + " => " + value));
        windowStream.to("stockStatsOutput", Produced.keySerde(WindowedSerdes.timeWindowedSerdeFrom(String.class)));
        //创建Streams客户端
        streams = new KafkaStreams(builder.build(), properties);
    }

    public void start() {
        if (streams != null) {
            streams.start();
        } else {
            throw new RuntimeException("Kafka stream not initialize.");
        }
    }

    public void stop() {
        if (streams != null) {
            try {
                streams.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    class Statistics {

        private Double value;

        public Double getValue() {
            return value;
        }

        public void setValue(Double value) {
            this.value = value;
        }

        public Statistics add(Double value) {
            this.value += value;
            return this;
        }

        @Override
        public String toString() {
            return "Statistics{" +
                    "value=" + value +
                    '}';
        }
    }
}
