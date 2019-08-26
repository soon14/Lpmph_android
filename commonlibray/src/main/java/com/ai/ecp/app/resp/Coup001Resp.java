package com.ai.ecp.app.resp;

import com.ailk.butterfly.app.model.AppBody;

import java.util.List;

/**
 * 类注释:
 * 项目名:pmph_android
 * 包名:com.ai.ecp.app.resp
 * 作者: Chrizz
 * 时间: 2016/4/19 14:31
 */
public class Coup001Resp extends AppBody{
    private Long coupCount;
    // 1:可使用 2:已使用 0:已过期
    private Long coupCount_0;
    private Long coupCount_1;
    private Long coupCount_2;
    private List<CoupDetailResp> respList;

    public List<CoupDetailResp> getRespList() {
        return respList;
    }

    public void setRespList(List<CoupDetailResp> respList) {
        this.respList = respList;
    }

    public Long getCoupCount() {
        return coupCount;
    }

    public void setCoupCount(Long coupCount) {
        this.coupCount = coupCount;
    }

    public Long getCoupCount_0() {
        return coupCount_0;
    }

    public void setCoupCount_0(Long coupCount_0) {
        this.coupCount_0 = coupCount_0;
    }

    public Long getCoupCount_1() {
        return coupCount_1;
    }

    public void setCoupCount_1(Long coupCount_1) {
        this.coupCount_1 = coupCount_1;
    }

    public Long getCoupCount_2() {
        return coupCount_2;
    }

    public void setCoupCount_2(Long coupCount_2) {
        this.coupCount_2 = coupCount_2;
    }
}
