package com.ai.ecp.app.resp;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 类注释:
 * 项目名:PMPH
 * 包名:com.ai.ecp.app.resp
 * 作者: Chrizz
 * 时间: 2016/3/23 09:24
 */
public class Ord00401Resp implements Serializable{

    /**
     * 明细促销ID
     */
    private Long promId;
    /**
     * 店铺促销ID
     */
    private Long ordPromId;
    /**
     * 满足促销列表
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
    private BigDecimal discountPrice;
    /**
     * 商品级别数量
     */
    private BigDecimal discountAmount;
    /**
     * 商品打折金额
     */
    private BigDecimal discountMoney;

    public Long getPromId() {
        return promId;
    }

    public void setPromId(Long promId) {
        this.promId = promId;
    }

    public Long getOrdPromId() {
        return ordPromId;
    }

    public void setOrdPromId(Long ordPromId) {
        this.ordPromId = ordPromId;
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

    public BigDecimal getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(BigDecimal discountPrice) {
        this.discountPrice = discountPrice;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    public BigDecimal getDiscountMoney() {
        return discountMoney;
    }

    public void setDiscountMoney(BigDecimal discountMoney) {
        this.discountMoney = discountMoney;
    }
}
