package com.liuz.common;

import com.liuz.common.mode.ApiResult;
import com.liuz.lotus.net.config.HttpGlobalConfig;
import com.liuz.lotus.net.func.ApiRetryFunc;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * date: 2018/7/3 15:47
 * author liuzhao
 */
public class ApiResultTransformer {

    public static <T> ObservableTransformer<ApiResult<T>, ApiResult<T>> norTransformer() {
        return new ObservableTransformer<ApiResult<T>, ApiResult<T>>() {
            @Override
            public ObservableSource<ApiResult<T>> apply(Observable<ApiResult<T>> apiResultObservable) {
                return apiResultObservable
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .retryWhen(new ApiRetryFunc(HttpGlobalConfig.getInstance().getRetryCount(),
                                HttpGlobalConfig.getInstance().getRetryDelayMillis()));
            }
        };
    }

    public static <T> ObservableTransformer<ApiResult<T>, ApiResult<T>> norTransformer(final int retryCount, final int retryDelayMillis) {
        return new ObservableTransformer<ApiResult<T>, ApiResult<T>>() {
            @Override
            public ObservableSource<ApiResult<T>> apply(Observable<ApiResult<T>> apiResultObservable) {
                return apiResultObservable
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .retryWhen(new ApiRetryFunc(retryCount, retryDelayMillis));
            }
        };
    }

}
