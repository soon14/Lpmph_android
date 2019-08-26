package com.ai.ecp.goods.dubbo.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;


public class GdsPropRespDTO implements Serializable{

	/**
	 * serialVersionUID:TODO(用一句话描述这个变量表示什么).
	 * 
	 * @since JDK 1.6
	 */
	private static final long serialVersionUID = 1019664979625833013L;

	private Long id;

	/**
	 * 属性名称
	 */
	private String propName;

	/**
	 * 属性别名
	 */
	private String propSname;
	
	/**
	 * 属性类型
	 */
	private String propType;

	/**
	 * 属性值类型
	 */
	private String propValueType;

	/**
	 * 属性值输入类型
	 */
	private String propInputType;

	/**
	 * 属性校验规则
	 */
	private String propInputRule;

	/**
	 * 是否必填
	 */
	private String ifHaveto;

	/**
	 * 是否基本属性
	 */
	private String ifBasic;

	/**
	 * 是否作为搜索属性
	 */
	private String ifSearch;

	/**
	 * 属性描述
	 */
	private String propDesc;

	/**
	 * 排序
	 */
	private Integer sortNo;

	/**
	 * 状态
	 */
	private String status;

	/**
	 * 创建时间
	 */
	private Timestamp createTime;

	/**
	 * 创建人
	 */
	private Long createStaff;
	
	/**
	 * 更新时间
	 */
	private Timestamp updateTime;

	/**
	 * 更新时间
	 */
	private Long updateStaff;
	
	/**
	 * 商品状态
	 */
	private String gdsStatus;
	
	private String ifAbleEdit;

	/**
	 * 属性值
	 */
	private List<GdsPropValueRespDTO> values;

	private boolean nameIsChecked;

	public boolean isNameIsChecked() {
		return nameIsChecked;
	}

	public void setNameIsChecked(boolean nameIsChecked) {
		this.nameIsChecked = nameIsChecked;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPropName() {
		return propName;
	}

	public void setPropName(String propName) {
		this.propName = propName == null ? null : propName.trim();
	}

	public String getPropSname() {
		return propSname;
	}

	public void setPropSname(String propSname) {
		this.propSname = propSname == null ? null : propSname.trim();
	}

	public String getPropValueType() {
		return propValueType;
	}

	public void setPropValueType(String propValueType) {
		this.propValueType = propValueType == null ? null : propValueType
				.trim();
	}

	public String getPropInputType() {
		return propInputType;
	}

	public void setPropInputType(String propInputType) {
		this.propInputType = propInputType;
	}

	public String getPropInputRule() {
		return propInputRule;
	}

	public void setPropInputRule(String propInputRule) {
		this.propInputRule = propInputRule;
	}

	public String getPropType() {
		return propType;
	}

	public void setPropType(String propType) {
		this.propType = propType == null ? null : propType.trim();
	}

	public String getIfHaveto() {
		return ifHaveto;
	}

	public void setIfHaveto(String ifHaveto) {
		this.ifHaveto = ifHaveto;
	}

	public String getPropDesc() {
		return propDesc;
	}

	public void setPropDesc(String propDesc) {
		this.propDesc = propDesc == null ? null : propDesc.trim();
	}

	public Integer getSortNo() {
		return sortNo;
	}

	public void setSortNo(Integer sortNo) {
		this.sortNo = sortNo;
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

	public List<GdsPropValueRespDTO> getValues() {
		return values;
	}

	public void setValues(List<GdsPropValueRespDTO> values) {
		this.values = values;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName());
		sb.append(" [");
		sb.append("Hash = ").append(hashCode());
		sb.append(", id=").append(id);
		sb.append(", propName=").append(propName);
		sb.append(", propSname=").append(propSname);
		sb.append(", propValueType=").append(propValueType);
		sb.append(", propType=").append(propType);
		sb.append(", propDesc=").append(propDesc);
		sb.append(", sortNo=").append(sortNo);
		sb.append(", status=").append(status);
		sb.append(", createTime=").append(createTime);
		sb.append(", createStaff=").append(createStaff);
		sb.append(", updateTime=").append(updateTime);
		sb.append(", updateStaff=").append(updateStaff);
		sb.append("]");
		return sb.toString();
	}

	public String getIfBasic() {
		return ifBasic;
	}

	public void setIfBasic(String ifBasic) {
		this.ifBasic = ifBasic;
	}

	public String getIfSearch() {
		return ifSearch;
	}

	public void setIfSearch(String ifSearch) {
		this.ifSearch = ifSearch;
	}

	public String getGdsStatus() {
		return gdsStatus;
	}

	public void setGdsStatus(String gdsStatus) {
		this.gdsStatus = gdsStatus;
	}

	public String getIfAbleEdit() {
		return ifAbleEdit;
	}

	public void setIfAbleEdit(String ifAbleEdit) {
		this.ifAbleEdit = ifAbleEdit == null ? null : ifAbleEdit.trim();
	}
	
	
}