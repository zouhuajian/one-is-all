package org.coastline.one.hadoop.oss;

import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * @author Jay.H.Zou
 * @date 2023/12/14
 */
public class TestOSSHDFSClient {
    private static final byte[] CONTENT = "Hi".getBytes();
    private static final byte[] APPEND = " X".getBytes();

    private FileSystem fs;

    @Before
    public void init() throws IOException {
        fs = OSSHDFSClient.buildFileSystem();
    }

    @Test
    public void testListStatus() throws IOException {
        FileStatus[] fileStatuses = fs.listStatus(new Path("oss://one-bigdata/"));
        for (FileStatus fileStatus : fileStatuses) {
            System.out.println(fileStatus.getPath());
        }
    }

    @Test
    public void testMkdir() throws IOException {
        boolean mkdirs = fs.mkdirs(new Path("oss://one-bigdata/warehouse"));
        Assert.assertTrue(mkdirs);
    }

    @Test
    public void testCreate() throws IOException {
        Path path = new Path("oss://one-bigdata/data/file_tmp");
        try (FSDataOutputStream out = fs.create(path, true)) {
            out.write(CONTENT);
        }
        FileStatus fileStatus = fs.getFileStatus(path);
        System.out.println(fileStatus);

    }
}
