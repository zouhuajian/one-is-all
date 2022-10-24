package org.coastline.one.common.java.concurrency;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Jay.H.Zou
 * @date 2022/10/24
 */
public class TestCountDownLatch {

    private static CountDownLatch startSignal = new CountDownLatch(1);

    public static void main(String[] args) {
        ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(1);
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                startSignal.countDown();
            }
        }, 1, 3, TimeUnit.SECONDS);
        while (true) {
            try {
                startSignal.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("------------ run finish ------------");
            startSignal = new CountDownLatch(1);
        }
    }
}
