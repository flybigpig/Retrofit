package com.example.baidu.retrofit.Study.Interceptor;

import android.text.TextUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 缓存拦截器
 *
 */
public class CacheInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        String cache = request.header("Cache-Time");
        if (!TextUtils.isEmpty(cache)) {//缓存时间不为空
            Response response1 = response.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    //cache for cache seconds
                    .header("Cache-Control", "max-age="+cache)
                    .build();
            return response1;
        } else {
            return response;
        }
    }
}
