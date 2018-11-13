package com.liuz.net.interceptor;

import com.liuz.design.utils.ActivityUtils;

import okhttp3.Request;
import okhttp3.Response;

/**
 * date: 2018/11/13 16:31
 * author liuzhao
 */
public class ResponseInterceptor extends HttpResponseInterceptor {
    @Override
    Response processAccessTokenExpired(Chain chain, Request request) {
        ActivityUtils.gotoLogin();
        return null;
    }

    @Override
    Response processRefreshTokenExpired(Chain chain, Request request) {
        return null;
    }

    @Override
    Response processOtherPhoneLogin(Chain chain, Request request) {
        return null;
    }

    @Override
    Response processSignError(Chain chain, Request request) {
        return null;
    }

    @Override
    Response processTimestampError(Chain chain, Request request) {
        return null;
    }

    @Override
    Response processNoAccessToken(Chain chain, Request request) {
        return null;
    }

    @Override
    Response processOtherError(int errorCode, Chain chain, Request request) {
        return null;
    }
}
