package org.coastline.one.flink.common.checkpoint;

import org.apache.flink.runtime.checkpoint.Checkpoints;
import org.coastline.one.flink.common.config.ConfigLoader;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Jay.H.Zou
 * @date 2022/7/9
 */
public class TestLoadCheckPoint {
    public static void main(String[] args) throws IOException {
        ClassLoader classLoader = ConfigLoader.class.getClassLoader();
        InputStream resourceAsStream = classLoader.getResourceAsStream("/checkpoint/_metadata");
        assert resourceAsStream != null;
        DataInputStream dataInputStream = new DataInputStream(resourceAsStream);
        Checkpoints.loadCheckpointMetadata(dataInputStream, null, null);
    }
}
