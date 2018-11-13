package com.liuz.mvvm.m;

import android.app.Application;

import com.liuz.design.bean.ArticleBeans;
import com.liuz.design.bean.BannerBean;
import com.liuz.lotus.net.ViseHttp;
import com.liuz.lotus.net.mode.ApiResult;
import com.liuz.net.api.WanApiServices;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * date: 2018/11/9 17:54
 * author liuzhao
 */
public class WanModel {

    private Application application;

    public WanModel(Application application) {
        this.application = application;
    }

    public Disposable getBanners() {
        Observable<ApiResult<List<BannerBean>>> observableBanner = ViseHttp.RETROFIT().create(WanApiServices.class)
                .getBanner();
        return observableBanner.subscribe();
    }


    public void loadData(int pageNo) {


        Observable<ApiResult<ArticleBeans>> observableArticle = ViseHttp.RETROFIT().create(WanApiServices.class)
                .getArticleList(pageNo);



    }

}
