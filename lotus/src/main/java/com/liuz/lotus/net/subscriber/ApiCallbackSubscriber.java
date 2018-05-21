package com.liuz.lotus.net.subscriber;

import android.content.Context;

import com.liuz.lotus.net.callback.ApiCallback;
import com.liuz.lotus.net.exception.ApiException;

import io.reactivex.disposables.Disposable;

/**
 * @Description: 包含回调的订阅者，如果订阅这个，上层在不使用订阅者的情况下可获得回调
 * @author: jeasinlee
 * @date: 2017-01-05 09:35
 */
public class ApiCallbackSubscriber<T> extends ApiSubscriber<T> {

    protected ApiCallback<T> callBack;

    public ApiCallbackSubscriber(Context context, ApiCallback<T> callBack) {
        super(context);
        if (callBack == null) {
            throw new NullPointerException("this callback is null!");
        }
        this.callBack = callBack;
    }

    @Override
    public void onSubscribe(Disposable s) {
        super.onSubscribe(s);
        callBack.onSubscribe();
    }

    @Override
    public void onError(ApiException e) {
        callBack.onError(e);
    }

    @Override
    public void onComplete() {
        callBack.onCompleted();
    }

    @Override
    public void onNext(T t) {
        callBack.onNext(t);
    }
}
