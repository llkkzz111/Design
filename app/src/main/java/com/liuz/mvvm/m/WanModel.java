package com.liuz.mvvm.m;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.liuz.design.bean.ArticleBeans;
import com.liuz.design.bean.BannerBean;
import com.liuz.lotus.net.ViseHttp;
import com.liuz.lotus.net.mode.ApiResult;
import com.liuz.net.api.WanApiServices;

import java.util.List;

import io.reactivex.Observable;

/**
 * date: 2018/11/9 17:54
 * author liuzhao
 */
public class WanModel {

    private Application application;

    public WanModel(Application application) {
        this.application = application;
    }

    public MediatorLiveData<List<BannerBean>> getBanners() {
        return getBanner();
    }

    private MediatorLiveData<List<BannerBean>> getBanner() {


        return new AbsDataSource<List<BannerBean>, List<BannerBean>>() {
            @Override
            protected void saveCallResult(@NonNull List<BannerBean> item) {

            }

            @Override
            protected boolean shouldFetch(@Nullable List<BannerBean> data) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<List<BannerBean>> loadFromDb() {

                LiveData<List<BannerBean>> entity = new MutableLiveData<>();
                return entity;
            }

            @NonNull
            @Override
            protected LiveData<ApiResult<List<BannerBean>>> createCall() {
                final MediatorLiveData<ApiResult<List<BannerBean>>> result = new MediatorLiveData<>();

                return result;

            }

            @Override
            protected void onFetchFailed() {

            }
        }.getAsLiveData();

    }


    public void loadData(int pageNo) {


        Observable<ApiResult<ArticleBeans>> observableArticle = ViseHttp.RETROFIT().create(WanApiServices.class)
                .getArticleList(pageNo);


    }

}
