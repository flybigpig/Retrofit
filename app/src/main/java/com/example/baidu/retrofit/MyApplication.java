package com.example.baidu.retrofit;

import android.app.Application;
import android.util.Log;

import com.squareup.leakcanary.LeakCanary;
import com.tencent.smtt.sdk.QbSdk;
import com.tool.cn.utils.CrashHandler;
import com.tool.cn.utils.LogUtils;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.im.android.api.JMessageClient;


public class MyApplication extends Application {

    private static MyApplication instance;
    public String registrationID;

    @Override
    public void onCreate() {
        super.onCreate();
//        MultiDex.install(this);
        instance = this;
        LogUtils.isDebug(true);
        CrashHandler.getInstance().init(this);

        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        registrationID = JPushInterface.getRegistrationID(this);

//        MobSDK.init(this, "2dc08f5fc143e", "5dd794d8c9662b965f8182f7d9b503c8");

        JMessageClient.setDebugMode(true);
        JMessageClient.init(this);

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);


//        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
//
//        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {
//
//            @Override
//            public void onViewInitFinished(boolean arg0) {
//                // TODO Auto-generated method stub
//                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
//                Log.d("app", " onViewInitFinished is " + arg0);
//            }
//
//            @Override
//            public void onCoreInitFinished() {
//                // TODO Auto-generated method stub
//            }
//        };
//        //x5内核初始化接口
//        QbSdk.initX5Environment(getApplicationContext(), cb);
        CrashHandler.getInstance().init(this);

    }

    public static MyApplication getInstance() {
        return instance;
    }
}
