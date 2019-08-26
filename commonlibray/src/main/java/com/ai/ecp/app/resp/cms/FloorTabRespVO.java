package com.ai.ecp.app.resp.cms;

import com.ailk.butterfly.app.model.AppBody;
import com.ailk.butterfly.app.model.IBody;

import java.io.Serializable;

public class FloorTabRespVO extends AppBody implements Serializable{

    private Long id;

    private String tabName;

    /**
     * 数据来源  行为分析  或者配置
     */
    private String dataSource;

    /**
     * 行为分析统计类型
     */
    private String countType;

    /**
     * 行为分析 统计对应商品分类
     */
    private String catgCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTabName() {
        return tabName;
    }

    public void setTabName(String tabName) {
        this.tabName = tabName;
    }

    public String getCatgCode() {
        return catgCode;
    }

    public void setCatgCode(String catgCode) {
        this.catgCode = catgCode;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public String getCountType() {
        return countType;
    }

    public void setCountType(String countType) {
        this.countType = countType;
    }
}

