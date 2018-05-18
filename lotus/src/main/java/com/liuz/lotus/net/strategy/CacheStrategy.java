package com.liuz.lotus.net.strategy;

import com.liuz.lotus.net.core.ApiCache;
import com.liuz.lotus.net.mode.CacheResult;
import com.liuz.lotus.utils.JLog;
import com.liuz.lotus.utils.JsonUtils;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;


/**
 * @Description: 缓存策略
 * @author: jeasinlee
 * @date: 16/12/31 14:28.
 */
public abstract class CacheStrategy<T> implements ICacheStrategy<T> {
    <T> Observable<CacheResult<T>> loadCache(final ApiCache apiCache, final String key, final Class<T>
            clazz) {
        return apiCache.<T>get(key).filter(new Predicate<String>() {

            @Override
            public boolean test(String s) throws Exception {
                return s != null;
            }

        }).map(new Function<String, CacheResult<T>>() {
            @Override
            public CacheResult<T> apply(String s) throws Exception {
                T t = JsonUtils.gson().fromJson(s, clazz);
                JLog.i("loadCache result=" + t);
                return new CacheResult<T>(true, t);
            }
        });
    }

    <T> Observable<CacheResult<T>> loadRemote(final ApiCache apiCache, final String key, Observable<T> source) {
        return source.map(new Function<T, CacheResult<T>>() {
            @Override
            public CacheResult<T> apply(T t) throws Exception {
                JLog.i("loadRemote result=" + t);
                apiCache.put(key, t).subscribeOn(Schedulers.io()).subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {

                    }
                });
                return new CacheResult<T>(false, t);
            }


        });
    }
}
