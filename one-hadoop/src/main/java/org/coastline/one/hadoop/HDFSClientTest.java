package org.coastline.one.hadoop;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.util.Progressable;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;

/**
 * @author Jay.H.Zou
 * @date 2021/10/14
 */
public class HDFSClientTest {

    public static void main(String[] args) throws IOException, InterruptedException {
        Configuration conf = new Configuration();
        conf.setBoolean("dfs.support.append", true);
        FileSystem fs = FileSystem.get(URI.create("hdfs://xx"), conf);
        Path path = new Path("/csoss/traces/local/local-test");

        FSDataOutputStream outputStream = fs.create(path, new Progressable() {
            @Override
            public void progress() {
                System.out.println("process...");
            }
        });
        /*outputStream.close();
        outputStream = fs.append(path);*/

        for (FileStatus fileStatus : fs.listStatus(path)) {
            System.out.println(fileStatus);
        }
        for (int i = 0; i < 3; i++) {
            outputStream.write(("one").getBytes(StandardCharsets.UTF_8));
            System.out.println(outputStream.size());
        }
        outputStream.hflush();
        outputStream.close();
        int length = 3;
        FSDataInputStream inputStream = fs.open(path, length);
        byte[] bytes = new byte[length];
        inputStream.readFully(4, bytes);
        System.out.println(new String(bytes));
        inputStream.close();
        fs.close();
    }
}
