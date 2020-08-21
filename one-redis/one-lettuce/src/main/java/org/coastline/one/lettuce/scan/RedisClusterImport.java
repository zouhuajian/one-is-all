package org.coastline.one.lettuce.scan;

import io.lettuce.core.KeyScanCursor;
import io.lettuce.core.RedisURI;
import io.lettuce.core.ScanArgs;
import io.lettuce.core.ScoredValue;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import io.lettuce.core.cluster.RedisClusterClient;
import io.lettuce.core.cluster.api.StatefulRedisClusterConnection;
import io.lettuce.core.cluster.api.async.RedisAdvancedClusterAsyncCommands;
import io.lettuce.core.cluster.api.sync.RedisAdvancedClusterCommands;
import io.lettuce.core.cluster.models.partitions.Partitions;
import io.lettuce.core.cluster.models.partitions.RedisClusterNode;
import io.lettuce.core.codec.ByteArrayCodec;
import io.lettuce.core.models.role.RedisInstance;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Jay.H.Zou
 * @date 2020/7/15
 */
public class RedisClusterImport {

    private static int COUNT = 10000;

    private static String MATCH = "*";

    private static final String STRING = "string";

    private static final String HASH = "hash";

    private static final String SET = "set";

    private static final String LIST = "list";

    private static final String ZSET = "zset";

    private static ExecutorService threadPool;

    private static StatefulRedisClusterConnection<byte[], byte[]> connection;

    private static RedisClusterClient redisClusterClient;

    private static StatefulRedisClusterConnection<byte[], byte[]> newConnection;

    private static RedisClusterClient newRedisClusterClient;

    private static void initResource() {
        int core = Runtime.getRuntime().availableProcessors();
        threadPool = new ThreadPoolExecutor(core, core, 60L, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(10000),
                new ThreadPoolExecutor.CallerRunsPolicy()
        );

        // 旧的集群
        RedisURI redisURI = RedisURI.create("", 1);
        redisClusterClient = RedisClusterClient.create(redisURI);
        connection = redisClusterClient.connect(new ByteArrayCodec());

        // 新的集群
        RedisURI newRedisURI = RedisURI.create("", 1);
        newRedisClusterClient = RedisClusterClient.create(newRedisURI);
        newConnection = newRedisClusterClient.connect(new ByteArrayCodec());
    }

    /**
     * process keys
     *
     * @param keys
     */
    private static void process(List<byte[]> keys) {
        if (keys == null || keys.isEmpty()) {
            return;
        }
        System.out.println(">>> " + keys.size());
        List<Future> futures = new ArrayList<>(keys.size() * 2);
        keys.forEach(key -> {
            String type = connection.sync().type(key);
            Long ttl = connection.sync().ttl(key);
            try {
                RedisAdvancedClusterCommands<byte[], byte[]> command = newConnection.sync();
                RedisAdvancedClusterAsyncCommands<byte[], byte[]> async = newConnection.async();
                switch (type) {
                    case STRING:
                        byte[] bytes = connection.sync().get(key);
                        //command.set(key, bytes);
                        futures.add(async.set(key, bytes));
                        break;
                    case HASH:
                        Map<byte[], byte[]> hgetall = connection.sync().hgetall(key);
                        //command.hset(key, hgetall);
                        futures.add(async.hset(key, hgetall));
                        break;
                    case LIST:
                        List<byte[]> lrange = connection.sync().lrange(key, 0, -1);
                        // command.lpush(key, lrange.toArray(new byte[0][0]));
                        futures.add(async.lpush(key, lrange.toArray(new byte[0][0])));
                        break;
                    case SET:
                        Set<byte[]> smembers = connection.sync().smembers(key);
                        //command.sadd(key, smembers.toArray(new byte[0][0]));
                        futures.add(async.sadd(key, smembers.toArray(new byte[0][0])));

                        break;
                    case ZSET:
                        List<ScoredValue<byte[]>> scoredValues = connection.sync().zrangeWithScores(key, 0, -1);
                        for (ScoredValue<byte[]> scoredValue : scoredValues) {
                            //command.zadd(key, scoredValue);
                            futures.add(async.zadd(key, scoredValue));
                        }
                        break;
                    default:
                        break;
                }
                // command.expire(key, ttl);
                futures.add(async.expire(key, ttl));
            } catch (Exception e) {
                System.out.println("Failed: " + new String(key));
                e.printStackTrace();
            }
        });
        for (Future future : futures) {
            try {
                future.get(5, TimeUnit.SECONDS);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    static class ScanCallback implements Callable<Integer> {

        private String masterId;

        public ScanCallback(String masterId) {
            this.masterId = masterId;
        }

        @Override
        public Integer call() throws Exception {
            AtomicInteger count = new AtomicInteger(0);
            StatefulRedisConnection<byte[], byte[]> nodeConnection = connection.getConnection(masterId);
            ScanArgs scanArgs = ScanArgs.Builder.limit(COUNT).match(MATCH);
            RedisCommands<byte[], byte[]> command = nodeConnection.sync();
            KeyScanCursor<byte[]> scanCursor = command.scan(scanArgs);
            List<byte[]> keys = scanCursor.getKeys();
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
        Partitions partitions = connection.getPartitions();
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
            futures.add(threadPool.submit(new ScanCallback(masterId)));
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
        System.out.println("Finished! count: " + count.get());
    }


}
