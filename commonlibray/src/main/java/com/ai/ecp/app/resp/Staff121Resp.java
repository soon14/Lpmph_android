/** 
 * Project Name:ecp-web-mall 
 * File Name:DemoResp.java 
 * Package Name:com.ai.ecp.app.resp 
 * Date:2016-2-22下午6:53:17 
 * Copyright (c) 2016, asia All Rights Reserved. 
 * 
 */ 
package com.ai.ecp.app.resp;



import com.ailk.butterfly.app.model.AppBody;

/**
 * 
 * Title: ECP <br>
 * Project Name:ecp-web-mall <br>
 * Description: 签到送积分出参<br>
 * Date:2016年9月21日下午3:41:54  <br>
 * Copyright (c) 2016 asia All Rights Reserved <br>
 * 
 * @author huangxl5
 * @version  
 * @since JDK 1.6
 */
public class Staff121Resp extends AppBody {
    
	private Long score;//签到送的积分
	
	private Long signCnt;//连续签到次数

	public Long getScore() {
		return score;
	}

	public void setScore(Long score) {
		this.score = score;
	}

	public Long getSignCnt() {
		return signCnt;
	}

	public void setSignCnt(Long signCnt) {
		this.signCnt = signCnt;
	}

}

