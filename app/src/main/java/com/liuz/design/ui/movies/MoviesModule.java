package com.liuz.design.ui.movies;

import com.liuz.design.di.FragmentScoped;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * date: 2018/6/20 17:08
 * author liuzhao
 */
@Module
public abstract class MoviesModule {


    @FragmentScoped
    @ContributesAndroidInjector
    abstract MoviesFragment moviesFragment();


}
