package com.liuz.lotus.net.request;

import com.liuz.lotus.net.callback.ACallback;
import com.liuz.lotus.net.core.ApiManager;
import com.liuz.lotus.net.subscriber.ApiCallbackSubscriber;

import java.lang.reflect.Type;

import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;

/**
 * @Description: Options请求
 * @author: <a href="http://www.xiaoyaoyou1212.com">DAWI</a>
 * @date: 2017-04-28 16:07
 */
public class OptionsRequest extends BaseHttpRequest<OptionsRequest> {
    public OptionsRequest(String suffixUrl) {
        super(suffixUrl);
    }

    @Override
    protected <T> Observable<T> execute(Type type) {
        return apiService.options(suffixUrl, params).compose(this.<T>norTransformer(type));
    }

    @Override
    protected <T> void execute(ACallback<T> callback) {
        DisposableObserver disposableObserver = new ApiCallbackSubscriber(callback);
        if (super.tag != null) {
            ApiManager.get().add(super.tag, disposableObserver);
        }
        this.execute(getType(callback)).subscribe(disposableObserver);
    }
}
