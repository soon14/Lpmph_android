package com.ai.ecp.app.req.gds;

import com.ailk.butterfly.app.model.AppBody;

public class GdsSku2PropPropIdxBaseInfo extends AppBody{
	private Long propId;

    private Long skuId;
    
    private Long gdsId;

    private Long shopId;

    private String propName;

    private Long propValueId;

    private String propValue;

	public Long getPropId() {
		return propId;
	}

	public void setPropId(Long propId) {
		this.propId = propId;
	}

	public Long getSkuId() {
		return skuId;
	}

	public void setSkuId(Long skuId) {
		this.skuId = skuId;
	}

	public Long getGdsId() {
		return gdsId;
	}

	public void setGdsId(Long gdsId) {
		this.gdsId = gdsId;
	}

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public String getPropName() {
		return propName;
	}

	public void setPropName(String propName) {
		this.propName = propName;
	}

	public Long getPropValueId() {
		return propValueId;
	}

	public void setPropValueId(Long propValueId) {
		this.propValueId = propValueId;
	}

	public String getPropValue() {
		return propValue;
	}

	public void setPropValue(String propValue) {
		this.propValue = propValue;
	}


}

