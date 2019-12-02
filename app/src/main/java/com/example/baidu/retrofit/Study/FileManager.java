package com.example.baidu.retrofit.Study;

import com.example.baidu.retrofit.Bean.UploadAvatarResponseBean;
import com.example.baidu.retrofit.util.RetrofitUtil;
import com.tool.cn.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DefaultObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class FileManager {


    /**
     * 上传图片
     */
    private void upload() {

        String picPath = "";
        File file = new File(picPath);
        RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("userId", "")
                .addFormDataPart("uploadFile", file.getName(), imageBody);
        List<MultipartBody.Part> parts = builder.build().parts();

        RetrofitUtil.getTestService()
                .register(parts)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<UploadAvatarResponseBean>() {
                    @Override
                    public void onNext(UploadAvatarResponseBean uploadAvatarResponseBean) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }

                });
    }

    /**
     * 文件下载
     *
     */
}
