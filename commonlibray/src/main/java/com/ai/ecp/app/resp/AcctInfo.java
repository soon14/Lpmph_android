package com.ai.ecp.app.resp;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/4/9.
 */
public class AcctInfo implements Serializable{

    private Long deductOrderMoney;//订单抵扣金额
    private String acctName;//账户名称
    private Long staffId;
    private String acctType;
    private String acctTypeName;
    private String adaptType;
    private String adpatTypeName;
    private Long shopId;
    private String staffName;
    private String ShopName;
    private Long id;
    private Long balance;
    private String shopLogo;

    public Long getDeductOrderMoney() {
        return deductOrderMoney;
    }

    public void setDeductOrderMoney(Long deductOrderMoney) {
        this.deductOrderMoney = deductOrderMoney;
    }

    public String getAcctName() {
        return acctName;
    }

    public void setAcctName(String acctName) {
        this.acctName = acctName;
    }

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public String getAcctType() {
        return acctType;
    }

    public void setAcctType(String acctType) {
        this.acctType = acctType;
    }

    public String getAcctTypeName() {
        return acctTypeName;
    }

    public void setAcctTypeName(String acctTypeName) {
        this.acctTypeName = acctTypeName;
    }

    public String getAdaptType() {
        return adaptType;
    }

    public void setAdaptType(String adaptType) {
        this.adaptType = adaptType;
    }

    public String getAdpatTypeName() {
        return adpatTypeName;
    }

    public void setAdpatTypeName(String adpatTypeName) {
        this.adpatTypeName = adpatTypeName;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getShopName() {
        return ShopName;
    }

    public void setShopName(String shopName) {
        ShopName = shopName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public String getShopLogo() {
        return shopLogo;
    }

    public void setShopLogo(String shopLogo) {
        this.shopLogo = shopLogo;
    }
}
