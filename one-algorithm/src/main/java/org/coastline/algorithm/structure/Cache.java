package org.coastline.algorithm.structure;

// 实现一个有限容量cache
// 要求能每分钟周期性删除掉访问频次最小的那个元素（如果该元素的访问频次小于trimThreshold的话）
// put:当容量到达上限时，若访问频次最小的那个元素，访问频次大于trimThreshold，抛出异常
// put:当容量到达上限时，若访问频次最小的那个元素，访问频次<=trimThreshold，删除该元素
// 要求尽量完整，不要语法错误

/**
 * 2020/10/23 饿了么蜂鸟专送面试题
 */

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Cache<K, V> {


    private int size;

    // 清除周期
    private int trimIntervalMs;

    // 访问频次
    private int trimThreshold;

    private Map<K, AtomicInteger> countMap;

    private Map<K, V> cacheMap;


    private ScheduledExecutorService timer;

    private final Lock lock;


    public Cache(int size, int trimIntervalMs, int trimThreshold) {
        // TODO: 检测入参
        this.size = size;
        this.trimIntervalMs = trimIntervalMs;
        this.trimThreshold = trimThreshold;
        countMap = new HashMap<>(size);
        cacheMap = new HashMap<>(size);
        lock = new ReentrantLock();
        timer = Executors.newScheduledThreadPool(1);
        timer.scheduleAtFixedRate(new CleanTask(), 0, 0, TimeUnit.MILLISECONDS);
    }

    public void resetTrimThreshold(int trimThreshold) {
        this.trimThreshold = trimThreshold;
    }

    public void resetSize(int size) {
        this.size = size;
    }

    public void put(K k, V v) {
        // lock.lock();
        // try {
        int currentSize = cacheMap.size();
        if (currentSize >= size) {
            K minKey = getMinKey();
            int min = countMap.get(minKey).get();
            if (min > trimThreshold) {
                throw new RuntimeException("xx");
            } else {
                countMap.remove(minKey);
                cacheMap.remove(minKey);
            }
        }
        countMap.put(k, new AtomicInteger(0));
        cacheMap.put(k, v);
        // } finally {
        //     lock.unlock();
        // }
    }

    public V get(K k) {
//        lock.lock();
//        try {
            V v = cacheMap.get(k);
            if (v == null) {
                return null;
            }
            AtomicInteger count = countMap.get(k);
            count.incrementAndGet();
            return v;
//        } finally {
//            lock.unlock();
//        }
    }

    private K getMinKey() {
        K minKey = null;
        int min = -1;
        for (Map.Entry<K, AtomicInteger> kAtomicIntegerEntry : countMap.entrySet()) {
            int keyCount = kAtomicIntegerEntry.getValue().get();
            if (min == -1) {
                min = keyCount;
                minKey = kAtomicIntegerEntry.getKey();
            } else if (min > keyCount) {
                min = keyCount;
                minKey = kAtomicIntegerEntry.getKey();
            }
        }
        return minKey;
    }

    class CleanTask implements Runnable {

        @Override
        public void run() {
//            lock.lock();
//            try {
                K minKey = getMinKey();
                if (minKey == null) {
                    return;
                }
                int min = countMap.get(minKey).get();
                if (min < trimThreshold) {
                    countMap.remove(minKey);
                    cacheMap.remove(minKey);
                }
//            } finally {
//                lock.unlock();
//            }
        }
    }


}