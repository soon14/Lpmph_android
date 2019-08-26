package com.ai.ecp.sys.dubbo.dto;

import java.sql.Timestamp;


public class MsgInsiteResDTO {
    private Long staffId;

    private Long msgInfoId;
    
    private String msgCode;
    
    private String msgName;

    private String msgContext;

    private String readFlag;

    private Timestamp recTime;

    private Long fromStaffId;
    
    private String msgType;

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    private static final long serialVersionUID = 1L;

    public String getMsgCode() {
        return msgCode;
    }

    public void setMsgCode(String msgCode) {
        this.msgCode = msgCode == null ? null : msgCode.trim();
    }

    public String getMsgContext() {
        return msgContext;
    }

    public void setMsgContext(String msgContext) {
        this.msgContext = msgContext == null ? null : msgContext.trim();
    }

    public String getReadFlag() {
        return readFlag;
    }

    public void setReadFlag(String readFlag) {
        this.readFlag = readFlag == null ? null : readFlag.trim();
    }

    public Timestamp getRecTime() {
        return recTime;
    }

    public void setRecTime(Timestamp recTime) {
        this.recTime = recTime;
    }

    public Long getFromStaffId() {
        return fromStaffId;
    }

    public void setFromStaffId(Long fromStaffId) {
        this.fromStaffId = fromStaffId;
    }

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public Long getMsgInfoId() {
        return msgInfoId;
    }

    public void setMsgInfoId(Long msgInfoId) {
        this.msgInfoId = msgInfoId;
    }

    public String getMsgName() {
		return msgName;
	}

	public void setMsgName(String msgName) {
		this.msgName = msgName;
	}

	@Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", staffId=").append(staffId);
        sb.append(", msgInfoId=").append(msgInfoId);
        sb.append(", msgCode=").append(msgCode);
        sb.append(", msgName=").append(msgName);
        sb.append(", msgContext=").append(msgContext);
        sb.append(", readFlag=").append(readFlag);
        sb.append(", recTime=").append(recTime);
        sb.append(", fromStaffId=").append(fromStaffId);
        sb.append("]");
        return sb.toString();
    }
}