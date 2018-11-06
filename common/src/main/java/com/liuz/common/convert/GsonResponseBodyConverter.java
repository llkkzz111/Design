package com.liuz.common.convert;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.liuz.lotus.net.exception.ApiException;
import com.liuz.lotus.net.mode.ApiCode;
import com.liuz.lotus.net.mode.ApiResult;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * @Description: ResponseBody to T
 * @author: <a href="http://www.xiaoyaoyou1212.com">DAWI</a>
 * @date: 2017-01-04 18:05
 */
final class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final TypeAdapter<T> adapter;

    GsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public T convert(@NonNull ResponseBody value) throws IOException {
        if (adapter != null && gson != null) {
            JsonReader jsonReader = gson.newJsonReader(value.charStream());
            try {
                T data = adapter.read(jsonReader);
                if (data == null)
                    throw new ApiException("server back data is null", ApiCode.Request.UNKNOWN);
                if (data instanceof ApiResult) {
                    ApiResult apiResult = (ApiResult) data;
                    if (!ApiException.isSuccess(apiResult)) {
                        throw new ApiException(apiResult.getMsg() == null ? "unknow error" : apiResult.getMsg(), apiResult.getCode());
                    }
                }
                return data;
            } finally {
                value.close();
            }
        } else {
            return null;
        }
    }
}
