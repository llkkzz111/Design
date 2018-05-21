package com.liuz.lotus.net.strategy;

import com.liuz.lotus.net.core.ApiCache;
import com.liuz.lotus.net.mode.CacheResult;

import java.lang.reflect.Type;

import io.reactivex.Observable;
import io.reactivex.functions.Predicate;

/**
 * @Description: 缓存策略--缓存和网络
 * @author: <a href="http://www.xiaoyaoyou1212.com">DAWI</a>
 * @date: 16/12/31 14:33.
 */
public class CacheAndRemoteStrategy<T> extends CacheStrategy<T> {
    @Override
    public <T> Observable<CacheResult<T>> execute(ApiCache apiCache, String cacheKey, Observable<T> source, final Type type) {
        Observable<CacheResult<T>> cache = loadCache(apiCache, cacheKey, type);
        final Observable<CacheResult<T>> remote = loadRemote(apiCache, cacheKey, source);
        return Observable.concat(cache, remote).filter(new Predicate<CacheResult<T>>() {
            @Override
            public boolean test(CacheResult<T> tCacheResult) throws Exception {
                return tCacheResult != null && tCacheResult.getCacheData() != null;
            }
        });
    }
}
