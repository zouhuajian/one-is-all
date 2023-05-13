package org.coastline.one.flink.core.functions;

import org.apache.flink.api.common.state.ListState;
import org.apache.flink.api.common.state.ListStateDescriptor;
import org.apache.flink.api.common.state.OperatorStateStore;
import org.apache.flink.api.common.state.StateTtlConfig;
import org.apache.flink.api.common.time.Time;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.runtime.state.FunctionInitializationContext;
import org.apache.flink.runtime.state.FunctionSnapshotContext;
import org.apache.flink.streaming.api.checkpoint.CheckpointedFunction;
import org.apache.flink.streaming.api.functions.ProcessFunction;
import org.apache.flink.util.Collector;

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
                .newBuilder(Time.hours(1))
                .cleanupFullSnapshot()
                .build();
        ListStateDescriptor<String> descriptor = new ListStateDescriptor<>("one", TypeInformation.of(String.class));
        OperatorStateStore operatorStateStore = context.getOperatorStateStore();
        ListState<String> listState = operatorStateStore.getListState(descriptor);
        ListState<String> unionListState = operatorStateStore.getUnionListState(descriptor);
    }

    @Override
    public void processElement(String value, ProcessFunction<String, String>.Context ctx, Collector<String> out) throws Exception {

    }
}
