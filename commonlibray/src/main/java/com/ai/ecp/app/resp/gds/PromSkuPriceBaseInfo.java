package com.ai.ecp.app.resp.gds;

import java.math.BigDecimal;

import com.ailk.butterfly.app.model.AppBody;
import com.ailk.butterfly.app.model.IBody;

public class PromSkuPriceBaseInfo  extends AppBody{

    private BigDecimal discountPrice = BigDecimal.ZERO;// 单价优惠 指优惠了多少单价
    private BigDecimal discountMoney = BigDecimal.ZERO;// 优惠金额 指优惠了多少金额
    private BigDecimal discountPriceMoney 	= BigDecimal.ZERO;// 除单价外的优惠金额 指优惠了多少金额
    private BigDecimal discountAmount 		= BigDecimal.ZERO;// 优惠数量 指优惠了多少数量
    private BigDecimal discountFinalPrice 	= BigDecimal.ZERO;//最终优惠后的价格
    private BigDecimal discountCaclPrice 	= BigDecimal.ZERO;//促销优惠计算标准价：buyPrice、basePrice

    public BigDecimal getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(BigDecimal discountPrice) {
        this.discountPrice = discountPrice;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    public BigDecimal getDiscountMoney() {
        return discountMoney;
    }

    public void setDiscountMoney(BigDecimal discountMoney) {
        this.discountMoney = discountMoney;
    }

    public BigDecimal getDiscountPriceMoney() {
        return discountPriceMoney;
    }

    public void setDiscountPriceMoney(BigDecimal discountPriceMoney) {
        this.discountPriceMoney = discountPriceMoney;
    }

	public BigDecimal getDiscountFinalPrice() {
		return discountFinalPrice;
	}

	public void setDiscountFinalPrice(BigDecimal discountFinalPrice) {
		this.discountFinalPrice = discountFinalPrice;
	}

	public BigDecimal getDiscountCaclPrice() {
		return discountCaclPrice;
	}

	public void setDiscountCaclPrice(BigDecimal discountCaclPrice) {
		this.discountCaclPrice = discountCaclPrice;
	}

}

