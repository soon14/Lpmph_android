package com.ai.ecp.app.req;

import java.util.Date;

import com.ailk.butterfly.app.model.AppBody;

/**
 *
 * Title: ECP <br>
 * Project Name:ecp-web-im <br>
 * Description: <br>
 * Date:2017年1月11日下午4:54:26  <br>
 * Copyright (c) 2017 asia All Rights Reserved <br>
 *
 * @author linby
 * @version
 * @since JDK 1.7
 */
public class IM004Req extends AppBody {
	private String from; //消息发送人
	private Long shopId; //店铺id
	private String userCode; //用户OF账号
	private String csaCode; //客服OF账号
	private String sessionId; // 会话id
	private String messageType; //消息类型
	private Date beginDate; //查询开始时间
	private Date endDate; //查询结束时间
	private Integer pageNo; //分页页码
	private Integer pageSize; //分页大小

	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getCsaCode() {
		return csaCode;
	}
	public void setCsaCode(String csaCode) {
		this.csaCode = csaCode;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getMessageType() {
		return messageType;
	}
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Integer getPageNo() {
		return pageNo;
	}
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Long getShopId() {
		return shopId;
	}
	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

}
