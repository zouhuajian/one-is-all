package org.coastline.one.hadoop.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapred.lib.CombineFileInputFormat;
import org.apache.hadoop.mapred.lib.CombineSequenceFileInputFormat;
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
        //System.setProperty("HADOOP_USER_NAME", "root");
        Configuration configuration = new Configuration();
        configuration.set("dfs.replication", "1");
        fileSystem = FileSystem.get(configuration);
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

    public FileSystem getFileSystem() {
        return fileSystem;
    }

    public boolean write(byte[] data, String path, String file) {
        FSDataOutputStream outputStream = null;
        try {
            Path hdfsPath = new Path(path);
            if (!fileSystem.exists(hdfsPath)) {
                fileSystem.mkdirs(hdfsPath);
            }
            outputStream = fileSystem.create(new Path(path + file));
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

    public boolean append(byte[] data, String path, String file) {
        FSDataOutputStream outputStream = null;
        try {
            Path hdfsPath = new Path(path);
            if (!fileSystem.exists(hdfsPath)) {
                fileSystem.mkdirs(hdfsPath);
            }
            outputStream = fileSystem.append(new Path(path + file));
            outputStream.write(data);
        } catch (Exception e) {
            LOGGER.error("append data failed.", e);
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

    public boolean mkdir(String path) {
        try {
            Path hdfsPath = new Path(path);
            if (!fileSystem.exists(hdfsPath)) {
                fileSystem.mkdirs(hdfsPath);
            }
            return true;
        } catch (Exception e) {
            LOGGER.error("mkdir failed.", e);
            return false;
        }
    }

    public boolean rename(String oldPath, String newPath) {
        try {
            return fileSystem.rename(new Path(oldPath), new Path(newPath));
        } catch (Exception e) {
            LOGGER.error("rename path failed.", e);
            return false;
        }
    }

    public FileStatus[] listStatus(String path) {
        try {
            Path hdfsPath = new Path(path);
            return fileSystem.listStatus(hdfsPath);
        } catch (Exception e) {
            LOGGER.error("list path status failed.", e);
            return new FileStatus[0];
        }
    }

    public FileStatus status(String path) {
        try {
            Path hdfsPath = new Path(path);
            return fileSystem.getFileStatus(hdfsPath);
        } catch (Exception e) {
            LOGGER.error("get path status failed.", e);
            return null;
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

    public void command(String path) {
        CombineFileInputFormat<String, String> fileInputFormat = new CombineSequenceFileInputFormat<>();
    }
}
