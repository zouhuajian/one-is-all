package org.coastline.one.flink.stream;

import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;
import org.coastline.one.flink.common.model.MonitorData;
import org.coastline.one.flink.stream.core.StreamJobExecutor;
import org.coastline.one.flink.stream.core.process.CommonProcessFunction;
import org.coastline.one.flink.stream.core.source.MemorySourceFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author Jay.H.Zou
 * @date 2021/8/5
 */
public class CommonStreamJob extends StreamJobExecutor {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommonStreamJob.class);

    private CommonStreamJob(String[] args) throws IOException {
        super(args);
    }

    @Override
    protected void customEnv(StreamExecutionEnvironment env) {
        env.disableOperatorChaining();
    }

    @Override
    public void buildJob(final StreamExecutionEnvironment env) throws Exception {
        env.addSource(MemorySourceFunction.create()).name("memory_source")
                .process(CommonProcessFunction.create()).name("common_process")
                .addSink(new SinkFunction<MonitorData>() {
                    @Override
                    public void invoke(MonitorData value, Context context) throws Exception {
                        System.out.println(value);
                    }
                });
    }

    public static void main(String[] args) throws Exception {
        CommonStreamJob job = new CommonStreamJob(args);
        job.execute("common_stream");
    }

    static class DataKeySelector implements KeySelector<MonitorData, String> {

        private DataKeySelector() {
        }

        public static DataKeySelector create() {
            return new DataKeySelector();
        }

        @Override
        public String getKey(MonitorData value) throws Exception {
            return value.getName();
        }
    }

}
