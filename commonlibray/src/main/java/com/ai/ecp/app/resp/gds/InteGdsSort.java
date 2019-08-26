package com.ai.ecp.app.resp.gds;

import com.ailk.butterfly.app.model.AppBody;

/**
 * 类注释:
 * 项目名:pmph_android
 * 包名:com.ai.ecp.app.resp.gds
 * 作者: Chrizz
 * 时间: 2016/5/22 15:19
 */
public class InteGdsSort extends AppBody {

    private String sort;
    private String sortType;
    private Long sortId;

    public String getSortType() {
        return sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public Long getSortId() {
        return sortId;
    }

    public void setSortId(Long sortId) {
        this.sortId = sortId;
    }
}
