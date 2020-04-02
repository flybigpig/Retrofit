package com.example.baidu.retrofit.util;

import android.util.Log;

import com.example.baidu.retrofit.Bpi;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 网络请求
 */
public class RetrofitUtil {
    private volatile static RetrofitUtil sInstance;
    private Retrofit mRetrofit;
    private Bpi mTestService;
    private OkHttpClient mClient;
    private String TAG = "OKHTTP";

    private RetrofitUtil() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(Bpi.surl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(OkHttpClient())
                .build();
        mTestService = mRetrofit.create(Bpi.class);
    }

    public static RetrofitUtil getInstance() {
        if (sInstance == null) {
            synchronized (RetrofitUtil.class) {
                if (sInstance == null) {
                    sInstance = new RetrofitUtil();
                }
            }
        }
        return sInstance;
    }

    public static Bpi getTestService() {
        return getInstance().mTestService;
    }

    private OkHttpClient OkHttpClient() {

        HttpLoggingInterceptor interceptor =
                new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                    @Override
                    public void log(String message) {
                        try {
                            Log.d(TAG, message);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

        //包含header、body数据
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//        setlevel用来设置日志打印的级别，共包括了四个级别：NONE,BASIC,HEADER,BODY
//        BASEIC:请求/响应行
//        HEADER:请求/响应行 + 头
//        BODY:请求/响应行 + 头 + 体
        //OkHttp进行添加拦截器loggingInterceptor
        mClient = new OkHttpClient.Builder().addInterceptor(interceptor).addInterceptor(new Interceptor() {

            @Override

            public Response intercept(Chain chain) throws IOException {

                Request request = chain.request()

                        .newBuilder()

                        .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")

                        .addHeader("Accept-Encoding", "gzip, deflate")

                        .addHeader("Connection", "keep-alive")

                        .addHeader("Accept", "*/*")

                        .addHeader("Cookie", "add cookies here")

                        .addHeader("name", "wk")

                        .build();

                return chain.proceed(request);

            }


        }).build();

        return mClient;

    }


}