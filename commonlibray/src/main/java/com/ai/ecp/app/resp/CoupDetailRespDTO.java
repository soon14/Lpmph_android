package com.ai.ecp.app.resp;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 类注释:
 * 项目名:PMPH
 * 包名:com.ai.ecp.app.resp
 * 作者: Chrizz
 * 时间: 2016/3/25 22:05
 */
public class CoupDetailRespDTO implements Serializable{

    //优惠券明细编码
    private Long id;
    //优惠券编码或优惠码(对外展示,使用)
    private String coupNo;
    //优惠券名称
    private String coupName;

    private Long siteId;

    private Long staffId;
    //优惠券信息ID(优惠券小类ID)
    private Long coupId;

    //展示优惠券使用条件
    private String conditionsShow;

    private Long coupTypeId;
    //优惠券面额
    private Long coupValue;
    //优惠券面额展示 例如满100 券面额50 展示100-50
    private String coupValueShow;

    private String useRuleCode;

    private String gainRuleCode;
    //优惠券来源,领取的方式  10:主动领取, 20被动领取
    private String coupSource;

    private Timestamp useTime;

    private Long shopId;

    private Long batchId;

    private String remark;

    private String ifUse;

    private String status;
    //生效时间
    private Timestamp activeTime;
    //失效时间
    private Timestamp inactiveTime;

    private Timestamp createTime;

    private Long createStaff;

    private Timestamp updateTime;

    private Long updateStaff;

    // 1:该优惠券为免邮优惠券
    private String noExpress;

    private static final long serialVersionUID = 1L;




    public String getCoupValueShow() {
        return coupValueShow;
    }

    public void setCoupValueShow(String coupValueShow) {
        this.coupValueShow = coupValueShow;
    }

    public String getConditionsShow() {
        return conditionsShow;
    }

    public void setConditionsShow(String conditionsShow) {
        this.conditionsShow = conditionsShow;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCoupNo() {
        return coupNo;
    }

    public void setCoupNo(String coupNo) {
        this.coupNo = coupNo == null ? null : coupNo.trim();
    }

    public Long getSiteId() {
        return siteId;
    }

    public void setSiteId(Long siteId) {
        this.siteId = siteId;
    }

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public Long getCoupId() {
        return coupId;
    }

    public void setCoupId(Long coupId) {
        this.coupId = coupId;
    }

    public Long getCoupTypeId() {
        return coupTypeId;
    }

    public void setCoupTypeId(Long coupTypeId) {
        this.coupTypeId = coupTypeId;
    }

    public Long getCoupValue() {
        return coupValue;
    }

    public void setCoupValue(Long coupValue) {
        this.coupValue = coupValue;
    }

    public String getUseRuleCode() {
        return useRuleCode;
    }

    public void setUseRuleCode(String useRuleCode) {
        this.useRuleCode = useRuleCode == null ? null : useRuleCode.trim();
    }

    public String getGainRuleCode() {
        return gainRuleCode;
    }

    public void setGainRuleCode(String gainRuleCode) {
        this.gainRuleCode = gainRuleCode == null ? null : gainRuleCode.trim();
    }

    public String getCoupSource() {
        return coupSource;
    }

    public void setCoupSource(String coupSource) {
        this.coupSource = coupSource == null ? null : coupSource.trim();
    }

    public Timestamp getUseTime() {
        return useTime;
    }

    public void setUseTime(Timestamp useTime) {
        this.useTime = useTime;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Long getBatchId() {
        return batchId;
    }

    public void setBatchId(Long batchId) {
        this.batchId = batchId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getIfUse() {
        return ifUse;
    }

    public void setIfUse(String ifUse) {
        this.ifUse = ifUse == null ? null : ifUse.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Timestamp getActiveTime() {
        return activeTime;
    }

    public void setActiveTime(Timestamp activeTime) {
        this.activeTime = activeTime;
    }

    public Timestamp getInactiveTime() {
        return inactiveTime;
    }

    public void setInactiveTime(Timestamp inactiveTime) {
        this.inactiveTime = inactiveTime;
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

    public String getCoupName() {
        return coupName;
    }

    public void setCoupName(String coupName) {
        this.coupName = coupName == null ? null : coupName.trim();
    }

    public String getNoExpress() {
        return noExpress;
    }

    public void setNoExpress(String noExpress) {
        this.noExpress = noExpress;
    }
}
