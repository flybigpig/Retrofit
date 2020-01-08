package com.example.baidu.retrofit.receiver;

import android.app.ListActivity;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.NotificationMessage;
import cn.jpush.android.helper.Logger;
import cn.jpush.android.service.JPushMessageReceiver;

/**
 * @author
 * @date 2020/1/8.
 * GitHub：
 * email：
 * description：
 */
public class JpushReceiver extends JPushMessageReceiver {

    private static final String TAG = "JIGUANG-Example";

    @Override
    public Notification getNotification(Context context, NotificationMessage notificationMessage) {
        return super.getNotification(context, notificationMessage);
    }

    @Override
    public void onRegister(Context context, String s) {
        super.onRegister(context, s);
        Log.d(TAG, s);
    }

    @Override
    public void onNotifyMessageArrived(Context context, NotificationMessage notificationMessage) {
        Log.d(TAG, notificationMessage.notificationContent);
    }


}
