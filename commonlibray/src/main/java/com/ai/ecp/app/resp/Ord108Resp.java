package com.ai.ecp.app.resp;

import java.sql.Timestamp;
import java.util.List;

import com.ailk.butterfly.app.model.AppBody;
import com.ailk.butterfly.app.model.IBody;

public class Ord108Resp extends AppBody {
    /** 
     * orderType:订单类型. 
     * @since JDK 1.6 
     */ 
    private String orderType;
    /** 
     * status:订单状态. 
     * @since JDK 1.6 
     */ 
    private String status;
    /** 
     * orderMoney:商品总金额. 
     * @since JDK 1.6 
     */ 
    private Long orderMoney;
    /** 
     * realExpressFee:运费. 
     * @since JDK 1.6 
     */ 
    private Long realExpressFee;
    /** 
     * realMoney:应付金额. 
     * @since JDK 1.6 
     */ 
    private Long realMoney;
    /** 
     * orderScore:订单总积分. 
     * @since JDK 1.6 
     */ 
    private Long orderScore;
    /** 
     * payType:付款方式. 
     * @since JDK 1.6 
     */ 
    private String payType;
    /** 
     * payTime:付款时间. 
     * @since JDK 1.6 
     */ 
    private Timestamp payTime;
    
    /** 
     * payFlag:支付状态. 
     * @since JDK 1.6 
     */ 
    private String payFlag;
    
    /** 
     * orderTime:下单时间. 
     * @since JDK 1.6 
     */ 
    private Timestamp orderTime;
    
    /** 
     * shopId:卖家ID. 
     * @since JDK 1.6 
     */ 
    private Long shopId;
    
    /** 
     * siteId:站点. 
     * @since JDK 1.6 
     */ 
    private Long siteId;
    
    /** 
     * contactName:收货人. 
     * @since JDK 1.6 
     */ 
    private String contactName;
    /** 
     * chnlAddress:地址. 
     * @since JDK 1.6 
     */ 
    private String chnlAddress;
    /** 
     * contactPhone:手机号. 
     * @since JDK 1.6 
     */ 
    private String contactPhone;
    /** 
     * contactNumber:固定电话. 
     * @since JDK 1.6 
     */ 
    private String contactNumber;
    
    /** 
     * invoiceType:发票类型. 
     * @since JDK 1.6 
     */ 
    private String invoiceType;
    /** 
     * invoiceTitle:发票抬头. 
     * @since JDK 1.6 
     */ 
    private String invoiceTitle;

    /** 
     * invoiceContent:发票内容. 
     * @since JDK 1.6 
     */ 
    private String invoiceContent;
    /** 
     * contactNumber:配送方式
     * @since JDK 1.6 
     */ 
    private String dispatchType;
    
    /** 
     * invoiceType:是否开票. 
     * @since JDK 1.6 
     */ 
    private String billingFlag;    
    
    /** 
     * sendInvoiceType:发票寄送方式. 
     * @since JDK 1.6 
     */ 
    private String sendInvoiceType;

    /** 
     * invoiceExpressNo:发票寄送快递单号. 
     * @since JDK 1.6 
     */ 
    private String invoiceExpressNo;
    
    /** 
     * id:订单号. 
     * @since JDK 1.6 
     */ 
    private String orderId;
    
    /** 
     * orderAmount:订单订购数量. 
     * @since JDK 1.6 
     */ 
    private Long orderAmount;
    
    /** 
     * basicMoney:订单码洋. 
     * @since JDK 1.6 
     */ 
    private Long basicMoney;
    
    /** 
     * payWay:支付通道. 
     * @since JDK 1.6 
     */ 
    private String payWay;
    
    /** 
     * discountOrderSum:店铺优惠. 
     * @since JDK 1.6 
     */ 
    private Long discountOrderSum;
    
    /** 
     * discountGdsSum:商品优惠. 
     * @since JDK 1.6 
     */ 
    private Long discountGdsSum;
    
    /** 
     * discountCoupSum:优惠券优惠. 
     * @since JDK 1.6 
     */ 
    private Long discountCoupSum;
    
    /** 
     * ord10801Resps:子订单信息列表. 
     * @since JDK 1.6 
     */ 
    private List<Ord10801Resp> ord10801Resps;
    
    /** 
     * ord10801Resps:订单日志列表. 
     * @since JDK 1.6 
     */ 
    private List<Ord10802Resp> ord10802Resps;
    
    /** 
     * ord10803Resps:物流信息列表. 
     * @since JDK 1.6 
     */ 
    private List<Ord10803Resp> ord10803Resps;
    
    

    public String getInvoiceTitle() {
        return invoiceTitle;
    }

    public void setInvoiceTitle(String invoiceTitle) {
        this.invoiceTitle = invoiceTitle;
    }

    public String getInvoiceContent() {
        return invoiceContent;
    }

    public void setInvoiceContent(String invoiceContent) {
        this.invoiceContent = invoiceContent;
    }

    public List<Ord10803Resp> getOrd10803Resps() {
        return ord10803Resps;
    }

    public void setOrd10803Resps(List<Ord10803Resp> ord10803Resps) {
        this.ord10803Resps = ord10803Resps;
    }

    public Long getDiscountOrderSum() {
        return discountOrderSum;
    }

    public void setDiscountOrderSum(Long discountOrderSum) {
        this.discountOrderSum = discountOrderSum;
    }

    public Long getDiscountGdsSum() {
        return discountGdsSum;
    }

    public void setDiscountGdsSum(Long discountGdsSum) {
        this.discountGdsSum = discountGdsSum;
    }

    public Long getDiscountCoupSum() {
        return discountCoupSum;
    }

    public void setDiscountCoupSum(Long discountCoupSum) {
        this.discountCoupSum = discountCoupSum;
    }

    public List<Ord10801Resp> getOrd10801Resps() {
        return ord10801Resps;
    }

    public void setOrd10801Resps(List<Ord10801Resp> ord10801Resps) {
        this.ord10801Resps = ord10801Resps;
    }

    public List<Ord10802Resp> getOrd10802Resps() {
        return ord10802Resps;
    }

    public void setOrd10802Resps(List<Ord10802Resp> ord10802Resps) {
        this.ord10802Resps = ord10802Resps;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getOrderMoney() {
        return orderMoney;
    }

    public void setOrderMoney(Long orderMoney) {
        this.orderMoney = orderMoney;
    }

    public Long getRealExpressFee() {
        return realExpressFee;
    }

    public void setRealExpressFee(Long realExpressFee) {
        this.realExpressFee = realExpressFee;
    }

    public Long getRealMoney() {
        return realMoney;
    }

    public void setRealMoney(Long realMoney) {
        this.realMoney = realMoney;
    }

    public Long getOrderScore() {
        return orderScore;
    }

    public void setOrderScore(Long orderScore) {
        this.orderScore = orderScore;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public Timestamp getPayTime() {
        return payTime;
    }

    public void setPayTime(Timestamp payTime) {
        this.payTime = payTime;
    }

    public String getPayFlag() {
        return payFlag;
    }

    public void setPayFlag(String payFlag) {
        this.payFlag = payFlag;
    }

    public Timestamp getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Timestamp orderTime) {
        this.orderTime = orderTime;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Long getSiteId() {
        return siteId;
    }

    public void setSiteId(Long siteId) {
        this.siteId = siteId;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getChnlAddress() {
        return chnlAddress;
    }

    public void setChnlAddress(String chnlAddress) {
        this.chnlAddress = chnlAddress;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
    }

    public String getDispatchType() {
        return dispatchType;
    }

    public void setDispatchType(String dispatchType) {
        this.dispatchType = dispatchType;
    }

    public String getBillingFlag() {
        return billingFlag;
    }

    public void setBillingFlag(String billingFlag) {
        this.billingFlag = billingFlag;
    }

    public String getSendInvoiceType() {
        return sendInvoiceType;
    }

    public void setSendInvoiceType(String sendInvoiceType) {
        this.sendInvoiceType = sendInvoiceType;
    }

    public String getInvoiceExpressNo() {
        return invoiceExpressNo;
    }

    public void setInvoiceExpressNo(String invoiceExpressNo) {
        this.invoiceExpressNo = invoiceExpressNo;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Long getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(Long orderAmount) {
        this.orderAmount = orderAmount;
    }

    public Long getBasicMoney() {
        return basicMoney;
    }

    public void setBasicMoney(Long basicMoney) {
        this.basicMoney = basicMoney;
    }

    public String getPayWay() {
        return payWay;
    }

    public void setPayWay(String payWay) {
        this.payWay = payWay;
    }
    
    
    
}

