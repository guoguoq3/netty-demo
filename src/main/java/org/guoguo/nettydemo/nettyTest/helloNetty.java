package org.guoguo.nettydemo.nettyTest;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class helloNetty {
    public static void main(String[] args) throws InterruptedException {
        // 服务器端通常需要两个事件循环组（boss 处理连接，worker 处理读写）
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1); // boss 线程组（1个线程足够）
        NioEventLoopGroup workerGroup = new NioEventLoopGroup(); // worker 线程组（默认 CPU 核心数 × 2）

        new ServerBootstrap()
                .group(bossGroup, workerGroup) // 绑定两个线程组
                .channel(NioServerSocketChannel.class) // 服务器端通道类型（正确）
                // 客户端连接的处理器（worker 线程执行）
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        // 字符串解码器：将客户端发送的字节流转为字符串
                        ch.pipeline().addLast(new StringDecoder());
                        // 字符串编码器：向客户端发送消息时需要
                        ch.pipeline().addLast(new StringEncoder());
                        // 服务器端业务处理器（处理客户端消息）
                        ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                // 打印客户端发送的消息
                                System.out.println("收到客户端消息：" + msg);
                                // 可选：向客户端回复消息
                                ctx.writeAndFlush("已收到：" + msg);
                            }
                        });
                    }
                })
                .bind(8080) // 绑定端口（可添加 sync() 等待绑定完成）
                .sync()
                .channel()
                .closeFuture()
                .sync(); // 阻塞等待服务器关闭（避免主线程退出）
    }
}