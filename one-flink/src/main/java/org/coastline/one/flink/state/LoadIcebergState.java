package org.coastline.one.flink.state;

import org.apache.flink.api.common.state.ListState;
import org.apache.flink.api.common.state.ListStateDescriptor;
import org.apache.flink.api.common.typeinfo.IntegerTypeInfo;
import org.apache.flink.api.common.typeutils.base.LongSerializer;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.runtime.state.FunctionInitializationContext;
import org.apache.flink.runtime.state.FunctionSnapshotContext;
import org.apache.flink.runtime.state.hashmap.HashMapStateBackend;
import org.apache.flink.state.api.SavepointReader;
import org.apache.flink.state.api.functions.StateBootstrapFunction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.util.function.Consumer;

/**
 * @author Jay.H.Zou
 * @date 2023/5/12
 */
public class LoadIcebergState {
    static String path = "file:///Users/zouhuajian/data/projects/jay/one-is-all/one-flink/checkpoint/iceberg";

    // new ListStateDescriptor<>("snapshot-id-state", LongSerializer.INSTANCE)
    public static void main(String[] args) throws Exception {
        Configuration devConfig = new Configuration();
        devConfig.setInteger("rest.port", 8002);
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        SavepointReader read = SavepointReader.read(env, path, new HashMapStateBackend());
        // 6a0545e6-dac7-4cae-b5b6-a0fa70fbcf6d
        // c49a7f4f-ef8d-4a3d-b9a8-d3817efb6188
        DataStream<Long> listStateStream = read.readListState("d830b5ef4fe5d765545dceb92dadd12c",
                "snapshot-id-state",
                IntegerTypeInfo.LONG_TYPE_INFO);
        listStateStream.executeAndCollect().forEachRemaining(new Consumer<Long>() {
            @Override
            public void accept(Long value) {
                System.out.println(value);
            }
        });
    }


    static class IcebergListStateReaderFunction extends StateBootstrapFunction<Long> {
        ListState<Long> lastSnapshotIdState;

        @Override
        public void processElement(Long value, Context ctx) throws Exception {

        }

        @Override
        public void snapshotState(FunctionSnapshotContext functionSnapshotContext) throws Exception {
            long checkpointId = functionSnapshotContext.getCheckpointId();
            System.out.println(checkpointId);
        }

        @Override
        public void initializeState(FunctionInitializationContext context) throws Exception {
            lastSnapshotIdState =
                    context.getOperatorStateStore()
                            .getListState(new ListStateDescriptor<>("snapshot-id-state", LongSerializer.INSTANCE));
        }
    }
}
