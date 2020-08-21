package org.coastline.one.lettuce.scan;

import io.lettuce.core.KeyScanCursor;
import io.lettuce.core.RedisURI;
import io.lettuce.core.ScanArgs;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import io.lettuce.core.cluster.RedisClusterClient;
import io.lettuce.core.cluster.api.StatefulRedisClusterConnection;
import io.lettuce.core.cluster.models.partitions.Partitions;
import io.lettuce.core.cluster.models.partitions.RedisClusterNode;
import io.lettuce.core.models.role.RedisInstance;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Jay.H.Zou
 * @date 2020/7/15
 */
public class RedisClusterSlotStatistics {

    private static int COUNT = 5000;

    private static String MATCH = "*";

    private static ExecutorService THREAD_POOL;

    private static StatefulRedisClusterConnection<String, String> CONNECTION;

    private static AtomicLong count = new AtomicLong();

    private static Map<String, AtomicLong> countMap = new HashMap<>();

    private static Map<String, List<String>> keyMap = new HashMap<>();

    private static void initResource() {
        int core = Runtime.getRuntime().availableProcessors();
        THREAD_POOL = new ThreadPoolExecutor(core, core, 60L, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(100),
                new ThreadPoolExecutor.CallerRunsPolicy()
        );
        RedisURI redisURI = RedisURI.create("", 1);
        RedisClusterClient redisClusterClient = RedisClusterClient.create(redisURI);
        CONNECTION = redisClusterClient.connect();
    }

    /**
     * process keys
     *
     * @param keys
     */
    private static void process(String address, List<String> keys) {
        if (keys == null || keys.isEmpty()) {
            return;
        }
        count.addAndGet(keys.size());
        AtomicLong atomicLong = countMap.get(address);
        if (atomicLong == null) {
            atomicLong = new AtomicLong();
        }
        atomicLong.addAndGet(keys.size());
        countMap.put(address, atomicLong);

        List<String> list = keyMap.get(address);
        if (list == null) {
            list = new LinkedList<>();
        }
        for (String key : keys) {
            String substring = key.substring(3);
            list.add(substring);
        }
        keyMap.put(address, list);
    }

    static class ScanCallback implements Callable<Boolean> {

        private String masterId;

        private String address;

        public ScanCallback(String masterId, String address) {
            this.masterId = masterId;
            this.address = address;
        }

        @Override
        public Boolean call() throws Exception {
            StatefulRedisConnection<String, String> nodeConnection = CONNECTION.getConnection(masterId);
            ScanArgs scanArgs = ScanArgs.Builder.limit(COUNT).match(MATCH);
            RedisCommands<String, String> command = nodeConnection.sync();
            KeyScanCursor<String> scanCursor = command.scan(scanArgs);
            List<String> keys = scanCursor.getKeys();
            process(address, keys);
            while (!scanCursor.isFinished()) {
                scanCursor = command.scan(scanCursor, scanArgs);
                keys = scanCursor.getKeys();
                process(address, keys);
            }
            return true;
        }
    }

    public static void main(String[] args) {
        initResource();
        Partitions partitions = CONNECTION.getPartitions();
        Map<String, String> masterMap = new HashMap<>();
        for (RedisClusterNode node : partitions) {
            String nodeId = node.getNodeId();
            RedisInstance.Role role = node.getRole();
            RedisURI uri = node.getUri();
            if (role == RedisInstance.Role.MASTER) {
                masterMap.put(nodeId, uri.getHost() + ":" + uri.getPort());
            }
        }
        System.out.println("=========================");
        List<Future<Boolean>> futures = new ArrayList<>(masterMap.size());
        masterMap.forEach((masterId, address) -> {
            futures.add(THREAD_POOL.submit(new ScanCallback(masterId, address)));
        });
        futures.forEach(future -> {
            try {
                future.get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        System.out.println("=========================");
        System.out.println("All: " + count.get());
        countMap.forEach((key, value) -> {
            System.out.println(key + ": " + value);
        });
        keyMap.forEach((key, value) -> {
            System.out.println(key + ": " + value);
        });
    }


}
