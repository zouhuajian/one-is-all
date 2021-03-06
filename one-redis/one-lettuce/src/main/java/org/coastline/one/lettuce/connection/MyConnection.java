package org.coastline.one.lettuce.connection;

import io.lettuce.core.RedisURI;
import io.lettuce.core.cluster.RedisClusterClient;
import io.lettuce.core.cluster.api.StatefulRedisClusterConnection;
import io.lettuce.core.cluster.api.async.RedisAdvancedClusterAsyncCommands;
import io.lettuce.core.cluster.api.sync.RedisAdvancedClusterCommands;

import java.util.List;

/**
 * @author Jay.H.Zou
 * @date 2020/5/11
 */
public class MyConnection {

    public static void main(String[] args) {
        RedisURI redisUri = RedisURI.Builder.redis("192.168.3.112").withPort(9002).build();
        RedisClusterClient client = RedisClusterClient.create(redisUri);
        StatefulRedisClusterConnection<String, String> connect = client.connect();
        RedisAdvancedClusterAsyncCommands<String, String> async = connect.async();
        RedisAdvancedClusterCommands<String, String> sync = connect.sync();
        String set = sync.set("one", "all");
        System.out.println(sync.get("one"));
        /*System.out.println(sync.commandInfo("set"));
        System.out.println(set);*/
        connect.close();
        client.shutdown();
    }

}
