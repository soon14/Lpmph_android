package com.ai.ecp.app.req;

import com.ailk.butterfly.app.model.AppBody;

public class Ord022Req extends AppBody{

	private String spCode;
	private String spLang;
	private String spValue;
	private int spOrder;

	public String getSpCode() {
		return spCode;
	}

	public void setSpCode(String spCode) {
		this.spCode = spCode;
	}

	public String getSpLang() {
		return spLang;
	}

	public void setSpLang(String spLang) {
		this.spLang = spLang;
	}

	public String getSpValue() {
		return spValue;
	}

	public void setSpValue(String spValue) {
		this.spValue = spValue;
	}

	public int getSpOrder() {
		return spOrder;
	}

	public void setSpOrder(int spOrder) {
		this.spOrder = spOrder;
	}
}
