package com.liuz.mvvm.vm;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.liuz.design.bean.ArticleBeans;
import com.liuz.mvvm.SingleLiveEvent;
import com.liuz.mvvm.m.WanModel;

/**
 * date: 2018/11/9 11:47
 * author liuzhao
 */
public class WanViewModel extends AndroidViewModel {
    private final SingleLiveEvent<ArticleBeans> liveData = new SingleLiveEvent<>();
    private WanModel model;

    public WanViewModel(@NonNull Application application) {
        super(application);
        model = new WanModel();
    }

    public SingleLiveEvent<ArticleBeans> getLiveData() {
        return liveData;
    }

    public void loadData(int pageNo) {

        model.loadData(pageNo);
    }
}
