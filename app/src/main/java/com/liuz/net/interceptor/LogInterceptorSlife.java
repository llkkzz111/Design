package com.liuz.net.interceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by hq on 2017/12/19.
 */

public class LogInterceptorSlife implements Interceptor {


    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        request = request.newBuilder()
                .url(request.url())
                .addHeader("Content-Type1", "application/json")
                .addHeader("Accept1", "application/json")
                .addHeader("Accept-Language1", "zh")
                .addHeader("app1", "01")
                .addHeader("appToken1", "--------------------------")
                .build();
        return chain.proceed(request);
    }


}