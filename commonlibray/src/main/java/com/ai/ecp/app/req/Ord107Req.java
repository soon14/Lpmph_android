package com.ai.ecp.app.req;

import com.ailk.butterfly.app.model.AppBody;

public class Ord107Req extends AppBody {
    
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

