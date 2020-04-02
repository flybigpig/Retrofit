package com.eyes.see.java;

import java.util.Random;

public class MyClass {


    public static void main(String[] args) {

        System.out.println(test2.b);

    }
}

interface test1 {
    int a = 1;
}

interface test2 extends test1 {
    int b = new Random().nextInt(12);
}

