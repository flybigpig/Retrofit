package com.tool.cn.utils;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.view.ViewConfiguration;

import java.lang.reflect.Method;

/**
 *  2017/6/15  16:05.
 *
 *
 * @version 1.0.0
 * @class NavigationBarUtils
 * @describe 虚拟键相关操作工具类
 */
public class NavigationBarUtils {

    /**
     * 获取虚拟按键的高度
     *
     * @param context 上下文对象
     * @return 虚拟按键的高度
     */
    public static int getNavigationBarHeight(Context context) {
        int result = 0;
        if (hasNavigationBar(context)) {
            Resources resources = context.getResources();
            int resourcesId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
            if (resourcesId > 0) {
                result = resources.getDimensionPixelSize(resourcesId);
            }
        }
        return result;
    }

    /**
     * 检查是否存在虚拟按键栏
     *
     * @param context 上下文对象
     * @return 是否存在虚拟按键栏
     */
    public static boolean hasNavigationBar(Context context) {
        Resources res = context.getResources();
        int resourcesId = res.getIdentifier("config_showNavigationBar", "bool", "android");
        if (0 != resourcesId) {
            boolean hasNav = res.getBoolean(resourcesId);
            String isOverrideNavBar = getNavigationBarOverride();
            if ("1".equals(isOverrideNavBar)) {
                hasNav = false;
            } else if ("0".equals(isOverrideNavBar)) {
                hasNav = true;
            }
            return hasNav;
        } else {
            return !ViewConfiguration.get(context).hasPermanentMenuKey();
        }

    }

    /**
     * 判断虚拟案件栏是否被重写
     *
     * @return 虚拟案件栏是否被重写
     */
    public static String getNavigationBarOverride() {
        String sNavBarOverride = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                Class c = Class.forName("android.os.SystemProperties");
                Method method = c.getDeclaredMethod("get", String.class);
                method.setAccessible(true);
                sNavBarOverride = (String) method.invoke(null, "qemu.hw.mainkeys");
            } catch (Exception e) {
                //e.printStackTrace();
                sNavBarOverride = "1";
            }
        }
        return sNavBarOverride;
    }

}
