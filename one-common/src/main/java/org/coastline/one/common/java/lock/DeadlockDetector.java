package org.coastline.one.common.java.lock;

import java.lang.management.ManagementFactory;
import java.lang.management.MonitorInfo;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author Jay.H.Zou
 * @date 2022/2/24
 */
public class DeadlockDetector {
    private final ScheduledExecutorService schedule = Executors.newScheduledThreadPool(1);

    private final ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();

    public void start() {
        this.schedule.scheduleAtFixedRate(() -> {
            ThreadInfo[] threads = threadMXBean.dumpAllThreads(true, true);
            handle(threads);
            long[] deadlockedThreads = threadMXBean.findDeadlockedThreads();
            ThreadInfo[] deadlockThreads = threadMXBean.getThreadInfo(deadlockedThreads);
            //handle(deadlockThreads);
        }, 2, 10, TimeUnit.SECONDS);
    }

    private void handle(final ThreadInfo[] threads) {
        StringBuilder threadDump = new StringBuilder(System.lineSeparator());
        if (threads != null) {
            System.err.println("=========================== THREADS ===========================");
            for (ThreadInfo threadInfo : threads) {
                MonitorInfo[] lockedMonitors = threadInfo.getLockedMonitors();
                long blockedCount = threadInfo.getBlockedCount();
                threadDump.append(threadInfo);
            }
        }
        System.out.println(threadDump);
    }


}