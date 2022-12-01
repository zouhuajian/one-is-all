package org.coastline.one.hadoop.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.coastline.one.hadoop.hbase.OneHBaseClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author Jay.H.Zou
 * @date 2021/10/14
 */
public class OneJuiceFSHDFSClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(OneHBaseClient.class);

    private final FileSystem fileSystem;

    public OneJuiceFSHDFSClient() throws IOException {
        Configuration conf = new Configuration();
        conf.set("fs.jfs.impl", "io.juicefs.JuiceFileSystem");
        conf.set("juicefs.meta", "mysql://root:xxx@(xxx:3306)/juicefs");  // JuiceFS 元数据引擎地址
        //conf.set("juicefs.debug", "true");
        Path p = new Path("jfs://onefs/");  // 请替换 {JFS_NAME} 为正确的值
        fileSystem = p.getFileSystem(conf);
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

    public void listRoot() throws IOException {
        FileStatus[] fileStatuses = fileSystem.listStatus(new Path("/"));
        // 遍历 JuiceFS 文件系统并打印文件路径
        for (FileStatus status : fileStatuses) {
            System.out.println(status.getPath());
        }
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

    public byte[] read(String path) {
        FSDataOutputStream outputStream = null;
    /*    try {
            Path hdfsPath = new Path(path);
            if (!fileSystem.exists(hdfsPath)) {
                return new byte[0];
            }
            if (!fileSystem.getFileStatus(hdfsPath).isDirectory()) {
                return new byte[0];
            }
            outputStream = fileSystem.create(new Path(path));
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
        }*/
        return new byte[0];
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
            ContentSummary contentSummary = fileSystem.getContentSummary(hdfsPath);
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
        OneJuiceFSHDFSClient oneHDFSClient = new OneJuiceFSHDFSClient();
        boolean write = oneHDFSClient.write("test file".getBytes(StandardCharsets.UTF_8), "/", "test.txt");
        oneHDFSClient.listRoot();
    }
}
