package com.ai.ecp.app.resp;

import java.util.List;

import com.ailk.butterfly.app.model.AppBody;

public class Pay102Resp extends AppBody {
    /** 
     * orderId:订单号. 
     * @since JDK 1.6 
     */ 
    private String orderId;
    
    /** 
     * pay00101Resps:支付通道相关信息. 
     * @since JDK 1.6 
     */ 
    List<PayWayResponse> wayList;
    
    /** 
     * hour:支付时间. 
     * @since JDK 1.6 
     */ 
    private Long hour;

    private long mergeTotalRealMoney;


    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }



    public List<PayWayResponse> getWayList() {
        return wayList;
    }

    public void setWayList(List<PayWayResponse> wayList) {
        this.wayList = wayList;
    }

    public Long getHour() {
        return hour;
    }

    public void setHour(Long hour) {
        this.hour = hour;
    }

    public long getMergeTotalRealMoney() {
        return mergeTotalRealMoney;
    }

    public void setMergeTotalRealMoney(long mergeTotalRealMoney) {
        this.mergeTotalRealMoney = mergeTotalRealMoney;
    }
}

