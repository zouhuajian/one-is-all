package org.coastline.one.zookeeper;

import lombok.Builder;
import lombok.Data;

/**
 *
 * @author Jay.H.Zou
 * @date 2021/8/25
 */
@Data
@Builder
public class ZookeeperConfig {
    public static final String DEFAULT_ADDRESS = "xx:2181";
    public static final String DEFAULT_PATH = "/coastline/one/config";

    private String address;
    private String path;
}
