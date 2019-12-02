package com.example.commonlibrary.utils;

/**
 *  2017/2/22  11:28.
 *
 *
 * @version 1.0.0
 * @class LogUtils
 * @describe 日志工具类
 */
public class LogUtils {

    private static final String TAG = "LogUtils";
    /**
     * 是否是调试模式，true是，false不是.
     */
    private static boolean isDebug = false;

    /**
     * 初始化状态
     *
     * @param debug 是否是调试模式，true是，false不是.
     */
    public static void isDebug(boolean debug) {
        isDebug = debug;
    }

    public static void v(String msg) {
        LogUtils.v(TAG, msg);
    }

    public static void v(String tag, String msg) {
        if (isDebug)
            try {
                android.util.Log.v(tag, msg);
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    public static void v(String tag, String msg, Throwable t) {
        if (isDebug)
            try {
                android.util.Log.v(tag, msg, t);
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    public static void d(String msg) {
        LogUtils.d(TAG, msg);
    }

    public static void d(String tag, String msg) {
        if (isDebug)
            android.util.Log.d(tag, msg);
    }

    public static void d(String tag, String msg, Throwable t) {
        if (isDebug)
            try {
                android.util.Log.d(tag, msg, t);
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    public static void i(String msg) {
        LogUtils.i(TAG, msg);
    }

    public static void i(String tag, String msg) {
        if (isDebug)
            try {
                android.util.Log.i(tag, msg);
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    public static void i(String tag, String msg, Throwable t) {
        if (isDebug)
            try {
                android.util.Log.i(tag, msg, t);
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    public static void w(String msg) {
        LogUtils.w(TAG, msg);
    }

    public static void w(String tag, String msg) {
        if (isDebug)
            try {
                android.util.Log.w(tag, msg);
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    public static void w(String tag, String msg, Throwable t) {
        if (isDebug)
            try {
                android.util.Log.w(tag, msg, t);
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    public static void e(String msg) {
        LogUtils.e(TAG, msg);
    }

    public static void e(String tag, String msg) {
        if (isDebug)
            try {
                android.util.Log.e(tag, msg);
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    public static void e(String tag, String msg, Throwable t) {
        if (isDebug)
            try {
                android.util.Log.e(tag, msg, t);
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

}
