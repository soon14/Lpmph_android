package com.ai.ecp.app.resp.gds;

import com.ailk.butterfly.app.model.AppBody;

public class PointGdsSkuBaseInfo extends AppBody{

	
	private Long id;
	
	private Long snapId;
	
	/**
	 * 单品属性串
	 */
	private String skuProps;
	
	/**
	 * 商品编码
	 */
	private Long gdsId;
	
	/**
	 * 单品库存真实值 (详情用)
	 */
	private Long realAmount;
	

	/**
	 * 当前查询条件默认价格
	 */
	private Long realPrice;

	/**
	 * 当前查询条件默认价格
	 */
	private Long discountPrice;
	
	/**
	 * 单品状态
	 */
	private String gdsStatus;
/**
 * 主图地址
 */
	private String mainPicUrl;

	
	public String getGdsName() {
	return gdsName;
}

public void setGdsName(String gdsName) {
	this.gdsName = gdsName;
}



public String getMainCatgs() {
	return mainCatgs;
}

public void setMainCatgs(String mainCatgs) {
	this.mainCatgs = mainCatgs;
}

	private String gdsName;
	
	public Long getGdsTypeId() {
		return gdsTypeId;
	}

	public void setGdsTypeId(Long gdsTypeId) {
		this.gdsTypeId = gdsTypeId;
	}

	private Long gdsTypeId;
	
	private String mainCatgs;
	
	
	public Long getSnapId() {
		return snapId;
	}

	public void setSnapId(Long snapId) {
		this.snapId = snapId;
	}

	public String getSkuProps() {
		return skuProps;
	}

	public void setSkuProps(String skuProps) {
		this.skuProps = skuProps;
	}

	public Long getGdsId() {
		return gdsId;
	}

	public void setGdsId(Long gdsId) {
		this.gdsId = gdsId;
	}

	public Long getRealAmount() {
		return realAmount;
	}

	public void setRealAmount(Long realAmount) {
		this.realAmount = realAmount;
	}

	public Long getRealPrice() {
		return realPrice;
	}

	public void setRealPrice(Long realPrice) {
		this.realPrice = realPrice;
	}

	public Long getDiscountPrice() {
		return discountPrice;
	}

	public void setDiscountPrice(Long discountPrice) {
		this.discountPrice = discountPrice;
	}

	public String getGdsStatus() {
		return gdsStatus;
	}

	public void setGdsStatus(String gdsStatus) {
		this.gdsStatus = gdsStatus;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMainPicUrl() {
		return mainPicUrl;
	}

	public void setMainPicUrl(String mainPicUrl) {
		this.mainPicUrl = mainPicUrl;
	}
}

