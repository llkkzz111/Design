package com.liuz.mvvm.vm;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.NonNull;

import com.liuz.common.ApiResultTransformer;
import com.liuz.common.subscriber.ApiResultSubscriber;
import com.liuz.db.WanDataBase;
import com.liuz.db.wan.AccountBean;
import com.liuz.design.bean.ArticleBean;
import com.liuz.design.bean.ArticleBeans;
import com.liuz.design.bean.BannerBean;
import com.liuz.lotus.net.ViseHttp;
import com.liuz.lotus.net.config.HttpGlobalConfig;
import com.liuz.lotus.net.exception.ApiException;
import com.liuz.lotus.net.func.ApiRetryFunc;
import com.liuz.lotus.net.mode.ApiResult;
import com.liuz.mvvm.m.WanModel;
import com.liuz.net.api.WanApiServices;

import java.util.List;

import io.reactivex.Observable;
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
    private final MediatorLiveData<ArticleBeans> liveData = new MediatorLiveData<>();
    private final MediatorLiveData<AccountBean> accLiveData = new MediatorLiveData<>();
    private WanModel model;

    public WanViewModel(@NonNull Application application) {
        super(application);
        model = new WanModel(application);
    }

    public MediatorLiveData<ArticleBeans> getLiveData() {
        return liveData;
    }

    public MediatorLiveData<AccountBean> getAccLiveData() {
        return accLiveData;
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


        ViseHttp.RETROFIT().create(WanApiServices.class).getArticleList(pageNo)
                .compose(ApiResultTransformer.norTransformer())
                .subscribe(new ApiResultSubscriber<ArticleBeans>() {
                    @Override
                    protected void onError(ApiException e) {
//                        liveData.postValue(e);
                    }

                    @Override
                    public void onSuccess(ArticleBeans data) {
                        liveData.postValue(data);
                    }


                });

        model.loadData(pageNo);
    }

    public void getAccount(String userName) {
        Observable.create((ObservableOnSubscribe<AccountBean>) emitter -> {
            AccountBean bean = WanDataBase.getInstance(getApplication()).accountDao().getAccountBean(userName);
            if (bean != null)
                emitter.onNext(bean);
        })
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(accountBean -> {
                    accLiveData.postValue(accountBean);
                });
    }
}
