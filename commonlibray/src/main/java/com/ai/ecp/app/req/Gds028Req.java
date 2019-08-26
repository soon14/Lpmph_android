package com.ai.ecp.app.req;


import com.ailk.butterfly.app.model.AppBody;

public class Gds028Req extends AppBody {
	private String catgCode;
	
	private String catgName;

    private Short catgLevel;
    
    private String catgType;

	public String getCatgCode() {
		return catgCode;
	}

	public void setCatgCode(String catgCode) {
		this.catgCode = catgCode;
	}

	public String getCatgName() {
		return catgName;
	}

	public void setCatgName(String catgName) {
		this.catgName = catgName;
	}

	public Short getCatgLevel() {
		return catgLevel;
	}

	public void setCatgLevel(Short catgLevel) {
		this.catgLevel = catgLevel;
	}

	public String getCatgType() {
		return catgType;
	}

	public void setCatgType(String catgType) {
		this.catgType = catgType;
	}

}

