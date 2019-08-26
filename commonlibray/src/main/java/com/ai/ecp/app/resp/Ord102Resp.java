package com.ai.ecp.app.resp;

import com.ailk.butterfly.app.model.AppBody;

import java.util.List;

/**
 * 类注释:
 * 项目名:pmph_android
 * 包名:com.ai.ecp.app.resp
 * 作者: Chrizz
 * 时间: 2016/5/13 22:40
 */
public class Ord102Resp extends AppBody {

    Long staffId;
    List<Ord10201Resp> ordCartList;

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public List<Ord10201Resp> getOrdCartList() {
        return ordCartList;
    }

    public void setOrdCartList(List<Ord10201Resp> ordCartList) {
        this.ordCartList = ordCartList;
    }
}
