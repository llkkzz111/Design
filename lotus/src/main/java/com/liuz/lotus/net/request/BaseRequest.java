package com.liuz.lotus.net.request;

import com.liuz.lotus.common.ViseConfig;
import com.liuz.lotus.net.ViseHttp;
import com.liuz.lotus.net.callback.UCallback;
import com.liuz.lotus.net.config.HttpGlobalConfig;
import com.liuz.lotus.net.core.ApiCookie;
import com.liuz.lotus.net.interceptor.UploadProgressInterceptor;
import com.liuz.lotus.net.mode.ApiHost;
import com.vise.log.ViseLog;
import com.vise.utils.assist.SSLUtil;

import java.io.File;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.ConnectionPool;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @Description: 请求基类
 * @author: <a href="http://www.xiaoyaoyou1212.com">DAWI</a>
 * @date: 2017-04-28 16:05
 */
public abstract class BaseRequest<R extends BaseRequest> {
    protected HttpGlobalConfig httpGlobalConfig;//全局配置
    protected Retrofit retrofit;//Retrofit对象
    protected List<Interceptor> interceptors = new ArrayList<>();//局部请求的拦截器
    protected List<Interceptor> networkInterceptors = new ArrayList<>();//局部请求的网络拦截器


    protected long readTimeOut;//读取超时时间
    protected long writeTimeOut;//写入超时时间
    protected long connectTimeOut;//连接超时时间
    protected boolean isHttpCache;//是否使用Http缓存
    protected UCallback uploadCallback;//上传进度回调


    /**
     * 生成局部配置
     */
    protected void generateLocalConfig() {
        OkHttpClient.Builder newBuilder = ViseHttp.getOkHttpClient().newBuilder();

        if (!interceptors.isEmpty()) {
            for (Interceptor interceptor : interceptors) {
                newBuilder.addInterceptor(interceptor);
            }
        }

        if (!networkInterceptors.isEmpty()) {
            for (Interceptor interceptor : networkInterceptors) {
                newBuilder.addNetworkInterceptor(interceptor);
            }
        }

        if (uploadCallback != null) {
            newBuilder.addNetworkInterceptor(new UploadProgressInterceptor(uploadCallback));
        }

        if (readTimeOut > 0) {
            newBuilder.readTimeout(readTimeOut, TimeUnit.SECONDS);
        }

        if (writeTimeOut > 0) {
            newBuilder.readTimeout(writeTimeOut, TimeUnit.SECONDS);
        }

        if (connectTimeOut > 0) {
            newBuilder.readTimeout(connectTimeOut, TimeUnit.SECONDS);
        }

        if (isHttpCache) {
            try {
                if (httpGlobalConfig.getHttpCache() == null) {
                    httpGlobalConfig.httpCache(new Cache(httpGlobalConfig.getHttpCacheDirectory(), ViseConfig.CACHE_MAX_SIZE));
                }
                httpGlobalConfig.cacheOnline(httpGlobalConfig.getHttpCache());
                httpGlobalConfig.cacheOffline(httpGlobalConfig.getHttpCache());
            } catch (Exception e) {
                ViseLog.e("Could not create http cache" + e);
            }
            newBuilder.cache(httpGlobalConfig.getHttpCache());
        }


        ViseHttp.getRetrofitBuilder().client(newBuilder.build());
        retrofit = ViseHttp.getRetrofitBuilder().build();

    }

    /**
     * 生成全局配置
     */
    protected void generateGlobalConfig() {
        httpGlobalConfig = ViseHttp.CONFIG();

        if (httpGlobalConfig.getBaseUrl() == null) {
            httpGlobalConfig.baseUrl(ApiHost.getHost());
        }
        ViseHttp.getRetrofitBuilder().baseUrl(httpGlobalConfig.getBaseUrl());

        if (httpGlobalConfig.getConverterFactory() == null) {
            httpGlobalConfig.converterFactory(GsonConverterFactory.create());
        }
        ViseHttp.getRetrofitBuilder().addConverterFactory(httpGlobalConfig.getConverterFactory());

        if (httpGlobalConfig.getCallAdapterFactory() == null) {
            httpGlobalConfig.callAdapterFactory(RxJava2CallAdapterFactory.create());
        }
        ViseHttp.getRetrofitBuilder().addCallAdapterFactory(httpGlobalConfig.getCallAdapterFactory());

        if (httpGlobalConfig.getCallFactory() != null) {
            ViseHttp.getRetrofitBuilder().callFactory(httpGlobalConfig.getCallFactory());
        }

        if (httpGlobalConfig.getHostnameVerifier() == null) {
            httpGlobalConfig.hostnameVerifier(new SSLUtil.UnSafeHostnameVerifier(httpGlobalConfig.getBaseUrl()));
        }
        ViseHttp.getOkHttpBuilder().hostnameVerifier(httpGlobalConfig.getHostnameVerifier());

        if (httpGlobalConfig.getSslSocketFactory() == null) {
            httpGlobalConfig.SSLSocketFactory(SSLUtil.getSslSocketFactory(null, null, null));
        }
        ViseHttp.getOkHttpBuilder().sslSocketFactory(httpGlobalConfig.getSslSocketFactory());

        if (httpGlobalConfig.getConnectionPool() == null) {
            httpGlobalConfig.connectionPool(new ConnectionPool(ViseConfig.DEFAULT_MAX_IDLE_CONNECTIONS,
                    ViseConfig.DEFAULT_KEEP_ALIVE_DURATION, TimeUnit.SECONDS));
        }
        ViseHttp.getOkHttpBuilder().connectionPool(httpGlobalConfig.getConnectionPool());

        if (httpGlobalConfig.isCookie() && httpGlobalConfig.getApiCookie() == null) {
            httpGlobalConfig.apiCookie(new ApiCookie(ViseHttp.getContext()));
        }
        if (httpGlobalConfig.isCookie()) {
            ViseHttp.getOkHttpBuilder().cookieJar(httpGlobalConfig.getApiCookie());
        }

        if (httpGlobalConfig.getHttpCacheDirectory() == null) {
            httpGlobalConfig.setHttpCacheDirectory(new File(ViseHttp.getContext().getCacheDir(), ViseConfig.CACHE_HTTP_DIR));
        }
        if (httpGlobalConfig.isHttpCache()) {
            try {
                if (httpGlobalConfig.getHttpCache() == null) {
                    httpGlobalConfig.httpCache(new Cache(httpGlobalConfig.getHttpCacheDirectory(), ViseConfig.CACHE_MAX_SIZE));
                }
                httpGlobalConfig.cacheOnline(httpGlobalConfig.getHttpCache());
                httpGlobalConfig.cacheOffline(httpGlobalConfig.getHttpCache());
            } catch (Exception e) {
                ViseLog.e("Could not create http cache" + e);
            }
        }
        if (httpGlobalConfig.getHttpCache() != null) {
            ViseHttp.getOkHttpBuilder().cache(httpGlobalConfig.getHttpCache());
        }
        ViseHttp.getOkHttpBuilder().connectTimeout(ViseConfig.DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        ViseHttp.getOkHttpBuilder().writeTimeout(ViseConfig.DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        ViseHttp.getOkHttpBuilder().readTimeout(ViseConfig.DEFAULT_TIMEOUT, TimeUnit.SECONDS);
    }

    /**
     * 获取第一级type
     *
     * @param t
     * @param <T>
     * @return
     */
    protected <T> Type getType(T t) {
        Type genType = t.getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        Type type = params[0];
        Type finalNeedType;
        if (params.length > 1) {
            if (!(type instanceof ParameterizedType)) throw new IllegalStateException("没有填写泛型参数");
            finalNeedType = ((ParameterizedType) type).getActualTypeArguments()[0];
        } else {
            finalNeedType = type;
        }
        return finalNeedType;
    }

    /**
     * 获取次一级type(如果有)
     *
     * @param t
     * @param <T>
     * @return
     */
    protected <T> Type getSubType(T t) {
        Type genType = t.getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        Type type = params[0];
        Type finalNeedType;
        if (params.length > 1) {
            if (!(type instanceof ParameterizedType)) throw new IllegalStateException("没有填写泛型参数");
            finalNeedType = ((ParameterizedType) type).getActualTypeArguments()[0];
        } else {
            if (type instanceof ParameterizedType) {
                finalNeedType = ((ParameterizedType) type).getActualTypeArguments()[0];
            } else {
                finalNeedType = type;
            }
        }
        return finalNeedType;
    }
}
