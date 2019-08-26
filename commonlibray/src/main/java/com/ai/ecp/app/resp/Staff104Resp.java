package com.ai.ecp.app.resp;

import com.ailk.butterfly.app.model.AppBody;

import java.util.List;

/**
 * Created by Administrator on 2016/4/9.
 */
public class Staff104Resp extends AppBody {

    private List<AcctTrade> resList;

    public List<AcctTrade> getResList() {
        return resList;
    }

    public void setResList(List<AcctTrade> resList) {
        this.resList = resList;
    }
}
