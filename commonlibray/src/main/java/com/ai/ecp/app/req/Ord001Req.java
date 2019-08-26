package com.ai.ecp.app.req;

import com.ailk.butterfly.app.model.AppBody;

import java.util.List;

public class Ord001Req extends AppBody {
    
    /** 
     * cartType:购物车类型. 
     * @since JDK 1.6 
     */ 
    private String cartType;

    /** 
     * staffId:当前用户编码. 
     * @since JDK 1.6 
     */ 
    private Long staffId;

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
    
    private List<Ord00101Req> ord00101Reqs;

    public String getCartType() {
        return cartType;
    }

    public void setCartType(String cartType) {
        this.cartType = cartType;
    }

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
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

    public List<Ord00101Req> getOrd00101Reqs() {
        return ord00101Reqs;
    }

    public void setOrd00101Reqs(List<Ord00101Req> ord00101Reqs) {
        this.ord00101Reqs = ord00101Reqs;
    }

    
}

