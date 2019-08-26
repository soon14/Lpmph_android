package com.ai.ecp.app.req;

import com.ailk.butterfly.app.model.AppBody;

/**
 * Created by HDF on 2016/6/1.
 */
public class Gds021Req extends AppBody {

    /**
     * 排序字段
     */
    private String field;

    /**
     * 排序字段排序
     */
    private String sort;

    /**
     * 搜索关键字
     */
    private String keyword;

    /**
     * 好评率
     */
    private String  evalRate;

    /**
     * 店铺id
     */
    private String id;

    /**
     * 分页大小
     */
    private int pageSize;

    /**
     * 页码
     */
    private int pageNumber;

    public String getEvalRate() {
        return evalRate;
    }

    public void setEvalRate(String evalRate) {
        this.evalRate = evalRate;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
