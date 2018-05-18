package com.liuz.design;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

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

}
