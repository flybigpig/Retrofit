package com.example.baidu.retrofit.Study;

import java.util.HashMap;
import java.util.Map;

public class HashMaps {

    /**
     * 分析 HashMap
     * <p>
     * 比较 SparseArray
     *
     *HashMap 和 LinkedHashMap 相关：

     java为数据结构中的映射定义了一个接口java.util.Map;
     它有四个实现类,分别是HashMap Hashtable LinkedHashMap 和TreeMap.

     Map：主要用于存储键值对，允许值重复，但不允许键重复（重复就覆盖了）；
     HashMap：HashMap 是根据键的 HashCode 值储存数据，根据键可以直接获取它的值，所以具有很快的访问速度。但是遍历时所获取的键值是随机的。
     HashMap 键不可重复但值可以重复，只允许一个键为 null，同时允许多个值为 null。
     HashMap 不是线程同步的，如果需要同步可以使用 Collections 的 synchronizedMap 方法或使用 ConcurrentHashMap。
     LinkedHashMap：是 HashMap 的子类，拥有 HashMap 的大部分特性，不同的是它保存了记录插入顺序。在使用 Iterator 遍历时得到的数据是按顺序获取的。
     Hashtable：与 HashMap 类似，主要特点是不允许键或值为 null，同时支持线程同步（主要 public 方法和有些代码块使用 synchronized 关键字修饰）。所以在写入时会比较慢。
     TreeMap：实现 SortMap 接口，能够把它保存的记录根据键排序，默认是按键值的升序排序。
     总结：

     HashMap 和 Hashtable ：
     HashMap 不允许键重复但允许值重复，所以可以存在键或值为 null 的情况。但是 Hashtable 不允许键或值为 null。
     HashMap 不支持同步，Hashtable 线程安全。
     HashMap 读取速度较快，Hashtable 由于需要同步所以较慢。
     HashMap 和 LinkedHashMap :
     主要区别是 LinkedHashMap 是有序的，HashMap 遍历时无序。

     作者：Marker_Sky
     链接：https://www.jianshu.com/p/ee7b943a6d41
     来源：简书
     著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。

     */
    /******
     * <a href="{@docRoot}openjdk-redirect.html?v=8&path=/technotes/guides/collections/index.html">
     *
     */

    /**
     * <a href="{@docRoot}openjdk-redirect.html?v=8&path=/technotes/guides/collections/index.html">
     *
     * @param initialCapacity
     * @param loadFactor
     */


    static HashMap<String, String> mHashMap = new HashMap<>();


    public static void main(String[] args) {


    }
}
