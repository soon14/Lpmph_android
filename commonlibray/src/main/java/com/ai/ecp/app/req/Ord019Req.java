package com.ai.ecp.app.req;

import com.ailk.butterfly.app.model.AppBody;

public class Ord019Req extends AppBody{

    private String coupCode;//优惠码
    private Long shopId;//店铺Id
    private String sourceKey;//来源
    private String redisKey;
    public String getCoupCode() {
        return coupCode;
    }
    public Long getShopId() {
        return shopId;
    }
    public String getSourceKey() {
        return sourceKey;
    }
    public void setCoupCode(String coupCode) {
        this.coupCode = coupCode;
    }
    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }
    public void setSourceKey(String sourceKey) {
        this.sourceKey = sourceKey;
    }
    public String getRedisKey() {
        return redisKey;
    }
    public void setRedisKey(String redisKey) {
        this.redisKey = redisKey;
    }
   
}

