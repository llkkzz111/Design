package com.liuz.lotus.net.strategy;

import com.liuz.lotus.net.core.ApiCache;
import com.liuz.lotus.net.mode.CacheResult;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

/**
 * @Description: 缓存策略--优先缓存
 * @author: jeasinlee
 * @date: 16/12/31 14:31.
 */
public class FirstCacheStrategy<T> extends CacheStrategy<T> {
    @Override
    public <T> Observable<CacheResult<T>> execute(ApiCache apiCache, String cacheKey, Observable<T> source, Class<T> clazz) {
        Observable<CacheResult<T>> cache = loadCache(apiCache, cacheKey, clazz);
        cache.onErrorReturn(new Function<Throwable, CacheResult<T>>() {
            @Override
            public CacheResult<T> apply(Throwable throwable) throws Exception {
                return null;
            }
        });
        Observable<CacheResult<T>> remote = loadRemote(apiCache, cacheKey, source);
        return Observable.concat(cache, remote).filter(new Predicate<CacheResult<T>>() {
            @Override
            public boolean test(CacheResult<T> tResultData) throws Exception {
                return tResultData != null && tResultData.getCacheData() != null;
            }
        });
    }
}
