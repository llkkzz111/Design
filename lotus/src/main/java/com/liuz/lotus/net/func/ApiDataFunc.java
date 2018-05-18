package com.liuz.lotus.net.func;

import com.liuz.lotus.net.exception.ApiException;
import com.liuz.lotus.net.mode.ApiResult;

import io.reactivex.functions.Function;

/**
 * @Description: ApiResult<T>转T
 * @author: jeasinlee
 * @date: 2016-12-30 17:55
 */
public class ApiDataFunc<T> implements Function<ApiResult<T>, T> {
    public ApiDataFunc() {
    }

    @Override
    public T apply(ApiResult<T> response) throws Exception {
        if (ApiException.isSuccess(response)) {
            return response.getData();
        } else {
            return (T) new ApiException(new Throwable(response.getMessage()), response.getCode());
        }
    }
}
