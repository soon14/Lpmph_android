package com.ai.ecp.app.resp;

import java.util.List;

import com.ailk.butterfly.app.model.AppBody;

public class Pay101Resp extends AppBody {
    
    /** 
     * orderId:订单号. 
     * @since JDK 1.6 
     */ 
    private String orderId;
    /** 
     * realMoney:支付金额. 
     * @since JDK 1.6 
     */ 
    private Long realMoney;
    
    /** 
     * pay00101Resps:支付通道相关信息. 
     * @since JDK 1.6 
     */ 
    List<PayWayResponse> wayList;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Long getRealMoney() {
        return realMoney;
    }

    public void setRealMoney(Long realMoney) {
        this.realMoney = realMoney;
    }

    public List<PayWayResponse> getWayList() {
        return wayList;
    }

    public void setWayList(List<PayWayResponse> wayList) {
        this.wayList = wayList;
    }

    
}

