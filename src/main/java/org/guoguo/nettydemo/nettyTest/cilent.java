package org.guoguo.nettydemo.nettyTest;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

public class cilent {
    public static void main(String[] args) throws IOException {
        SocketChannel sc=SocketChannel.open();
        sc.connect(new InetSocketAddress("localhost",8080));
        System.out.println("connected");

    }
}
