package com.ai.ecp.app.resp;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * 类注释:
 * 项目名:pmph_android
 * 包名:com.ai.ecp.app.resp
 * 作者: Chrizz
 * 时间: 2016/4/3 15:25
 */
public class PayWayResponse implements Serializable {

    private String id;
    private String payWayName;
    private String payWayType;
    private String payAcctType;
    private String payImage;
    private String payLogo;
    private String charSet;
    private String payActionUrl;
    private String payQueryUrl;
    private String payRefundUrl;
    private String bindUrl;
    private String bindTransferUrl;
    private String payReturnUrl;
    private String payNotifyUrl;
    private String payBindNotifyUrl;
    private String payRefundNotifyUrl;
    private String payErrorUrl;
    private String signType;
    private String payMercCode;
    private String payPrivateKey;
    private String payPrivateUser;
    private String payPrivatePasswd;
    private String payVerifyCert;
    private String propertyFile;
    private String logFileUrl;
    private Short showOrder;
    private String useFlag;
    private Long createStaff;
    private Timestamp createTime;
    private Long updateStaff;
    private Timestamp updateTime;
    private String payAuditUrl;
    private String payRefundMethod;
    private List<PayWayItem> payWayList;
    private String resultFlag;
    private String resultMsg;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPayWayName() {
        return payWayName;
    }

    public void setPayWayName(String payWayName) {
        this.payWayName = payWayName;
    }

    public String getPayWayType() {
        return payWayType;
    }

    public void setPayWayType(String payWayType) {
        this.payWayType = payWayType;
    }

    public String getPayAcctType() {
        return payAcctType;
    }

    public void setPayAcctType(String payAcctType) {
        this.payAcctType = payAcctType;
    }

    public String getPayImage() {
        return payImage;
    }

    public void setPayImage(String payImage) {
        this.payImage = payImage;
    }

    public String getPayLogo() {
        return payLogo;
    }

    public void setPayLogo(String payLogo) {
        this.payLogo = payLogo;
    }

    public String getCharSet() {
        return charSet;
    }

    public void setCharSet(String charSet) {
        this.charSet = charSet;
    }

    public String getPayActionUrl() {
        return payActionUrl;
    }

    public void setPayActionUrl(String payActionUrl) {
        this.payActionUrl = payActionUrl;
    }

    public String getPayQueryUrl() {
        return payQueryUrl;
    }

    public void setPayQueryUrl(String payQueryUrl) {
        this.payQueryUrl = payQueryUrl;
    }

    public String getPayRefundUrl() {
        return payRefundUrl;
    }

    public void setPayRefundUrl(String payRefundUrl) {
        this.payRefundUrl = payRefundUrl;
    }

    public String getBindUrl() {
        return bindUrl;
    }

    public void setBindUrl(String bindUrl) {
        this.bindUrl = bindUrl;
    }

    public String getBindTransferUrl() {
        return bindTransferUrl;
    }

    public void setBindTransferUrl(String bindTransferUrl) {
        this.bindTransferUrl = bindTransferUrl;
    }

    public String getPayReturnUrl() {
        return payReturnUrl;
    }

    public void setPayReturnUrl(String payReturnUrl) {
        this.payReturnUrl = payReturnUrl;
    }

    public String getPayNotifyUrl() {
        return payNotifyUrl;
    }

    public void setPayNotifyUrl(String payNotifyUrl) {
        this.payNotifyUrl = payNotifyUrl;
    }

    public String getPayBindNotifyUrl() {
        return payBindNotifyUrl;
    }

    public void setPayBindNotifyUrl(String payBindNotifyUrl) {
        this.payBindNotifyUrl = payBindNotifyUrl;
    }

    public String getPayRefundNotifyUrl() {
        return payRefundNotifyUrl;
    }

    public void setPayRefundNotifyUrl(String payRefundNotifyUrl) {
        this.payRefundNotifyUrl = payRefundNotifyUrl;
    }

    public String getPayErrorUrl() {
        return payErrorUrl;
    }

    public void setPayErrorUrl(String payErrorUrl) {
        this.payErrorUrl = payErrorUrl;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getPayMercCode() {
        return payMercCode;
    }

    public void setPayMercCode(String payMercCode) {
        this.payMercCode = payMercCode;
    }

    public String getPayPrivateKey() {
        return payPrivateKey;
    }

    public void setPayPrivateKey(String payPrivateKey) {
        this.payPrivateKey = payPrivateKey;
    }

    public String getPayPrivateUser() {
        return payPrivateUser;
    }

    public void setPayPrivateUser(String payPrivateUser) {
        this.payPrivateUser = payPrivateUser;
    }

    public String getPayPrivatePasswd() {
        return payPrivatePasswd;
    }

    public void setPayPrivatePasswd(String payPrivatePasswd) {
        this.payPrivatePasswd = payPrivatePasswd;
    }

    public String getPayVerifyCert() {
        return payVerifyCert;
    }

    public void setPayVerifyCert(String payVerifyCert) {
        this.payVerifyCert = payVerifyCert;
    }

    public String getPropertyFile() {
        return propertyFile;
    }

    public void setPropertyFile(String propertyFile) {
        this.propertyFile = propertyFile;
    }

    public String getLogFileUrl() {
        return logFileUrl;
    }

    public void setLogFileUrl(String logFileUrl) {
        this.logFileUrl = logFileUrl;
    }

    public Short getShowOrder() {
        return showOrder;
    }

    public void setShowOrder(Short showOrder) {
        this.showOrder = showOrder;
    }

    public String getUseFlag() {
        return useFlag;
    }

    public void setUseFlag(String useFlag) {
        this.useFlag = useFlag;
    }

    public Long getCreateStaff() {
        return createStaff;
    }

    public void setCreateStaff(Long createStaff) {
        this.createStaff = createStaff;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Long getUpdateStaff() {
        return updateStaff;
    }

    public void setUpdateStaff(Long updateStaff) {
        this.updateStaff = updateStaff;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public String getPayAuditUrl() {
        return payAuditUrl;
    }

    public void setPayAuditUrl(String payAuditUrl) {
        this.payAuditUrl = payAuditUrl;
    }

    public String getPayRefundMethod() {
        return payRefundMethod;
    }

    public void setPayRefundMethod(String payRefundMethod) {
        this.payRefundMethod = payRefundMethod;
    }

    public List<PayWayItem> getPayWayList() {
        return payWayList;
    }

    public void setPayWayList(List<PayWayItem> payWayList) {
        this.payWayList = payWayList;
    }

    public String getResultFlag() {
        return resultFlag;
    }

    public void setResultFlag(String resultFlag) {
        this.resultFlag = resultFlag;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

}
