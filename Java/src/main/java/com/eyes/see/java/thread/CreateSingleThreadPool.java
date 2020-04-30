package com.eyes.see.java.thread;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author
 * @date 2020/4/30.
 * GitHub：
 * email：
 * description：
 */
public class CreateSingleThreadPool {

    private static ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
            .setNameFormat("demo-pool-%d").build();

    private static ExecutorService pool = new ThreadPoolExecutor(5, 200,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());

    public static void main(String[] args) {

        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            pool.execute(new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("asdasd" + Thread.currentThread().getName());
                }
            }));
        }
    }

    private static void rightCreate2() {
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            pool.execute(new Thread());
        }
    }

    private static void rightCreate() {
        ExecutorService executorService = new ThreadPoolExecutor(
                5, 10, 10, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(5));

        for (int i = 0; i < 20; i++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println("开始线程池中的任务");
                }
            });
        }

    }


    private static void test() throws InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 10; i++) {
            final int taskId = i;
            Future future = executorService.submit(new Runnable() {
                @Override
                public void run() {
                    System.out.println("线程 " + Thread.currentThread().getName() + " 正在执行" + taskId);
                }
            });
            try {
                if (future.get() == null) {//如果Future's get返回null，任务完成
                    System.out.println("任务完成");
                } else {

                }
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
                System.out.println("任务失败 " + e.getCause().getMessage());
            }
            Thread.sleep(2000);

        }
    }
}
