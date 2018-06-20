package com.liuz.design.di;

import com.liuz.design.ui.movies.MTimesMoviesActivity;
import com.liuz.design.ui.movies.MoviesModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * date: 2018/6/20 18:08
 * author liuzhao
 */
@Module
public abstract class ActivityBindingModule {


    @ActivityScoped
    @ContributesAndroidInjector(modules = MoviesModule.class)
    abstract MTimesMoviesActivity mTimesMoviesActivity();
}
