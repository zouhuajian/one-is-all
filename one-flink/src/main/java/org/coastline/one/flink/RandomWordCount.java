package org.coastline.one.flink;

import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.SourceFunction;

/**
 * @author Jay.H.Zou
 * @date 7/10/2020
 */
public class RandomWordCount {

    public static void main(String[] args) throws Exception {
        // get the execution environment
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        //note 模拟两个数据源，它们会生成一行随机单词组（单词之间是空格分隔）
        DataStream<String> inputStream = env.addSource(new SourceFunction<String>() {
            @Override
            public void run(SourceContext<String> ctx) {
                for (int i = 0; i < 1000; i++) {
                    String data = "Source_No_" + i;
                    ctx.collect(data);
                }
            }
            @Override
            public void cancel() {
            }
        });
        //note: 先对流做 union，然后做一个过滤后，做 word-count
        inputStream.print()
                .setParallelism(2);
        env.execute("Random WordCount");
    }

}
