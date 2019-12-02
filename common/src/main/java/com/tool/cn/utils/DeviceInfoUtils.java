package com.tool.cn.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

/**
 *  2017/4/24  17:04.
 *
 *
 * @version 1.0.0
 * @class DeviceInfoUtils
 * @describe 设备信息帮助类
 */
public class DeviceInfoUtils {

    /**
     * 获取手机IMEI
     *
     * @param context
     * @return
     */
    public static final String getIMEI(Context context) {
        try {
            //实例化TelephonyManager对象
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            //获取IMEI号
            @SuppressLint("MissingPermission") String imei = telephonyManager.getDeviceId();
            //再次做个验证，也不是什么时候都能获取到的啊
            if (imei == null) {
                imei = "";
            }
            return imei;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

    }

    /**
     * 获取手机IMSI
     */
    public static String getIMSI(Context context) {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            //获取IMSI号
            @SuppressLint("MissingPermission") String imsi = telephonyManager.getSubscriberId();
            if (null == imsi) {
                imsi = "";
            }
            return imsi;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 获取app当前的渠道号或application中指定的meta-data
     *
     * @return 如果没有获取成功(没有对应值，或者异常)，则返回值为空
     */
    public static String getAppMetaData(Context context, String key) {
        if (context == null || TextUtils.isEmpty(key)) {
            return null;
        }
        String channelNumber = null;
        try {
            PackageManager packageManager = context.getPackageManager();
            if (packageManager != null) {
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
                if (applicationInfo != null) {
                    if (applicationInfo.metaData != null) {
                        channelNumber = applicationInfo.metaData.getString(key);
                    }
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return channelNumber;
    }

    /**
     * 获取显示屏参数
     *
     * @return 显示屏参数
     */
    public static String getDisplay() {
        return Build.DISPLAY;
    }

    /**
     * 获取手机制造商
     *
     * @return 手机制造商
     */
    public static String getProduct() {
        return Build.PRODUCT;
    }

    /**
     * 获取版本
     *
     * @return 版本
     */
    public static String getModel() {
        return Build.MODEL;
    }

    /**
     * 获取CPU指令集
     *
     * @return CPU指令集
     */
    public static String getCPU_ABI() {
        return Build.CPU_ABI;
    }

    /**
     * 获取设备参数
     *
     * @return 设备参数
     */
    public static String getDevice() {
        return Build.DEVICE;
    }

    /**
     * 获取硬件名称
     *
     * @return 硬件名称
     */
    public static String getFingerprint() {
        return Build.FINGERPRINT;
    }

    /**
     * 获取Android系统定制商
     *
     * @return Android系统定制商
     */
    public static String getBrand() {
        return Build.BRAND;//http://dev.xiaomi.com/doc/p=9494/index.html
    }

}
