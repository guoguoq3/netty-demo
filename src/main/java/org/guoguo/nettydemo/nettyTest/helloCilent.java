package org.guoguo.nettydemo.nettyTest;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.net.InetSocketAddress;

public class helloCilent {
    public static void main(String[] args) throws InterruptedException {
        new Bootstrap()
                .group(new NioEventLoopGroup())
                // 客户端必须使用 NioSocketChannel（而非服务器端的 NioServerSocketChannel）
                .channel(NioSocketChannel.class)
                // 客户端的处理器：添加编码器（发送字符串时需要）和解码器（接收服务器消息时需要）
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        // 字符串编码器：将发送的字符串转为字节流
                        ch.pipeline().addLast(new StringEncoder());
                        // 字符串解码器：将接收的字节流转为字符串
                        ch.pipeline().addLast(new StringDecoder());
                        // 客户端业务处理器（可处理服务器返回的消息）
                        ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                System.out.println("收到服务器消息：" + msg);
                            }
                        });
                    }
                })
                // 连接服务器
                .connect(new InetSocketAddress("localhost", 8080))
                .sync()
                .channel();
        System.out.println("客户端启动成功！");
                // 发送消息（需先通过 StringEncoder 编码）
                //.writeAndFlush("hello"); // 修正拼写错误 "heelo" -> "hello"

    }
}