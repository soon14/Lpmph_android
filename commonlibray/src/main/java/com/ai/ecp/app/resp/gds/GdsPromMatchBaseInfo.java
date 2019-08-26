package com.ai.ecp.app.resp.gds;

import java.util.List;

import com.ailk.butterfly.app.model.AppBody;

public class GdsPromMatchBaseInfo extends AppBody{
    
    private PromBaseInfo promInfoDTO;
    
    private List<GdsPromMatchSkuBaseInfo> gdsPromMatchSkuVOList;

	public PromBaseInfo getPromInfoDTO() {
		return promInfoDTO;
	}

	public void setPromInfoDTO(PromBaseInfo promInfoDTO) {
		this.promInfoDTO = promInfoDTO;
	}

	public List<GdsPromMatchSkuBaseInfo> getGdsPromMatchSkuVOList() {
		return gdsPromMatchSkuVOList;
	}

	public void setGdsPromMatchSkuVOList(List<GdsPromMatchSkuBaseInfo> gdsPromMatchSkuVOList) {
		this.gdsPromMatchSkuVOList = gdsPromMatchSkuVOList;
	}
}

