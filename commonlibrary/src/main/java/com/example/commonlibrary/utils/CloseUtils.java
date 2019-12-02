package com.example.commonlibrary.utils;

import java.io.Closeable;
import java.io.IOException;

/**
 *  2017/2/22  14:16.
 *
 *
 * @version 1.0.0
 * @class CloseUtils
 * @describe 关闭IO流相关工具类
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class CloseUtils {

    /**
     * 关闭IO
     *
     * @param closeables closeable
     */
    public static void closeIO(Closeable... closeables) {
        if (closeables == null) return;
        for (Closeable closeable : closeables) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 安静关闭IO
     *
     * @param closeables closeable
     */
    public static void closeIOQuietly(Closeable... closeables) {
        if (closeables == null) return;
        for (Closeable closeable : closeables) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (IOException ignored) {
                }
            }
        }
    }

}
