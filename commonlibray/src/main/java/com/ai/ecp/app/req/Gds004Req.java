package com.ai.ecp.app.req;

import com.ailk.butterfly.app.model.AppBody;

public class Gds004Req extends AppBody {

    private Long gdsId;
    
    private Long skuId;
    
    private Long shopId;

    public Long getGdsId() {
        return gdsId;
    }

    public void setGdsId(Long gdsId) {
        this.gdsId = gdsId;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }


    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }
}

