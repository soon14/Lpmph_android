package exception;

import java.io.IOException;

/**
 * Project : XHS
 * Created by 王可 on 2016/12/13.
 */

public class DeserializeException extends IOException{
    public DeserializeException() {
        super();
    }

    public DeserializeException(String message) {
        super(message);
    }

    public DeserializeException(String message, Throwable cause) {
        super(message, cause);
    }

    public DeserializeException(Throwable cause) {
        super(cause);
    }
}
