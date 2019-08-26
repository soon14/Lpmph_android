package com.ai.ecp.app.resp;

import com.ai.ecp.app.resp.gds.PointGdsDetailBaseInfo;
import com.ailk.butterfly.app.model.AppBody;

import java.util.List;

/**
 * 类注释:
 * 项目名:pmph_android
 * 包名:com.ai.ecp.app.resp
 * 作者: Chrizz
 * 时间: 2016/5/16 15:19
 */
public class Pointmgds002Resp extends AppBody {

    private List<PointGdsDetailBaseInfo> rankGdsList;

    public List<PointGdsDetailBaseInfo> getRankGdsList() {
        return rankGdsList;
    }

    public void setRankGdsList(List<PointGdsDetailBaseInfo> rankGdsList) {
        this.rankGdsList = rankGdsList;
    }
}
