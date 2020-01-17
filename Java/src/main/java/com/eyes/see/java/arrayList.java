package com.eyes.see.java;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.xml.soap.Node;

/**
 * @author
 * @date 2020/1/15.
 * GitHub：
 * email：
 * description：
 */
public class arrayList  {

    /**
     * ArrayList是非线程安全外
     *
     * @param args
     */
    public static void main(String[] args) {

        List<String> strings = Collections.synchronizedList(new ArrayList<String>());

        List<String> string = new ArrayList<>();

        LinkedList<Integer> linkedList = new LinkedList<>();


    }
}
