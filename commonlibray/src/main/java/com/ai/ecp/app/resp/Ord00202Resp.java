package com.ai.ecp.app.resp;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wang on 16/3/14.
 */
public class Ord00202Resp implements Serializable{
    private boolean isChoosed;
    /**
     * 购物车明细ID
     */
    private Long id;
    /**
     * 店铺促销ID
     */
    private Long ordPromId;
    /**
     * 促销ID
     */
    private Long promId;
    /**
     * 商品购买价格
     */
    private Long buyPrice;
    /**
     * 商品基准价格
     */
    private Long basePrice;
    /**
     * 商品状态
     */
    private boolean gdsStatus;
    /**
     * 单品ID
     */
    private Long skuId;
    /**
     * 上一级购物车ID
     */
    private Long cartId;
    /**
     * 商品URL
     */
    private String gdsUrl;
    /**
     * 商品图片ID
     */
    private String picId;
    /**
     * 商品图片地址
     */
    private String picUrl;
    /**
     * 商品名称
     */
    private String gdsName;
    /**
     * 商品主分类名称
     */
    private String gdsCateName;
    /**
     * 商品l
     */
    private String gdsType;
    private Long gdsId;
    private Long orderAmount;
    /**
     * 各种促销信息（列表展示）
     */
    private List<Ord00203Resp> promInfoDTOList;
    /**
     * 优惠条件是否满足
     */
    private boolean ifFulfillProm;
    /**
     * 满足优惠条件展示信息
     */
    private String fulfilMsg;
    /**
     * 不满足优惠条件展示信息
     */
    private String noFulfilMsg;
    /**
     * 店铺打折后价格
     */
    private Long discountPrice;
    /**
     * 商品级别数量
     */
    private Long discountAmount;
    /**
     * 商品打折金额
     */
    private Long discountMoney;
    /**
     * 商品基准价格
     */
    private Long discountCaclPrice;

    private boolean isVirtual;

    private static final long serialVersionUID = 1L;

    public Long getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(Long orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getGdsType() {
        return gdsType;
    }

    public void setGdsType(String gdsType) {
        this.gdsType = gdsType;
    }

    public Long getPromId() {
        return promId;
    }

    public void setPromId(Long promId) {
        this.promId = promId;
    }

    public Long getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(Long buyPrice) {
        this.buyPrice = buyPrice;
    }

    public Long getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(Long basePrice) {
        this.basePrice = basePrice;
    }

    public boolean isGdsStatus() {
        return gdsStatus;
    }

    public void setGdsStatus(boolean gdsStatus) {
        this.gdsStatus = gdsStatus;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
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

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getGdsName() {
        return gdsName;
    }

    public void setGdsName(String gdsName) {
        this.gdsName = gdsName;
    }

    public String getGdsCateName() {
        return gdsCateName;
    }

    public void setGdsCateName(String gdsCateName) {
        this.gdsCateName = gdsCateName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Ord00203Resp> getPromInfoDTOList() {
        return promInfoDTOList;
    }

    public void setPromInfoDTOList(List<Ord00203Resp> promInfoDTOList) {
        this.promInfoDTOList = promInfoDTOList;
    }

    public boolean isIfFulfillProm() {
        return ifFulfillProm;
    }

    public void setIfFulfillProm(boolean ifFulfillProm) {
        this.ifFulfillProm = ifFulfillProm;
    }

    public String getFulfilMsg() {
        return fulfilMsg;
    }

    public void setFulfilMsg(String fulfilMsg) {
        this.fulfilMsg = fulfilMsg;
    }

    public String getNoFulfilMsg() {
        return noFulfilMsg;
    }

    public void setNoFulfilMsg(String noFulfilMsg) {
        this.noFulfilMsg = noFulfilMsg;
    }

    public Long getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(Long discountPrice) {
        this.discountPrice = discountPrice;
    }

    public Long getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Long discountAmount) {
        this.discountAmount = discountAmount;
    }

    public Long getDiscountMoney() {
        return discountMoney;
    }

    public void setDiscountMoney(Long discountMoney) {
        this.discountMoney = discountMoney;
    }

    public Long getOrdPromId() {
        return ordPromId;
    }

    public void setOrdPromId(Long ordPromId) {
        this.ordPromId = ordPromId;
    }

    public boolean isChoosed() {
        return isChoosed;
    }

    public void setIsChoosed(boolean isChoosed) {
        this.isChoosed = isChoosed;
    }

    public Long getGdsId() {
        return gdsId;
    }

    public void setGdsId(Long gdsId) {
        this.gdsId = gdsId;
    }

    public Long getDiscountCaclPrice() {
        return discountCaclPrice;
    }

    public void setDiscountCaclPrice(Long discountCaclPrice) {
        this.discountCaclPrice = discountCaclPrice;
    }

    public boolean isVirtual() {
        return isVirtual;
    }

    public void setVirtual(boolean virtual) {
        isVirtual = virtual;
    }
}
