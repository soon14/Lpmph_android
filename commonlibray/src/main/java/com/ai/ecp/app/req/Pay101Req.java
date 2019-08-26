package com.ai.ecp.app.req;

import com.ailk.butterfly.app.model.AppBody;

public class Pay101Req extends AppBody {
    
    /** 
     * redisKey:缓存中的key. 
     * @since JDK 1.6 
     */ 
    private String redisKey;

    public String getRedisKey() {
        return redisKey;
    }

    public void setRedisKey(String redisKey) {
        this.redisKey = redisKey;
    }
    
}

