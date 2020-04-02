package com.example.baidu.retrofit;

import com.example.baidu.retrofit.Bean.UploadAvatarResponseBean;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * @author
 * @date 2020/4/2.
 * GitHub：
 * email：
 * description：
 */
public interface Bpi {

    String Article = "Article";
    String GanHuo = "GanHuo";
    String Girl = "Girl";


    String surl = "https://gank.io/api/v2/";


    @Multipart
    @POST("user/register.do")
    Observable<UploadAvatarResponseBean> register(@Part List<MultipartBody.Part> partList);
}
