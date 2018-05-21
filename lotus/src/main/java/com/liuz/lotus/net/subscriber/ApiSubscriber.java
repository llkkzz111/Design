package com.liuz.lotus.net.subscriber;

import android.accounts.NetworkErrorException;
import android.content.Context;

import com.liuz.lotus.net.exception.ApiException;
import com.liuz.lotus.net.mode.ApiCode;
import com.liuz.lotus.utils.assist.Network;

import java.lang.ref.WeakReference;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @Description: API统一订阅者，采用弱引用管理上下文，防止内存泄漏
 * @author: jeasinlee
 * @date: 2017-01-03 14:07
 */
public abstract class ApiSubscriber<T> implements Observer<T> {
    public WeakReference<Context> contextWeakReference;

    public ApiSubscriber(Context context) {
        contextWeakReference = new WeakReference<Context>(context);
    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof ApiException) {
            onError((ApiException) e);
        } else {
            onError(new ApiException(e, ApiCode.Request.UNKNOWN));
        }
    }

    @Override
    public void onSubscribe(Disposable s) {
        if (!Network.isConnected(contextWeakReference.get())) {
            onError(new ApiException(new NetworkErrorException(), ApiCode.Request.NETWORK_ERROR));
        }
    }


    public abstract void onError(ApiException e);
}
