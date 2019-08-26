package com.ai.ecp.app.resp;

import java.io.Serializable;
import java.util.List;

/**
 * 类注释:
 * 项目名:PMPH
 * 包名:com.ai.ecp.app.resp
 * 作者: Chrizz
 * 时间: 2016/3/25 21:49
 */
public class CoupCheckBeanRespDTO implements Serializable{


    private Long applyId;
    /**
     * 优惠券信息ID
     */
    private Long coupId;
    /**
     * 具体优惠券信息 包含:优惠券编码,具体生失效时间,面额等等
     */
    private Long coupValue;
    /**
     * 优惠券数量
     */
    private int coupSize;
    /**
     * 优惠券名称
     */
    private String coupName;
    /**
     * 该优惠券为免邮优惠券
     */
    private String noExpress;
    /**
     * 1:与部分优惠券可以共同使用
     * 2:与所有优惠互斥
     * 默认为0 就是可与所有优惠券共同使用
     */
    private String varLimit;
    /**
     * 如果varLimit==1时,该list存放可与此优惠券共同使用的其他优惠券ID
     */
    private List<Long> coupVarBeans;
    /**
     * 单个订单里,此类优惠券可使用的张数
     */
    private int ordUseNum;
    /**
     * 具体优惠券信息 包含:优惠券编码,具体生失效时间,面额等等
     */
    private List<CoupDetailRespDTO> coupDetails;

    public Long getApplyId() {
        return applyId;
    }

    public void setApplyId(Long applyId) {
        this.applyId = applyId;
    }

    public Long getCoupId() {
        return coupId;
    }

    public void setCoupId(Long coupId) {
        this.coupId = coupId;
    }

    public Long getCoupValue() {
        return coupValue;
    }

    public void setCoupValue(Long coupValue) {
        this.coupValue = coupValue;
    }

    public int getCoupSize() {
        return coupSize;
    }

    public void setCoupSize(int coupSize) {
        this.coupSize = coupSize;
    }

    public String getCoupName() {
        return coupName;
    }

    public void setCoupName(String coupName) {
        this.coupName = coupName;
    }

    public String getNoExpress() {
        return noExpress;
    }

    public void setNoExpress(String noExpress) {
        this.noExpress = noExpress;
    }

    public String getVarLimit() {
        return varLimit;
    }

    public void setVarLimit(String varLimit) {
        this.varLimit = varLimit;
    }

    public List<Long> getCoupVarBeans() {
        return coupVarBeans;
    }

    public void setCoupVarBeans(List<Long> coupVarBeans) {
        this.coupVarBeans = coupVarBeans;
    }

    public int getOrdUseNum() {
        return ordUseNum;
    }

    public void setOrdUseNum(int ordUseNum) {
        this.ordUseNum = ordUseNum;
    }

    public List<CoupDetailRespDTO> getCoupDetails() {
        return coupDetails;
    }

    public void setCoupDetails(List<CoupDetailRespDTO> coupDetails) {
        this.coupDetails = coupDetails;
    }
}
