package com.liuz.lotus.net.func;


import com.liuz.lotus.net.exception.ApiException;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * @Description: Throwable转Observable<T>
 * @author: jeasinlee
 * @date: 2017-01-03 16:00
 */
public class ApiErrFunc<T> implements Function<Throwable, Observable<T>> {
    @Override
    public Observable<T> apply(Throwable throwable) throws Exception {
        return Observable.error(ApiException.handleException(throwable));
    }
}
