package org.guoguo.nettydemo.nettyTest;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.codec.http.HttpServerCodec;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
public class TestHttp {
    public static void main(String[] args) {
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.channel(NioServerSocketChannel.class);
            serverBootstrap.group(boss, worker);
            serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new LoggingHandler(LogLevel.DEBUG));
                    ch.pipeline().addLast(new HttpServerCodec());
                    //可以专门收集一种请求
                    ch.pipeline().addLast(new SimpleChannelInboundHandler<HttpRequest>() {

                        @Override
                        protected void channelRead0(ChannelHandlerContext channelHandlerContext, HttpRequest httpRequest) throws Exception {
                            System.out.println(httpRequest.uri());

                            //返回响应 DefaultFullHttpResponse 是 Netty 中表示 完整 HTTP 响应 的类（包含响应头和响应体）。
                            DefaultFullHttpResponse response = new DefaultFullHttpResponse(httpRequest.protocolVersion(), io.netty.handler.codec.http.HttpResponseStatus.OK);
                            //response.content() 返回一个字节容器（ByteBuf），用于存储响应体数据。
                            //将字符串 <h1>hello world</h1> 转换为字节数组并写入响应体
                            response.content().writeBytes("<h1>hello world</h1>".getBytes());
                             //写回响应
                            channelHandlerContext.writeAndFlush(response);
                        }
                    });
//                    ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
//                        @Override
//                        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//                            System.out.println(msg.getClass());
//                            System.out.println((msg));
//                        }
//                    });
                }
            });
            ChannelFuture channelFuture = serverBootstrap.bind(8080).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            System.out.println(e);
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
}