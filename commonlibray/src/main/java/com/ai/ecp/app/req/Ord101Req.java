package com.ai.ecp.app.req;

import java.util.List;

import com.ailk.butterfly.app.model.AppBody;
import com.ailk.butterfly.app.model.IBody;

public class Ord101Req extends AppBody {
    
    /** 
     * cartType:购物车类型. 
     * @since JDK 1.6 
     */ 
    private String cartType;

    /** 
     * shopId:店铺编码. 
     * @since JDK 1.6 
     */ 
    private Long shopId;

    /** 
     * promId:促销id. 
     * @since JDK 1.6 
     */ 
    private Long promId;
    
    private List<Ord10101Req> ord10101Reqs;

    public String getCartType() {
        return cartType;
    }

    public void setCartType(String cartType) {
        this.cartType = cartType;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Long getPromId() {
        return promId;
    }

    public void setPromId(Long promId) {
        this.promId = promId;
    }

    public List<Ord10101Req> getOrd10101Reqs() {
        return ord10101Reqs;
    }

    public void setOrd10101Reqs(List<Ord10101Req> ord10101Reqs) {
        this.ord10101Reqs = ord10101Reqs;
    }

    
}

