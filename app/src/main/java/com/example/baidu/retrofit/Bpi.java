package com.example.baidu.retrofit;

import com.example.baidu.retrofit.Bean.GanhuoNews;
import com.example.baidu.retrofit.Bean.ResultBean;
import com.example.baidu.retrofit.Bean.UploadAvatarResponseBean;
import com.example.baidu.retrofit.Bean.home.AndroidBean;
import com.example.baidu.retrofit.Bean.home.ArticleBean;
import com.example.baidu.retrofit.Bean.home.BannerBean;
import com.example.baidu.retrofit.Bean.home.DataBean;
import com.example.baidu.retrofit.Bean.home.GongZhongHao;
import com.example.baidu.retrofit.Bean.home.UserBean;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

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


    String surl = "https://www.wanandroid.com/";

    @GET("article/list/{page}/json")
    Observable<ArticleBean<DataBean>> getWanArtical(@Path("page") int page);

    @GET("banner/json")
    Observable<ResultBean<List<BannerBean>>> getBanner();

    @GET("wxarticle/chapters/json")
    Observable<ResultBean<List<GongZhongHao>>> getGongZhongHao();

    @GET("wxarticle/list/{id}/{page}/json")
    Observable<ArticleBean<DataBean>> getArticalHistory(@Path("id") int id, @Path("page") int page);


    @GET("data/category/GanHuo/type/Android/page/{page}/count/{size}")
    Observable<ResultBean<List<AndroidBean>>> getArticle(@Path("size") int size, @Path("page") int page);

    @GET("data/category/Girl/type/Girl/page/{page}/count/{size}")
    Observable<ResultBean<List<GanhuoNews>>> getGirl(@Path("size") int size, @Path("page") int page);

    @Multipart
    @POST("user/register.do")
    Observable<UploadAvatarResponseBean> register(@Part List<MultipartBody.Part> partList);
}
