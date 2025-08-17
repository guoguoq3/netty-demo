package org.guoguo.nettydemo.nettyTest;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LoggingHandler;
import java.net.InetSocketAddress;
//*3\r\n        // 表示命令包含 3 个参数
//$3\r\n        // 第一个参数的长度（"set" 是 3 个字符）
//set\r\n       // 第一个参数（命令名）
//$5\r\n        // 第二个参数的长度（"mykey" 是 5 个字符）
//mykey\r\n     // 第二个参数（键名）
//$7\r\n        // 第三个参数的长度（"myvalue" 是 7 个字符）
//myvalue\r\n   // 第三个参数（值）
public class NettyRedisClient {

    // 定义换行符字节数组，通常 Redis 协议中用 \r\n 作为命令结束标识，这里假设你代码里的 LINE 是这个含义
    final static byte[] LINE = {13, 10}; // 对应 \r\n

    public static void main(String[] args) {
        EventLoopGroup worker = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.group(worker);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) {
                    ChannelPipeline pipeline = ch.pipeline();
                    // 添加日志处理器，方便查看通信过程
                    pipeline.addLast(new LoggingHandler()); 
                    // 添加自定义的业务处理器，用于发送命令和处理响应
                    pipeline.addLast(new ChannelInboundHandlerAdapter() {
                        // 当通道激活（连接建立成功）时触发，用于发送 Redis 命令
                        @Override
                        public void channelActive(ChannelHandlerContext ctx) {
                            ByteBuf buf = ctx.alloc().buffer();
                            // 以下是构造要发送给 Redis 的命令，这里以简单的示例命令为例，比如设置一个键值对（set 命令）
                            // 实际使用中，要根据 Redis 协议规范和需求构造准确命令
                            buf.writeBytes("*3".getBytes()); // 表示后面跟着 3 个参数，RESP 数组格式开头，这里简单模拟，后续建议严格按 RESP 规范
                            buf.writeBytes(LINE);
                            buf.writeBytes("$3".getBytes()); // 第一个参数长度（set 命令长度为 3）
                            buf.writeBytes(LINE);
                            buf.writeBytes("set".getBytes());
                            buf.writeBytes(LINE);
                            buf.writeBytes("$5".getBytes()); // 第二个参数长度（假设 key 是 "mykey"，长度为 5 ）
                            buf.writeBytes(LINE);
                            buf.writeBytes("mykey".getBytes());
                            buf.writeBytes(LINE);
                            buf.writeBytes("$7".getBytes()); // 第三个参数长度（假设 value 是 "myvalue"，长度为 7 ）
                            buf.writeBytes(LINE);
                            buf.writeBytes("myvalue".getBytes());
                            buf.writeBytes(LINE);

                            // 发送构造好的命令到 Redis 服务端
                            ctx.writeAndFlush(buf); 
                        }

                        // 当接收到服务端响应数据时触发，这里简单打印响应内容
                        @Override
                        public void channelRead(ChannelHandlerContext ctx, Object msg) {
                            ByteBuf byteBuf = (ByteBuf) msg;
                            byte[] respBytes = new byte[byteBuf.readableBytes()];
                            byteBuf.readBytes(respBytes);
                            System.out.println("收到 Redis 响应: " + new String(respBytes));
                            ctx.fireChannelRead(msg);
                        }

                        // 当通道读取完成时触发
                        @Override
                        public void channelReadComplete(ChannelHandlerContext ctx) {
                            ctx.flush();
                        }

                        // 当发生异常时触发，打印异常信息并关闭通道
                        @Override
                        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
                            cause.printStackTrace();
                            ctx.close();
                        }
                    });
                }
            });

            // 连接 Redis 服务端，这里假设 Redis 运行在 localhost，端口 6379
            ChannelFuture channelFuture = bootstrap.connect(new InetSocketAddress("localhost", 6379)).sync();
            // 阻塞等待通道关闭，保证客户端不会立即退出
            channelFuture.channel().closeFuture().sync(); 
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // 优雅关闭事件循环组，释放资源
            worker.shutdownGracefully(); 
        }
    }
}