package com.eyes.see.java.thread;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.Callable;
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
public class ThreadPoolUtil {

    private static ThreadPoolUtil threadPool;
    private ThreadPoolExecutor executor;

    private TimeUnit unit = TimeUnit.SECONDS;

    public ThreadPoolUtil() {
        LinkedBlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>(1024);
        int corePoolSize = 10;
        long keepAliveTime = 1;
        int maximumPoolSize = 15;
        final ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("hbase-thread-%d").build();
        executor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, new ThreadPoolExecutor.AbortPolicy());
        System.out.println("线程池初始化成功");
    }

    public static ThreadPoolUtil init() {
        if (threadPool == null) {
            threadPool = new ThreadPoolUtil();
        }
        return threadPool;
    }

    public void awaitTermination() throws InterruptedException {
        long timeout = 10;
        executor.awaitTermination(timeout, unit);
    }

    public void execute(Runnable t) {
        executor.execute(t);
    }

    public void execute(Thread t) {
        executor.execute(t);
    }

    public int getQueueSize() {
        return executor.getQueue().size();
    }

    public void shutdown() {
        getExecutor().shutdown();
    }

    private ThreadPoolExecutor getExecutor() {
        return executor;
    }

    public Future<?> submit(Runnable t) {
        return executor.submit(t);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public Future<?> submit(Callable t) {
        return getExecutor().submit(t);
    }

}