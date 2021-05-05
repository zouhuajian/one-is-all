package org.coastline.one.flink.stream;

import com.alibaba.fastjson.JSON;
import org.apache.flink.api.common.ExecutionConfig;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.AsyncDataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.async.ResultFuture;
import org.apache.flink.streaming.api.functions.async.RichAsyncFunction;
import org.apache.flink.streaming.api.functions.sink.PrintSinkFunction;
import org.coastline.one.flink.stream.model.MonitorData;
import org.coastline.one.flink.stream.source.MonitorDataSourceFunction;

import java.util.Collections;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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
        SingleOutputStreamOperator<MonitorData> dataStream = env.addSource(new MonitorDataSourceFunction()).name("monitor_source");

        dataStream = AsyncDataStream.unorderedWait(dataStream, new RichAsyncFunction<MonitorData, MonitorData>() {

            public transient ThreadPoolExecutor executor;

            @Override
            public void open(Configuration parameters) throws Exception {
                executor = new ThreadPoolExecutor(5, //
                        10, 1, TimeUnit.MINUTES, new LinkedBlockingDeque<>());
            }

            @Override
            public void asyncInvoke(MonitorData input, ResultFuture<MonitorData> resultFuture) throws Exception {
                CompletableFuture.runAsync(() -> {
                    int mills = new Random().nextInt(10);
                    System.out.println("异步处理数据:" + Thread.currentThread().getId() + "|" + JSON.toJSONString(input));
                    try {
                        TimeUnit.SECONDS.sleep(mills);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    resultFuture.complete(Collections.singleton(input));
                }, executor);
            }

            @Override
            public void timeout(MonitorData input, ResultFuture<MonitorData> resultFuture) throws Exception {
                //超时后的处理
            }

            @Override
            public void close() throws Exception {
                executor.shutdownNow();
            }

        }, 1, TimeUnit.MINUTES, 1000).setParallelism(1);
        dataStream.addSink(new PrintSinkFunction<>());

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
