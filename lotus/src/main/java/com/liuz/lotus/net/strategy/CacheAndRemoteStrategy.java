package com.liuz.lotus.net.strategy;

import com.liuz.lotus.net.core.ApiCache;
import com.liuz.lotus.net.mode.CacheResult;

import io.reactivex.Observable;
import io.reactivex.functions.Predicate;

/**
 * @Description: 缓存策略--缓存和网络
 * @author: jeasinlee
 * @date: 16/12/31 14:33.
 */
public class CacheAndRemoteStrategy<T> extends CacheStrategy<T> {
    @Override
    public <T> Observable<CacheResult<T>> execute(ApiCache apiCache, String cacheKey, Observable<T> source, final Class<T> clazz) {
        Observable<CacheResult<T>> cache = loadCache(apiCache, cacheKey, clazz);
        final Observable<CacheResult<T>> remote = loadRemote(apiCache, cacheKey, source);
        return Observable.concat(cache, remote).filter(new Predicate<CacheResult<T>>() {
            @Override
            public boolean test(CacheResult<T> tCacheResult) throws Exception {
                return tCacheResult.getCacheData() != null;
            }
        });
    }
}
