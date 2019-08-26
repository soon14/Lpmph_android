package com.ailk.butterfly.app.model;

/**
 * Project : PMPH
 * Created by 王可 on 16/3/4.
 */
public class AppHeader implements IHeader{
    private String bizCode; //请求编码（业务编码）
    private String identityId; //报文流水
    private String respCode; //响应编码（0000为成功）
    private String respMsg; //响应信息
    private String mode;
    private String sign;

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    @Override
    public String getBizCode() {
        return this.bizCode;
    }

    @Override
    public String getIdentityId() {
        return this.identityId;
    }

    @Override
    public void setRespCode(String respCode) {
        this.respCode = respCode;

    }

    @Override
    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }

    @Override
    public String toString() {
        return "APPHeader [bizCode=" + bizCode + ", identityId=" + identityId +
                ", respCode=" + respCode + ", respMsg=" + respMsg;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
    }

    public void setIdentityId(String identityId) {
        this.identityId = identityId;
    }

    public String getRespCode() {
        return respCode;
    }

    public String getRespMsg() {
        return respMsg;
    }
}
