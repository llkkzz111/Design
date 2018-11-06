package com.liuz.lotus.net;

import android.content.Context;

import com.liuz.lotus.net.config.HttpGlobalConfig;
import com.liuz.lotus.net.core.ApiManager;
import com.liuz.lotus.net.request.RetrofitRequest;

import io.reactivex.disposables.Disposable;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * @Description: 网络请求入口
 * @author: <a href="http://www.xiaoyaoyou1212.com">DAWI</a>
 * @date: 2017-04-28 15:07
 */
public class ViseHttp {
    private static final HttpGlobalConfig NET_GLOBAL_CONFIG = HttpGlobalConfig.getInstance();
    private static Context context;
    private static OkHttpClient.Builder okHttpBuilder;
    private static Retrofit.Builder retrofitBuilder;

    private static OkHttpClient okHttpClient;

    public static HttpGlobalConfig CONFIG() {
        return NET_GLOBAL_CONFIG;
    }

    public static void init(Context appContext) {
        if (context == null && appContext != null) {
            context = appContext.getApplicationContext();
            okHttpBuilder = new OkHttpClient.Builder();
            retrofitBuilder = new Retrofit.Builder();
        }
    }

    public static Context getContext() {
        if (context == null) {
            throw new IllegalStateException("Please call ViseHttp.init(this) in Application to initialize!");
        }
        return context;
    }

    public static OkHttpClient.Builder getOkHttpBuilder() {
        if (okHttpBuilder == null) {
            throw new IllegalStateException("Please call ViseHttp.init(this) in Application to initialize!");
        }
        return okHttpBuilder;
    }

    public static Retrofit.Builder getRetrofitBuilder() {
        if (retrofitBuilder == null) {
            throw new IllegalStateException("Please call ViseHttp.init(this) in Application to initialize!");
        }
        return retrofitBuilder;
    }

    public static OkHttpClient getOkHttpClient() {
        if (okHttpClient == null) {
            okHttpClient = getOkHttpBuilder().build();
        }
        return okHttpClient;
    }

    /**
     * 可传入自定义Retrofit接口服务的请求类型
     *
     * @return
     */
    public static <T> RetrofitRequest RETROFIT() {
        return new RetrofitRequest();
    }

    /**
     * 添加请求订阅者
     *
     * @param tag
     * @param disposable
     */
    public static void addDisposable(Object tag, Disposable disposable) {
        ApiManager.get().add(tag, disposable);
    }

    /**
     * 根据Tag取消请求
     */
    public static void cancelTag(Object tag) {
        ApiManager.get().cancel(tag);
    }

    /**
     * 取消所有请求请求
     */
    public static void cancelAll() {
        ApiManager.get().cancelAll();
    }


}
