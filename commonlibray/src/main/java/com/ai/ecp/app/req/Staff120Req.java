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
 * Description: 标记我的消息为已读入参<br>
 * Date:2016-2-22下午6:52:57  <br>
 * Copyright (c) 2016 asia All Rights Reserved <br>
 * 
 * @author huangxl5
 * @version  
 * @since JDK 1.6 
 */
public class Staff120Req extends AppBody {
	
	private String msgInfoIds;//如果是多个消息，以英文的逗号间隔开

	public String getMsgInfoIds() {
		return msgInfoIds;
	}

	public void setMsgInfoIds(String msgInfoIds) {
		this.msgInfoIds = msgInfoIds;
	}

}

