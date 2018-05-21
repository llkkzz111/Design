package com.liuz.basekit.request;

import com.liuz.basekit.func.ApiDataFunc;
import com.liuz.basekit.mode.ApiResult;
import com.liuz.lotus.net.func.ApiRetryFunc;
import com.liuz.lotus.net.request.BaseHttpRequest;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @Description: 返回APIResult的基础请求类
 * @author: <a href="http://www.xiaoyaoyou1212.com">DAWI</a>
 * @date: 17/5/28 15:46.
 */
public abstract class ApiBaseRequest<R extends ApiBaseRequest> extends BaseHttpRequest<R> {
    public ApiBaseRequest(String suffixUrl) {
        super(suffixUrl);
    }

    protected <T> ObservableTransformer<ApiResult<T>, T> apiTransformer() {
        return new ObservableTransformer<ApiResult<T>, T>() {
            @Override
            public ObservableSource<T> apply(Observable<ApiResult<T>> apiResultObservable) {
                return apiResultObservable
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .map(new ApiDataFunc<T>())
                        .observeOn(AndroidSchedulers.mainThread())
                        .retryWhen(new ApiRetryFunc(retryCount, retryDelayMillis));
            }
        };
    }
}
