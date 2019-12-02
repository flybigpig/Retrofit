package com.example.baidu.retrofit.Study;

import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;

import java.util.HashMap;

/**
 * Android为什么推荐使用SparseArray来替代HashMap？
 * <p>
 * https://blog.csdn.net/woshizisezise/article/details/79361458
 */

/**
 * 稀疏矩阵
 * <p>
 * 替换 HashMap
 *
 * 主要是因为SparseArray不需要对key和value进行auto-boxing
 *
 * <p>
 * 相应的也有SparseBooleanArray，用来取代HashMap<Integer, Boolean>，SparseIntArray 用来取代HashMap<Integer, Integer>，大家有兴趣的可以研究。
 * <p>
 * <p>
 * <p>
 * 总结：SparseArray是android里为<Interger,Object>这样的Hashmap而专门写的类,目的是提高内存效率，其核心是折半查找函数（binarySearch）。
 * <p>
 * 注意内存二字很重要，因为它仅仅提高内存效率，而不是提高执行效率，所以也决定它只适用于android系统（内存对android项目有多重要，地球人都知道）。
 * <p>
 *
 *   http://images.cnitblog.com/i/384215/201403/211043340846219.png
 *
 *   http://images.cnitblog.com/i/384215/201403/211044337711918.png
 *
 * SparseArray有两个优点：
 *      1.避免了自动装箱（auto-boxing），
 *
 *      2.数据结构不会依赖于外部对象映射。我们知道HashMap 采用一种所谓的“Hash 算法”来决定每个元素的存储位置，
 * <p>
 *      存放的都是数组元素的引用，通过每个对象的hash值来映射对象。而SparseArray则是用数组数据结构来保存映射，然后通过折半查找来找到对象。但其实一般来说，
 * <p>
 *      SparseArray执行效率比HashMap要慢一点，因为查找需要折半查找，而添加删除则需要在数组中执行，而HashMap都是通过外部映射。但相对来说影响不大，
 * <p>
 *      最主要是SparseArray不需要开辟内存空间来额外存储外部映射，从而节省内存。
 */
public class SparseArrayClass extends SparseArray {

    private HashMap<Integer, Object> hashmap = new HashMap<Integer, Object>();

    private void getSparseArrayTest() {

        SparseArray<String> sparse = new SparseArray<>();
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            sparse.put(i, String.valueOf(i));
        }
        long memory = Runtime.getRuntime().totalMemory();
        long end = System.currentTimeMillis();
        long usedTime = end - start;
        System.out.println("SparseArray消耗的时间是：" + usedTime + "，SparseArray占用的内存是：" + memory);
    }

}
