package com.eyes.see.java.utils;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author
 * @date 2020/3/11.
 * GitHub：
 * email：
 * description：
 */
public class Callable {
    /**
     * 实现callable 接口
     * 线程池执行
     * future 回调
     */
    public static class TestCallable implements java.util.concurrent.Callable {

        @Override
        public String call() throws Exception {
            return "hellow orld";
        }
    }

    public static void main(String[] args) {

        TestCallable testCallable = new TestCallable();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future mFuture = executorService.submit(testCallable);
        try {
            System.out.print(mFuture.get().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
