package org.coastline.one.flink.common.checkpoint;

import org.apache.flink.api.common.state.ListState;
import org.apache.flink.api.common.state.StateTtlConfig;
import org.apache.flink.api.common.time.Time;
import org.apache.flink.runtime.state.FunctionInitializationContext;
import org.apache.flink.runtime.state.FunctionSnapshotContext;
import org.apache.flink.streaming.api.checkpoint.CheckpointedFunction;
import org.apache.flink.streaming.api.functions.ProcessFunction;
import org.apache.flink.util.Collector;

import java.time.Duration;

/**
 * @author Jay.H.Zou
 * @date 2022/7/11
 */
public class StateProcessFunction extends ProcessFunction<String, String> implements CheckpointedFunction {

    @Override
    public void snapshotState(FunctionSnapshotContext context) throws Exception {

    }

    @Override
    public void initializeState(FunctionInitializationContext context) throws Exception {
        StateTtlConfig
                .newBuilder(Time.seconds(10))
                .cleanupFullSnapshot()
                .build();
    }

    @Override
    public void processElement(String value, ProcessFunction<String, String>.Context ctx, Collector<String> out) throws Exception {

    }
}
