package com.ai.ecp.app.resp;

import com.ailk.butterfly.app.model.AppBody;

import java.util.List;

/**
 * 类注释:
 * 项目名:pmph_android
 * 包名:com.ai.ecp.app.resp
 * 作者: Chrizz
 * 时间: 2016/4/8 16:34
 */
public class Staff102Resp extends AppBody {

    private long totalScore;
    private List<ScoreTrade> resList;

    public long getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(long totalScore) {
        this.totalScore = totalScore;
    }

    public List<ScoreTrade> getResList() {
        return resList;
    }

    public void setResList(List<ScoreTrade> resList) {
        this.resList = resList;
    }
}
