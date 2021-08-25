package org.coastline.one.common.concurrency;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Jay.H.Zou
 * @date 2020/6/25
 */
public class ThreadPoolLearn {

    public static void main(String[] args) {
        Executor executor = new ThreadPoolExecutor(5, 10,
                1000, TimeUnit.MICROSECONDS,
                new ArrayBlockingQueue<>(100),
                Thread::new,
                new ThreadPoolExecutor.CallerRunsPolicy()
        );

        executor.execute(() -> {
            System.out.println(111);
        });
    }

}
