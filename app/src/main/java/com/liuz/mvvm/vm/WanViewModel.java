package com.liuz.mvvm.vm;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.NonNull;

import com.liuz.common.ApiResultTransformer;
import com.liuz.common.subscriber.ApiSubscriber;
import com.liuz.db.WanDataBase;
import com.liuz.db.wan.AccountBean;
import com.liuz.design.bean.ArticleBeans;
import com.liuz.design.bean.BannerBean;
import com.liuz.lotus.net.ViseHttp;
import com.liuz.lotus.net.exception.ApiException;
import com.liuz.lotus.net.mode.ApiResult;
import com.liuz.mvvm.m.WanModel;
import com.liuz.net.api.WanApiServices;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * date: 2018/11/9 11:47
 * author liuzhao
 */
public class WanViewModel extends AndroidViewModel {
    private final MediatorLiveData<ApiResult> liveData = new MediatorLiveData<>();
    private WanModel model;

    public WanViewModel(@NonNull Application application) {
        super(application);
        model = new WanModel(application);
    }

    public MediatorLiveData<ApiResult> getLiveData() {
        return liveData;
    }


    public void loadData(int pageNo) {


        Observable<ApiResult<ArticleBeans>> observableArticle = ViseHttp.RETROFIT().create(WanApiServices.class)
                .getArticleList(pageNo);

        Observable<ApiResult<List<BannerBean>>> observableBanner = ViseHttp.RETROFIT().create(WanApiServices.class)
                .getBanner();

        Observable.concat(observableBanner, observableArticle)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ApiResult>() {

                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(ApiResult apiResult) {
                        liveData.setValue(apiResult);
                    }


                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });


        model.loadData(pageNo);
    }

    public void getAccount(String userName) {

        Observable.create((ObservableOnSubscribe<AccountBean>) emitter -> {
            AccountBean bean = WanDataBase.getInstance(getApplication()).accountDao().getAccountBean(userName);
            if (bean != null)
                liveData.postValue(new ApiResult().setData(bean));
        }).subscribeOn(Schedulers.io()).subscribe();
    }

    public void loadMore(int pageNo) {
        ViseHttp.RETROFIT().create(WanApiServices.class).getArticleList(pageNo)
                .compose(ApiResultTransformer.norTransformer())
                .subscribe(new ApiSubscriber<ApiResult<ArticleBeans>>() {
                    @Override
                    public void onNext(ApiResult<ArticleBeans> articleBeansApiResult) {
                        liveData.postValue(articleBeansApiResult);
                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    protected void onError(ApiException e) {
                    }

                });

    }
}
