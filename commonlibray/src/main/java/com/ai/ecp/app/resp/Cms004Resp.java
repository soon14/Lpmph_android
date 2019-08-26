package com.ai.ecp.app.resp;

import java.util.ArrayList;
import java.util.List;

import com.ai.ecp.app.resp.cms.FloorTabRespVO;
import com.ailk.butterfly.app.model.AppBody;

/**
 * Title: ECP <br>
 * Project Name:ecp-web-mall <br>
 * Description: 获取APP排行榜服务-出参<br>
 * Date:2016-2-22下午6:53:17  <br>
 * Copyright (c) 2016 asia All Rights Reserved <br>
 * 
 * @author jiangzh
 * @version  
 * @since JDK 1.6 
 */
public class Cms004Resp extends AppBody {
    
    /** 
     * id:楼层ID
     */ 
    private Long id;
    /** 
     * id:楼层名称
     */ 
    private String floorName;
    /**
     * APP楼层页签LIST
     */
    List<FloorTabRespVO> tabList = new  ArrayList<FloorTabRespVO>();
    
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

    public String getFloorName() {
        return floorName;
    }

    public void setFloorName(String floorName) {
        this.floorName = floorName;
    }

    public List<FloorTabRespVO> getTabList() {
        return tabList;
    }

    public void setTabList(List<FloorTabRespVO> tabList) {
        this.tabList = tabList;
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

