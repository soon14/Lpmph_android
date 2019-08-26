package com.ai.ecp.app.req;

import com.ailk.butterfly.app.model.AppBody;

/**
 * Created by Administrator on 2016/4/9.
 */
public class Staff104Req extends AppBody {

    private Long shopId;//店铺 id

    private String adaptType;//资金适用类型

    private String acctType;//资金类型

    private int pageNo;

    private int pageSize;

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public String getAdaptType() {
        return adaptType;
    }

    public void setAdaptType(String adaptType) {
        this.adaptType = adaptType;
    }

    public String getAcctType() {
        return acctType;
    }

    public void setAcctType(String acctType) {
        this.acctType = acctType;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
