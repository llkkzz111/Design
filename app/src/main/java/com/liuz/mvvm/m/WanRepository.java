package com.liuz.mvvm.m;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;

import com.liuz.common.subscriber.ApiSubscriber;
import com.liuz.db.WanDataBase;
import com.liuz.db.wan.AccountBean;
import com.liuz.design.bean.ArticleBeans;
import com.liuz.design.bean.BannerBean;
import com.liuz.lotus.net.ViseHttp;
import com.liuz.lotus.net.exception.ApiException;
import com.liuz.lotus.net.mode.ApiResult;
import com.liuz.net.api.WanApiServices;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * date: 2018/11/9 17:54
 * author liuzhao
 * https://blog.csdn.net/u012295927/article/details/72621756
 */
public class WanRepository {

    private Application application;
    private MutableLiveData<ApiResult<List<BannerBean>>> banners;
    private MutableLiveData<ApiResult<ArticleBeans>> articles;
    private MutableLiveData<AccountBean> accountBean;

    public WanRepository(Application application) {
        this.application = application;

    }

    public void setBanners(MutableLiveData<ApiResult<List<BannerBean>>> banners) {
        this.banners = banners;
    }

    public void setArticles(MutableLiveData<ApiResult<ArticleBeans>> articles) {
        this.articles = articles;
    }

    public void setAccountBean(MutableLiveData<AccountBean> accountBean) {
        this.accountBean = accountBean;
    }

    public void loadData(int pageNo) {

        ViseHttp.RETROFIT().create(WanApiServices.class).getArticles(pageNo).enqueue(new Callback<ApiResult<ArticleBeans>>() {
            @Override
            public void onResponse(Call<ApiResult<ArticleBeans>> call, Response<ApiResult<ArticleBeans>> response) {
                articles.postValue(response.body());
            }

            @Override
            public void onFailure(Call<ApiResult<ArticleBeans>> call, Throwable t) {

            }
        });

    }

    public void loadBanner() {

        ViseHttp.RETROFIT().create(WanApiServices.class).getBanner().subscribeOn(Schedulers.io()).subscribe(new ApiSubscriber<ApiResult<List<BannerBean>>>() {

            @Override
            public void onNext(ApiResult<List<BannerBean>> listApiResult) {
                banners.postValue(listApiResult);
            }


            @Override
            protected void onError(ApiException e) {

            }

            @Override
            public void onComplete() {

            }
        });

    }


    public void getAccount(String userName) {
        Observable.create((ObservableOnSubscribe<AccountBean>) emitter -> {
            AccountBean bean = WanDataBase.getInstance(application).accountDao().getAccountBean(userName);
            if (bean != null)
                accountBean.postValue(bean);
        }).subscribeOn(Schedulers.io()).subscribe();
    }
}
