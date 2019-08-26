package com.ai.ecp.app.resp;

import com.ailk.butterfly.app.model.AppBody;
import com.ailk.butterfly.app.model.IBody;

public class PromSkuRespVO extends AppBody{
	
	/** 
     * promTypeCode:促销类型编码. 
     */ 
    private String promTypeCode;
    /** 
     * promTypeName:促销类型名称. 
     */ 
    private String promTypeName;
    /** 
     * nameShort:简称. 
     */ 
    private String nameShort;
    /** 
     * 商品编码
     */ 
    private Long gdsId;
    /** 
     * 商品名称
     */ 
    private String gdsName;
    
    /** 
     * 单品编码
     */ 
    private Long skuId;
    
    /** 
     *单品编码 
     */ 
    private String skuName;
    
    /** 
     * 促销编码
     */ 
    private Long promId;
    
    /** 
     * 促销名称
     */ 
    private String promTheme;
    
    /** 
     * 单价
     */ 
    
    private Long price;
    
    /** 
     * 促销单价
     */ 
    private Long promPrice;
    
    private String parentId;
    
    private String childId;
    
    private Long sales;
    
    private String imageUrl;

	public String getPromTypeCode() {
		return promTypeCode;
	}

	public void setPromTypeCode(String promTypeCode) {
		this.promTypeCode = promTypeCode;
	}

	public String getPromTypeName() {
		return promTypeName;
	}

	public void setPromTypeName(String promTypeName) {
		this.promTypeName = promTypeName;
	}

	public String getNameShort() {
		return nameShort;
	}

	public void setNameShort(String nameShort) {
		this.nameShort = nameShort;
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

	public String getSkuName() {
		return skuName;
	}

	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}

	public Long getPromId() {
		return promId;
	}

	public void setPromId(Long promId) {
		this.promId = promId;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public Long getPromPrice() {
		return promPrice;
	}

	public void setPromPrice(Long promPrice) {
		this.promPrice = promPrice;
	}

	public String getPromTheme() {
		return promTheme;
	}

	public void setPromTheme(String promTheme) {
		this.promTheme = promTheme;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getChildId() {
		return childId;
	}

	public void setChildId(String childId) {
		this.childId = childId;
	}

	public Long getSales() {
		return sales;
	}

	public void setSales(Long sales) {
		this.sales = sales;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
    
}
