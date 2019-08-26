package com.ai.ecp.app.resp;

import java.util.List;

import com.ai.ecp.app.resp.gds.CategoryTree;
import com.ailk.butterfly.app.model.AppBody;

public class Gds027Resp extends AppBody {
	private List<CategoryTree> gdsCategoryList;

	public List<CategoryTree> getGdsCategoryList() {

		return gdsCategoryList;
	}

	public void setGdsCategoryList(List<CategoryTree> gdsCategoryList) {
		this.gdsCategoryList = gdsCategoryList;
	}
}

