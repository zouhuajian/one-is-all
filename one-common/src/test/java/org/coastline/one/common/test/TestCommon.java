package org.coastline.one.common.test;

import org.junit.Test;

import java.nio.ByteBuffer;

/**
 * @author Jay.H.Zou
 * @date 2023/3/9
 */
public class TestCommon {

    @Test
    public void getJavaVersion() {
        String javaVersion = System.getProperty("java.version");
        System.out.println("Java version is " + javaVersion);
    }

    @Test
    public void allocMemory() {
        //分配128MB直接内存
        while (true) {
            ByteBuffer bb = ByteBuffer.allocateDirect(1024);
        }
    }

    @Test
    public void testStr() {
        String[] names = "/".split("/");
        for (String name : names) {
            System.err.println("--" +name);
        }

    }
}
