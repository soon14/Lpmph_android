package com.ai.ecp.app.resp;

import com.ailk.butterfly.app.model.AppBody;

public class Ord007Resp extends AppBody {

    /**
     * 是否成功
     */
    private boolean success;
    /**
     * 返回信息
     */
    private String message;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}

