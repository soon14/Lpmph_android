package com.ai.ecp.app.req;

import com.ailk.butterfly.app.model.AppBody;

public class Ord011Req extends AppBody {
    

    /** 
     * orderId:订单号. 
     */
    private String orderId;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    
}

