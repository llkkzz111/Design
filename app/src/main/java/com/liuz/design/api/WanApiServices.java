package com.liuz.design.api;

import com.liuz.common.mode.ApiResult;
import com.liuz.db.wan.AccountBean;
import com.liuz.design.bean.BannerBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * date: 2018/7/2 17:40
 * author liuzhao
 */
public interface WanApiServices {

    @FormUrlEncoded
    @POST("user/login")
    Observable<ApiResult<AccountBean>> userLogin(@Field("username") String username, @Field("password") String password);

    @GET("banner/json")
    Observable<ApiResult<BannerBean>> getBanner();
}
