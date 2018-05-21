package com.liuz.design;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.liuz.lotus.net.ViseHttp;
import com.liuz.lotus.net.interceptor.HttpLogInterceptor;
import com.vise.log.ViseLog;
import com.vise.log.inner.LogcatTree;

/**
 * date: 2018/5/17 16:19
 * author liuzhao
 */
public class LotusApplication extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initLog();
        initNet();
    }

    private void initLog() {
        ViseLog.getLogConfig()
                .configAllowLog(true)//是否输出日志
                .configShowBorders(false);//是否排版显示
        ViseLog.plant(new LogcatTree());//添加打印日志信息到Logcat的树
    }

    private void initNet() {
        ViseHttp.init(this);
        ViseHttp.CONFIG()
                //配置请求主机地址
                .baseUrl("http://192.168.1.105/")
                .setCookie(true)
                //配置日志拦截器
                .networkInterceptor(new StethoInterceptor())
                .interceptor(new HttpLogInterceptor()
                        .setLevel(HttpLogInterceptor.Level.BODY));

    }

}
