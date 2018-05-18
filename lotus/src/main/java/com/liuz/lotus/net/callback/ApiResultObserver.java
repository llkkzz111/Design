package com.liuz.lotus.net.callback;

import android.content.Context;

import com.liuz.lotus.net.exception.ApiException;
import com.liuz.lotus.net.mode.ApiResult;
import com.liuz.lotus.net.subscriber.ApiObserver;


/**
 * Created by liu on 2017/5/5.
 */

public abstract class ApiResultObserver<T> extends ApiObserver<ApiResult<T>> {

    private Context context;

    public ApiResultObserver(Context context) {
        super(context);
        this.context = context;
    }


    @Override
    public void onNext(ApiResult<T> apiResult) {
        onSuccess(apiResult.getData());
    }

    public abstract void onSuccess(T apiResult);

    @Override
    public void onError(ApiException e) {

    }
}

