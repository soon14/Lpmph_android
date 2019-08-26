package com.ai.ecp.app.resp;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.ailk.butterfly.app.model.AppBody;

public class Gds018Resp extends AppBody {
	  public List<String> getPromTypes() {
		return promTypes;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public BigDecimal getGuidePrice() {
		return guidePrice;
	}

	public void setPromTypes(List<String> promTypes) {
		this.promTypes = promTypes;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public void setGuidePrice(BigDecimal guidePrice) {
		this.guidePrice = guidePrice;
	}

	//促销列表信息
    private List<String> promTypes = new ArrayList<String>();
    //第一条促销信息对应的价格
    private BigDecimal price;

    //经过促销那边计算出来的指导价（划横线的价格）
    private BigDecimal guidePrice;
	
}

