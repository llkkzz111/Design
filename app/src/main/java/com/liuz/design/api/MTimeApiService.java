package com.liuz.design.api;

import com.liuz.design.bean.AreasBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

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
    @GET("https://api-m.mtime.cn/PageSubArea/HotPlayMovies.api")
    Observable<AreasBean> getAreas(@Query(value = "locationId") String locationId);
}
