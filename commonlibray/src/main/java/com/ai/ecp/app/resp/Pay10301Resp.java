package com.ai.ecp.app.resp;

import java.io.Serializable;

public class Pay10301Resp implements Serializable{
    private boolean isChoosed;
    /** 
     * shopId:店铺ID. 
     * @since JDK 1.6 
     */ 
    private Long shopId;
    
    /** 
     * shopName:店铺名称. 
     * @since JDK 1.6 
     */ 
    private String shopName;
    
    /** 
     * orderId:订单号. 
     * @since JDK 1.6 
     */ 
    private String orderId;
    
    /** 
     * realMoney:实付金额. 
     * @since JDK 1.6 
     */ 
    private Long realMoney;

    private Long orderScore;

    public Long getOrderScore() {
        return orderScore;
    }

    public void setOrderScore(Long orderScore) {
        this.orderScore = orderScore;
    }

    public Long getRealMoney() {
        return realMoney;
    }

    public void setRealMoney(Long realMoney) {
        this.realMoney = realMoney;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public boolean isChoosed() {
        return isChoosed;
    }

    public void setIsChoosed(boolean isChoosed) {
        this.isChoosed = isChoosed;
    }

}

