package com.liuz.mvvm.vm;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.NonNull;

import com.liuz.db.WanDataBase;
import com.liuz.db.wan.AccountBean;
import com.liuz.design.bean.ArticleBeans;
import com.liuz.design.bean.BannerBean;
import com.liuz.lotus.net.mode.ApiResult;
import com.liuz.mvvm.m.WanRepository;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.schedulers.Schedulers;

/**
 * date: 2018/11/9 11:47
 * author liuzhao
 */
public class WanViewModel extends AndroidViewModel {

    private WanRepository model;


    private LiveData<ApiResult<List<BannerBean>>> banners = new MediatorLiveData<>();

    public WanViewModel(@NonNull Application application) {
        super(application);
        model = new WanRepository(application);

    }

    public LiveData<ApiResult<List<BannerBean>>> loadBanner() {
        return this.model.loadBanner();
    }


    public LiveData<ApiResult<ArticleBeans>> loadData(int pageNo) {
        return this.model.loadData(pageNo);
    }

    public void getAccount(String userName) {
        Observable.create((ObservableOnSubscribe<AccountBean>) emitter -> {
            AccountBean bean = WanDataBase.getInstance(getApplication()).accountDao().getAccountBean(userName);
//            if (bean != null)
//                liveData.postValue(new ApiResult().setData(bean));
        }).subscribeOn(Schedulers.io()).subscribe();
    }


}
