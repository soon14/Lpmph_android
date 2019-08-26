package exception;

/**
 * Project : XHS
 * Created by 王可 on 2016/12/13.
 */

public class XhsError {
    int errorCode;
    String message;

    public XhsError(int errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
