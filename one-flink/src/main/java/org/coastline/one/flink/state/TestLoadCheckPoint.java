package org.coastline.one.flink.state;

import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.runtime.checkpoint.Checkpoints;
import org.apache.flink.runtime.checkpoint.OperatorState;
import org.apache.flink.runtime.checkpoint.OperatorSubtaskState;
import org.apache.flink.runtime.checkpoint.StateObjectCollection;
import org.apache.flink.runtime.checkpoint.metadata.CheckpointMetadata;
import org.apache.flink.runtime.state.KeyedStateHandle;
import org.apache.flink.runtime.state.hashmap.HashMapStateBackend;
import org.apache.flink.state.api.SavepointReader;
import org.apache.flink.state.api.functions.KeyedStateReaderFunction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collection;
import java.util.function.Consumer;

/**
 * @author Jay.H.Zou
 * @date 2022/7/9
 */
public class TestLoadCheckPoint {

    static String path = "file:///Users/zouhuajian/Jay/project/zouhuajian/one-is-all/one-flink/checkpoint/c3e4a0b57c5e6cb09ba9b00e494b5cba/chk-4";

    public static void main(String[] args) throws Exception {
        Configuration devConfig = new Configuration();
        devConfig.setInteger("rest.port", 8002);
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        SavepointReader read = SavepointReader.read(env, path, new HashMapStateBackend());
        DataStream<String> keyedStateStream = read.readKeyedState("aggregate_id", new CustomKeyedStateReaderFunction());
        keyedStateStream.executeAndCollect().forEachRemaining(new Consumer<String>() {
            @Override
            public void accept(String key) {
                System.out.println(key);
            }
        });
    }


    static class CustomKeyedStateReaderFunction extends KeyedStateReaderFunction<String, String> {
        ValueState<String> state;
        @Override
        public void open(Configuration parameters) throws Exception {
            ValueStateDescriptor<String> valueStateDescriptor = new ValueStateDescriptor<>("name", TypeInformation.of(String.class));
            state = getRuntimeContext().getState(valueStateDescriptor);
        }

        @Override
        public void readKey(String key, KeyedStateReaderFunction.Context ctx, Collector<String> out) throws Exception {
            String value = state.value();
            out.collect(key);
        }
    }


    @Deprecated
    private static void wrongWay() throws IOException {

        ClassLoader classLoader = TestLoadCheckPoint.class.getClassLoader();
        DataInputStream dataInputStream = new DataInputStream(Files.newInputStream(new File(path).toPath()));
        CheckpointMetadata checkpointMetadata = Checkpoints.loadCheckpointMetadata(dataInputStream, classLoader, null);
        Collection<OperatorState> operatorStates = checkpointMetadata.getOperatorStates();
        for (OperatorState operatorState : operatorStates) {
            OperatorSubtaskState state = operatorState.getState(1);
            if (state == null) {
                continue;
            }
            StateObjectCollection<KeyedStateHandle> managedKeyedState = state.getManagedKeyedState();
            if (managedKeyedState == null || managedKeyedState.isEmpty()) {
                continue;
            }

            for (KeyedStateHandle keyedStateHandle :managedKeyedState) {

            }
        }
        System.out.println("===");
    }
}