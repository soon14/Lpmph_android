package com.ai.ecp.app.req;

import com.ailk.butterfly.app.model.AppBody;

public class Gds001Req extends AppBody {

    private Long gdsId;
    
    private Long skuId;
    
    private Long staffId;

    private Integer middleImgHeight;

    private Integer middleImgWidth;

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

    public Integer getMiddleImgHeight() {
        return middleImgHeight;
    }

    public void setMiddleImgHeight(Integer middleImgHeight) {
        this.middleImgHeight = middleImgHeight;
    }

    public Integer getMiddleImgWidth() {
        return middleImgWidth;
    }

    public void setMiddleImgWidth(Integer middleImgWidth) {
        this.middleImgWidth = middleImgWidth;
    }

}

