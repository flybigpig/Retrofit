package com.eyes.see.java.algorithm;

import java.util.ArrayList;
import java.util.List;

/**
 * @author
 * @date 2020/3/18.
 * GitHub：
 * email：
 * description：
 */
public class NewAvg {

    public static void main(String[] args) {

        int avg = 0;
        List<Integer> list = new ArrayList<>();


        list.add(1);

        for (int i = 1; i < 5; i++) {
            avg = avgs(list, i, avg);
            System.out.println(i + "avg: " + avg);
            list.add(i);
        }

    }

    private static int avgs(List<Integer> list, int newDate, int avg) {

        if (list == null) {
            return -1;
        }
        if (list.size() == 0) {
            list.add(newDate);
            avg = list.get(0);
        } else {
            avg = (avg * (list.size()) + newDate) / (list.size() + 1);
        }
        return avg;
    }
}
