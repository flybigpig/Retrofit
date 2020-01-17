package com.eyes.see.java;

import java.util.HashMap;

/**
 * @author
 * @date 2020/1/15.
 * GitHub：
 * email：
 * description：
 */
public class Hashmaps {
    private static HashMap<String, String> mHashMap = new HashMap<>();

    public static void main(String[] args) {
        mHashMap.put("1", "2");
        String value = mHashMap.put("1", "3");
        System.out.println(value);
        System.out.println("100".hashCode());


        System.out.println(mHashMap.get("1"));
        mHashMap.put("", "23");


        mHashMap.put("", "");
        System.out.println(mHashMap.get("") + "34343242");
//
//
//        System.out.println(Integer.highestOneBit(10));
//
//        System.out.println(1 << 30);


    }

    /**
     * 取最高位的值（不管后面位置数据）
     * example-->10
     * 0000 1010
     * <p>
     * 0000 1111
     * -0000 0111
     * ----------
     * 0000 1000
     *
     * @param i
     * @return
     */
    public static int highestOneBit(int i) {
        // HD, Figure 3-1
        i |= (i >> 1);
        i |= (i >> 2);
        i |= (i >> 4);
        i |= (i >> 8);
        i |= (i >> 16);
        return i - (i >>> 1);
    }

}
