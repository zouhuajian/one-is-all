package org.coastline.one.hadoop.oss;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.IOException;

/**
 * @author Jay.H.Zou
 * @date 2023/12/13
 */
public class OSSHDFSClient {

    public static FileSystem buildFileSystem() throws IOException {
        Path path = new Path("oss://one-bigdata/");
        // FileSystem fs = FileSystem.get(path.toUri(), new Configuration());
        FileSystem fs = path.getFileSystem(new Configuration());
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                fs.close();
                System.out.println("Filesystem is closed...");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }));
        return fs;
    }
}
