package org.coastline.one.flink.stream;

import org.apache.flink.api.common.ExecutionConfig;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.async.ResultFuture;
import org.apache.flink.streaming.api.functions.async.RichAsyncFunction;
import org.apache.flink.streaming.api.functions.sink.PrintSinkFunction;
import org.coastline.one.flink.stream.model.MonitorData;
import org.coastline.one.flink.stream.source.MonitorDataSourceFunction;

/**
 * async job
 *
 * @author Jay.H.Zou
 * @date 2021/5/4
 */
public class OneAsyncJob {

    public static void main(String[] args) throws Exception {
        Configuration configuration = new Configuration();
        configuration.setInteger("rest.port", 8002);
        ExecutionConfig config = new ExecutionConfig();
        config.setAutoWatermarkInterval(300);
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.createLocalEnvironmentWithWebUI(configuration);
        env.addSource(new MonitorDataSourceFunction()).name("monitor_source")
                .addSink(new PrintSinkFunction<>());
        env.execute("one-async-job");
    }

    class ValueAsyncFunction extends RichAsyncFunction<MonitorData, MonitorData> {
        @Override
        public void asyncInvoke(MonitorData input, ResultFuture<MonitorData> resultFuture) throws Exception {

        }

        @Override
        public void timeout(MonitorData input, ResultFuture<MonitorData> resultFuture) throws Exception {
            super.timeout(input, resultFuture);
        }
    }

}
