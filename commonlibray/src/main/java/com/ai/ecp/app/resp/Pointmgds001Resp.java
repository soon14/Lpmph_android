package com.ai.ecp.app.resp;

import com.ai.ecp.app.resp.gds.PointGdsDetailBaseInfo;
import com.ailk.butterfly.app.model.AppBody;

public class Pointmgds001Resp extends AppBody {

	private PointGdsDetailBaseInfo gdsDetailBaseInfo;
	//剩余积分
	private Long remainScore;

	public PointGdsDetailBaseInfo getGdsDetailBaseInfo() {
		return gdsDetailBaseInfo;
	}

	public void setGdsDetailBaseInfo(PointGdsDetailBaseInfo gdsDetailBaseInfo) {
		this.gdsDetailBaseInfo = gdsDetailBaseInfo;
	}

	public Long getRemainScore() {
		return remainScore;
	}

	public void setRemainScore(Long remainScore) {
		this.remainScore = remainScore;
	}


}

