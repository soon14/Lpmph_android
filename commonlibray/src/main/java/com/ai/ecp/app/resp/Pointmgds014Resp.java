package com.ai.ecp.app.resp;

import java.util.List;

import com.ai.ecp.app.resp.gds.PointGdsPropBaseInfo;
import com.ailk.butterfly.app.model.AppBody;

public class Pointmgds014Resp extends AppBody {

    private List<PointGdsPropBaseInfo> propList;

	public List<PointGdsPropBaseInfo> getPropList() {
		return propList;
	}

	public void setPropList(List<PointGdsPropBaseInfo> propList) {
		this.propList = propList;
	}


	
	
	
}

