package com.ai.ecp.app.req;

import com.ailk.butterfly.app.model.AppBody;

/**
 * 类注释:
 * 项目名:pmph_android
 * 包名:com.ai.ecp.app
 * 作者: Chrizz
 * 时间: 2016/5/11 20:37
 */
public class Ord108Req extends AppBody {

    /**
     * orderId:订单号.
     * @since JDK 1.6
     */
    private String orderId;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
