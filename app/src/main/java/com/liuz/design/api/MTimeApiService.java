package com.liuz.design.api;

import com.liuz.design.bean.AreasBean;
import com.liuz.design.bean.HotMoviesBean;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * date: 2018/5/28 16:50
 * author liuzhao
 */
public interface MTimeApiService {
    @Headers({
            "Host:api-m.mtime.cn"
    })
    @GET("http://api-m.mtime.cn/Showtime/HotCitiesByCinema.api")
    Observable<AreasBean> getAreas();

    @Headers({
            "Host:api-m.mtime.cn"
    })
    @GET("http://api-m.mtime.cn/PageSubArea/HotPlayMovies.api")
    Observable<HotMoviesBean> getHotPlayMovies(@QueryMap Map<String, Object> filters);
}
