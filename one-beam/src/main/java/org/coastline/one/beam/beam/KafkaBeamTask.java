package org.coastline.one.beam.beam;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.kafka.KafkaIO;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.transforms.Values;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.vendor.guava.v26_0_jre.com.google.common.collect.ImmutableMap;
import org.apache.kafka.common.serialization.StringDeserializer;

public class KafkaBeamTask {

    public static void main(String[] args) {
        // 建立选项
        PipelineOptions pipelineOptions = PipelineOptionsFactory.create();
        // 建立管道
        Pipeline pipeline = Pipeline.create(pipelineOptions);

        PCollection<String> source = pipeline.apply(KafkaIO.<String, String>read()
                .withBootstrapServers("localhost:9092")//
                .withTopic("order_service")    // use withTopics(List<String>) to read from multiple topics.
                .withKeyDeserializer(StringDeserializer.class)
                .withValueDeserializer(StringDeserializer.class)
                // Above four are required configuration. returns   PCollection<KafkaRecord<Long, String>>
                // Rest of the settings are optional :
                // you can further customize KafkaConsumer used to read the records by   adding more
                // settings for ConsumerConfig. e.g :
                .withConsumerConfigUpdates(ImmutableMap.of("group.id", "jay"))

                // set event times and watermark based on 'LogAppendTime'. To provide   a custom
                // policy see withTimestampPolicyFactory(). withProcessingTime() is   the default.
                // Use withCreateTime() with topics that have 'CreateTime' timestamps.
                .withLogAppendTime()
                // restrict reader to committed messages on Kafka (see method   documentation).
                .withReadCommitted()
                // offset consumed by the pipeline can be committed back.
                .commitOffsetsInFinalize()
                // finally, if you don't need Kafka metadata, you can drop it.g
                .withoutMetadata() // PCollection<KV<Long, String>>
        ).apply(Values.create());// PCollection<String>

        PCollection<String> stringLowerCase = source.apply(ParDo.of(new StringToLowerCaseFn()));

        stringLowerCase.apply(ParDo.of(new PrintFn()));

        // run
        pipeline.run().waitUntilFinish();

    }
}
