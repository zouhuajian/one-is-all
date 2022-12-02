package org.coastline.one.hadoop.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.coastline.one.hadoop.hbase.OneHBaseClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author Jay.H.Zou
 * @date 2021/10/14
 */
public class OneHDFSClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(OneHBaseClient.class);

    private static final String NN1 = "hdfs://xxx:4007";
    private static final String NN2 = "hdfs://yyy:4007";

    private final FileSystem fileSystem;

    public OneHDFSClient() throws IOException {
        Configuration conf = new Configuration();
        conf.setInt("io.file.buffer.size", 65536); // 64kB
        // 默认文件系统的名称
        conf.set("fs.defaultFS", "hdfs://coastline");
        // namenode 集群的名字
        conf.set("dfs.nameservices", "coastline");
        // ns1 下有两个 NameNode，逻辑地址分别是 nn1，nn2
        conf.set("dfs.ha.namenodes.coastline", "nn1,nn2");
        // nn1 的 http 通信地址
        conf.set("dfs.namenode.rpc-address.coastline.nn1", NN1);
        // nn2 的 http 通信地址
        conf.set("dfs.namenode.rpc-address.coastline.nn2", NN2);
        // 配置读取失败自动切换的实现方式
        conf.set("dfs.client.failover.proxy.provider.coastline", "org.apache.hadoop.hdfs.server.namenode.ha.ConfiguredFailoverProxyProvider");
        fileSystem = FileSystem.get(conf);
        Runtime.getRuntime().addShutdownHook(new Thread(this::close));
    }

    private void close() {
        if (fileSystem != null) {
            try {
                fileSystem.close();
            } catch (IOException e) {
                LOGGER.error("close file system error", e);
            }
        }
    }

    public boolean write(byte[] data, String path, String file) {
        FSDataOutputStream outputStream = null;
        try {
            Path hdfsPath = new Path(path);
            if (!fileSystem.exists(hdfsPath)) {
                fileSystem.mkdirs(hdfsPath);
            }
            outputStream = fileSystem.append(new Path(path + file));
            outputStream.write(data);
        } catch (Exception e) {
            LOGGER.error("write data failed.", e);
            return false;
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (Exception e) {
                    LOGGER.error("close stream failed.", e);
                }
            }
        }
        return true;
    }

    public boolean rename(String oldPath, String newPath) {
        try {
            return fileSystem.rename(new Path(oldPath), new Path(newPath));
        } catch (Exception e) {
            LOGGER.error("rename path failed.", e);
            return false;
        }
    }

    public FileStatus[] list(String path) {
        try {
            Path hdfsPath = new Path(path);
            return fileSystem.listStatus(hdfsPath);
        } catch (Exception e) {
            LOGGER.error("rename path failed.", e);
            return new FileStatus[0];
        }
    }

    public boolean delete(String path) {
        try {
            return fileSystem.delete(new Path(path), true);
        } catch (Exception e) {
            LOGGER.error("rename path failed.", e);
            return false;
        }
    }

    public static void main(String[] args) throws IOException {
       OneHDFSClient oneHDFSClient = new OneHDFSClient();
        for (FileStatus fileStatus : oneHDFSClient.list("/")) {
            long accessTime = fileStatus.getAccessTime();
            System.out.println(fileStatus);
            System.out.println();
        }

    }
}
