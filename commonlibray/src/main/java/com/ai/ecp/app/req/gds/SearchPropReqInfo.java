package com.ai.ecp.app.req.gds;

import java.util.List;

import com.ailk.butterfly.app.model.AppBody;

public class SearchPropReqInfo extends AppBody{

	
	private String propertyId;
	
	private List<String> propertyValues;

	public String getPropertyId() {
		return propertyId;
	}

	public void setPropertyId(String propertyId) {
		this.propertyId = propertyId;
	}

	public List<String> getPropertyValues() {
		return propertyValues;
	}

	public void setPropertyValues(List<String> propertyValues) {
		this.propertyValues = propertyValues;
	}
}

