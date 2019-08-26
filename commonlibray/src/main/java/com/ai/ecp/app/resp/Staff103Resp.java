package com.ai.ecp.app.resp;

import com.ailk.butterfly.app.model.AppBody;

import java.util.List;

/**
 * Created by Administrator on 2016/4/9.
 */
public class Staff103Resp extends AppBody {

    private List<AcctInfo> resList;

    public List<AcctInfo> getResList() {
        return resList;
    }

    public void setResList(List<AcctInfo> resList) {
        this.resList = resList;
    }
}
