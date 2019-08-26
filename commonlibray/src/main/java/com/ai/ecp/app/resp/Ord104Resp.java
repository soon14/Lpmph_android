package com.ai.ecp.app.resp;

import com.ailk.butterfly.app.model.AppBody;

public class Ord104Resp extends AppBody {

    /** 
     * cartPromBeanRespDTO:促销返回信息. 
     * @since JDK 1.6 
     */ 
    private boolean success;

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

