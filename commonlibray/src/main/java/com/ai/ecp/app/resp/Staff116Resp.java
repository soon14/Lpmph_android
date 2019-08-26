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

import java.sql.Timestamp;

/**
 * Title: ECP <br>
 * Project Name:ecp-web-mall <br>
 * Description: 查询店铺信息出参<br>
 * Date:2016-2-22下午6:53:17  <br>
 * Copyright (c) 2016 asia All Rights Reserved <br>
 * 
 * @author huangxl5
 * @version  
 * @since JDK 1.6 
 */
public class Staff116Resp extends AppBody {
    
	private Long id;

    private Long companyId;

    private String shopName;

    private String shopFullName;

    private String shopGrade;

    private String shopDesc;

    private String shopStatus;

    private String shopDealType;

    private Long cautionMoney;

    private String microMessageExt;

    private String hotShowSupported;

    private Short hotShowSort;

    private String logoPath;
    
    private String logoPathURL;

    private String linkPerson;

    private String linkMobilephone;

    private String linkEmail;

    private String isUseVip;

    private String offlineSupported;

    private String onlineSupported;

    private String isWhiteList;

    private String isBlackList;

    private Timestamp invalidDate;

    private String invalidStaff;

    private Long checkStaff;

    private Timestamp checkDate;

    private String checkRemark;

    private Timestamp createTime;

    private Long createStaff;

    private Timestamp updateTime;

    private Long updateStaff;
    
    private String companyName;
    
    private long collectCount;//收藏人气
    
    private Long siteId;
    
    private Timestamp collTime;//收藏时间
    
    private String collectFlag;//是否收藏：1、是； 0、否

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getShopFullName() {
		return shopFullName;
	}

	public void setShopFullName(String shopFullName) {
		this.shopFullName = shopFullName;
	}

	public String getShopGrade() {
		return shopGrade;
	}

	public void setShopGrade(String shopGrade) {
		this.shopGrade = shopGrade;
	}

	public String getShopDesc() {
		return shopDesc;
	}

	public void setShopDesc(String shopDesc) {
		this.shopDesc = shopDesc;
	}

	public String getShopStatus() {
		return shopStatus;
	}

	public void setShopStatus(String shopStatus) {
		this.shopStatus = shopStatus;
	}

	public String getShopDealType() {
		return shopDealType;
	}

	public void setShopDealType(String shopDealType) {
		this.shopDealType = shopDealType;
	}

	public Long getCautionMoney() {
		return cautionMoney;
	}

	public void setCautionMoney(Long cautionMoney) {
		this.cautionMoney = cautionMoney;
	}

	public String getMicroMessageExt() {
		return microMessageExt;
	}

	public void setMicroMessageExt(String microMessageExt) {
		this.microMessageExt = microMessageExt;
	}

	public String getHotShowSupported() {
		return hotShowSupported;
	}

	public void setHotShowSupported(String hotShowSupported) {
		this.hotShowSupported = hotShowSupported;
	}

	public Short getHotShowSort() {
		return hotShowSort;
	}

	public void setHotShowSort(Short hotShowSort) {
		this.hotShowSort = hotShowSort;
	}

	public String getLogoPath() {
		return logoPath;
	}

	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}

	public String getLogoPathURL() {
		return logoPathURL;
	}

	public void setLogoPathURL(String logoPathURL) {
		this.logoPathURL = logoPathURL;
	}

	public String getLinkPerson() {
		return linkPerson;
	}

	public void setLinkPerson(String linkPerson) {
		this.linkPerson = linkPerson;
	}

	public String getLinkMobilephone() {
		return linkMobilephone;
	}

	public void setLinkMobilephone(String linkMobilephone) {
		this.linkMobilephone = linkMobilephone;
	}

	public String getLinkEmail() {
		return linkEmail;
	}

	public void setLinkEmail(String linkEmail) {
		this.linkEmail = linkEmail;
	}

	public String getIsUseVip() {
		return isUseVip;
	}

	public void setIsUseVip(String isUseVip) {
		this.isUseVip = isUseVip;
	}

	public String getOfflineSupported() {
		return offlineSupported;
	}

	public void setOfflineSupported(String offlineSupported) {
		this.offlineSupported = offlineSupported;
	}

	public String getOnlineSupported() {
		return onlineSupported;
	}

	public void setOnlineSupported(String onlineSupported) {
		this.onlineSupported = onlineSupported;
	}

	public String getIsWhiteList() {
		return isWhiteList;
	}

	public void setIsWhiteList(String isWhiteList) {
		this.isWhiteList = isWhiteList;
	}

	public String getIsBlackList() {
		return isBlackList;
	}

	public void setIsBlackList(String isBlackList) {
		this.isBlackList = isBlackList;
	}

	public Timestamp getInvalidDate() {
		return invalidDate;
	}

	public void setInvalidDate(Timestamp invalidDate) {
		this.invalidDate = invalidDate;
	}

	public String getInvalidStaff() {
		return invalidStaff;
	}

	public void setInvalidStaff(String invalidStaff) {
		this.invalidStaff = invalidStaff;
	}

	public Long getCheckStaff() {
		return checkStaff;
	}

	public void setCheckStaff(Long checkStaff) {
		this.checkStaff = checkStaff;
	}

	public Timestamp getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(Timestamp checkDate) {
		this.checkDate = checkDate;
	}

	public String getCheckRemark() {
		return checkRemark;
	}

	public void setCheckRemark(String checkRemark) {
		this.checkRemark = checkRemark;
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

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public long getCollectCount() {
		return collectCount;
	}

	public void setCollectCount(long collectCount) {
		this.collectCount = collectCount;
	}

	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}

	public Timestamp getCollTime() {
		return collTime;
	}

	public void setCollTime(Timestamp collTime) {
		this.collTime = collTime;
	}

	public String getCollectFlag() {
		return collectFlag;
	}

	public void setCollectFlag(String collectFlag) {
		this.collectFlag = collectFlag;
	}
    
}

