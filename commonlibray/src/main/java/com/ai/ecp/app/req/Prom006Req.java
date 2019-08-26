package com.ai.ecp.app.req;

import com.ailk.butterfly.app.model.AppBody;

public class Prom006Req extends AppBody{
	
	private Long siteId;
	
    private int pageSize;
    
    private int pageNo;

	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
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
