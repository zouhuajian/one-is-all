package org.coastline.one.common.concurrency;

/**
 * @author Jay.H.Zou
 * @date 2020/8/30
 */
public class ThreadBlockState {
    private static Object object = new Object();

    public static void main(String[] args) {
        Runnable task = new Runnable() {

            @Override
            public void run() {
                synchronized (object) {
                    long begin = System.currentTimeMillis();
                    long end = System.currentTimeMillis();
                    // 让线程运行5分钟,会一直持有object的监视器
                    while ((end - begin) <= 5 * 60 * 1000) {

                    }
                }
            }
        };

        new Thread(task, "t1").start();
        new Thread(task, "t2").start();
    }

}
