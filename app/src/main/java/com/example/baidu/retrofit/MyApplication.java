package com.example.baidu.retrofit;

import android.app.Application;

import com.tool.cn.utils.CrashHandler;
import com.tool.cn.utils.LogUtils;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtils.isDebug(true);
        CrashHandler.getInstance().init(this);
    }
}
