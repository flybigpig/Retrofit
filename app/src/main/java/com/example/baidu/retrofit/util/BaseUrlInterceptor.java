package com.example.baidu.retrofit.util;

import com.example.baidu.retrofit.Constants;

import java.io.IOException;
import java.util.List;

import cn.jmessage.support.okhttp3.HttpUrl;
import cn.jmessage.support.okhttp3.Interceptor;
import cn.jmessage.support.okhttp3.Request;
import cn.jmessage.support.okhttp3.Response;

/**
 * @author
 * @date 2020/4/10.
 * GitHub：
 * email：
 * description：
 */
public class BaseUrlInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {

        //
        Request originalRequest = chain.request();

        HttpUrl oldIrl = originalRequest.url();

        Request.Builder builder = originalRequest.newBuilder();

        List<String> headerType = originalRequest.headers("name");

        if (headerType != null && headerType.size() > 0) {

            builder.removeHeader("name");

            String name = headerType.get(0);

            HttpUrl baseUrl = null;

            if ("header_content_1".equals(name)) {
                Constants.surl = HttpUrl.parse("").toString();
            } else if ("header_content_2".equals(name)) {
                Constants.surl = HttpUrl.parse("").toString();
            } else if ("java".equals(name)) {
                Constants.surl = HttpUrl.parse("").toString();
            }

            //重建新的HttpUrl，需要重新设置的url部分
            baseUrl = HttpUrl.get(Constants.surl);

            HttpUrl newHttpUrl = oldIrl.newBuilder()
                    .scheme(baseUrl.scheme())//http协议如：http或者https
                    .host(baseUrl.host())//主机地址
                    .port(baseUrl.port())//端口
                    .build();
            String header = originalRequest.header("NoToken");
            if (header == null) {
                builder.addHeader("Cookie", "cookie")
                        .addHeader("Content-Type", "application/x-www-form-urlencoded")
                        .addHeader("X-Requested-With", "XMLHttpRequest");
            }

            //获取处理后的新newRequest
            Request newRequest = builder.url(newHttpUrl).build();
            return chain.proceed(newRequest);
        } else {
            return chain.proceed(originalRequest);
        }

    }
}
