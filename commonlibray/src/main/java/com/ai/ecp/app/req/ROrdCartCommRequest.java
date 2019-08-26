package com.ai.ecp.app.req;

import java.util.List;

/**
 * 类注释:
 * 项目名:PMPH
 * 包名:com.ai.ecp.app.req
 * 作者: Chrizz
 * 时间: 2016/3/23 09:10
 */
public class ROrdCartCommRequest {

    private Long id;
    private Long promId;
    private List<ROrdCartItemCommRequest> ordCartItemCommList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPromId() {
        return promId;
    }

    public void setPromId(Long promId) {
        this.promId = promId;
    }

    public List<ROrdCartItemCommRequest> getOrdCartItemCommList() {
        return ordCartItemCommList;
    }

    public void setOrdCartItemCommList(List<ROrdCartItemCommRequest> ordCartItemCommList) {
        this.ordCartItemCommList = ordCartItemCommList;
    }
}
