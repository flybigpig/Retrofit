package com.example.baidu.retrofit;

import com.example.baidu.retrofit.Bean.GanhuoNews;
import com.example.baidu.retrofit.Bean.HttpResult;
import com.example.baidu.retrofit.Bean.ResultBean;
import com.example.baidu.retrofit.Bean.UploadAvatarResponseBean;
import com.example.baidu.retrofit.Bean.WANAndroid;
import com.example.baidu.retrofit.Bean.home.AndroidBean;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface Api {

    String surl = "http://gank.io/api/data/";
    String downloadUrl = "";

    @GET("福利/{count}/{page}")
    Observable<ResultBean<List<GanhuoNews>>> getNews(@Path("count") int count, @Path("page") int page);


    @GET("Android/{size}/{page}")
    Observable<ResultBean<List<AndroidBean>>> getArticle(@Path("size") int size, @Path("page") int page);

    @Multipart
    @POST("user/register.do")
    Observable<UploadAvatarResponseBean> register(@Part List<MultipartBody.Part> partList);


    //    wanAndroid
}
