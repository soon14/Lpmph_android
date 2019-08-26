package com.ai.ecp.app.resp;

import com.ailk.butterfly.app.model.AppBody;

import java.util.Map;

/**
 * 类注释:
 * 项目名:pmph_android
 * 包名:com.ai.ecp.app.resp
 * 作者: Chrizz
 * 时间: 2016/4/6 15:01
 */
public class Pay004Resp extends AppBody {

    private boolean isStatus;
    private String msg;
    private String actionUrl;
    private String method;
    private String charset;
    private Map<String,String> formData;
    private String cerPassword;
    private String joinOrdId;
    private String appActionUrl;

    public boolean isStatus() {
        return isStatus;
    }

    public void setIsStatus(boolean isStatus) {
        this.isStatus = isStatus;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getActionUrl() {
        return actionUrl;
    }

    public void setActionUrl(String actionUrl) {
        this.actionUrl = actionUrl;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public Map<String, String> getFormData() {
        return formData;
    }

    public void setFormData(Map<String, String> formData) {
        this.formData = formData;
    }

    public String getCerPassword() {
        return cerPassword;
    }

    public void setCerPassword(String cerPassword) {
        this.cerPassword = cerPassword;
    }

    public String getJoinOrdId() {
        return joinOrdId;
    }

    public void setJoinOrdId(String joinOrdId) {
        this.joinOrdId = joinOrdId;
    }

    public String getAppActionUrl() {
        return appActionUrl;
    }

    public void setAppActionUrl(String appActionUrl) {
        this.appActionUrl = appActionUrl;
    }
}
