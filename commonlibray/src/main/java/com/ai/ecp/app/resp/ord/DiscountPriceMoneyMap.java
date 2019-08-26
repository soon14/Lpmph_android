package com.ai.ecp.app.resp.ord;

import java.io.Serializable;
import java.util.Map;

/**
 * 类注释:
 * 项目名:PMPH
 * 包名:com.ai.ecp.app.resp.ord
 * 作者: Chrizz
 * 时间: 2016/3/28 16:59
 */
public class DiscountPriceMoneyMap implements Serializable {

    private Map<Long, Long> discountPriceMoneyMap;

    public Map<Long, Long> getDiscountPriceMoneyMap() {
        return discountPriceMoneyMap;
    }

    public void setDiscountPriceMoneyMap(Map<Long, Long> discountPriceMoneyMap) {
        this.discountPriceMoneyMap = discountPriceMoneyMap;
    }
}
