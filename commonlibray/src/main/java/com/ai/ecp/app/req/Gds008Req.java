package com.ai.ecp.app.req;

import java.util.List;

import com.ai.ecp.app.req.gds.GdsSku2PropPropIdxBaseInfo;
import com.ailk.butterfly.app.model.AppBody;

public class Gds008Req extends AppBody {

    private Long gdsId;
    
    private Long shopId;
    
    private Long skuId;
    
    
    private List<GdsSku2PropPropIdxBaseInfo> gdsPropValueReqDTOs;

    public Long getGdsId() {
        return gdsId;
    }

    public void setGdsId(Long gdsId) {
        this.gdsId = gdsId;
    }

 

 

    public List<GdsSku2PropPropIdxBaseInfo> getGdsPropValueReqDTOs() {
        return gdsPropValueReqDTOs;
    }

    public void setGdsPropValueReqDTOs(List<GdsSku2PropPropIdxBaseInfo> gdsPropValueReqDTOs) {
        this.gdsPropValueReqDTOs = gdsPropValueReqDTOs;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }
}

