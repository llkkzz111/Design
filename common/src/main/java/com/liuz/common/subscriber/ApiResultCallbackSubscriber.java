package com.liuz.common.subscriber;

import com.liuz.common.mode.ApiResult;
import com.liuz.lotus.net.callback.ACallback;
import com.liuz.lotus.net.exception.ApiException;
import com.liuz.lotus.net.subscriber.ApiCallbackSubscriber;


/**
 * @Description: 包含回调的订阅者，如果订阅这个，上层在不使用订阅者的情况下可获得回调
 * @author: <a href="http://www.xiaoyaoyou1212.com">DAWI</a>
 * @date: 2017-01-05 09:35
 */
public class ApiResultCallbackSubscriber<T> extends ApiCallbackSubscriber<ApiResult<T>> {

    ACallback<ApiResult<T>> callBack;


    public ApiResultCallbackSubscriber(ACallback<ApiResult<T>> callBack) {
        super(callBack);
    }


    @Override
    public void onError(ApiException e) {
        if (e == null) {
            callBack.onFail(-1, "This ApiException is Null.");
            return;
        }
        callBack.onFail(e.getCode(), e.getMessage());
    }
}
