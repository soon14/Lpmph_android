package com.ai.ecp.app.resp;

import com.ai.ecp.app.resp.gds.GdsDetailBaseInfo;
import com.ailk.butterfly.app.model.AppBody;
import com.ailk.butterfly.app.model.IBody;

public class Gds008Resp extends AppBody {
 
    private String digitsPrinPrice;
    
    private GdsDetailBaseInfo  gdsDetailBaseInfo;
    
    private String stockStatus;
    
	private String mainPicUrl;

    public String getDigitsPrinPrice() {
        return digitsPrinPrice;
    }

    public void setDigitsPrinPrice(String digitsPrinPrice) {
        this.digitsPrinPrice = digitsPrinPrice;
    }


    public String getStockStatus() {
        return stockStatus;
    }

    public void setStockStatus(String stockStatus) {
        this.stockStatus = stockStatus;
    }

	public String getMainPicUrl() {
		return mainPicUrl;
	}

	public void setMainPicUrl(String mainPicUrl) {
		this.mainPicUrl = mainPicUrl;
	}

	public GdsDetailBaseInfo getGdsDetailBaseInfo() {
		return gdsDetailBaseInfo;
	}

	public void setGdsDetailBaseInfo(GdsDetailBaseInfo gdsDetailBaseInfo) {
		this.gdsDetailBaseInfo = gdsDetailBaseInfo;
	}
     
}

