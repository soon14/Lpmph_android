package com.ai.ecp.app.resp;

import java.util.List;

import com.ai.ecp.app.resp.gds.GdsEvalRespBaseInfo;
import com.ailk.butterfly.app.model.AppBody;

public class Gds005Resp extends AppBody {
    
    private List<GdsEvalRespBaseInfo> gdsEvalRespList;    

	private long count = 0;// 总条数

	private long pageCount;

	public List<GdsEvalRespBaseInfo> getGdsEvalRespList() {
		return gdsEvalRespList;
	}

	public void setGdsEvalRespList(List<GdsEvalRespBaseInfo> gdsEvalRespList) {
		this.gdsEvalRespList = gdsEvalRespList;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public long getPageCount() {
		return pageCount;
	}

	public void setPageCount(long pageCount) {
		this.pageCount = pageCount;
	}





    
}

