package org.coastline.one.common.mmap;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Java MMAP 与 FileChannel操作文件对比
 * @author Jay.H.Zou
 * @date 2021/7/23
 */
public class MMAPTest {

    public static void main(String[] args) throws Exception {
        //记录开始时间
        long start = System.currentTimeMillis();
        //通过RandomAccessFile的方式获取文件的Channel，这种方式针对随机读写的文件较为常用，我们用文件一般是随机读写
        RandomAccessFile randomAccessFile = new RandomAccessFile("./queue", "rw");
        FileChannel channel = randomAccessFile.getChannel();
        System.out.println("FileChannel\t初始化：" + (System.currentTimeMillis() - start) + "ms");

        //内存映射文件，模式是READ_WRITE，如果文件不存在，就会被创建
        MappedByteBuffer mappedByteBuffer1 = channel.map(FileChannel.MapMode.READ_WRITE, 0, 128 * 1024 * 1024);
        MappedByteBuffer mappedByteBuffer2 = channel.map(FileChannel.MapMode.READ_WRITE, 0, 128 * 1024 * 1024);

        System.out.println("MMAPFile\t初始化：" + (System.currentTimeMillis() - start) + "ms");

        start = System.currentTimeMillis();
        testFileChannelSequentialRW(channel);
        System.out.println("FileChannel\t顺序读写：" + (System.currentTimeMillis() - start) + "ms");

        start = System.currentTimeMillis();
        testFileMMapSequentialRW(mappedByteBuffer1, mappedByteBuffer2);
        System.out.println("MMAPFile\t顺序读写：" + (System.currentTimeMillis() - start) + "ms");

        start = System.currentTimeMillis();
        try {
            testFileChannelRandomRW(channel);
            System.out.println("FileChannel\t随机读写：" + (System.currentTimeMillis() - start) + "ms");
        } finally {
            randomAccessFile.close();
        }

        //文件关闭不影响MMAP写入和读取
        start = System.currentTimeMillis();
        testFileMMapRandomRW(mappedByteBuffer1, mappedByteBuffer2);
        System.out.println("MMAPFile\t随机读写：" + (System.currentTimeMillis() - start) + "ms");
    }


    /**
     * FileChannel 顺序读写
     *
     * @param fileChannel
     * @throws Exception
     */
    public static void testFileChannelSequentialRW(FileChannel fileChannel) throws Exception {
        byte[] bytes = "测试字符串1测试字符串1测试字符串1测试字符串1测试字符串1测试字符串1测试字符串1测试字符串1测试字符串1测试字符串1测试字符串1".getBytes();
        byte[] to = new byte[bytes.length];
        //分配直接内存，减少复制
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(bytes.length);
        //顺序写入
        for (int i = 0; i < 100000; i++) {
            byteBuffer.put(bytes);
            byteBuffer.flip();
            fileChannel.write(byteBuffer);
            byteBuffer.flip();
        }
        fileChannel.position(0);
        //顺序读取
        for (int i = 0; i < 100000; i++) {
            fileChannel.read(byteBuffer);
            byteBuffer.flip();
            byteBuffer.get(to);
            byteBuffer.flip();
        }
    }

    /**
     * MMAP 顺序读写
     *
     * @param mappedByteBuffer1
     * @param mappedByteBuffer2
     * @throws Exception
     */
    public static void testFileMMapSequentialRW(MappedByteBuffer mappedByteBuffer1, MappedByteBuffer mappedByteBuffer2) throws Exception {
        byte[] bytes = "测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2".getBytes();
        byte[] to = new byte[bytes.length];

        //顺序写入
        for (int i = 0; i < 100000; i++) {
            mappedByteBuffer1.put(bytes);
        }
        //顺序读取
        for (int i = 0; i < 100000; i++) {
            mappedByteBuffer2.get(to);
        }
    }

    public static void testFileChannelRandomRW(FileChannel fileChannel) throws Exception {
        try {
            ThreadLocalRandom random = ThreadLocalRandom.current();

            byte[] bytes = "测试字符串1测试字符串1测试字符串1测试字符串1测试字符串1测试字符串1测试字符串1测试字符串1测试字符串1测试字符串1测试字符串1".getBytes();
            byte[] to = new byte[bytes.length];
            //分配直接内存，减少复制
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(bytes.length);
            //随机写入
            for (int i = 0; i < 100000; i++) {
                byteBuffer.put(bytes);
                byteBuffer.flip();
                fileChannel.position(random.nextInt(bytes.length * 100000));
                fileChannel.write(byteBuffer);
                byteBuffer.flip();
            }
            //随机读取
            for (int i = 0; i < 100000; i++) {
                fileChannel.position(random.nextInt(bytes.length * 100000));
                fileChannel.read(byteBuffer);
                byteBuffer.flip();
                byteBuffer.get(to);
                byteBuffer.flip();
            }
        } finally {
            fileChannel.close();
        }
    }

    public static void testFileMMapRandomRW(MappedByteBuffer mappedByteBuffer1, MappedByteBuffer mappedByteBuffer2) throws Exception {
        byte[] bytes = "测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2".getBytes();
        byte[] to = new byte[bytes.length];
        ThreadLocalRandom random = ThreadLocalRandom.current();
        // 随机写入
        for (int i = 0; i < 100000; i++) {
            mappedByteBuffer1.position(random.nextInt(bytes.length * 100000));
            mappedByteBuffer1.put(bytes);
        }
        //随机读取
        for (int i = 0; i < 100000; i++) {
            mappedByteBuffer2.position(random.nextInt(bytes.length * 100000));
            mappedByteBuffer2.get(to);
        }
    }
}
