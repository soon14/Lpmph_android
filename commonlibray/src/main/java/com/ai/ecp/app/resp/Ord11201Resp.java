package com.ai.ecp.app.resp;

import java.io.Serializable;
import java.sql.Timestamp;

public class Ord11201Resp implements Serializable{
    /** 
     * skuId:商品编号. 
     * @since JDK 1.6 
     */ 
    private Long skuId;
    /** 
     * skuHisId:单品快照ID. 
     * @since JDK 1.6 
     */ 
    private Long skuHisId;
    /** 
     * gdsName:商品名称. 
     * @since JDK 1.6 
     */ 
    private String gdsName;
    /** 
     * buyPrice:商品价格. 
     * @since JDK 1.6 
     */ 
    private Long buyPrice;
    /** 
     * orderAmount:商品数量. 
     * @since JDK 1.6 
     */ 
    private Long orderAmount;
    
    /** 
     * discountPrice:促销优惠后单价. 
     * @since JDK 1.6 
     */ 
    private Long discountPrice;
    
    /** 
     * dscountMoney:优惠后总价. 
     * @since JDK 1.6 
     */ 
    private Long discountMoney;
    
    /** 
     * score:商品积分. 
     * @since JDK 1.6 
     */ 
    private Long score;
    /** 
     * orderSubScore:子订单总积分. 
     * @since JDK 1.6 
     */ 
    private Long orderSubScore;
    
    /** 
     * picUrl:图片URL. 
     * @since JDK 1.6 
     */ 
    private String picUrl;
    /** 
     * gdsUrl:商品详情URL. 
     * @since JDK 1.6 
     */ 
    private String gdsUrl;
    
    
    private String picId;
    
    
    /** 
     * evalFlag:评价标示. 
     * @since JDK 1.6 
     */ 
    private String evalFlag;
    
    /** 
     * prnFlag:数字印刷标识. 
     * @since JDK 1.6 
     */ 
    private String prnFlag;
    
    
    /** 
     * isbn号. 
     * @since JDK 1.6 
     */ 
    private String isbn;

    /**
     * basePrice商品原价
     * @return
     */
    private Long basePrice;

    /**
     * txCode条形码
     * @return
     */
    private String txCode;

    /**
     * zsCode制式码
     * @return
     */
    private String zsCode;
    
    /** 
     * skuInfo:商品信息. 
     * @since JDK 1.6 
     */ 
    private String skuInfo;
    
    /** 
     * gdsCateName:商品分类. 
     * @since JDK 1.6 
     */ 
    private String gdsCateName;
    
    /** 
     * orderSubId:子订单号. 
     * @since JDK 1.6 
     */ 
    private String orderSubId;
    
    /** 
     * gdsType:商品类型. 
     * @since JDK 1.6 
     */ 
    private Long gdsType;
    
    /** 
     * standardPrice:基准价. 
     * @since JDK 1.6 
     */ 
    private Long standardPrice;

    /**
     * payTime:付款时间.
     * @since JDK 1.6
     */
    private Timestamp payTime;

    /**
     * orderTime:下单时间.
     * @since JDK 1.6
     */
    private Timestamp orderTime;

    public Long getGdsId() {
        return gdsId;
    }

    public void setGdsId(Long gdsId) {
        this.gdsId = gdsId;
    }

    private Long gdsId;

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public Long getSkuHisId() {
        return skuHisId;
    }

    public void setSkuHisId(Long skuHisId) {
        this.skuHisId = skuHisId;
    }

    public String getGdsName() {
        return gdsName;
    }

    public void setGdsName(String gdsName) {
        this.gdsName = gdsName;
    }

    public Long getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(Long buyPrice) {
        this.buyPrice = buyPrice;
    }

    public Long getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(Long orderAmount) {
        this.orderAmount = orderAmount;
    }

    public Long getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(Long discountPrice) {
        this.discountPrice = discountPrice;
    }

    public Long getDiscountMoney() {
        return discountMoney;
    }

    public void setDiscountMoney(Long discountMoney) {
        this.discountMoney = discountMoney;
    }

    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
    }

    public Long getOrderSubScore() {
        return orderSubScore;
    }

    public void setOrderSubScore(Long orderSubScore) {
        this.orderSubScore = orderSubScore;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getGdsUrl() {
        return gdsUrl;
    }

    public void setGdsUrl(String gdsUrl) {
        this.gdsUrl = gdsUrl;
    }

    public String getPicId() {
        return picId;
    }

    public void setPicId(String picId) {
        this.picId = picId;
    }

    public String getEvalFlag() {
        return evalFlag;
    }

    public void setEvalFlag(String evalFlag) {
        this.evalFlag = evalFlag;
    }

    public String getPrnFlag() {
        return prnFlag;
    }

    public void setPrnFlag(String prnFlag) {
        this.prnFlag = prnFlag;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Long getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(Long basePrice) {
        this.basePrice = basePrice;
    }

    public String getTxCode() {
        return txCode;
    }

    public void setTxCode(String txCode) {
        this.txCode = txCode;
    }

    public String getZsCode() {
        return zsCode;
    }

    public void setZsCode(String zsCode) {
        this.zsCode = zsCode;
    }

    public String getSkuInfo() {
        return skuInfo;
    }

    public void setSkuInfo(String skuInfo) {
        this.skuInfo = skuInfo;
    }

    public String getGdsCateName() {
        return gdsCateName;
    }

    public void setGdsCateName(String gdsCateName) {
        this.gdsCateName = gdsCateName;
    }

    public String getOrderSubId() {
        return orderSubId;
    }

    public void setOrderSubId(String orderSubId) {
        this.orderSubId = orderSubId;
    }

    public Long getGdsType() {
        return gdsType;
    }

    public void setGdsType(Long gdsType) {
        this.gdsType = gdsType;
    }

    public Long getStandardPrice() {
        return standardPrice;
    }

    public void setStandardPrice(Long standardPrice) {
        this.standardPrice = standardPrice;
    }

    public Timestamp getPayTime() {
        return payTime;
    }

    public void setPayTime(Timestamp payTime) {
        this.payTime = payTime;
    }

    public Timestamp getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Timestamp orderTime) {
        this.orderTime = orderTime;
    }
}

