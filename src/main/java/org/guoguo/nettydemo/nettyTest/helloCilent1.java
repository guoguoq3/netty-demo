package org.guoguo.nettydemo.nettyTest;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.net.InetSocketAddress;

public class helloCilent1 {
    public static void main(String[] args) throws InterruptedException {
        ChannelFuture localhost = new Bootstrap()
                .group(new NioEventLoopGroup())
                // 客户端必须使用 NioSocketChannel（而非服务器端的 NioServerSocketChannel）
                .channel(NioSocketChannel.class)
                // 客户端的处理器：添加编码器（发送字符串时需要）和解码器（接收服务器消息时需要）
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        // 字符串编码器：将发送的字符串转为字节流
                        ch.pipeline().addLast(new StringEncoder());
                    }
                })
                // 连接服务器
                .connect(new InetSocketAddress("localhost", 8080));


//        Channel channel = localhost.channel();
//        localhost.sync();
//        channel.writeAndFlush("hello");
//        System.out.println("客户端启动成功！");


        //回调 异步处理、结果
            localhost.addListener(new ChannelFutureListener() {
                @Override
                //在nio线程建立连接后 会调用operationcomplete 方法
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    Channel channel = channelFuture.channel();
                    System.out.println(channel);
                    channel.writeAndFlush("hello");
                }
            });

    }
}