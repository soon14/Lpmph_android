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
 * Title: ECP <br>
 * Project Name:ecp-web-mall <br>
 * Description: 查询我的消息数量入参<br>
 * Date:2016-2-22下午6:52:57  <br>
 * Copyright (c) 2016 asia All Rights Reserved <br>
 * 
 * @author huangxl5
 * @version  
 * @since JDK 1.6 
 */
public class Staff118Req extends AppBody {
	
	private String readFlag;

	public String getReadFlag() {
		return readFlag;
	}

	public void setReadFlag(String readFlag) {
		this.readFlag = readFlag;
	}

	
}

