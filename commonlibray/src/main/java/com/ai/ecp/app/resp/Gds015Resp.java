package com.ai.ecp.app.resp;

import java.util.List;

import com.ai.ecp.app.resp.gds.GdsEvalReplyRespBaseInfo;
import com.ailk.butterfly.app.model.AppBody;
import com.ailk.butterfly.app.model.IBody;

public class Gds015Resp extends AppBody {

    private List<GdsEvalReplyRespBaseInfo> evalReplyInfoList;

	public List<GdsEvalReplyRespBaseInfo> getEvalReplyInfoList() {
		return evalReplyInfoList;
	}

	public void setEvalReplyInfoList(List<GdsEvalReplyRespBaseInfo> evalReplyInfoList) {
		this.evalReplyInfoList = evalReplyInfoList;
	}

	
}

