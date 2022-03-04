package org.coastline.one.common.java.lock;

import java.lang.management.ThreadInfo;

/**
 * @author Jay.H.Zou
 * @date 2022/2/24
 */
public class DeadlockHandler {
    public void handle(final ThreadInfo[] deadLockThreads) {
        if (deadLockThreads != null) {
            System.err.println("Deadlock detected!");
            // Map<Thread,StackTraceElement[]> stackTraceMap = Thread.getAllStackTraces();
            for (ThreadInfo threadInfo : deadLockThreads) {
                if (threadInfo != null) {
                    for (Thread thread : Thread.getAllStackTraces().keySet()) {
                        if (thread.getId() == threadInfo.getThreadId()) {
                            System.err.println(threadInfo.toString().trim());
                            for (StackTraceElement ste : thread.getStackTrace()) {
                                System.err.println("t" + ste.toString().trim());
                            }
                        }
                    }
                }
            }

        }
    }
}
