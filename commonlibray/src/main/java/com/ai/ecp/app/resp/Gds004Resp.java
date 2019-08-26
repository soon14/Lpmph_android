package com.ai.ecp.app.resp;

import java.util.List;

import com.ai.ecp.app.resp.gds.GdsPromMatchBaseInfo;
import com.ailk.butterfly.app.model.AppBody;

public class Gds004Resp extends AppBody {


    private List<GdsPromMatchBaseInfo> autoCombineList;

    private List<GdsPromMatchBaseInfo> fixedCombineList;

    private Long skuId;


    public List<GdsPromMatchBaseInfo> getAutoCombineList() {
        return autoCombineList;
    }

    public void setAutoCombineList(List<GdsPromMatchBaseInfo> autoCombineList) {
        this.autoCombineList = autoCombineList;
    }

    public List<GdsPromMatchBaseInfo> getFixedCombineList() {
        return fixedCombineList;
    }

    public void setFixedCombineList(List<GdsPromMatchBaseInfo> fixedCombineList) {
        this.fixedCombineList = fixedCombineList;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

}
