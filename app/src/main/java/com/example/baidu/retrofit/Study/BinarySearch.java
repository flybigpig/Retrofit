package com.example.baidu.retrofit.Study;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * 二分查找法
 */
public class BinarySearch {


    /**
     * ContainerHelpers.binarySearch
     *
     * @param array
     * @param size
     * @param value
     * @return
     */


    // This is Arrays.binarySearch(), but doesn't do any argument validation.
    static int binarySearch(int[] array, int size, int value) {
        int lo = 0;
        int hi = size - 1;

        while (lo <= hi) {
            final int mid = (lo + hi) >>> 1;
            final int midVal = array[mid];

            if (midVal < value) {
                lo = mid + 1;
            } else if (midVal > value) {
                hi = mid - 1;
            } else {
                return mid;  // value found
            }
        }
        return ~lo;  // value not present
    }


    /**
     * @param a
     * @param fromIndex
     * @param toIndex
     * @param key
     * @return
     */
    private static int binarySearch0(int[] a, int fromIndex, int toIndex,
                                     int key) {
        int low = fromIndex;
        int high = toIndex - 1;

        while (low <= high) {
            int mid = (low + high) >>> 1;
            int midVal = a[mid];

            if (midVal < key)
                low = mid + 1;
            else if (midVal > key)
                high = mid - 1;
            else
                return mid; // key found
        }
        return -(low + 1);  // key not found.
    }


    /**
     * 递归二分查找
     *
     * @param arr
     * @param start
     * @param end
     * @param key
     * @return
     */
    public static int binarySearch(int[] arr, int start, int end, int key) {
        if (start > end) {
            return -1;
        }
        int mid = (end + start) >>> 1;
        if (arr[mid] == key) {
            return mid;
        } else if (arr[mid] < key) {
            return binarySearch(arr, mid + 1, end, key);
        } else if (arr[mid] > key) {
            return binarySearch(arr, start, mid - 1, key);
        } else {
            return -1;
        }
    }
}
