package com.ai.ecp.app.req;

import com.ailk.butterfly.app.model.AppBody;
import com.ailk.butterfly.app.model.IBody;

public class Prom005Req extends AppBody{
	
	/**
	 * shopId 店铺编码
	 */
	private Long shopId;
	
	/**
	 * promTypeCode 促销类型编码
	 */
	private String promTypeCode;
	
    private int pageSize;
    
    private int pageNo;

	public Long getShopId() {
		return shopId;
	}


	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}


	public String getPromTypeCode() {
		return promTypeCode;
	}


	public void setPromTypeCode(String promTypeCode) {
		this.promTypeCode = promTypeCode;
	}


	public int getPageSize() {
		return pageSize;
	}


	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}


	public int getPageNo() {
		return pageNo;
	}


	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
}
