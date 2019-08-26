/** 
 * Project Name:ecp-web-mall 
 * File Name:DemoResp.java 
 * Package Name:com.ai.ecp.app.resp 
 * Date:2016-2-22下午6:53:17 
 * Copyright (c) 2016, asia All Rights Reserved. 
 * 
 */ 
package com.ai.ecp.app.resp;



import com.ai.ecp.sys.dubbo.dto.MsgInsiteResDTO;
import com.ailk.butterfly.app.model.AppBody;

import java.util.List;

/**
 * Title: ECP <br>
 * Project Name:ecp-web-mall <br>
 * Description: 查询我的消息出参<br>
 * Date:2016-2-22下午6:53:17  <br>
 * Copyright (c) 2016 asia All Rights Reserved <br>
 * 
 * @author huangxl5
 * @version  
 * @since JDK 1.6 
 */
public class Staff117Resp extends AppBody {
    
	List<MsgInsiteResDTO> resList;

	public List<MsgInsiteResDTO> getResList() {
		return resList;
	}

	public void setResList(List<MsgInsiteResDTO> resList) {
		this.resList = resList;
	}
    
}

