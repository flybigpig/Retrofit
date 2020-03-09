package com.eyes.see.java.javaQuestion;

/**
 * @author
 * @date 2020/1/17.
 * GitHub：
 * email：
 * description：
 */
public class longInt {
    public static void main(String[] args) {
        final long time = 24 * 60 * 60 * 1000 * 1000;
        final long tt = 24 * 60 * 60 * 1000;
        System.out.println(time / tt);

        System.out.println(12345 + 5432L);

        System.out.println(Long.toHexString(0x100000000L + 0xcafebabe));

        System.out.println((int) (char) (byte) -1);

    }
}
