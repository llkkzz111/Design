package com.liuz.common.temp;


import com.liuz.lotus.net.mode.ApiCode;

import java.util.List;

/**
 * @Description:
 * @author: <a href="http://xiaoyaoyou1212.360doc.com">DAWI</a>
 * @date: 2018/1/20 16:31
 */
public class DefaultResponseState implements IResponseState {
    @Override
    public int httpSuccess() {
        return ApiCode.Response.HTTP_SUCCESS;
    }

    @Override
    public int accessTokenExpired() {
        return ApiCode.Response.ACCESS_TOKEN_EXPIRED;
    }

    @Override
    public int refreshTokenExpired() {
        return ApiCode.Response.REFRESH_TOKEN_EXPIRED;
    }

    @Override
    public int otherPhoneLogin() {
        return ApiCode.Response.OTHER_PHONE_LOGIN;
    }

    @Override
    public int timestampError() {
        return ApiCode.Response.TIMESTAMP_ERROR;
    }

    @Override
    public int noAccessToken() {
        return ApiCode.Response.NO_ACCESS_TOKEN;
    }

    @Override
    public int signError() {
        return ApiCode.Response.SIGN_ERROR;
    }

    @Override
    public List<Integer> otherError() {
        return null;
    }
}
