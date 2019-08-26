package com.ai.ecp.app.resp;

import com.ailk.butterfly.app.model.AppBody;

import java.util.List;

public class Ord002Resp extends AppBody {

    /**
     * 登录用户ID
     */
    private Long staffId;

    /** 
     * 购物车明细
     */
    private List<Ord00201Resp> ordCartList;
    

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public List<Ord00201Resp> getOrdCartList() {
        return ordCartList;
    }

    public void setOrdCartList(List<Ord00201Resp> ordCartList) {
        this.ordCartList = ordCartList;
    }

}

