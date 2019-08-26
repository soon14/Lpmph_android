package com.ai.ecp.app.resp;

import java.util.List;

import com.ai.ecp.app.resp.gds.GdsEvalBaseInfo;
import com.ailk.butterfly.app.model.AppBody;

public class Gds012Resp extends AppBody {

    
    private List<GdsEvalBaseInfo> gdsEvalRespList;    

	private long count = 0;// 总条数

	private long pageCount;

	private String msg;


	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public List<GdsEvalBaseInfo> getGdsEvalRespList() {
		return gdsEvalRespList;
	}

	public void setGdsEvalRespList(List<GdsEvalBaseInfo> gdsEvalRespList) {
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

