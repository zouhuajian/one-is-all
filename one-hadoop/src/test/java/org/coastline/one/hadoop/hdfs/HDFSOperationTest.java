package org.coastline.one.hadoop.hdfs;

import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.permission.AclStatus;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author Jay.H.Zou
 * @date 2023/4/9
 */
public class HDFSOperationTest {


    private OneHDFSClient hdfsClient;

    @Before
    public void init() throws IOException {
        hdfsClient = new OneHDFSClient();
    }

    @Test
    public void testListStatus() {
        String path = "/user/root/bigdata.tmall_order_report_agg_partition_tbl";
        FileStatus[] fileStatuses = hdfsClient.listStatus("/data/test/");
        for (FileStatus fileStatus : fileStatuses) {
            System.out.println(fileStatus);
        }
        FileStatus status = hdfsClient.status(path);
        System.out.println(status);
    }

    @Test
    public void testGetStatus() {
        FileStatus status = hdfsClient.status("/");
        System.out.println(status);
    }

    @Test
    public void testWrite() throws IOException {
       /* for (FileStatus fileStatus : oneHDFSClient.list("/")) {
            long accessTime = fileStatus.getAccessTime();
            System.out.println(fileStatus);
            System.out.println();
        }*/
        byte[] content = "123".getBytes(StandardCharsets.UTF_8);
        System.out.println("file size = " + content.length);
        boolean write = hdfsClient.write(content, "/data/test/", "one.txt");
        System.out.println("write status: " + write);

        FileStatus[] list = hdfsClient.listStatus("/data/test");
        for (FileStatus fileStatus : list) {
            System.out.println(fileStatus);
        }
    }


    @Test
    public void testCommand() throws IOException {
        FileSystem fileSystem = hdfsClient.getFileSystem();
        AclStatus aclStatus = fileSystem.getAclStatus(new Path("/data/test/new_dir"));
        System.out.println(aclStatus);

    }

}
