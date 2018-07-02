package com.liuz.design.api;

import com.liuz.design.bean.HotMoviesBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * date: 2018/7/2 17:40
 * author liuzhao
 */
public interface WanApiServices {

    @FormUrlEncoded
    @POST("user/login")
    Observable<HotMoviesBean> userLogin(@Field("username") String username, @Field("password") String password);
}
