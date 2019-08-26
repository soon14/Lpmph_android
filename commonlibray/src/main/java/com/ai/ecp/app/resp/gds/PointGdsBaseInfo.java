package com.ai.ecp.app.resp.gds;

import com.ailk.butterfly.app.model.AppBody;
import com.ailk.butterfly.app.model.IBody;

/**
 * 
 * Title: ECP  商品基本信息<br>
 * Project Name:ecp-web-mall <br>
 * Description: <br>
 * Date:2016年3月11日下午4:01:38  <br>
 * Copyright (c) 2016 asia All Rights Reserved <br>
 * 
 * @author linwb3
 * @version  
 * @since JDK 1.6
 */
public class PointGdsBaseInfo extends AppBody {

    /**
     * 商品名称
     */
    private String gdsName;

    /**
     * 商品编码
     */
    private Long gdsId;

    /**
     * 单品编码
     */
    private Long skuId;

    /**
     * 店铺编码
     */
    private Long shopId;

    /**
     * 基本价格
     */
    private Long basePrice;

    /**
     * 购买价格
     */
    private Long buyPrice;
    
    /**
     * 图片路径
     */
    private String imgPath;

    public String getGdsName() {
        return gdsName;
    }

    public void setGdsName(String gdsName) {
        this.gdsName = gdsName;
    }

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

    public Long getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(Long basePrice) {
        this.basePrice = basePrice;
    }

    public Long getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(Long buyPrice) {
        this.buyPrice = buyPrice;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

}
