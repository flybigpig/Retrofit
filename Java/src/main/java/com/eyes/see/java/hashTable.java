package com.eyes.see.java;

import java.util.Hashtable;

/**
 * @author
 * @date 2020/1/15.
 * GitHub：
 * email：
 * description：
 */
public class hashTable {

    private static Hashtable<Object, Object> mhashTable;

    public static void main(String[] args) {
        mhashTable = new Hashtable<>();
        mhashTable.put("1", "2");
        System.out.println(mhashTable.get("1"));
    }
}
