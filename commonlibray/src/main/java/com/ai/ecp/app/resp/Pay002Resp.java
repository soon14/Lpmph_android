package com.ai.ecp.app.resp;

import com.ailk.butterfly.app.model.AppBody;

import java.util.List;

/**
 * 类注释:
 * 项目名:pmph_android
 * 包名:com.ai.ecp.app.resp
 * 作者: Chrizz
 * 时间: 2016/4/8 23:03
 */
public class Pay002Resp extends AppBody {

    private String orderId;
    private Long hour;
    private List<PayWayResponse> wayList;
    private long mergeTotalRealMoney;


    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Long getHour() {
        return hour;
    }

    public void setHour(Long hour) {
        this.hour = hour;
    }

    public List<PayWayResponse> getWayList() {
        return wayList;
    }

    public void setWayList(List<PayWayResponse> wayList) {
        this.wayList = wayList;
    }

    public long getMergeTotalRealMoney() {
        return mergeTotalRealMoney;
    }

    public void setMergeTotalRealMoney(long mergeTotalRealMoney) {
        this.mergeTotalRealMoney = mergeTotalRealMoney;
    }
}
