package com.ai.ecp.app.req;

import com.ailk.butterfly.app.model.AppBody;
import com.ailk.butterfly.app.model.IBody;

public class Prom004Req extends AppBody{

	/**
	 * shopId 店铺编码
	 */
	private Long shopId;

	public Long getShopId() {
		return shopId;
	}


	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}
	 

}
