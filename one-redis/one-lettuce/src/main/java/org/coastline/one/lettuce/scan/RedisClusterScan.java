package org.coastline.one.lettuce.scan;

import  io.lettuce.core.KeyScanCursor;
import io.lettuce.core.RedisURI;
import io.lettuce.core.ScanArgs;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import io.lettuce.core.cluster.RedisClusterClient;
import io.lettuce.core.cluster.api.StatefulRedisClusterConnection;
import io.lettuce.core.cluster.models.partitions.Partitions;
import io.lettuce.core.cluster.models.partitions.RedisClusterNode;
import io.lettuce.core.models.role.RedisInstance;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Jay.H.Zou
 * @date 2020/7/15
 */
public class RedisClusterScan {

    private static int COUNT = 10000;

    private static String MATCH = "";

    private static ExecutorService THREAD_POOL;

    private static StatefulRedisClusterConnection<String, String> CONNECTION;

    private static void initResource() {
        int core = Runtime.getRuntime().availableProcessors();
        THREAD_POOL = new ThreadPoolExecutor(core, core, 60L, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(100),
                new ThreadPoolExecutor.CallerRunsPolicy()
        );

        RedisURI redisURI = RedisURI.create("", 12);
        RedisClusterClient redisClusterClient = RedisClusterClient.create(redisURI);
        CONNECTION = redisClusterClient.connect();
    }

    /**
     * process keys
     *
     * @param keys
     */
    private static void process(List<String> keys) {
        System.out.println(keys);
    }

    static class ScanCallback implements Callable<Integer> {

        private String masterId;

        public ScanCallback(String masterId) {
            this.masterId = masterId;
        }

        @Override
        public Integer call() throws Exception {
            AtomicInteger count = new AtomicInteger(0);
            StatefulRedisConnection<String, String> nodeConnection = CONNECTION.getConnection(masterId);
            ScanArgs scanArgs = ScanArgs.Builder.limit(COUNT).match(MATCH);
            RedisCommands<String, String> command = nodeConnection.sync();
            KeyScanCursor<String> scanCursor = command.scan(scanArgs);
            List<String> keys = scanCursor.getKeys();
            count.addAndGet(keys.size());
            process(keys);
            while (!scanCursor.isFinished()) {
                scanCursor = command.scan(scanCursor, scanArgs);
                keys = scanCursor.getKeys();
                count.addAndGet(keys.size());
                process(keys);
            }
            return count.get();
        }
    }

    public static void main(String[] args) {
        initResource();
        Partitions partitions = CONNECTION.getPartitions();
        List<String> masterIds = new LinkedList<>();
        for (RedisClusterNode node : partitions) {
            String nodeId = node.getNodeId();
            RedisInstance.Role role = node.getRole();
            if (role == RedisInstance.Role.MASTER) {
                masterIds.add(nodeId);
            }
        }

        List<Future<Integer>> futures = new ArrayList<>(masterIds.size());
        for (String masterId : masterIds) {
            futures.add(THREAD_POOL.submit(new ScanCallback(masterId)));
        }
        // 计数
        AtomicInteger count = new AtomicInteger();
        futures.forEach(future -> {
            try {
                count.addAndGet(future.get());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }


}
