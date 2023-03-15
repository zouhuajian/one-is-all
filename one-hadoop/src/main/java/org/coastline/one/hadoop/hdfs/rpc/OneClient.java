package org.coastline.one.hadoop.hdfs.rpc;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.RPC;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * @author Jay.H.Zou
 * @date 2023/3/6
 */
public class OneClient {
    public static void main(String[] args) throws IOException {
        IProxyProtocol proxy = RPC.getProxy(IProxyProtocol.class,
                IProxyProtocol.versionID,
                new InetSocketAddress("127.0.0.1", 8888),
                new Configuration());
        String result = proxy.doSomething(1, "test");
        System.out.println(result);
        RPC.stopProxy(proxy);
    }
}
