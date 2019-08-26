package com.ai.ecp.app.resp;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 类注释:
 * 项目名:PMPH
 * 包名:com.ai.ecp.app.resp
 * 作者: Chrizz
 * 时间: 2016/3/25 21:49
 */
public class CustAddrResDTO implements Serializable{
    /**
     * 收货信息编码
     */
    private Long id;
    /**
     * 客户编码
     */
    private Long staffId;
    /**
     * 收货人姓名
     */
    private String contactName;
    /**
     * 收货人固定电话
     */
    private String contactNumber;
    /**
     * 收货人手机号
     */
    private String contactPhone;
    /**
     * 邮政编码
     */
    private String postCode;
    /**
     * 送货地址
     */
    private String chnlAddress;
    /**
     * 国家编码
     */
    private String countryCode;
    /**
     * 行政省份编码
     */
    private String province;
    /**
     * 行政地市编码
     */
    private String cityCode;
    /**
     * 行政区县编码
     */
    private String countyCode;
    /**
     * 状态
     */
    private String status;
    /**
     * 默认地址标记
     */
    private String usingFlag;
    /**
     * 创建时间
     */
    private Timestamp createTime;
    /**
     * 创建人
     */
    private Long createStaff;
    /**
     * 修改时间
     */
    private Timestamp updateTime;
    /**
     * 修改人
     */
    private Long updateStaff;
    /**
     * 自提人姓名
     */
    private String bringName;
    /**
     * 自提人证件类型
     */
    private String cardType;
    /**
     * 自提人证件号码
     */
    private String cardId;
    /**
     * 自提人固定电话
     */
    private String bringNumber;
    /**
     * 自提人手机号
     */
    private String bringPhone;

    private String pccName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getChnlAddress() {
        return chnlAddress;
    }

    public void setChnlAddress(String chnlAddress) {
        this.chnlAddress = chnlAddress;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCountyCode() {
        return countyCode;
    }

    public void setCountyCode(String countyCode) {
        this.countyCode = countyCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUsingFlag() {
        return usingFlag;
    }

    public void setUsingFlag(String usingFlag) {
        this.usingFlag = usingFlag;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Long getCreateStaff() {
        return createStaff;
    }

    public void setCreateStaff(Long createStaff) {
        this.createStaff = createStaff;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public Long getUpdateStaff() {
        return updateStaff;
    }

    public void setUpdateStaff(Long updateStaff) {
        this.updateStaff = updateStaff;
    }

    public String getBringName() {
        return bringName;
    }

    public void setBringName(String bringName) {
        this.bringName = bringName;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getBringNumber() {
        return bringNumber;
    }

    public void setBringNumber(String bringNumber) {
        this.bringNumber = bringNumber;
    }

    public String getBringPhone() {
        return bringPhone;
    }

    public void setBringPhone(String bringPhone) {
        this.bringPhone = bringPhone;
    }

    public String getPccName() {
        return pccName;
    }

    public void setPccName(String pccName) {
        this.pccName = pccName;
    }
}
