package com.ai.ecp.app.resp;

import com.ailk.butterfly.app.model.AppBody;

import java.util.List;

/**
 * 类注释:
 * 项目名:pmph_android
 * 包名:com.ai.ecp.app.resp
 * 作者: Chrizz
 * 时间: 2016/4/7 17:37
 */
public class Staff111Resp extends AppBody {

    private List<BaseAreaAdminRespDTO> resList;

    public List<BaseAreaAdminRespDTO> getResList() {
        return resList;
    }

    public void setResList(List<BaseAreaAdminRespDTO> resList) {
        this.resList = resList;
    }
}
