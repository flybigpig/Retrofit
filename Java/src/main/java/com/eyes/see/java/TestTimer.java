package com.eyes.see.java;

/**
 * @author
 * @date 2020/3/20.
 * GitHub：
 * email：
 * description：
 */

import java.util.Timer;
import java.util.TimerTask;

public class TestTimer {
    public static void main(String[] args) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("i have done the work" + "  " + System.currentTimeMillis());
            }
        }, 1000, 2000);
    }
}