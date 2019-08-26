package com.ai.ecp.app.resp.gds;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.ailk.butterfly.app.model.AppBody;

public class GdsEvalReplyRespBaseInfo extends AppBody{
    /** 
     * serialVersionUID:TODO(用一句话描述这个变量表示什么). 
     * @since JDK 1.6 
     */ 
    private static final long serialVersionUID = -6599507846353717721L;

    private Long id;

    private Long evalId;

    private Long replyId;

    private String content;

    private Long gdsId;

    private Long shopId;

    private String orderId;

    private String orderSubId;

    private Long staffId;

    private Timestamp replayTime;

    private String status;

    private Timestamp createTime;

    private Long createStaff;

    private Timestamp updateTime;

    private Long updateStaff;
    
    private String replyType;
    
    private String staffName;
    /**
     * 会员等级.
     */
    private String staffLevelCode;
    
    private String detail;

    private String custPic;
    
    private List<GdsEvalReplyRespBaseInfo> subReplys;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEvalId() {
        return evalId;
    }

    public void setEvalId(Long evalId) {
        this.evalId = evalId;
    }

    public Long getReplyId() {
        return replyId;
    }

    public void setReplyId(Long replyId) {
        this.replyId = replyId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Long getGdsId() {
        return gdsId;
    }

    public void setGdsId(Long gdsId) {
        this.gdsId = gdsId;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public String getOrderSubId() {
        return orderSubId;
    }

    public void setOrderSubId(String orderSubId) {
        this.orderSubId = orderSubId == null ? null : orderSubId.trim();
    }

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public Timestamp getReplayTime() {
        return replayTime;
    }

    public void setReplayTime(Timestamp replayTime) {
        this.replayTime = replayTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Long getCreateStaff() {
        return createStaff;
    }

    public void setCreateStaff(Long createStaff) {
        this.createStaff = createStaff;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public Long getUpdateStaff() {
        return updateStaff;
    }

    public void setUpdateStaff(Long updateStaff) {
        this.updateStaff = updateStaff;
    }

    public List<GdsEvalReplyRespBaseInfo> getSubReplys() {
        return subReplys;
    }

    public void setSubReplys(List<GdsEvalReplyRespBaseInfo> subReplys) {
        this.subReplys = subReplys;
    }
    
    public void setSubReply(GdsEvalReplyRespBaseInfo subReply) {
        if(null != subReply){
           if(null == subReplys){
               subReplys = new ArrayList<>();
           }
           subReplys.add(subReply);
        }
    }
    

	public String getReplyType() {
		return replyType;
	}

	public void setReplyType(String replyType) {
		this.replyType = replyType;
	}
	
	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}
	

	public String getStaffLevelCode() {
		return staffLevelCode;
	}

	public void setStaffLevelCode(String staffLevelCode) {
		this.staffLevelCode = staffLevelCode;
	}

    public String getCustPic() {
        return custPic;
    }

    public void setCustPic(String custPic) {
        this.custPic = custPic;
    }
}

