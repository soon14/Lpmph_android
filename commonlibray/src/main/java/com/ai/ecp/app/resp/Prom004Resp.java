package com.ai.ecp.app.resp;

import java.util.List;

import com.ailk.butterfly.app.model.AppBody;
import com.ailk.butterfly.app.model.IBody;

public class Prom004Resp extends AppBody{
	List<PromTypeRespVO> resList;

	public List<PromTypeRespVO> getResList() {
		return resList;
	}

	public void setResList(List<PromTypeRespVO> resList) {
		this.resList = resList;
	}

}
