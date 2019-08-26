package com.ai.ecp.app.req;

/**
 * 类注释:
 * 项目名:pmph_android
 * 包名:com.ai.ecp.app.req
 * 作者: Chrizz
 * 时间: 2016/3/30 09:18
 */
public class CoupDetailRespVO {

    private Long coupId;
    private String coupName;
    private String coupNo;
    private Long coupValue;
    private Long id;

    public Long getCoupId() {
        return coupId;
    }

    public void setCoupId(Long coupId) {
        this.coupId = coupId;
    }

    public String getCoupName() {
        return coupName;
    }

    public void setCoupName(String coupName) {
        this.coupName = coupName;
    }

    public String getCoupNo() {
        return coupNo;
    }

    public void setCoupNo(String coupNo) {
        this.coupNo = coupNo;
    }

    public Long getCoupValue() {
        return coupValue;
    }

    public void setCoupValue(Long coupValue) {
        this.coupValue = coupValue;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
