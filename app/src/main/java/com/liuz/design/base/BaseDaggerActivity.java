package com.liuz.design.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasFragmentInjector;
import dagger.android.support.HasSupportFragmentInjector;

/**
 * date: 2018/6/20 17:04
 * author liuzhao
 */
public abstract class BaseDaggerActivity extends TranslucentBarBaseActivity implements HasFragmentInjector, HasSupportFragmentInjector {

    @Inject
    DispatchingAndroidInjector<Fragment> supportFragmentInjector;
    @Inject
    DispatchingAndroidInjector<android.app.Fragment> frameworkFragmentInjector;

    public BaseDaggerActivity() {
    }

    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
    }

    public AndroidInjector<Fragment> supportFragmentInjector() {
        return this.supportFragmentInjector;
    }

    public AndroidInjector<android.app.Fragment> fragmentInjector() {
        return this.frameworkFragmentInjector;
    }
}
