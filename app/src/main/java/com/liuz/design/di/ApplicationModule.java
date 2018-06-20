package com.liuz.design.di;

import android.app.Application;
import android.content.Context;

import dagger.Binds;
import dagger.Module;

/**
 * date: 2018/6/20 16:19
 * author liuzhao
 */
@Module
public abstract class ApplicationModule {
    //expose Application as an injectable context
    @Binds
    abstract Context bindContext(Application application);
}
