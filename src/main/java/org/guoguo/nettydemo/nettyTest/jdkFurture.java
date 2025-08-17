package org.guoguo.nettydemo.nettyTest;

import io.netty.util.concurrent.GenericFutureListener;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;
@Slf4j
public class jdkFurture {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //创建线程池的
        ExecutorService service= Executors.newFixedThreadPool(2);

        //提交任务
        Future<Integer> future=service.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                System.out.println("call");
               Thread.sleep(5000);
                return 50;
            }

        });
        //主线程通过futrue来获取结果

    }
}
