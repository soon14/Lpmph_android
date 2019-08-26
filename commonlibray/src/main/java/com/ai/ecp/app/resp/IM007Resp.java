package com.ai.ecp.app.resp;

import com.ailk.butterfly.app.model.AppBody;

import java.sql.Timestamp;

/**
 * Project : pmph_android
 * Created by 王可 on 2017/3/8.
 */

public class IM007Resp extends AppBody{
    Long count;
    String csaCode;
    Timestamp sessionTime;

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public String getCsaCode() {
        return csaCode;
    }

    public void setCsaCode(String csaCode) {
        this.csaCode = csaCode;
    }

    public Timestamp getSessionTime() {
        return sessionTime;
    }

    public void setSessionTime(Timestamp sessionTime) {
        this.sessionTime = sessionTime;
    }
}
