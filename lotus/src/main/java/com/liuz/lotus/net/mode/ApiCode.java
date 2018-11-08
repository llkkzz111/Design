package com.liuz.lotus.net.mode;

/**
 * @Description: 网络通用状态码定义
 * @author: <a href="http://xiaoyaoyou1212.360doc.com">DAWI</a>
 * @date: 2016-12-30 18:11
 */
public class ApiCode {

    /**
     * 对应HTTP的状态码
     */
    public static class Http {
        public static final int UNAUTHORIZED = 401;
        public static final int FORBIDDEN = 403;
        public static final int NOT_FOUND = 404;
        public static final int REQUEST_TIMEOUT = 408;
        public static final int INTERNAL_SERVER_ERROR = 500;
        public static final int BAD_GATEWAY = 502;
        public static final int SERVICE_UNAVAILABLE = 503;
        public static final int GATEWAY_TIMEOUT = 504;
    }

    /**
     * Request请求码
     */
    public static class Request {
        //未知错误
        public static final int UNKNOWN = 1000;
        //解析错误
        public static final int PARSE_ERROR = 1001;
        //网络错误
        public static final int NETWORK_ERROR = 1002;
        //协议出错
        public static final int HTTP_ERROR = 1003;
        //证书出错
        public static final int SSL_ERROR = 1005;
        //连接超时
        public static final int TIMEOUT_ERROR = 1006;
        //调用错误
        public static final int INVOKE_ERROR = 1007;
        //类转换错误
        public static final int CONVERT_ERROR = 1008;

    }

    public static class Response {
        //HTTP请求成功状态码
        public static final int HTTP_SUCCESS = 0;
        //AccessToken错误或已过期
        public static final int ACCESS_TOKEN_EXPIRED = -1001;
        //RefreshToken错误或已过期
        public static final int REFRESH_TOKEN_EXPIRED = 10002;
        //帐号在其它手机已登录
        public static final int OTHER_PHONE_LOGIN = 10003;
        //时间戳过期
        public static final int TIMESTAMP_ERROR = 10004;
        //缺少授权信息,没有AccessToken
        public static final int NO_ACCESS_TOKEN = 10005;
        //签名错误
        public static final int SIGN_ERROR = 10006;
    }
}
