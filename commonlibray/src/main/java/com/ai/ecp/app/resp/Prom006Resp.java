package com.ai.ecp.app.resp;


import java.util.List;

import com.ailk.butterfly.app.model.AppBody;

public class Prom006Resp extends AppBody{

	List<KillPromInfoRespVO> resList;

	public List<KillPromInfoRespVO> getResList() {
		return resList;
	}

	public void setResList(List<KillPromInfoRespVO> resList) {
		this.resList = resList;
	}
	
}
