package org.coastline.one.common.java.lock;

import java.lang.management.ManagementFactory;
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
    private final DeadlockHandler deadlockHandler;
    private final long period;
    private final TimeUnit unit;
    private final ThreadMXBean mBean = ManagementFactory.getThreadMXBean();

    private final ScheduledExecutorService schedule = Executors.newScheduledThreadPool(1);

    public DeadlockDetector(final DeadlockHandler deadlockHandler,
                            final long period, final TimeUnit unit) {
        this.deadlockHandler = deadlockHandler;
        this.period = period;
        this.unit = unit;
    }

    final Runnable deadlockCheck = new Runnable() {

        @Override
        public void run() {
            long[] deadlockedThreads = DeadlockDetector.this.mBean.findDeadlockedThreads();

            if (deadlockedThreads != null) {
                ThreadInfo[] threadInfos = DeadlockDetector.this.mBean.getThreadInfo(deadlockedThreads);
                DeadlockDetector.this.deadlockHandler.handle(threadInfos);
            }
        }

    };

    public void start() {
        this.schedule.scheduleAtFixedRate(this.deadlockCheck, this.period, this.period, this.unit);
    }


}