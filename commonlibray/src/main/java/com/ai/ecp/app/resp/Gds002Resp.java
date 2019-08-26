package com.ai.ecp.app.resp;

import java.util.List;

import com.ai.ecp.app.resp.gds.GdsDetailBaseInfo;
import com.ailk.butterfly.app.model.AppBody;
import com.ailk.butterfly.app.model.IBody;

public class Gds002Resp extends AppBody {

    
    
    private List<GdsDetailBaseInfo> commondCatGds;



    public List<GdsDetailBaseInfo> getCommondCatGds() {
        return commondCatGds;
    }

    public void setCommondCatGds(List<GdsDetailBaseInfo> commondCatGds) {
        this.commondCatGds = commondCatGds;
    }  
    
    
}

