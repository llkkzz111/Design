package com.liuz.lotus.net.callback;

import com.liuz.lotus.net.mode.ApiResult;

public abstract class ApiSuccessCallback<T> extends ApiCallback<ApiResult<T>> {
    @Override
    public void onSubscribe() {

    }


    public abstract void onSuccess(T apiResult);

    @Override
    public void onCompleted() {

    }

    @Override
    public void onNext(ApiResult<T> tApiResult) {

    }


}
