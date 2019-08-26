package com.ai.ecp.app.resp;

import java.util.List;

import com.ailk.butterfly.app.model.AppBody;
import com.ailk.butterfly.app.model.IBody;

public class Pay103Resp extends AppBody {
    
    /** 
     * redisKey:缓存中的key. 
     * @since JDK 1.6 
     */ 
    private String redisKey;
    
    /** 
     * pay10301Resps:支付店铺列表. 
     * @since JDK 1.6 
     */ 
    private List<Pay10301Resp> pay10301Resps;

    public String getRedisKey() {
        return redisKey;
    }

    public void setRedisKey(String redisKey) {
        this.redisKey = redisKey;
    }

    public List<Pay10301Resp> getPay10301Resps() {
        return pay10301Resps;
    }

    public void setPay10301Resps(List<Pay10301Resp> pay10301Resps) {
        this.pay10301Resps = pay10301Resps;
    }
    
    

}

