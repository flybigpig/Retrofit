package com.example.commonlibrary.utils;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;

import java.util.List;

/**
 * APK信息
 *  2016/8/2.
 */
@SuppressWarnings("unused")
public class PackageUtils {

    public static int getAndroidSDKVersion() {
        return Build.VERSION.SDK_INT;
    }

    /**
     * 判断应用程序运行是否运行在后台
     *
     * @param context 上下文对象
     * @return true后台，false前台
     */
    public static boolean isAppOnForeground(@NonNull Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(context.getPackageName())) {
                if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_BACKGROUND) {
                    LogUtils.i("后台", appProcess.processName);
                    return true;
                } else {
                    LogUtils.i("前台", appProcess.processName);
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * 获取应用程序名称
     *
     * @param context 上下文对象
     * @return 应用程序名称
     */
    public static String getAppName(@NonNull Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            getVersionName(context);
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 获取应用程序包名
     * @param context
     * @return
     */
    public static String getPackageName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.packageName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取应用程序版本名
     *
     * @param context 上下文对象
     * @return 应用程序版本名
     */
    @SuppressWarnings("WeakerAccess")
    public static String getVersionName(@NonNull Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取应用程序版本号
     *
     * @param context 上下文对象
     * @return 应用程序版本号
     */
    public static int getVersionCode(@NonNull Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 判断手机是否安装app客户端
     *
     * @param context 上下文对象
     * @return true已安装，false未安装
     */
    public static boolean isWeixinAvilible(@NonNull Context context,String packageName) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals(packageName)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 重新启动当前APP
     *
     * @param context 上下文对象
     */
    public void restartAPP(@NonNull Context context) {
        Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage("");
        context.startActivity(launchIntent);
    }

    /**
     * 延迟millis时间后重新启动APP
     *
     * @param context 上下文对象
     * @param millis  延迟millis时间
     */
    public void pendingRestartAPP(@NonNull Context context, long millis) {
        Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage("");
        int requestCode = 0;
        PendingIntent pendingIntent = PendingIntent.getActivity(context, requestCode, launchIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC, System.currentTimeMillis() + millis, pendingIntent);
        AppManager.getInstance().AppExit(context);
    }

}
