package com.example.baidu.retrofit.util;

import android.content.Context;

import com.allenliu.versionchecklib.callback.APKDownloadListener;
import com.allenliu.versionchecklib.core.http.HttpHeaders;
import com.allenliu.versionchecklib.core.http.HttpParams;
import com.allenliu.versionchecklib.core.http.HttpRequestMethod;
import com.allenliu.versionchecklib.v2.AllenVersionChecker;
import com.allenliu.versionchecklib.v2.builder.UIData;
import com.allenliu.versionchecklib.v2.callback.RequestVersionListener;
import com.example.baidu.retrofit.Api;

import java.io.File;

import io.reactivex.annotations.Nullable;

/**
 * 版本检测
 */
public class Versionchecklib {


    public static void checkVersion(Context context) {

        AllenVersionChecker.getInstance()
                .downloadOnly(crateUIData())
                .setApkDownloadListener(new APKDownloadListener() {
                    @Override
                    public void onDownloading(int progress) {

                    }

                    @Override
                    public void onDownloadSuccess(File file) {

                    }

                    @Override
                    public void onDownloadFail() {

                    }
                })
                .setShowNotification(false)
                .setForceRedownload(true)
                .executeMission(context);

    }


    /**
     * @return
     * @important 使用请求版本功能，可以在这里设置downloadUrl
     * 这里可以构造UI需要显示的数据
     * UIData 内部是一个Bundle
     */
    private static UIData crateUIData() {
        UIData uiData = UIData.create();
        uiData.setTitle("itle");
        uiData.setDownloadUrl("http://test-1251233192.coscd.myqcloud.com/1_1.apk");
        uiData.setContent("32");
        return uiData;
    }
}
