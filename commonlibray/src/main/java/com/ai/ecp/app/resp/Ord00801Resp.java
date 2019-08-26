package com.ai.ecp.app.resp;

import java.io.Serializable;
import java.util.List;

/**
 * 类注释:
 * 项目名:PMPH
 * 包名:com.ai.ecp.app.resp
 * 作者: Chrizz
 * 时间: 2016/3/29 16:01
 */
public class Ord00801Resp implements Serializable {

    private Long shopId;
    private List<CoupCheckBeanRespDTO> coupOrdSkuList;
    private List<AcctInfoResDTO> acctInfoList;

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public List<CoupCheckBeanRespDTO> getCoupOrdSkuList() {
        return coupOrdSkuList;
    }

    public void setCoupOrdSkuList(List<CoupCheckBeanRespDTO> coupOrdSkuList) {
        this.coupOrdSkuList = coupOrdSkuList;
    }

    public List<AcctInfoResDTO> getAcctInfoList() {
        return acctInfoList;
    }

    public void setAcctInfoList(List<AcctInfoResDTO> acctInfoList) {
        this.acctInfoList = acctInfoList;
    }
}
