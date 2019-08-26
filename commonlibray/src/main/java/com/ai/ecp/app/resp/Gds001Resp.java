package com.ai.ecp.app.resp;

import com.ai.ecp.app.resp.gds.GdsDetailBaseInfo;
import com.ailk.butterfly.app.model.AppBody;
import com.ailk.butterfly.app.model.IBody;

public class Gds001Resp extends AppBody {
    
    private GdsDetailBaseInfo gdsDetailBaseInfo; 
    

	public GdsDetailBaseInfo getGdsDetailBaseInfo() {
		return gdsDetailBaseInfo;
	}

	public void setGdsDetailBaseInfo(GdsDetailBaseInfo gdsDetailBaseInfo) {
		this.gdsDetailBaseInfo = gdsDetailBaseInfo;
	}  
    
    
}

