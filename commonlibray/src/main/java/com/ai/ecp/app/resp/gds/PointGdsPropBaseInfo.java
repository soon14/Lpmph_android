package com.ai.ecp.app.resp.gds;

import java.util.List;

import com.ailk.butterfly.app.model.AppBody;
import com.ailk.butterfly.app.model.IBody;

public class PointGdsPropBaseInfo  extends AppBody{

	private Long id;

	/**
	 * 属性名称
	 */
	private String propName;

	
	/**
	 * 属性类型
	 */
	private String propType;

	/**
	 * 属性值类型
	 */
	private String propValueType;

	/**
	 * 属性值
	 */
	private List<PointGdsPropValueBaseInfo> values;

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
		this.propName = propName;
	}

	public String getPropType() {
		return propType;
	}

	public void setPropType(String propType) {
		this.propType = propType;
	}

	public String getPropValueType() {
		return propValueType;
	}

	public void setPropValueType(String propValueType) {
		this.propValueType = propValueType;
	}

	public List<PointGdsPropValueBaseInfo> getValues() {
		return values;
	}

	public void setValues(List<PointGdsPropValueBaseInfo> values) {
		this.values = values;
	}
	
}

