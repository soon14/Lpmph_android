package com.ai.ecp.app.resp;


import com.ai.ecp.app.resp.vo.MessageHistoryRespVO;
import com.ailk.butterfly.app.model.AppBody;

import java.util.List;

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
public class IM004Resp extends AppBody {
	private List<MessageHistoryRespVO> pageResp;
	private Long count = 0l;// 总条数
	private Long pageCount; // 分页总数

	public List<MessageHistoryRespVO> getPageResp() {
		return pageResp;
	}
	public void setPageResp(List<MessageHistoryRespVO> pageResp) {
		this.pageResp = pageResp;
	}
	public Long getCount() {
		return count;
	}
	public void setCount(Long count) {
		this.count = count;
	}
	public Long getPageCount() {
		return pageCount;
	}
	public void setPageCount(Long pageCount) {
		this.pageCount = pageCount;
	}
}
