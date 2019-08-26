package com.ai.ecp.app.resp;

import com.ailk.butterfly.app.model.AppBody;

/**
 * 
 * @author linwei
 *
 */
public class Ord018Resp extends AppBody{
    private Long ordCartItemNum;

	public Long getOrdCartItemNum() {
		return ordCartItemNum;
	}

	public void setOrdCartItemNum(Long ordCartItemNum) {
		this.ordCartItemNum = ordCartItemNum;
	}
    
}
