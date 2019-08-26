package exception;

import java.net.ConnectException;
import java.net.UnknownHostException;

import retrofit2.adapter.rxjava.HttpException;

/**
 * Project : XHS
 * Created by 王可 on 2016/12/13.
 */

public class ExceptionEngine {

    //对应HTTP的状态码
//    private static final int UNAUTHORIZED = 401;
//    private static final int FORBIDDEN = 403;
//    private static final int NOT_FOUND = 404;
//    private static final int REQUEST_TIMEOUT = 408;
//    private static final int INTERNAL_SERVER_ERROR = 500;
//    private static final int BAD_GATEWAY = 502;
//    private static final int SERVICE_UNAVAILABLE = 503;
//    private static final int GATEWAY_TIMEOUT = 504;

    public static XhsException handleException(Throwable e) {
        if (e instanceof UnknownHostException) {
            return new XhsException(e, XhsException.ERROR_HOST);
        } else if (e instanceof ConnectException){
            return new XhsException(e,XhsException.ERROR_HTTP);
        }else if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            return new XhsException(httpException, XhsException.ERROR_HTTP);
        } else if (e instanceof DeserializeException) {
            return new XhsException(e, XhsException.ERROR_DESERIALIZE);
        } else {
            return new XhsException(e, XhsException.ERROR_OTHER);
        }
    }
}