package com.ai.ecp.app.req;

import com.ailk.butterfly.app.model.AppBody;

public class Gds010Req extends AppBody {
    
    private Long skuId;
    
    public Long getCollectId() {
		return collectId;
	}

	public void setCollectId(Long collectId) {
		this.collectId = collectId;
	}

	private Long collectId;
    
    //是否取消收藏
    private Boolean ifCancel;

    public Boolean getIfCancel() {
		return ifCancel;
	}

	public void setIfCancel(Boolean ifCancel) {
		this.ifCancel = ifCancel;
	}

	public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }
}

