package org.coastline.one.common;

import java.nio.ByteBuffer;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author Jay.H.Zou
 * @date 2022/2/25
 */
public class TempTest {

    public static void main(String[] args) throws InterruptedException {
        //分配128MB直接内存
        while (true) {
            ByteBuffer bb = ByteBuffer.allocateDirect(1024);
        }

    }
}
