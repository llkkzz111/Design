package com.liuz.lotus.net.func;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.liuz.lotus.net.mode.ApiResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import io.reactivex.functions.Function;
import okhttp3.ResponseBody;

/**
 * @Description: ResponseBody转ApiResult<T>
 * @author: jeasinlee
 * @date: 2016-12-30 17:55
 */
public class ApiResultFunc<T> implements Function<ResponseBody, ApiResult<T>> {
    protected Class<T> clazz;

    public ApiResultFunc(Class<T> clazz) {
        this.clazz = clazz;
    }

    private ApiResult parseApiResult(String json, ApiResult apiResult) throws JSONException {
        if (TextUtils.isEmpty(json)) return null;
        JSONObject jsonObject = new JSONObject(json);
        if (jsonObject.has("code")) {
            apiResult.setCode(jsonObject.getInt("code"));
        }
        if (jsonObject.has("data")) {
            apiResult.setData(jsonObject.getString("data"));
        }
        if (jsonObject.has("msg")) {
            apiResult.setMessage(jsonObject.getString("msg"));
        }
        return apiResult;
    }

    @Override
    public ApiResult<T> apply(ResponseBody responseBody) throws Exception {
        Gson gson = new Gson();
        ApiResult<T> apiResult = new ApiResult<T>();
        apiResult.setCode(-1);
        try {
            String json = responseBody.string();
            if (clazz.equals(String.class)) {
                apiResult.setData((T) json);
                apiResult.setCode(0);
            } else {
                ApiResult result = parseApiResult(json, apiResult);
                if (result != null) {
                    apiResult = result;
                    if (apiResult.getData() != null) {
                        T data = gson.fromJson(apiResult.getData().toString(), clazz);
                        apiResult.setData(data);
                    } else {
                        apiResult.setMessage("ApiResult's data is null");
                    }
                } else {
                    apiResult.setMessage("json is null");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            apiResult.setMessage(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            apiResult.setMessage(e.getMessage());
        } finally {
            responseBody.close();
        }
        return apiResult;
    }
}
