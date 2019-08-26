package com.ai.ecp.app.resp;

import java.util.List;

import com.ailk.butterfly.app.model.AppBody;
import com.ailk.butterfly.app.model.IBody;

public class Prom005Resp extends AppBody{
	List<PromSkuRespVO> resList;

	public List<PromSkuRespVO> getResList() {
		return resList;
	}

	public void setResList(List<PromSkuRespVO> resList) {
		this.resList = resList;
	}

}
