package com.ai.ecp.app.resp.staff;

import java.sql.Timestamp;


/**
 * 
 * Title: ECP <br>
 * Project Name:ecp-web-mall Maven Webapp <br>
 * Description: 我收藏的店铺javabean，用于Staff113Resp的返回对象List的元素<br>
 * Date:2016-3-4下午4:49:08  <br>
 * Copyright (c) 2016 asia All Rights Reserved <br>
 * 
 * @author huangxl5
 * @version  
 * @since JDK 1.6
 */
public class CollectionShopInfo {

	private Long id;

    private String shopName;

    private String shopFullName;

    private String logoPath;
    
    private String logoPathURL;

    private Timestamp collTime;//收藏时间
    
    private Long gdsCnt;//店铺商品数量
    
    private Long saleGdsCnt;//销售的商品数量

	public Long getGdsCnt() {
		return gdsCnt;
	}

	public void setGdsCnt(Long gdsCnt) {
		this.gdsCnt = gdsCnt;
	}


	public Long getSaleGdsCnt() {
		return saleGdsCnt;
	}

	public void setSaleGdsCnt(Long saleGdsCnt) {
		this.saleGdsCnt = saleGdsCnt;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getShopFullName() {
		return shopFullName;
	}

	public void setShopFullName(String shopFullName) {
		this.shopFullName = shopFullName;
	}

	public String getLogoPath() {
		return logoPath;
	}

	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}

	public String getLogoPathURL() {
		return logoPathURL;
	}

	public void setLogoPathURL(String logoPathURL) {
		this.logoPathURL = logoPathURL;
	}

	public Timestamp getCollTime() {
		return collTime;
	}

	public void setCollTime(Timestamp collTime) {
		this.collTime = collTime;
	}
	
}
