package com.liuz.lotus.net.func;

import com.google.gson.Gson;

import java.io.IOException;

import io.reactivex.functions.Function;
import okhttp3.ResponseBody;

/**
 * @Description: ResponseBody转T
 * @author: jeasinlee
 * @date: 2017-01-05 14:39
 */
public class ApiFunc<T> implements Function<ResponseBody, T> {
    protected Class<T> clazz;

    public ApiFunc(Class<T> clazz) {
        this.clazz = clazz;
    }


    @Override
    public T apply(ResponseBody responseBody) throws Exception {
        Gson gson = new Gson();
        String json = null;
        try {
            json = responseBody.string();
            if (clazz.equals(String.class)) {
                return (T) json;
            } else {
                return gson.fromJson(json, clazz);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            responseBody.close();
        }
        return (T) json;
    }
}
