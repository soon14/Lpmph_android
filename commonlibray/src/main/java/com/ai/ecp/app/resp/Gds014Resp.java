package com.ai.ecp.app.resp;

import java.util.List;

import com.ai.ecp.app.resp.gds.GdsPropBaseInfo;
import com.ailk.butterfly.app.model.AppBody;

public class Gds014Resp extends AppBody {

    private List<GdsPropBaseInfo> propList;

	public List<GdsPropBaseInfo> getPropList() {
		return propList;
	}

	public void setPropList(List<GdsPropBaseInfo> propList) {
		this.propList = propList;
	}


	
	
	
}

