package exception;

import android.support.annotation.NonNull;

/**
 * Project : XHS
 * Created by 王可 on 2016/12/13.
 */

public class XhsException extends Exception{
    final public static int CODE_HOST = -1;
    final public static int CODE_HTTP = -2;
    final public static int CODE_DESERIALIZE = -3;
    final public static int CODE_RESPONSE = -4;
    final public static int CODE_OTHER = -999;

    final public static String ERROR_MSG_HOST = "网络连接错误";
    final public static String ERROR_MSG_HTTP = "网络连接错误";
    final public static String ERROR_MSG_DESERIALIZE = "接口数据异常，请升级到最新版本";
    final public static String ERROR_MSG_RESPONSE = "接口数据返回异常";
    final public static String ERROR_MSG_ACCOUNT = "您当前账号已被锁定";
    final public static String ERROR_MSG_PASSWORD = "您当前密码已失效，请修改用户密码";
    final public static String ERROR_MSG_OTHER = "未知错误";


    final public static XhsError ERROR_HOST = new XhsError(CODE_HOST,ERROR_MSG_HOST);
    final public static XhsError ERROR_HTTP = new XhsError(CODE_HTTP,ERROR_MSG_HTTP);
    final public static XhsError ERROR_DESERIALIZE = new XhsError(CODE_DESERIALIZE,ERROR_MSG_DESERIALIZE);
    final public static XhsError ERROR_RESPONSE = new XhsError(CODE_RESPONSE,ERROR_MSG_RESPONSE);
    final public static XhsError ERROR_ACCOUNT = new XhsError(CODE_RESPONSE,ERROR_MSG_ACCOUNT);
    final public static XhsError ERROR_PASSWORD = new XhsError(CODE_RESPONSE,ERROR_MSG_PASSWORD);
    final public static XhsError ERROR_OTHER = new XhsError(CODE_OTHER,ERROR_MSG_OTHER);

    private int code;
    private String displayMessage;
    public XhsException(Throwable throwable, @NonNull XhsError error) {
        super(throwable);
        this.code = error.getErrorCode();
        this.displayMessage = error.getMessage();

    }

    public void setDisplayMessage(String displayMessage) {
        this.displayMessage = displayMessage;
    }

    public String getDisplayMessage() {
        return displayMessage;
    }

    public int getCode() {
        return code;
    }
}
