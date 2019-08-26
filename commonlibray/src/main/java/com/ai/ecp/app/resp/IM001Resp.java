package com.ai.ecp.app.resp;

import com.ailk.butterfly.app.model.AppBody;

/**
 * 类注释:
 * 项目名:pmph_android
 * 包名:com.ai.ecp.app.resp
 * 作者: Chrizz
 * 时间: 2017/1/19
 */

public class IM001Resp extends AppBody {

    /**
     * 当前用户OF账号
     */
    private String userCode;
    /**
     * 客服OF账号，接入的会话
     */
    private String csaCode;
    /**
     * 客服名称
     */
    private String hotlinePerson;
    /**
     * 客服头像url
     */
    private String hotlinePhoto;
    /**
     * 发起聊天记录ID
     */
    private String sessionId;
    /**
     * 客服队列满员，排队超出总量的人数（正常时数量为0，客服都不在线-99，排队时以返回值为准）
     */
    private int waitCount;
    /**
     * 买家头像url
     */
    private String custPic;

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

    public String getHotlinePerson() {
        return hotlinePerson;
    }

    public void setHotlinePerson(String hotlinePerson) {
        this.hotlinePerson = hotlinePerson;
    }

    public String getHotlinePhoto() {
        return hotlinePhoto;
    }

    public void setHotlinePhoto(String hotlinePhoto) {
        this.hotlinePhoto = hotlinePhoto;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public int getWaitCount() {
        return waitCount;
    }

    public void setWaitCount(int waitCount) {
        this.waitCount = waitCount;
    }

    public String getCustPic() {
        return custPic;
    }

    public void setCustPic(String custPic) {
        this.custPic = custPic;
    }
}
