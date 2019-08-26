package com.ai.ecp.app.resp;

import com.ailk.butterfly.app.model.AppBody;

import java.util.List;

/**
 * 类注释:
 * 项目名:pmph_android
 * 包名:com.ai.ecp.app.resp
 * 作者: Chrizz
 * 时间: 2016/4/6 21:10
 */
public class Staff107Resp extends AppBody {

    private List<CustAddrResDTO> resList;

    public List<CustAddrResDTO> getResList() {
        return resList;
    }

    public void setResList(List<CustAddrResDTO> resList) {
        this.resList = resList;
    }
}
