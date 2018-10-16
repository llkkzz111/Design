package com.liuz.design.ui.movies;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.support.annotation.Nullable;

import com.liuz.design.api.MTimeApiService;
import com.liuz.design.bean.HotMoviesBean;
import com.liuz.lotus.net.ViseHttp;
import com.liuz.lotus.net.callback.ACallback;
import com.liuz.lotus.net.core.ApiTransformer;
import com.liuz.lotus.net.subscriber.ApiCallbackSubscriber;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * date: 2018/6/20 12:20
 * author liuzhao
 */

public class MoviesPresenter implements MoviesContract.Presenter {

    @Nullable
    private MoviesContract.View moviesView;

    @Override
    public void onCreate(@NotNull LifecycleOwner owner) {

    }

    @Override
    public void onDestroy(@NotNull LifecycleOwner owner) {

    }

    @Override
    public void onLifecycleChanged(@NotNull LifecycleOwner owner, @NotNull Lifecycle.Event event) {

    }

    @Override
    public void getAlDeta(int locationid) {
        Map<String, Object> params = new HashMap<>();
        if (locationid > 0) {
            params.put("locationId", locationid);
        }

        ViseHttp.RETROFIT()
                .create(MTimeApiService.class)
                .getHotPlayMovies(params)
                .compose(ApiTransformer.<HotMoviesBean>norTransformer())
                .subscribe(new ApiCallbackSubscriber<>(new ACallback<HotMoviesBean>() {
                    @Override
                    public void onSuccess(HotMoviesBean data) {
                        moviesView.showView(data);
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {

                    }
                }));
    }

    @Override
    public void takeView(MoviesContract.View view) {
        this.moviesView = view;
    }

    @Override
    public void dropView() {
        moviesView = null;
    }
}
