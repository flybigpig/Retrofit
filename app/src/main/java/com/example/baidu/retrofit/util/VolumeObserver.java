package com.example.baidu.retrofit.util;

import android.content.Context;
import android.database.ContentObserver;
import android.media.AudioManager;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * @author
 * @date 2020/3/20.
 * GitHub：
 * email：
 * description：
 */
public class VolumeObserver extends ContentObserver {
    Context context;
    private Handler mCustomHandler;

    public VolumeObserver(Context c, Handler handler) {
        super(handler);
        mCustomHandler = handler;
        context = c;
    }

    @Override
    public boolean deliverSelfNotifications() {
        return super.deliverSelfNotifications();
    }

    @Override
    public void onChange(boolean selfChange) {
        super.onChange(selfChange);
        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        Message msg = new Message();
        msg.what = 2;
        msg.obj = currentVolume;
        mCustomHandler.sendMessageAtFrontOfQueue(msg);
        Log.d("currVolume:", "+" + currentVolume);
    }
}
