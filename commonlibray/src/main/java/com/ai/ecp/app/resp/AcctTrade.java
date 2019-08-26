package com.ai.ecp.app.resp;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by Administrator on 2016/4/9.
 */
public class AcctTrade implements Serializable {

    private Long tradeMoney;//交易金额（分）

    private String tradeTypeName;//交易类型名称

    private String debitCredit;//交易方向：1：收入、2：支出

    private String orderId;//订单编码

    private Timestamp createTime;

    public Long getTradeMoney() {
        return tradeMoney;
    }

    public void setTradeMoney(Long tradeMoney) {
        this.tradeMoney = tradeMoney;
    }

    public String getTradeTypeName() {
        return tradeTypeName;
    }

    public void setTradeTypeName(String tradeTypeName) {
        this.tradeTypeName = tradeTypeName;
    }

    public String getDebitCredit() {
        return debitCredit;
    }

    public void setDebitCredit(String debitCredit) {
        this.debitCredit = debitCredit;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }
}
