/** 
 * Project Name:ecp-web-mall 
 * File Name:DemoReq.java 
 * Package Name:com.ai.ecp.app.req 
 * Date:2016-2-22下午6:52:57 
 * Copyright (c) 2016, asia All Rights Reserved. 
 * 
 */ 
package com.ai.ecp.app.req;

import com.ailk.butterfly.app.model.AppBody;

/**
 * 
 * Title: ECP <br>
 * Project Name:ecp-web-mall <br>
 * Description: <br>
 * Date:2016年3月7日上午11:40:49  <br>
 * Copyright (c) 2016 asia All Rights Reserved <br>
 * 
 * @author huanghe5
 * @version  
 * @since JDK 1.7
 */
public class Coup004Req extends AppBody {
    
	//优惠券ID
	private Long coupId;

	public Long getCoupId() {
		return coupId;
	}

	public void setCoupId(Long coupId) {
		this.coupId = coupId;
	}
	
    
	
}

