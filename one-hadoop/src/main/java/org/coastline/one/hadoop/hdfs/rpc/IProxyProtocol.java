package org.coastline.one.hadoop.hdfs.rpc;

import org.apache.hadoop.ipc.VersionedProtocol;

/**
 * @author Jay.H.Zou
 * @date 2023/3/6
 */
public interface IProxyProtocol extends VersionedProtocol {
    long versionID = 0001;

    String doSomething(int num, String str);
}
