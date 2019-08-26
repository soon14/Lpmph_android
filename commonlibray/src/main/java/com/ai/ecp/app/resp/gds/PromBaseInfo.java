package com.ai.ecp.app.resp.gds;

import java.sql.Timestamp;

import com.ailk.butterfly.app.model.AppBody;
import com.ailk.butterfly.app.model.IBody;

public class PromBaseInfo extends AppBody{
	 private Long id;//促销id
	    
	    private Long siteId;//站点编码
	    
	    private String siteName;//站点名称

	    private Long groupId;//主题促销id
	    
	    private String groupName;//主题促销名称

	    private String promTypeCode;//促销类型编码
	    
	    private String promTypeName;//促销类型名称
	    
	    private String nameShort;//促销简称

	    private String ifShow;//是否展示

	    private String promClass;//促销类型 类别 10订单 20单品 30外围活动
	    
	    private String promClassName;//促销类型 类别名称

	    private String promTheme;//促销名称

	    private String promContent;//促销内容

	    private String promTypeShow;//促销展示内容

	    private Long priority;//优先级

	    private String status;//状态
	    
	    private String statusName;//状态名称

	    private Timestamp startTime;//生效开始时间

	    private Timestamp endTime;//生效截止时间

	    private Timestamp showStartTime;//展示开始时间

	    private Timestamp showEndTime;//展示截止时间

	    private Long shopId;//店铺编码

	    private String promUrl;//链接url

	    private String promImg;

	    private String joinRange;//参与范围 全场参与  部分参与

	    private String ifBlacklist;//是否黑名单参与 1黑名单参与

	    private Timestamp createTime;//创建时间

	    private Long createStaff;//创建人员

	    private Timestamp updateTime;//更新时间

	    private Long updateStaff;//更新人员

	    private String ifMatch;//是否搭配
	    
	    private String ifPlatQuery;//1 平台审核列表  空（null or ""）店铺查询列表
	    
	    private String ifComposit;//是否允许重叠
	    
	    private String keyWord;//搜索关键字
	    
	    private Integer tableIndex;//分表下标

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public Long getSiteId() {
			return siteId;
		}

		public void setSiteId(Long siteId) {
			this.siteId = siteId;
		}

		public String getSiteName() {
			return siteName;
		}

		public void setSiteName(String siteName) {
			this.siteName = siteName;
		}

		public Long getGroupId() {
			return groupId;
		}

		public void setGroupId(Long groupId) {
			this.groupId = groupId;
		}

		public String getGroupName() {
			return groupName;
		}

		public void setGroupName(String groupName) {
			this.groupName = groupName;
		}

		public String getPromTypeCode() {
			return promTypeCode;
		}

		public void setPromTypeCode(String promTypeCode) {
			this.promTypeCode = promTypeCode;
		}

		public String getPromTypeName() {
			return promTypeName;
		}

		public void setPromTypeName(String promTypeName) {
			this.promTypeName = promTypeName;
		}

		public String getNameShort() {
			return nameShort;
		}

		public void setNameShort(String nameShort) {
			this.nameShort = nameShort;
		}

		public String getIfShow() {
			return ifShow;
		}

		public void setIfShow(String ifShow) {
			this.ifShow = ifShow;
		}

		public String getPromClass() {
			return promClass;
		}

		public void setPromClass(String promClass) {
			this.promClass = promClass;
		}

		public String getPromClassName() {
			return promClassName;
		}

		public void setPromClassName(String promClassName) {
			this.promClassName = promClassName;
		}

		public String getPromTheme() {
			return promTheme;
		}

		public void setPromTheme(String promTheme) {
			this.promTheme = promTheme;
		}

		public String getPromContent() {
			return promContent;
		}

		public void setPromContent(String promContent) {
			this.promContent = promContent;
		}

		public String getPromTypeShow() {
			return promTypeShow;
		}

		public void setPromTypeShow(String promTypeShow) {
			this.promTypeShow = promTypeShow;
		}

		public Long getPriority() {
			return priority;
		}

		public void setPriority(Long priority) {
			this.priority = priority;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public String getStatusName() {
			return statusName;
		}

		public void setStatusName(String statusName) {
			this.statusName = statusName;
		}

		public Timestamp getStartTime() {
			return startTime;
		}

		public void setStartTime(Timestamp startTime) {
			this.startTime = startTime;
		}

		public Timestamp getEndTime() {
			return endTime;
		}

		public void setEndTime(Timestamp endTime) {
			this.endTime = endTime;
		}

		public Timestamp getShowStartTime() {
			return showStartTime;
		}

		public void setShowStartTime(Timestamp showStartTime) {
			this.showStartTime = showStartTime;
		}

		public Timestamp getShowEndTime() {
			return showEndTime;
		}

		public void setShowEndTime(Timestamp showEndTime) {
			this.showEndTime = showEndTime;
		}

		public Long getShopId() {
			return shopId;
		}

		public void setShopId(Long shopId) {
			this.shopId = shopId;
		}

		public String getPromUrl() {
			return promUrl;
		}

		public void setPromUrl(String promUrl) {
			this.promUrl = promUrl;
		}

		public String getPromImg() {
			return promImg;
		}

		public void setPromImg(String promImg) {
			this.promImg = promImg;
		}

		public String getJoinRange() {
			return joinRange;
		}

		public void setJoinRange(String joinRange) {
			this.joinRange = joinRange;
		}

		public String getIfBlacklist() {
			return ifBlacklist;
		}

		public void setIfBlacklist(String ifBlacklist) {
			this.ifBlacklist = ifBlacklist;
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

		public String getIfMatch() {
			return ifMatch;
		}

		public void setIfMatch(String ifMatch) {
			this.ifMatch = ifMatch;
		}

		public String getIfPlatQuery() {
			return ifPlatQuery;
		}

		public void setIfPlatQuery(String ifPlatQuery) {
			this.ifPlatQuery = ifPlatQuery;
		}

		public String getIfComposit() {
			return ifComposit;
		}

		public void setIfComposit(String ifComposit) {
			this.ifComposit = ifComposit;
		}

		public String getKeyWord() {
			return keyWord;
		}

		public void setKeyWord(String keyWord) {
			this.keyWord = keyWord;
		}

		public Integer getTableIndex() {
			return tableIndex;
		}

		public void setTableIndex(Integer tableIndex) {
			this.tableIndex = tableIndex;
		}
	    
}

