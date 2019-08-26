/** 
 * Project Name:ecp-web-mall 
 * File Name:DemoResp.java 
 * Package Name:com.ai.ecp.app.resp 
 * Date:2016-2-22下午6:53:17 
 * Copyright (c) 2016, asia All Rights Reserved. 
 * 
 */ 
package com.ai.ecp.app.resp;


import com.ai.ecp.app.resp.staff.CollectionShopInfo;
import com.ailk.butterfly.app.model.AppBody;

import java.util.List;

/**
 * Title: ECP <br>
 * Project Name:ecp-web-mall <br>
 * Description: 我收藏的店铺出参<br>
 * Date:2016-2-22下午6:53:17  <br>
 * Copyright (c) 2016 asia All Rights Reserved <br>
 * 
 * @author huangxl5
 * @version  
 * @since JDK 1.6 
 */
public class Staff113Resp extends AppBody {
    
	List<CollectionShopInfo> resList;

	public List<CollectionShopInfo> getResList() {
		return resList;
	}

	public void setResList(List<CollectionShopInfo> resList) {
		this.resList = resList;
	}

}

