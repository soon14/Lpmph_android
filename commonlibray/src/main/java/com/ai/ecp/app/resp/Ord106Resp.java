package com.ai.ecp.app.resp;

import com.ailk.butterfly.app.model.AppBody;

public class Ord106Resp extends AppBody {
    
    /** 
     * staffId:当前用户ID. 
     * @since JDK 1.6 
     */ 
    private Long staffId;
    
    /** 
     * redisKey:数据缓存中的Key. 
     * @since JDK 1.6 
     */ 
    private String redisKey;

    /**
     * 是否有异常标识，1为异常
     */
    private String exceptionFlag;

    /**
     * 异常内容
     */
    private String exceptionContent;

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public String getRedisKey() {
        return redisKey;
    }

    public void setRedisKey(String redisKey) {
        this.redisKey = redisKey;
    }

    public String getExceptionFlag() {
        return exceptionFlag;
    }

    public void setExceptionFlag(String exceptionFlag) {
        this.exceptionFlag = exceptionFlag;
    }

    public String getExceptionContent() {
        return exceptionContent;
    }

    public void setExceptionContent(String exceptionContent) {
        this.exceptionContent = exceptionContent;
    }

}

