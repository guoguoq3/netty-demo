package org.guoguo.nettydemo.nettyTest;

import io.netty.channel.DefaultEventLoop;
import io.netty.channel.EventLoop;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;

import java.util.concurrent.TimeUnit;

public class nettydemo7 {
    public static void main(String[] args) {
        //创建事件循环组
        EventLoopGroup group = new NioEventLoopGroup(3);//io任务普通任务
        //普通任务 定时任务
//        EventLoopGroup eventLoop = new DefaultEventLoop();
        System.out.println(group.next());
        System.out.println(group.next());
        System.out.println(group.next());
        System.out.println(group.next());
        System.out.println(group.next());
        System.out.println(group.next());

        //执行普通任务
        group.next().submit(()->{
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("普通任务执行完毕");
        });


        //执行定时任务 第一个是延迟时间 第二个是间隔时间
        group.next().scheduleAtFixedRate( ()->{
            System.out.println(1111);
        }, 1000,1000, TimeUnit.MILLISECONDS);



        System.out.println("普通任务提qwqw交完毕");
    }
}
