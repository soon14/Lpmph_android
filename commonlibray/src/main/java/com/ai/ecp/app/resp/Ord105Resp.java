package com.ai.ecp.app.resp;

import java.util.List;
import java.util.Map;

import com.ailk.butterfly.app.model.AppBody;

public class Ord105Resp extends AppBody {

    /** 
     * redisKey:数据缓存中的Key. 
     * @since JDK 1.6 
     */ 
    private String redisKey;
    
    /** 
     * agentMoneys:提交订单时的合计价格. 
     * @since JDK 1.6 
     */ 
    private Long agentMoneys;
    
    /** 
     * orderMoneys:总金额. 
     * @since JDK 1.6 
     */ 
    private Long orderMoneys;

    private Long orderScores;
    
    /** 
     * realExpressFees:运费. 
     * @since JDK 1.6 
     */ 
    private Long realExpressFees;
    
    /** 
     * allMoney:应付总额. 
     * @since JDK 1.6 
     */ 
    private Long allMoney;
    
    /** 
     * orderAmounts:总数量. 
     * @since JDK 1.6 
     */ 
    private Long orderAmounts;
    
    /** 
     * discountMoneys:总优惠金额. 
     * @since JDK 1.6 
     */ 
    private Long discountMoneys;
    
    /** 
     * orderMoneyMap:订单金额. 
     * @since JDK 1.6 
     */ 
    private Map<Long,Long> orderMoneyMap;

    private Map<Long,Long> orderScoreMap;
    
    /** 
     * realExpressFeesMap:店铺运费. 
     * @since JDK 1.6 
     */ 
    private Map<Long,Long> realExpressFeesMap;
    
    /** 
     * discountPriceMoneyMap:购物车优惠金额. 
     * @since JDK 1.6 
     */ 
    private Map<Long,Long> discountPriceMoneyMap;
    
    /** 
     * deliverTypes:配送方式. 
     * @since JDK 1.6 
     */ 
    private Map<Long,String> deliverTypes;
    
    /** 
     * payList:支付方式. 
     * @since JDK 1.6 
     */ 
    private Map<String,String> payList;
    
    /** 
     * addrs:收货地址. 
     * @since JDK 1.6 
     */ 
    private List<CustAddrResDTO> addrs;
    
    /** 
     * Ord00801Resps:优惠券和现金账户相关信息. 
     * @since JDK 1.6 
     */ 
    private List<Ord10501Resp> Ord10501Resps;

    public Long getOrderScores() {
        return orderScores;
    }

    public void setOrderScores(Long orderScores) {
        this.orderScores = orderScores;
    }

    public Map<Long, Long> getOrderScoreMap() {
        return orderScoreMap;
    }

    public void setOrderScoreMap(Map<Long, Long> orderScoreMap) {
        this.orderScoreMap = orderScoreMap;
    }

    public Map<Long, Long> getOrderMoneyMap() {
        return orderMoneyMap;
    }

    public void setOrderMoneyMap(Map<Long, Long> orderMoneyMap) {
        this.orderMoneyMap = orderMoneyMap;
    }


    public List<Ord10501Resp> getOrd10501Resps() {
        return Ord10501Resps;
    }

    public void setOrd10501Resps(List<Ord10501Resp> ord10501Resps) {
        Ord10501Resps = ord10501Resps;
    }

    public Map<Long, Long> getRealExpressFeesMap() {
        return realExpressFeesMap;
    }

    public void setRealExpressFeesMap(Map<Long, Long> realExpressFeesMap) {
        this.realExpressFeesMap = realExpressFeesMap;
    }


    public List<CustAddrResDTO> getAddrs() {
        return addrs;
    }

    public void setAddrs(List<CustAddrResDTO> addrs) {
        this.addrs = addrs;
    }


    public Long getAgentMoneys() {
        return agentMoneys;
    }

    public void setAgentMoneys(Long agentMoneys) {
        this.agentMoneys = agentMoneys;
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

    public String getRedisKey() {
        return redisKey;
    }

    public void setRedisKey(String redisKey) {
        this.redisKey = redisKey;
    }
    
}

