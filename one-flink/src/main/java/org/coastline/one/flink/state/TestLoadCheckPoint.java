package org.coastline.one.flink.state;

import org.apache.flink.runtime.checkpoint.Checkpoints;
import org.apache.flink.runtime.checkpoint.MasterState;
import org.apache.flink.runtime.checkpoint.OperatorState;
import org.apache.flink.runtime.checkpoint.metadata.CheckpointMetadata;

import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collection;

/**
 * @author Jay.H.Zou
 * @date 2022/7/9
 */
public class TestLoadCheckPoint {

    static String path = "/Users/zouhuajian/Jay/project/zouhuajian/one-is-all/one-flink/checkpoint/9b16c03b110d7d50b419ca81941d1165/chk-12/_metadata";

    public static void main(String[] args) throws IOException {
        ClassLoader classLoader = TestLoadCheckPoint.class.getClassLoader();
        DataInputStream dataInputStream = new DataInputStream(Files.newInputStream(new File(path).toPath()));
        CheckpointMetadata checkpointMetadata = Checkpoints.loadCheckpointMetadata(dataInputStream, classLoader, null);
        long checkpointId = checkpointMetadata.getCheckpointId();
        Collection<MasterState> masterStates = checkpointMetadata.getMasterStates();

        for (OperatorState operatorState : checkpointMetadata.getOperatorStates()) {
        }

        System.out.println(checkpointId);
    }
}