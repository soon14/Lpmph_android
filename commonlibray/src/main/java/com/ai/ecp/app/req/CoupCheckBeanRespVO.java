package com.ai.ecp.app.req;

import java.util.List;

/**
 * 类注释:
 * 项目名:pmph_android
 * 包名:com.ai.ecp.app.req
 * 作者: Chrizz
 * 时间: 2016/3/30 09:17
 */
public class CoupCheckBeanRespVO {

    private Long coupId;
    private int coupSize;
    private String varLimit;
    private List<CoupDetailRespVO> coupDetails;

    public Long getCoupId() {
        return coupId;
    }

    public void setCoupId(Long coupId) {
        this.coupId = coupId;
    }

    public int getCoupSize() {
        return coupSize;
    }

    public void setCoupSize(int coupSize) {
        this.coupSize = coupSize;
    }

    public String getVarLimit() {
        return varLimit;
    }

    public void setVarLimit(String varLimit) {
        this.varLimit = varLimit;
    }

    public List<CoupDetailRespVO> getCoupDetails() {
        return coupDetails;
    }

    public void setCoupDetails(List<CoupDetailRespVO> coupDetails) {
        this.coupDetails = coupDetails;
    }
}
