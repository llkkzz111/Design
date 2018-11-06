package com.liuz.common.subscriber;

import com.liuz.lotus.net.mode.ApiResult;

/**
 * date: 2018/7/3 15:15
 * author liuzhao
 */
public abstract class ApiResultSubscriber<T> extends ApiSubscriber<ApiResult<T>> {


    @Override
    public void onNext(ApiResult<T> result) {
        onSuccess(result.getData());
    }

    public abstract void onSuccess(T data);

    @Override
    public void onComplete() {
    }

}
