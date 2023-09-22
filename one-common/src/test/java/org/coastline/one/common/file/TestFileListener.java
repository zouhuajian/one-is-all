package org.coastline.one.common.file;

import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.junit.Test;

import java.io.File;
import java.util.concurrent.TimeUnit;

/**
 * @author Jay.H.Zou
 * @date 2023/9/8
 */
public class TestFileListener {

    @Test
    public void testListener() throws Exception {
        File directory = new File("/Users/zouhuajian/data/projects/jay/one-is-all/one-data");
        // 轮询间隔 5 秒
        long interval = TimeUnit.SECONDS.toMillis(10);
        // 创建一个文件观察器用于处理文件的格式
        /*FileAlterationObserver observer = new FileAlterationObserver(directory,
                FileFilterUtils.and(FileFilterUtils.fileFileFilter(), FileFilterUtils.suffixFileFilter(".txt")));*/
        FileAlterationObserver observer = new FileAlterationObserver(directory,
                FileFilterUtils.and(FileFilterUtils.fileFileFilter()));
        // 设置文件变化监听器
        observer.addListener(new FileListener());
        FileAlterationMonitor monitor = new FileAlterationMonitor(interval, observer);
        monitor.start();
        System.err.println("restart...");
        TimeUnit.HOURS.sleep(1);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                monitor.stop();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }));

    }
}
