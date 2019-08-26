package com.ai.ecp.app.resp.gds;

import com.ailk.butterfly.app.model.AppBody;
import com.ailk.butterfly.app.model.IBody;

public class PromListBaseInfo extends AppBody{
    private PromBaseInfo promBaseInfo;
    
    private PromSkuPriceBaseInfo promSkuPriceBaseInfo;

	public PromBaseInfo getPromBaseInfo() {
		return promBaseInfo;
	}

	public void setPromBaseInfo(PromBaseInfo promBaseInfo) {
		this.promBaseInfo = promBaseInfo;
	}

	public PromSkuPriceBaseInfo getPromSkuPriceBaseInfo() {
		return promSkuPriceBaseInfo;
	}

	public void setPromSkuPriceBaseInfo(PromSkuPriceBaseInfo promSkuPriceBaseInfo) {
		this.promSkuPriceBaseInfo = promSkuPriceBaseInfo;
	}
}

