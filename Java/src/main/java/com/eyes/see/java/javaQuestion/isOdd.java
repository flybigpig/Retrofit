package com.eyes.see.java.javaQuestion;

/**
 * @author
 * @date 2020/1/17.
 * GitHub：
 * email：
 * description：
 */
public class isOdd {
    public static void main(String[] args) {
        System.out.println(isOdd(5));
    }

    /**
     * 判断奇偶
     *
     * @param i
     * @return
     */
    public static boolean isOdd(int i) {
        return (i & 1) != 0;
    }
}
