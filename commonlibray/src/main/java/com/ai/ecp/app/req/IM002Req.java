package com.ai.ecp.app.req;

import com.ailk.butterfly.app.model.AppBody;

/**
 * 类注释:
 * 项目名:pmph_android
 * 包名:com.ai.ecp.app.req
 * 作者: Chrizz
 * 时间: 2017/2/27
 */

public class IM002Req extends AppBody {

    private Long shopId;
    private String userCode;
    private String csaCode;
    private String sessionId;
    //满意度类型：{非常满意：4,满意：3，一般：2，不满意：1，非常不满意：0}
    private String notSatisfyType;
    //1：业务不满意，2：服务不满意
    private String satisfyType;
    private String notSatisfyReason;

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getCsaCode() {
        return csaCode;
    }

    public void setCsaCode(String csaCode) {
        this.csaCode = csaCode;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getSatisfyType() {
        return satisfyType;
    }

    public void setSatisfyType(String satisfyType) {
        this.satisfyType = satisfyType;
    }

    public String getNotSatisfyType() {
        return notSatisfyType;
    }

    public void setNotSatisfyType(String notSatisfyType) {
        this.notSatisfyType = notSatisfyType;
    }

    public String getNotSatisfyReason() {
        return notSatisfyReason;
    }

    public void setNotSatisfyReason(String notSatisfyReason) {
        this.notSatisfyReason = notSatisfyReason;
    }
}
