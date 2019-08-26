package com.ai.ecp.app.resp.gds;

import com.ailk.butterfly.app.model.AppBody;
import com.ailk.butterfly.app.model.IBody;

import java.util.List;

/**
 * Created by HDF on 2016/6/1.
 */
public class ShopSearchResultVO extends AppBody {

    public final static String SUFFIX_IMAGE_SIZE = "_220x220!";

    /**
     * 店铺id
     */
    private String id;

    /**
     * 店铺类型
     */
    private String shopType;

    /**
     * 店铺名
     */
    private String shopName;

    /**
     * 店铺描述
     */
    private String shopDesc;

    /**
     * 店铺logo mongodb主键
     */
    private String logoPath;

    /**
     * 商品好评率
     */
    private Double gdsEvalRate;

    /**
     * 店铺商品销量
     */
    private Long saleCount;

    /**
     * 店铺商品总数
     */
    private Long gdsCount;

    /**
     * 店铺logo地址
     */
    private String logoUrl;

    private  List<GoodSearchResultVO> goodList ;

    public Long getGdsCount() {
        return gdsCount;
    }

    public void setGdsCount(Long gdsCount) {
        this.gdsCount = gdsCount;
    }

    public Double getGdsEvalRate() {
        return gdsEvalRate;
    }

    public void setGdsEvalRate(Double gdsEvalRate) {
        this.gdsEvalRate = gdsEvalRate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogoPath() {
        return logoPath;
    }

    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public List<GoodSearchResultVO> getGoodList() {
        return goodList;
    }

    public void setGoodList(List<GoodSearchResultVO> goodList) {
        this.goodList = goodList;
    }

    public Long getSaleCount() {
        return saleCount;
    }

    public void setSaleCount(Long saleCount) {
        this.saleCount = saleCount;
    }

    public String getShopDesc() {
        return shopDesc;
    }

    public void setShopDesc(String shopDesc) {
        this.shopDesc = shopDesc;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopType() {
        return shopType;
    }

    public void setShopType(String shopType) {
        this.shopType = shopType;
    }

    public static String getSuffixImageSize() {
        return SUFFIX_IMAGE_SIZE;
    }
}
