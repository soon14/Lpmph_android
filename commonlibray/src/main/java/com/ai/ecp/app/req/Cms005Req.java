package com.ai.ecp.app.req;

import com.ailk.butterfly.app.model.AppBody;
import com.ailk.butterfly.app.model.IBody;

/**
 * Title: ECP <br>
 * Project Name:ecp-web-mall <br>
 * Description: 获取APP排行榜页签下商品服务-入参<br>
 * Date:2016-2-22下午6:52:57  <br>
 * Copyright (c) 2016 asia All Rights Reserved <br>
 * 
 * @author jiangzh
 * @version  
 * @since JDK 1.6 
 */
public class Cms005Req extends AppBody {
    /**
     * 页签id
     */
    private Long tabId;
    /**
     * 楼层Id
     */
    private Long floorId;

    /**
     * 查询页数
     */
    private Integer pageNo;
    /**
     * 查询条数
     */
    private Integer pageSize;
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

    public Long getTabId() {
        return tabId;
    }
    public void setTabId(Long tabId) {
        this.tabId = tabId;
    }
    public Long getFloorId() {
        return floorId;
    }
    public void setFloorId(Long floorId) {
        this.floorId = floorId;
    }
    public Integer getPageNo() {
        return pageNo;
    }
    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }
    public Integer getPageSize() {
        return pageSize;
    }
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
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
    public String getCatgCode() {
        return catgCode;
    }
    public void setCatgCode(String catgCode) {
        this.catgCode = catgCode;
    }
    
}

