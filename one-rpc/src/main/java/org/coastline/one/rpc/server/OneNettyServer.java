package org.coastline.one.rpc.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.util.concurrent.DefaultThreadFactory;

import java.nio.ByteBuffer;
import java.util.List;

/**
 * [4][N]
 *
 * @author Jay.H.Zou
 * @date 2021/7/30
 */
public abstract class OneNettyServer {

    private static ChannelFuture channelFuture;
    private static EventLoopGroup bossGroup;
    private static EventLoopGroup workerGroup;

    public static void initialize() throws Exception {
        /*requestCodec = ProtostuffCodec.create(RpcRequest.class);
        responseCodec = ProtostuffCodec.create(RpcResponse.class);*/
        int threads = Runtime.getRuntime().availableProcessors();
        bossGroup = new NioEventLoopGroup(8, new DefaultThreadFactory("netty-boss"));
        workerGroup = new NioEventLoopGroup(8, new DefaultThreadFactory("netty-worker"));
        ServerBootstrap bootstrap = new ServerBootstrap(); // (2)
        bootstrap.group(workerGroup)
                .channel(NioServerSocketChannel.class) // (3)
                .childHandler(new ChannelInitializer<SocketChannel>() { // (4)
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline()
                                .addLast(new RpcDecoder()) // request decode
                                .addLast(new RpcEncoder()) // response encode
                                .addLast(new ServerHandler()); // 使用 ServerHandler 类来处理接收到的消息
                    }
                })
                // 设置的是服务端用于接收进来的连接，也就是 boosGroup 线程
                // Socket 参数，服务端接受连接的队列长度，如果队列已满，客户端连接将被拒绝。默认值，Windows 为200，其他为128。
                .option(ChannelOption.SO_BACKLOG, 1024)          // (5)
                // Socket 参数，TCP 数据接收缓冲区大小。
                //.childOption(ChannelOption.SO_RCVBUF, 128)
                // TCP 参数，立即发送数据，默认值为 True
                .childOption(ChannelOption.TCP_NODELAY, true)
                // Socket 参数，连接保活，默认值为 false。启用该功能时，TCP 会主动探测空闲连接的有效性。
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.SO_REUSEADDR, true)
                .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT); // (6)
        Runtime.getRuntime().addShutdownHook(new Thread(OneNettyServer::close));

        // Bind and start to accept incoming connections.
        channelFuture = bootstrap.bind(8080).sync(); // (7)
    }

    public static void main(String[] args) throws Exception {
        initialize();
    }

    public static void close() {
        try {
            // Wait until the server socket is closed.
            // In this example, this does not happen, but you can do that to gracefully
            // shut down your server.
            channelFuture.channel().closeFuture();
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * RpcRequest decode
     */
    static class RpcDecoder extends ByteToMessageDecoder {

        @Override
        protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
            //不够长度丢弃
            if (in.readableBytes() < 4) {
                return;
            }
            //标记一下当前的readIndex的位置
            in.markReaderIndex();
            // 读取传送过来的消息的长度。ByteBuf 的readInt()方法会让他的readIndex增加4
            int dataLength = in.readInt();
            // 读到的消息体长度如果小于我们传送过来的消息长度，则resetReaderIndex. 这个配合markReaderIndex使用的。把readIndex重置到mark的地方
            if (in.readableBytes() < dataLength) {
                in.resetReaderIndex();
                return;
            }
            byte[] data = new byte[dataLength];
            in.readBytes(data);
            //out.add(requestCodec.decode(data));
        }
    }

    /**
     * `RpcResponse` encode
     */
    static class RpcEncoder extends MessageToByteEncoder<String> {

        @Override
        protected void encode(ChannelHandlerContext ctx, String msg, ByteBuf out) throws Exception {
            /*byte[] data = responseCodec.encode(msg);
            // write header(data length)
            out.writeInt(data.length);
            //消息体中包含我们要发送的数据
            out.writeBytes(data);*/
        }
    }

    /**
     * process request after encoding
     */
    private static class ServerHandler extends SimpleChannelInboundHandler<String> {

        //接受client发送的消息
        @Override
        protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
            /*countIn.increment();
            Status status;
            try {
                status = output(msg);
            } catch (Exception e) {
                logger.error("process data error", e);
                countError.increment();
                status = Status.UNKNOWN;
            }
            // response encode
            ctx.writeAndFlush(response);*/
        }

        //读操作时捕获到异常时调用
        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            cause.printStackTrace();
            ctx.close();
        }

    }

}
