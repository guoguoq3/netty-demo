package org.guoguo.nettydemo.nettyTest;

import io.netty.channel.EventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.DefaultPromise;

import java.util.concurrent.ExecutionException;

public class nettyPromise {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        EventLoop eventLoop=new NioEventLoopGroup().next();

        DefaultPromise<Integer> promise=new DefaultPromise<>(eventLoop);

        new Thread(()-> {
            System.out.println("call");
                try {
                    int i=10/0;
                    Thread.sleep(1000);
                    promise.setSuccess(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    promise.setFailure(e);

                }
             //promise.setSuccess(50);
        }).start();
        System.out.println(111);
        System.out.println(promise.get());
    }
}
