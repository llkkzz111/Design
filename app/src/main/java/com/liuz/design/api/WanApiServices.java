package com.liuz.design.api;

import com.liuz.common.mode.ApiResult;
import com.liuz.db.wan.AccountBean;
import com.liuz.design.bean.ArticleBeans;
import com.liuz.design.bean.BannerBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * date: 2018/7/2 17:40
 * author liuzhao
 */
public interface WanApiServices {

    @FormUrlEncoded
    @POST("user/login")
    Observable<ApiResult<AccountBean>> userLogin(@Field("username") String username, @Field("password") String password);

    @GET("banner/json")
    Observable<ApiResult<List<BannerBean>>> getBanner();

    @GET("article/list/{page}/json")
    Observable<ApiResult<ArticleBeans>> getArticleList(@Path("page") int page);

    @POST("lg/collect/{page}/json")
    Observable<ApiResult<String>> addCollect(@Path("page") int page);

    @POST("lg/uncollect_originId/{page}/json")
    Observable<ApiResult<String>> delCollect(@Path("page") int page);
}
