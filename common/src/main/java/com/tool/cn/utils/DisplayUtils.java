package com.tool.cn.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 *  2017/2/21  15:04.
 *
 *
 * @version 1.0.0
 * @class DisplayUtils
 * @describe 获取屏幕大小等工具类
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class DisplayUtils {

    /**
     * Andorid.util 包下的DisplayMetrics 类提供了一种关于显示的通用信息，如显示大小，分辨率和字体。
     *
     * @param context 上下文对象
     * @return DisplayMetrics对象
     */
    public static DisplayMetrics getMetrics(Context context) {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metrics);
        return metrics;
    }

    /**
     * dp转pixel
     *
     * @param context 上下文对象
     * @param dp      dp
     * @return pixel
     */
    public static int dp2px(Context context, float dp) {
        DisplayMetrics metrics = getMetrics(context);
        return (int) (dp * (metrics.densityDpi / 160f));
    }

    /**
     * pixel转dp
     *
     * @param context 上下文对象
     * @param px      pixel
     * @return dp
     */
    public static float px2dp(Context context, float px) {
        DisplayMetrics metrics = getMetrics(context);
        return px / (metrics.densityDpi / 160f);
    }

    /**
     * 获取屏幕宽度(单位：像素pixel)
     *
     * @param context 上下文对象
     * @return 屏幕宽度
     */
    public static int getScreenWidth(Context context) {
        DisplayMetrics dm = getMetrics(context);
        return dm.widthPixels;
    }

    /**
     * 获取屏幕高度(单位：像素pixel)
     *
     * @param context 上下文对象
     * @return 屏幕宽度
     */
    public static int getScreenHeight(Context context) {
        DisplayMetrics dm = getMetrics(context);
        return dm.heightPixels;
    }

    /**
     * 获取状态栏高度
     * (通过反射来实现状态栏高度的获取)
     * 注：该方法在任何时候都能正常获取状态栏高度
     *
     * @param context 上下文对象
     * @return 状态栏高度
     */
    public static int getStatusBarHeight(Context context) {
        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object obj = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height").get(obj).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    /**
     * 获取状态栏高度
     * (在源码程序中获取状态栏高度)
     *
     * @param context 上下文对象
     * @return 状态栏高度
     */
    @Deprecated
    public static int getStatusBarHeight2(Context context) {
        int statusHeight = 0;
        //statusHeight = context.getResources().getDimensionPixelSize(com.android.internal.R.dimen.status_bar_height);
        return statusHeight;
    }

    /**
     * 获取状态栏高度(状态栏相对Window的位置)
     * 注：在onCreate()方法中无效（返回0）
     *
     * @param activity Activity
     * @return 状态栏高度
     */
    public static int getStatusBarHeight(Activity activity) {
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        return frame.top;
    }

    /**
     * 获取标题栏高度
     * 注：在onCreate()方法中无效（返回0）
     *
     * @param activity Activity
     * @return 标题栏高度
     */
    public static int getTitleBarHeight(Activity activity) {
        //获取程序不包括标题栏的部分
        View view = activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT);
        int contentTop = view.getTop();
        int statusBarHeight = getStatusBarHeight(activity);
        return contentTop - statusBarHeight;
    }

    /**
     * 获取当前屏幕截图，包含状态栏
     *
     * @param activity Activity
     * @return 当前屏幕截图，包含状态栏
     */
    public static Bitmap screenshotWithStatusBar(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        int width = getScreenWidth(activity);
        int height = getScreenHeight(activity);
        Bitmap screenBitmap;
        screenBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height);
        view.destroyDrawingCache();
        return screenBitmap;
    }

    /**
     * 获取当前屏幕截图，不包含状态栏
     *
     * @param activity Activity
     * @return 当前屏幕截图，不包含状态栏
     */
    public static Bitmap screenshotWithoutStatusBar(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();

        int statusBarHeight = getStatusBarHeight(activity);

        int width = getScreenWidth(activity);
        int height = getScreenHeight(activity);
        Bitmap screenBitmap;
        screenBitmap = Bitmap.createBitmap(bitmap, 0, statusBarHeight, width, height - statusBarHeight);
        view.destroyDrawingCache();
        return screenBitmap;
    }

    /**
     * 设置透明状态栏
     * 最好用代码设置，style有时有的机型不起作用.
     *
     * @param activity activity
     */
    public static void setTransparentBar(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

}
