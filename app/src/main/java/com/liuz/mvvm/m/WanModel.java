package com.liuz.mvvm.m;

import com.liuz.design.bean.ArticleBean;
import com.liuz.design.bean.ArticleBeans;
import com.liuz.design.bean.BannerBean;
import com.liuz.lotus.net.ViseHttp;
import com.liuz.lotus.net.config.HttpGlobalConfig;
import com.liuz.lotus.net.func.ApiRetryFunc;
import com.liuz.lotus.net.mode.ApiResult;
import com.liuz.net.api.WanApiServices;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * date: 2018/11/9 17:54
 * author liuzhao
 */
public class WanModel {


    public Disposable getBanners() {
        Observable<ApiResult<List<BannerBean>>> observableBanner = ViseHttp.RETROFIT().create(WanApiServices.class)
                .getBanner();
        return observableBanner.subscribe();
    }


    public void loadData(int pageNo) {


        Observable<ApiResult<ArticleBeans>> observableArticle = ViseHttp.RETROFIT().create(WanApiServices.class)
                .getArticleList(pageNo);
        Observable.concat(observableBanner, observableArticle)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retryWhen(new ApiRetryFunc(HttpGlobalConfig.getInstance().getRetryCount(),
                        HttpGlobalConfig.getInstance().getRetryDelayMillis()))
                .subscribe(new Observer<ApiResult<? extends Object>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(ApiResult<?> apiResult) {
                        if (apiResult.getData() != null)
                            if (apiResult.getData() instanceof ArticleBeans) {
                                beanList.addAll(((ArticleBeans) apiResult.getData()).getDatas());
                                articleAdapter.notifyDataSetChanged();
                            } else {
                                beanList.clear();
                                beanList.add(new ArticleBean());
                                articleAdapter.setBannerBean((List<BannerBean>) apiResult.getData());
                            }
                    }

                    @Override
                    public void onError(Throwable e) {
                        rvArticle.setPullLoadMoreCompleted();
                    }

                    @Override
                    public void onComplete() {
                        rvArticle.setPullLoadMoreCompleted();
                    }
                });
    }
}
