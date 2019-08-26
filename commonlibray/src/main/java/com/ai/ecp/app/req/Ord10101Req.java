package com.ai.ecp.app.req;

import java.io.Serializable;

public class Ord10101Req implements Serializable{

    /** 
     * shopId:店铺编码. 
     * @since JDK 1.6 
     */ 
    private Long shopId;
    
    /** 
     * gdsId:商品ID. 
     * @since JDK 1.6 
     */ 
    private Long gdsId;
    
    /** 
     * gdsName:商品名称. 
     * @since JDK 1.6 
     */ 
    private String gdsName;
    
    /** 
     * skuId:单品ID. 
     * @since JDK 1.6 
     */ 
    private Long skuId;
    
    /** 
     * skuInfo:单品信息. 
     * @since JDK 1.6 
     */ 
    private String skuInfo;
    
    /** 
     * groupType:是否组合商品. 
     * @since JDK 1.6 
     */ 
    private String groupType;
    
    /** 
     * groupDetail:组合商品名细. 
     * @since JDK 1.6 
     */ 
    private String groupDetail;
    
    /** 
     * gdsType:商品类型. 
     * @since JDK 1.6 
     */ 
    private Long gdsType;
    
    /** 
     * orderAmount:商品数量. 
     * @since JDK 1.6 
     */ 
    private Long orderAmount;
    
    /** 
     * categoryCode:商品分类. 
     * @since JDK 1.6 
     */ 
    private String categoryCode;
    
    /** 
     * scoreTypeId:积分使用类型. 
     * @since JDK 1.6 
     */ 
    private Long scoreTypeId;
    
    /** 
     * prnFlag:数字印刷标识. 
     * @since JDK 1.6 
     */ 
    private String prnFlag;
    
    /** 
     * promId:促销编码. 
     * @since JDK 1.6 
     */ 
    private Long promId;

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Long getGdsId() {
        return gdsId;
    }

    public void setGdsId(Long gdsId) {
        this.gdsId = gdsId;
    }

    public String getGdsName() {
        return gdsName;
    }

    public void setGdsName(String gdsName) {
        this.gdsName = gdsName;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public String getSkuInfo() {
        return skuInfo;
    }

    public void setSkuInfo(String skuInfo) {
        this.skuInfo = skuInfo;
    }

    public String getGroupType() {
        return groupType;
    }

    public void setGroupType(String groupType) {
        this.groupType = groupType;
    }

    public String getGroupDetail() {
        return groupDetail;
    }

    public void setGroupDetail(String groupDetail) {
        this.groupDetail = groupDetail;
    }


    public Long getGdsType() {
        return gdsType;
    }

    public void setGdsType(Long gdsType) {
        this.gdsType = gdsType;
    }

    public Long getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(Long orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public Long getScoreTypeId() {
        return scoreTypeId;
    }

    public void setScoreTypeId(Long scoreTypeId) {
        this.scoreTypeId = scoreTypeId;
    }

    public String getPrnFlag() {
        return prnFlag;
    }

    public void setPrnFlag(String prnFlag) {
        this.prnFlag = prnFlag;
    }

    public Long getPromId() {
        return promId;
    }

    public void setPromId(Long promId) {
        this.promId = promId;
    }
    

}
