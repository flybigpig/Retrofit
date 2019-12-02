package com.tool.cn;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;

import com.tool.cn.utils.DisplayUtils;
import com.tool.cn.utils.GlideImageManager;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 *  2017/3/15  18:18.
 *
 *
 * @version 1.0.0
 * @class BaseApplication
 * @describe 公共的Application
 */
public abstract class BaseApplication extends Application {

    protected final String TAG = getClass().getSimpleName();

    public static int mHeightPixels;//屏幕高度
    public static int mWidthPixels;//屏幕宽度
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        mHeightPixels = DisplayUtils.getScreenHeight(this);
        mWidthPixels = DisplayUtils.getScreenWidth(this);
        context = getApplicationContext();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //兼容android7.0 使用共享文件的形式
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }
        closeAndroidPDialog();
    }

    public static Context getContext() {
        return context;
    }

    /**
     * 在子类的onCreate方法中调用创建Project Folder.
     *
     * @param rootPath eg："/Common"
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    protected final void createFile(String rootPath) {
        //SDCard路径
        final String sdCardPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        //项目文件夹
        final String basePath = sdCardPath + rootPath;
        //image文件夹
        BaseConstants.IMAGE_PATH = basePath + "/Image/";
        //video文件夹
        BaseConstants.VIDEO_PATH = basePath + "/video/";
        //dowmload文件夹
        BaseConstants.DOWNLOAD_PATH = basePath + "/Download/";
        //crash异常信息收集文件夹
        BaseConstants.CRASH_PATH = basePath + "/Crash/";
        File file;

        file = new File(basePath);
        if (!file.exists()) {
            file.mkdirs();
        }

        file = new File(BaseConstants.IMAGE_PATH);
        if (!file.exists()) {
            file.mkdirs();
        }

        file = new File(BaseConstants.DOWNLOAD_PATH);
        if (!file.exists()) {
            file.mkdirs();
        }

        file = new File(BaseConstants.CRASH_PATH);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    /**
     * Android P 后谷歌限制了开发者调用非官方公开API 方法或接口,干掉每次启动都会弹出的提醒窗口
     */
    private void closeAndroidPDialog() {
        try {
            Class aClass = Class.forName("android.content.pm.PackageParser$Package");
            Constructor declaredConstructor = aClass.getDeclaredConstructor(String.class);
            declaredConstructor.setAccessible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Class cls = Class.forName("android.app.ActivityThread");
            Method declaredMethod = cls.getDeclaredMethod("currentActivityThread");
            declaredMethod.setAccessible(true);
            Object activityThread = declaredMethod.invoke(null);
            Field mHiddenApiWarningShown = cls.getDeclaredField("mHiddenApiWarningShown");
            mHiddenApiWarningShown.setAccessible(true);
            mHiddenApiWarningShown.setBoolean(activityThread, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
