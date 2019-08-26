package com.ai.ecp.app.req;

import com.ailk.butterfly.app.model.AppBody;

public class Pointmgds001Req extends AppBody {

    private Long gdsId;
    
    private Long skuId;
    
    private Long staffId;

    public Long getGdsId() {
        return gdsId;
    }

    public void setGdsId(Long gdsId) {
        this.gdsId = gdsId;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }
}

