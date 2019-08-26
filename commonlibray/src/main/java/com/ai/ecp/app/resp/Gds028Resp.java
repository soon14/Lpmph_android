package com.ai.ecp.app.resp;

import java.util.List;

import com.ai.ecp.goods.dubbo.dto.GdsPropRespDTO;
import com.ailk.butterfly.app.model.AppBody;

public class Gds028Resp extends AppBody {
	private List<GdsPropRespDTO> gdsPropList;

	public List<GdsPropRespDTO> getGdsPropList() {
		return gdsPropList;
	}

	public void setGdsPropList(List<GdsPropRespDTO> gdsPropList) {
		this.gdsPropList = gdsPropList;
	}

	
}

