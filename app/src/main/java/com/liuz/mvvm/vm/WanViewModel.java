package com.liuz.mvvm.vm;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.liuz.db.wan.AccountBean;
import com.liuz.design.bean.ArticleBeans;
import com.liuz.design.bean.BannerBean;
import com.liuz.lotus.net.mode.ApiResult;
import com.liuz.mvvm.m.WanRepository;

import java.util.List;

/**
 * date: 2018/11/9 11:47
 * author liuzhao
 */
public class WanViewModel extends AndroidViewModel {

    private WanRepository model;

    private MutableLiveData<ApiResult<List<BannerBean>>> banners = new MutableLiveData<>();
    private MutableLiveData<ApiResult<ArticleBeans>> articles = new MutableLiveData<>();
    private MutableLiveData<AccountBean> accountBean = new MutableLiveData<>();

    public WanViewModel(@NonNull Application application) {
        super(application);
        model = new WanRepository(application);
        model.setAccountBean(accountBean);
        model.setBanners(banners);
        model.setArticles(articles);

    }

    public MutableLiveData<ApiResult<List<BannerBean>>> getBanners() {
        return banners;
    }

    public MutableLiveData<ApiResult<ArticleBeans>> getArticles() {
        return articles;
    }

    public void loadBanner() {
        this.model.loadBanner();
    }


    public void loadData(int pageNo) {
        this.model.loadData(pageNo);
    }

    public void getAccount(String userName) {
        this.model.getAccount(userName);
    }


}
