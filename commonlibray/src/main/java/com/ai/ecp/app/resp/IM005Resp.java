package com.ai.ecp.app.resp;

import com.ai.ecp.busi.im.history.vo.MessageHistoryReqVO;
import com.ailk.butterfly.app.model.AppBody;

/**
 * 
 * Title: ECP <br>
 * Project Name:ecp-web-im <br>
 * Description: <br>
 * Date:2017年1月11日下午4:54:31  <br>
 * Copyright (c) 2017 asia All Rights Reserved <br>
 * 
 * @author linby
 * @version  
 * @since JDK 1.7
 */
public class IM005Resp extends AppBody {
	private MessageHistoryReqVO messageHistory;

	public MessageHistoryReqVO getMessageHistory() {
		return messageHistory;
	}

	public void setMessageHistory(MessageHistoryReqVO messageHistory) {
		this.messageHistory = messageHistory;
	}

}
