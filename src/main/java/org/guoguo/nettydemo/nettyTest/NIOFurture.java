package org.guoguo.nettydemo.nettyTest;

import io.netty.channel.EventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.GenericFutureListener;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

@Slf4j
public class NIOFurture {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //创建线程池的
        NioEventLoopGroup group=new NioEventLoopGroup();
        EventLoop eventLoop=group.next();
        //提交任务
        io.netty.util.concurrent.Future<Integer> future=group.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                System.out.println("call");
               Thread.sleep(5000);
                return 50;
            }

        });
        //主线程通过futrue来获取结果
//        System.out.println(future.get());
        // ... existing code ...
        //主线程通过futrue来获取结果
//        System.out.println(future.get());
        future.addListener(new GenericFutureListener<io.netty.util.concurrent.Future<? super Integer>>() {
            @Override
            public void operationComplete(io.netty.util.concurrent.Future<? super Integer> future) throws Exception {
                System.out.println(future.getNow());
            }
        });
// ... existing code ...

    }

}
