package com.liuz.lotus.net.mode;

/**
 * @Description: 封装的通用服务器返回对象，可自行定义
 * @author: <a href="http://www.xiaoyaoyou1212.com">DAWI</a>
 * @date: 2016-12-30 16:43
 */
public class ApiResult<T> {
    private int errorCode;
    private String errorMsg;
    private T data;

    public int getCode() {
        return errorCode;
    }

    public ApiResult setCode(int errorCode) {
        this.errorCode = errorCode;
        return this;
    }

    public String getMsg() {
        return errorMsg;
    }

    public ApiResult setMsg(String msg) {
        this.errorMsg = msg;
        return this;
    }

    public T getData() {
        return data;
    }

    public ApiResult setData(T data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        return "ApiResult{" +
                "errorCode=" + errorCode +
                ", errorMsg='" + errorMsg + '\'' +
                ", data=" + data +
                '}';
    }
}
