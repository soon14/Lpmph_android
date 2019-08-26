package com.ai.ecp.app.resp;

import java.util.List;

import com.ai.ecp.search.dubbo.search.result.CollationReuslt;
import com.ailk.butterfly.app.model.AppBody;

public class Gds011Resp extends AppBody {
	public List<CollationReuslt> getCollationReuslts() {
		return collationReuslts;
	}

	public void setCollationReuslts(List<CollationReuslt> collationReuslts) {
		this.collationReuslts = collationReuslts;
	}

	private List<CollationReuslt> collationReuslts ;

	
}

