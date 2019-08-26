package com.ai.ecp.app.resp.ord;

import java.io.Serializable;
import java.util.Map;

/**
 * 类注释:
 * 项目名:pmph_android
 * 包名:com.ai.ecp.app.resp.ord
 * 作者: Chrizz
 * 时间: 2016/4/11 15:15
 */
public class OrderMoneyMap implements Serializable {

    private Map<Long, Long> orderMoneyMap;

    public Map<Long, Long> getOrderMoneyMap() {
        return orderMoneyMap;
    }

    public void setOrderMoneyMap(Map<Long, Long> orderMoneyMap) {
        this.orderMoneyMap = orderMoneyMap;
    }
}
