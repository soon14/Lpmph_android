package com.ai.ecp.app.resp;


import java.sql.Date;
import java.util.List;

import com.ailk.butterfly.app.model.AppBody;

public class Prom007Resp extends AppBody{

	private List<KillGdsInfoRespVO> killGdsList;

	private String ifStart;

	private Date startTime;

	private Date endTime;

	private Date nowTime;

	public List<KillGdsInfoRespVO> getKillGdsList() {
		return killGdsList;
	}
	public void setKillGdsList(List<KillGdsInfoRespVO> killGdsList) {
		this.killGdsList = killGdsList;
	}
	public String getIfStart() {
		return ifStart;
	}
	public Date getStartTime() {
		return startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setIfStart(String ifStart) {
		this.ifStart = ifStart;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Date getNowTime() {
		return nowTime;
	}
	public void setNowTime(Date nowTime) {
		this.nowTime = nowTime;
	}
	
}
