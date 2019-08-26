package com.ai.ecp.app.req;

import com.ailk.butterfly.app.model.AppBody;

/**
 * 类注释:
 * 项目名:PMPH
 * 包名:com.ai.ecp.app.req
 * 作者: Chrizz
 * 时间: 2016/3/24 10:51
 */
public class Ord014Req extends AppBody {

    private ROrdCartChangeRequest rOrdCartChangeRequest;

    public ROrdCartChangeRequest getrOrdCartChangeRequest() {
        return rOrdCartChangeRequest;
    }

    public void setrOrdCartChangeRequest(ROrdCartChangeRequest rOrdCartChangeRequest) {
        this.rOrdCartChangeRequest = rOrdCartChangeRequest;
    }
}
