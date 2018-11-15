package com.liuz.mvvm.m;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.liuz.common.subscriber.ApiSubscriber;
import com.liuz.design.bean.ArticleBeans;
import com.liuz.design.bean.BannerBean;
import com.liuz.lotus.net.ViseHttp;
import com.liuz.lotus.net.exception.ApiException;
import com.liuz.lotus.net.mode.ApiResult;
import com.liuz.net.api.WanApiServices;

import java.util.List;

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

    public WanRepository(Application application) {
        this.application = application;
    }


    public LiveData<ApiResult<ArticleBeans>> loadData(int pageNo) {
        final MutableLiveData<ApiResult<ArticleBeans>> liveData = new MutableLiveData<>();
        ViseHttp.RETROFIT().create(WanApiServices.class).getArticles(pageNo).enqueue(new Callback<ApiResult<ArticleBeans>>() {
            @Override
            public void onResponse(Call<ApiResult<ArticleBeans>> call, Response<ApiResult<ArticleBeans>> response) {
                liveData.postValue(response.body());
            }

            @Override
            public void onFailure(Call<ApiResult<ArticleBeans>> call, Throwable t) {

            }
        });
        return liveData;

    }

    public LiveData<ApiResult<List<BannerBean>>> loadBanner() {
        final MutableLiveData<ApiResult<List<BannerBean>>> liveData = new MutableLiveData<>();
        ViseHttp.RETROFIT().create(WanApiServices.class).getBanner().subscribeOn(Schedulers.io()).subscribe(new ApiSubscriber<ApiResult<List<BannerBean>>>() {

            @Override
            public void onNext(ApiResult<List<BannerBean>> listApiResult) {
                liveData.postValue(listApiResult);
            }


            @Override
            protected void onError(ApiException e) {

            }

            @Override
            public void onComplete() {

            }
        });
        return liveData;

    }


}
