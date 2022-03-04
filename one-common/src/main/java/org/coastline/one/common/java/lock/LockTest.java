package org.coastline.one.common.java.lock;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.TimeUnit;

/**
 * @author Jay.H.Zou
 * @date 2022/2/10
 */
public class LockTest {

    public static void main(String[] args) {
        DeadlockDetector deadlockDetector = new DeadlockDetector(new DeadlockHandler(), 5, TimeUnit.SECONDS);
        deadlockDetector.start();
        final Object lock1 = new Object();
        final Object lock2 = new Object();

        Thread thread1 = new Thread(() -> {
            synchronized (lock1) {
                System.out.println("Thread1 acquired lock1");

                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                synchronized (lock2) {
                    System.out.println("Thread1 acquired lock2");
                }
            }
        });
        thread1.setName("Thread_1");
        thread1.start();
        Thread thread2 = new Thread(() -> {
            synchronized (lock2) {
                System.out.println("Thread2 acquired lock2");

                synchronized (lock1) {
                    System.out.println("Thread2 acquired lock1");
                }
            }
        });
        thread2.setName("Thread_2");
        thread2.start();
    }


    private static void print() {
        StringBuilder threadDump = new StringBuilder(System.lineSeparator());
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        int threadCount = threadMXBean.getThreadCount();
        int peakThreadCount = threadMXBean.getPeakThreadCount();
        long totalStartedThreadCount = threadMXBean.getTotalStartedThreadCount();
        int daemonThreadCount = threadMXBean.getDaemonThreadCount();


        long[] monitorDeadlockedThreads = threadMXBean.findMonitorDeadlockedThreads();
        long[] deadlockedThreads = threadMXBean.findDeadlockedThreads();
        for (ThreadInfo threadInfo : threadMXBean.dumpAllThreads(true, true)) {
            threadDump.append(threadInfo.toString());
        }
    }


}
