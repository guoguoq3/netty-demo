package org.guoguo.nettydemo.nettyTest;

import io.netty.channel.ServerChannel;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;
@Slf4j
public class nettydemo6 {
    public static void main(String[] args) throws IOException {
        //单线程

        ByteBuffer buffer = ByteBuffer.allocate(16);
        //创建服务器
        ServerSocketChannel ssc=ServerSocketChannel.open();
        //非阻塞模式
        ssc.configureBlocking(false);
        //绑定端口
        ssc.bind(new InetSocketAddress(8080));

        //连接集合 一直来获取连接
        List<SocketChannel> channels = new ArrayList<>();
        while (true){
            System.out.println("等待连接...");
            //接收连接
            SocketChannel accept = ssc.accept();

            System.out.println("连接成功"+ accept  );
            //添加到连接集合
            channels.add(accept);
            //处理连接
            for (SocketChannel channel : channels) {
                //处理连接
                channel.read(buffer);
                buffer.flip();
                System.out.println("数据dayi");
                while (buffer.hasRemaining()) {
                    System.out.print((char) buffer.get());
                    System.out.println();
                }
                buffer.clear();
                System.out.println("数据处理完毕");
            }
        }


    }
}
