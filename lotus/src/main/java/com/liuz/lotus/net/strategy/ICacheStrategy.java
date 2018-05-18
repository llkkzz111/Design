package com.liuz.lotus.net.strategy;

import com.liuz.lotus.net.core.ApiCache;
import com.liuz.lotus.net.mode.CacheResult;

import io.reactivex.Observable;

/**
 * @Description: 缓存策略接口
 * @author: jeasinlee
 * @date: 16/12/31 14:21.
 */
public interface ICacheStrategy<T> {
    <T> Observable<CacheResult<T>> execute(ApiCache apiCache, String cacheKey, Observable<T> source, Class<T> clazz);
}
