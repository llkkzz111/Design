package com.liuz.net.interceptor;

import android.util.Log;

import com.liuz.design.utils.ActivityUtils;
import com.liuz.lotus.BuildConfig;

import java.io.IOException;
import java.util.Set;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * date: 2018/10/18 17:24
 * author liuzhao
 */
public class HeaderInterceptor extends HttpResponseInterceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        super.intercept(chain);
        Request request = chain.request();
        RequestBody requestBody = request.body();
        request = request
                .newBuilder()
                .url(request.url())
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .addHeader("Accept-Language", "zh")
                .addHeader("app", "01")
                .addHeader("appToken", "hhehehehehehehehhee")
                .build();
        if (request.method().equals("GET")) {
            HttpUrl url = request.url();
            Set<String> parameterNames = url.queryParameterNames();      //
            for (String key : parameterNames) {                          //循环参数列表
                if (BuildConfig.DEBUG) Log.e("MyInterceptor", key);      // 如果要对已有的参数做进一步处理可以这样拿到参数
            }                                                            //只添加的话 倒是没有必要
            String sUrl = url.toString();
            int index = sUrl.indexOf("?");
            if (index > 0) {
                sUrl = sUrl + "所需参数拼接";    //所需参数拼接 ==>就是类似于 name=123&version=12&....这些
            } else {
                sUrl = sUrl + "?" + "所需参数拼接";
            }
            request = request.newBuilder().url(sUrl).build();   //重新构建
        } else if (request.method().equals("POST")) {
            if (requestBody instanceof FormBody) {
                FormBody.Builder builder = new FormBody.Builder();
                FormBody formBody = (FormBody) requestBody;
                for (int i = 0; i < formBody.size(); i++) {    // 如果要对已有的参数做进一步处理可以这样拿到参数
                    Log.e("MyInterceptor", "encodedNames:" + formBody.encodedName(i) + " encodedValues:" + formBody.encodedValue(i));
                    builder.addEncoded(formBody.encodedName(i), formBody.encodedValue(i));
                }
                builder.addEncoded("参数1", "值1");  //添加公共参数
                builder.addEncoded("参数2", "值2");
                builder.addEncoded("参数3", "值3");
                request = request.newBuilder().post(builder.build()).build();  //重新构建
            }
        }


        Response response = chain.proceed(request);


        return response;
    }

    @Override
    Response processAccessTokenExpired(Chain chain, Request request) {
        ActivityUtils.gotoLogin();
        return null;
    }

    @Override
    Response processRefreshTokenExpired(Chain chain, Request request) {
        return null;
    }

    @Override
    Response processOtherPhoneLogin(Chain chain, Request request) {
        return null;
    }

    @Override
    Response processSignError(Chain chain, Request request) {
        return null;
    }

    @Override
    Response processTimestampError(Chain chain, Request request) {
        return null;
    }

    @Override
    Response processNoAccessToken(Chain chain, Request request) {
        return null;
    }

    @Override
    Response processOtherError(int errorCode, Chain chain, Request request) {
        return null;
    }
}
