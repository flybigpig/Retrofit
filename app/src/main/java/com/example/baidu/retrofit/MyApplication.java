package com.example.baidu.retrofit;

import android.app.Application;

import com.tool.cn.utils.CrashHandler;
import com.tool.cn.utils.LogUtils;

public class MyApplication extends Application {

    private static MyApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        LogUtils.isDebug(true);
        CrashHandler.getInstance().init(this);
    }

    public static MyApplication getInstance() {
        return instance;
    }
}
