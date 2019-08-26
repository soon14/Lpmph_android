package com.ai.ecp.app.req;

import com.ailk.butterfly.app.model.AppBody;

public class Gds015Req extends AppBody {
	private Long evalId;
	
	private int pageNumber;
	
	private int pageSize;

	public Long getEvalId() {
		return evalId;
	}

	public void setEvalId(Long evalId) {
		this.evalId = evalId;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}


	
}

