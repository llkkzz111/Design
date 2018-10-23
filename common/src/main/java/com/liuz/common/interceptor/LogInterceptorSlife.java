package com.liuz.common.interceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by hq on 2017/12/19.
 */

public class LogInterceptorSlife implements Interceptor {


    @Override
    public Response intercept(Chain chain) throws IOException {
        Request oldRequest = chain.request();
        Request.Builder newRequestBuild = oldRequest.newBuilder();
        Request newRequest;
        String newRequestUrl = oldRequest.url().toString();
        newRequest = newRequestBuild
                .url(newRequestUrl)
                .addHeader("Content-Type1", "application/json")
                .addHeader("Accept1", "application/json")
                .addHeader("Accept-Language1", "zh")
                .addHeader("app1", "01")
                .addHeader("appToken1", "--------------------------")
                .build();
        Response response = chain.proceed(newRequest);
        MediaType mediaType = response.body().contentType();
        String content = response.body().string();
        return response.newBuilder()
                .body(okhttp3.ResponseBody.create(mediaType, content))
                .build();
    }


}