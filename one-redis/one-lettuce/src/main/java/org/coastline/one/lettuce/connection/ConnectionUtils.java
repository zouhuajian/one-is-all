package org.coastline.one.lettuce.connection;

import io.lettuce.core.RedisURI;
import io.lettuce.core.cluster.RedisClusterClient;
import io.lettuce.core.cluster.api.StatefulRedisClusterConnection;

/**
 * @author Jay.H.Zou
 * @date 2020/5/11
 */
public class ConnectionUtils {

    private static RedisClusterClient client;

    private static StatefulRedisClusterConnection<String, String> connect;


    private ConnectionUtils() {
    }

    public static StatefulRedisClusterConnection<String, String> getConnection() {
        RedisURI redisUri = RedisURI.Builder.redis("192.168.3.112").withPort(9000).build();
        client = RedisClusterClient.create(redisUri);
        connect = client.connect();
        return connect;
    }

    public static void close() {
        connect.close();
        client.shutdown();
    }

}
