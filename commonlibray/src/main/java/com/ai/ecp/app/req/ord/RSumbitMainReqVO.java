package com.ai.ecp.app.req.ord;

import com.ai.ecp.app.req.CoupCheckBeanRespVO;
import com.ai.ecp.app.req.ROrdInvoiceCommRequest;
import com.ai.ecp.app.req.ROrdInvoiceTaxRequest;
import com.ai.ecp.app.resp.AcctInfoResDTO;

import java.util.List;

/**
 * 类注释:
 * 项目名:pmph_android
 * 包名:com.ai.ecp.app.req
 * 作者: Chrizz
 * 时间: 2016/3/30 09:16
 */
public class RSumbitMainReqVO {

    private Long cartId;
    private Long realExpressFee;
    private Long realMoney;
    private Long shopDiscountMoney;
    private Long discountMoney;
    private String invoiceType;
    private String deliverType;
    private List<AcctInfoResDTO> ordAcctInfoList;
    private ROrdInvoiceCommRequest rOrdInvoiceCommRequest;
    private ROrdInvoiceTaxRequest rOrdInvoiceTaxRequest;
    private List<CoupCheckBeanRespVO> coupCheckBean;

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
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

    public Long getShopDiscountMoney() {
        return shopDiscountMoney;
    }

    public void setShopDiscountMoney(Long shopDiscountMoney) {
        this.shopDiscountMoney = shopDiscountMoney;
    }

    public Long getDiscountMoney() {
        return discountMoney;
    }

    public void setDiscountMoney(Long discountMoney) {
        this.discountMoney = discountMoney;
    }

    public String getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
    }

    public String getDeliverType() {
        return deliverType;
    }

    public void setDeliverType(String deliverType) {
        this.deliverType = deliverType;
    }

    public List<AcctInfoResDTO> getOrdAcctInfoList() {
        return ordAcctInfoList;
    }

    public void setOrdAcctInfoList(List<AcctInfoResDTO> ordAcctInfoList) {
        this.ordAcctInfoList = ordAcctInfoList;
    }

    public ROrdInvoiceCommRequest getrOrdInvoiceCommRequest() {
        return rOrdInvoiceCommRequest;
    }

    public void setrOrdInvoiceCommRequest(ROrdInvoiceCommRequest rOrdInvoiceCommRequest) {
        this.rOrdInvoiceCommRequest = rOrdInvoiceCommRequest;
    }

    public ROrdInvoiceTaxRequest getrOrdInvoiceTaxRequest() {
        return rOrdInvoiceTaxRequest;
    }

    public void setrOrdInvoiceTaxRequest(ROrdInvoiceTaxRequest rOrdInvoiceTaxRequest) {
        this.rOrdInvoiceTaxRequest = rOrdInvoiceTaxRequest;
    }

    public List<CoupCheckBeanRespVO> getCoupCheckBean() {
        return coupCheckBean;
    }

    public void setCoupCheckBean(List<CoupCheckBeanRespVO> coupCheckBean) {
        this.coupCheckBean = coupCheckBean;
    }
}
