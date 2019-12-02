package com.example.baidu.retrofit.util;

import android.database.Observable;

public class SmsObserver<T> extends Observable<T> {
    public static String SMS_URI;

    public interface SmsListener {
        void onResult();
    }
}
