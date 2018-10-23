package com.liuz.common.interceptor;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by hq on 2017/12/19.
 */

public class LogInterceptorSlife implements Interceptor {
    private static final Charset UTF8 = Charset.forName("UTF-8");

    public String TAG = "LogInterceptorSlife";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        request = request
                .newBuilder()
                .url(request.url())
                .addHeader("Content-Type1", "application/json")
                .addHeader("Accept1", "application/json")
                .addHeader("Accept-Language1", "zh")
                .addHeader("app1", "01")
                .addHeader("appToken1", "hhehehehehehehehhee")
                .removeHeader("user-agent1")
                .addHeader("user-agent1", "slife-agent-x3NKb7qR=")
                .build();
        return chain.proceed(request);
    }

}