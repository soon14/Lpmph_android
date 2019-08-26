package com.ai.ecp.app.resp.gds;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.ailk.butterfly.app.model.AppBody;

public class GdsEvalRespBaseInfo extends AppBody{


    private Long id;

    private Long staffId;

    private String staffName;

    private Timestamp evaluationTime;

    private Timestamp buyTime;

    private Long gdsId;

    private String gdsName;

    private Long skuId;

    private Long shopId;

    private String orderId;

    private String orderSubId;

    private Short score;

    private Short correspondencyScore;

    private Short serviceattitudeScore;

    private Short deliveryspeedScore;

    private String content;

    private String isAnonymous;

    private Short isReply;

    private String labelNames;

    private String status;

    private Timestamp createTime;

    private Long createStaff;

    private Timestamp updateTime;

    private Long updateStaff;
    
    private Timestamp beginTime;
    
    private Timestamp endTime;
    // 评价详情.
    private String detail;
    
    // 标签中文名称.
    private String labelName;
    // 评价回复数.
    private Long replyCount;
    
    private int intScore;
    /**
     * 会员等级，add by gongxq 
     */
    private String staffLevelCode;
    /**
     * 会员等级名称 add by gongxq 
     */
    private String staffLevelName;
    // 头像url
    private String custPic;
    
    private List<GdsEvalReplyRespBaseInfo> evalReplyRespDTOs = new ArrayList<GdsEvalReplyRespBaseInfo>();


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public Timestamp getEvaluationTime() {
        return evaluationTime;
    }

    public void setEvaluationTime(Timestamp evaluationTime) {
        this.evaluationTime = evaluationTime;
    }

    public Long getGdsId() {
        return gdsId;
    }

    public void setGdsId(Long gdsId) {
        this.gdsId = gdsId;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public Long  getShopId() {
        return shopId;
    }

    public void setShopId(Long  shopId) {
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

    public Short getCorrespondencyScore() {
        return correspondencyScore;
    }

    public void setCorrespondencyScore(Short correspondencyScore) {
        this.correspondencyScore = correspondencyScore;
    }

    public Short getServiceattitudeScore() {
        return serviceattitudeScore;
    }

    public void setServiceattitudeScore(Short serviceattitudeScore) {
        this.serviceattitudeScore = serviceattitudeScore;
    }

    public Short getDeliveryspeedScore() {
        return deliveryspeedScore;
    }

    public void setDeliveryspeedScore(Short deliveryspeedScore) {
        this.deliveryspeedScore = deliveryspeedScore;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getIsAnonymous() {
        return isAnonymous;
    }

    public void setIsAnonymous(String isAnonymous) {
        this.isAnonymous = isAnonymous == null ? null : isAnonymous.trim();
    }

    public Short getIsReply() {
        return isReply;
    }

    public void setIsReply(Short isReply) {
        this.isReply = isReply;
    }

    public String getLabelNames() {
        return labelNames;
    }

    public void setLabelNames(String labelNames) {
        this.labelNames = labelNames;
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
    
    

    public Timestamp getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Timestamp beginTime) {
        this.beginTime = beginTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }
    
    public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public Short getScore() {
		return score;
	}

	public void setScore(Short score) {
		this.score = score;
	}

	public String getLabelName() {
		return labelName;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}

	public Long getReplyCount() {
		return replyCount;
	}

	public void setReplyCount(Long replyCount) {
		this.replyCount = replyCount;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public Timestamp getBuyTime() {
		return buyTime;
	}

	public void setBuyTime(Timestamp buyTime) {
		this.buyTime = buyTime;
	}

	public String getGdsName() {
		return gdsName;
	}

	public void setGdsName(String gdsName) {
		this.gdsName = gdsName;
	}

	public int getIntScore() {
		return null != score ? Integer.valueOf(score) : 0;
	}

    public String getStaffLevelCode() {
        return staffLevelCode;
    }

    public void setStaffLevelCode(String staffLevelCode) {
        this.staffLevelCode = staffLevelCode;
    }

    public String getStaffLevelName() {
        return staffLevelName;
    }

    public void setStaffLevelName(String staffLevelName) {
        this.staffLevelName = staffLevelName;
    }

    public List<GdsEvalReplyRespBaseInfo> getEvalReplyRespDTOs() {
        return evalReplyRespDTOs;
    }

    public void setEvalReplyRespDTOs(List<GdsEvalReplyRespBaseInfo> evalReplyRespDTOs) {
        this.evalReplyRespDTOs = evalReplyRespDTOs;
    }

    public String getCustPic() {
        return custPic;
    }

    public void setCustPic(String custPic) {
        this.custPic = custPic;
    }
}

