package com.ai.ecp.app.req;

import com.ai.ecp.app.resp.CoupCheckBeanRespDTO;
import com.ailk.butterfly.app.model.AppBody;

import java.util.List;

public class Ord009Req extends AppBody {

    /** 
     * redisKey:数据缓存中的Key. 
     */
    private String redisKey;
    
    /** 
     * staffId:当前用户ID. 
     */
    private Long staffId;
    
    /** 
     * addrId:订单收货地址信息ID. 
     */
    private Long addrId;
    
    /** 
     * payType:支付方式. 
     */
    private String payType;
    
    /** 
     * gdsType:. 
     */
    private String gdsType;
    
    /** 
     * sumbitMainList:发票运费资金账户等信息. 
     */
    private List<RSumbitMainReqVO> sumbitMainList;

    /**
     * coupPlatfBean:可使用的优惠券信息(平台).
     */
    private List<CoupCheckBeanRespDTO> coupPlatfBean;

    public String getRedisKey() {
        return redisKey;
    }

    public void setRedisKey(String redisKey) {
        this.redisKey = redisKey;
    }

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public Long getAddrId() {
        return addrId;
    }

    public void setAddrId(Long addrId) {
        this.addrId = addrId;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getGdsType() {
        return gdsType;
    }

    public void setGdsType(String gdsType) {
        this.gdsType = gdsType;
    }

    public List<RSumbitMainReqVO> getSumbitMainList() {
        return sumbitMainList;
    }

    public void setSumbitMainList(List<RSumbitMainReqVO> sumbitMainList) {
        this.sumbitMainList = sumbitMainList;
    }

    public List<CoupCheckBeanRespDTO> getCoupPlatfBean() {
        return coupPlatfBean;
    }

    public void setCoupPlatfBean(List<CoupCheckBeanRespDTO> coupPlatfBean) {
        this.coupPlatfBean = coupPlatfBean;
    }

}

