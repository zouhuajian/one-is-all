package org.coastline.one.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.nio.charset.StandardCharsets;

/**
 * @author Jay.H.Zou
 * @date 2021/8/25
 */
public class OneZookeeper {

    private static final byte[] CONTENT = "services=".getBytes(StandardCharsets.UTF_8);
    private static CuratorFramework client;
    private static NodeCache nodeCache;

    public static void main(String[] args) throws Exception {
        ZookeeperConfig config = ZookeeperConfig.builder()
                .address(ZookeeperConfig.DEFAULT_ADDRESS)
                .path(ZookeeperConfig.DEFAULT_PATH)
                .build();
        client = CuratorFrameworkFactory.builder()
                .connectString(config.getAddress())
                .sessionTimeoutMs(5000)
                .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                .build();
        client.start();

        String path = config.getPath();
        /*if (client.checkExists().forPath(path) == null) {
            client.create().creatingParentContainersIfNeeded().forPath(path, CONTENT);
        }*/

        nodeCache = new NodeCache(client, path);
        nodeCache.start();
        nodeCache.getListenable().addListener(new NodeCacheListener() {
            @Override
            public void nodeChanged() throws Exception {
                update(nodeCache.getCurrentData().getData());
            }
        });
        nodeCache.close();
        client.close();
    }

    private static void update(byte[] data) {
        System.out.println(new String(data));
    }


}
