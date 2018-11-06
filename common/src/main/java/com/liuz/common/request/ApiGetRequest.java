package com.liuz.common.request;

import com.liuz.common.func.ApiResultFunc;
import com.liuz.lotus.net.callback.ACallback;
import com.liuz.lotus.net.core.ApiManager;
import com.liuz.lotus.net.subscriber.ApiCallbackSubscriber;

import java.lang.reflect.Type;

import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;

/**
 * @Description: 返回APIResult的GET请求类
 * @author: <a href="http://www.xiaoyaoyou1212.com">DAWI</a>
 * @date: 17/5/13 14:31.
 */
public class ApiGetRequest extends ApiBaseRequest<ApiGetRequest> {
    public ApiGetRequest(String suffixUrl) {
        super(suffixUrl);
    }

    @Override
    protected <T> Observable<T> execute(Type type) {
        return apiService.get(suffixUrl, params).map(new ApiResultFunc<T>(type)).compose(this.<T>apiTransformer());
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
