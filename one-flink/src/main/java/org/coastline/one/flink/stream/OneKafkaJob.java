package org.coastline.one.flink.stream;

import org.apache.flink.api.common.ExecutionConfig;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer;

import java.time.LocalDateTime;
import java.util.Properties;

/**
 * @author Jay.H.Zou
 * @date 2020/12/1
 */
public class OneKafkaJob {


    public static void main(String[] args) throws Exception {
        System.out.println("job start time: " + LocalDateTime.now().minusDays(1));
        Configuration configuration = new Configuration();
        configuration.setInteger("rest.port", 8002);
        ExecutionConfig config = new ExecutionConfig();
        config.setAutoWatermarkInterval(300);
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.createLocalEnvironmentWithWebUI(configuration);
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "localhost:9092");
        properties.setProperty("group.id", "test");
        DataStreamSource<String> dataStreamSource = env.addSource(new FlinkKafkaConsumer<>("topic", new SimpleStringSchema(), properties));

        // 至少一次
        FlinkKafkaProducer<String> myProducer = new FlinkKafkaProducer<String>("my-topic", new SimpleStringSchema(), properties);
        dataStreamSource.addSink(myProducer);
        dataStreamSource.print();
        env.execute("one-kafka-job");
    }
}
