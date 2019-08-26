package com.ai.ecp.app.resp;

import com.ailk.butterfly.app.model.AppBody;

import java.util.List;
import java.util.Map;

public class Ord008Resp extends AppBody{

    /** 
     * 保存在缓存中的Key.
     */
    private String redisKey;
    /**
     * 总金额
     */
    private Long orderMoneys;
    /**
     * 运费
     */
    private Long realExpressFees;
    /**
     * 应付总额
     */
    private Long allMoney;
    /**
     * 总数量
     */
    private Long orderAmounts;
    /**
     * 总优惠金额
     */
    private Long discountMoneys;

    private Map<Long, Long> orderMoneyMap;
    /**
     * 店铺运费
     */
    private Map<Long, Long> realExpressFeesMap;
    /**
     * 购物车优惠金额
     */
    private Map<Long, Long> discountPriceMoneyMap;
    /**
     * 配送方式
     */
    private Map<Long, String> deliverTypes;
    /**
     * 支付方式
     */
    private Map<String, String> payList;
    /**
     * 用户收货地址数据
     */
    private List<CustAddrResDTO> addrs;
    /**
     * 优惠券和现金账户相关信息
     */
    private List<Ord00801Resp> Ord00801Resps;
    /**
     * 发票内容
     */
    private List<String> invoiceConList;
    /**
     * 是否开启优惠码 1开启
     */
    private String ifcoupCodeOpen;

    public String getRedisKey() {
        return redisKey;
    }

    public void setRedisKey(String redisKey) {
        this.redisKey = redisKey;
    }

    public Long getOrderMoneys() {
        return orderMoneys;
    }

    public void setOrderMoneys(Long orderMoneys) {
        this.orderMoneys = orderMoneys;
    }

    public Long getRealExpressFees() {
        return realExpressFees;
    }

    public void setRealExpressFees(Long realExpressFees) {
        this.realExpressFees = realExpressFees;
    }

    public Long getAllMoney() {
        return allMoney;
    }

    public void setAllMoney(Long allMoney) {
        this.allMoney = allMoney;
    }

    public Long getOrderAmounts() {
        return orderAmounts;
    }

    public void setOrderAmounts(Long orderAmounts) {
        this.orderAmounts = orderAmounts;
    }

    public Long getDiscountMoneys() {
        return discountMoneys;
    }

    public void setDiscountMoneys(Long discountMoneys) {
        this.discountMoneys = discountMoneys;
    }

    public Map<Long, Long> getDiscountPriceMoneyMap() {
        return discountPriceMoneyMap;
    }

    public void setDiscountPriceMoneyMap(Map<Long, Long> discountPriceMoneyMap) {
        this.discountPriceMoneyMap = discountPriceMoneyMap;
    }

    public Map<Long, String> getDeliverTypes() {
        return deliverTypes;
    }

    public void setDeliverTypes(Map<Long, String> deliverTypes) {
        this.deliverTypes = deliverTypes;
    }

    public Map<String, String> getPayList() {
        return payList;
    }

    public void setPayList(Map<String, String> payList) {
        this.payList = payList;
    }

    public List<CustAddrResDTO> getAddrs() {
        return addrs;
    }

    public void setAddrs(List<CustAddrResDTO> addrs) {
        this.addrs = addrs;
    }

    public Map<Long, Long> getRealExpressFeesMap() {
        return realExpressFeesMap;
    }

    public void setRealExpressFeesMap(Map<Long, Long> realExpressFeesMap) {
        this.realExpressFeesMap = realExpressFeesMap;
    }

    public List<Ord00801Resp> getOrd00801Resps() {
        return Ord00801Resps;
    }

    public void setOrd00801Resps(List<Ord00801Resp> ord00801Resps) {
        Ord00801Resps = ord00801Resps;
    }

    public Map<Long, Long> getOrderMoneyMap() {
        return orderMoneyMap;
    }

    public void setOrderMoneyMap(Map<Long, Long> orderMoneyMap) {
        this.orderMoneyMap = orderMoneyMap;
    }

    public List<String> getInvoiceConList() {
        return invoiceConList;
    }

    public void setInvoiceConList(List<String> invoiceConList) {
        this.invoiceConList = invoiceConList;
    }

    public String getIfcoupCodeOpen() {
        return ifcoupCodeOpen;
    }

    public void setIfcoupCodeOpen(String ifcoupCodeOpen) {
        this.ifcoupCodeOpen = ifcoupCodeOpen;
    }
}

