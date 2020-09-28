package org.coastline.one.zookeeper;

/**
 * 客户端如何正确处理 CONNECTION LOSS(连接断开) 和 SESSIONEXPIRED(Session 过期)两类连接异常
 *
 * 在ZooKeeper中，服务器和客户端之间维持的是一个长连接，
 * 在 SESSION_TIMEOUT 时间内，服务器会确定客户端是否正常连接(客户端会定时向服务器发送heart_beat),
 * 服务器重置下次SESSION_TIMEOUT时间。
 * 因此，在正常情况下，Session一直有效，并且zk集群所有机器上都保存这个Session信息。
 * 在出现问题情况下，客户端与服务器之间连接断了（客户端所连接的那台zk机器挂了，或是其它原因的网络闪断），
 * 这个时候客户端会主动在地址列表（初始化的时候传入构造方法的那个参数connectString）中选择新的地址进行连接。
 * @author Jay.H.Zou
 * @date 2020/9/25
 */
public class ZookeeperConnection {
}
