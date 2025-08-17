package org.guoguo.nettydemo.nettyTest;

import java.util.concurrent.*;
import java.util.function.Supplier;

public class AsyncOperations {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        // 方法1：使用Thread和Runnable（无返回值）
        System.out.println("=== 方法1：Thread + Runnable ===");
        new Thread(() -> {
            System.out.println("异步任务1执行中...");
            try {
                Thread.sleep(1000); // 模拟耗时操作
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("异步任务1完成");
        }).start();
        
        // 方法2：使用ExecutorService和Callable（有返回值）
        System.out.println("\n=== 方法2：ExecutorService + Callable ===");
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<String> future = executor.submit(() -> {
            System.out.println("异步任务2执行中...");
            Thread.sleep(1500); // 模拟耗时操作
            return "异步任务2的返回结果";
        });
        
        // 主线程可以做其他事情
        System.out.println("主线程继续执行...");
        
        // 获取异步任务结果（会阻塞直到任务完成）
        String result = future.get();
        System.out.println("获取到异步任务2的结果：" + result);
        executor.shutdown();
        
        // 方法3：使用CompletableFuture（Java 8+，推荐）
        System.out.println("\n=== 方法3：CompletableFuture ===");
        
        // 3.1 无返回值的异步操作
        CompletableFuture.runAsync(() -> {
            System.out.println("异步任务3执行中...");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("异步任务3完成");
        });
        
        // 3.2 有返回值的异步操作
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("异步任务4执行中...");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "异步任务4的返回结果";
        });
        
        // 3.3 异步处理结果（非阻塞）
        completableFuture.thenAccept(result4 -> {
            System.out.println("异步处理任务4的结果：" + result4);
        });
        
        // 3.4 组合多个异步操作
        CompletableFuture<String> combinedFuture = completableFuture.thenCompose(result4 -> {
            return CompletableFuture.supplyAsync(() -> {
                System.out.println("基于任务4的结果执行任务5...");
                return result4 + " + 任务5的结果";
            });
        });
        
        // 等待所有异步操作完成
        System.out.println("\n等待所有异步操作完成...");
        Thread.sleep(3000);
        System.out.println("组合任务的结果：" + combinedFuture.get());
        System.out.println("所有操作完成");
    }
}
