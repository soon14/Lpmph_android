package com.ai.ecp.app.resp;

import java.util.ArrayList;
import java.util.List;

import com.ai.ecp.app.resp.cms.FloorTabRespVO;
import com.ai.ecp.app.resp.gds.GdsBaseInfo;
import com.ailk.butterfly.app.model.AppBody;
import com.ailk.butterfly.app.model.IBody;

/**
 * Title: ECP <br>
 * Project Name:ecp-web-mall <br>
 * Description: 获取APP排行榜页签下商品服务-出参<br>
 * Date:2016-2-22下午6:53:17  <br>
 * Copyright (c) 2016 asia All Rights Reserved <br>
 * 
 * @author jiangzh
 * @version  
 * @since JDK 1.6 
 */
public class Cms005Resp extends AppBody {
    
    /** 
     * id:楼层ID
     */ 
    private Long tabId;
    /**
     * APP楼层数据LIST
     */
    List<GdsBaseInfo> gdsList = new  ArrayList<GdsBaseInfo>();
    
    public Long getTabId() {
        return tabId;
    }

    public void setTabId(Long tabId) {
        this.tabId = tabId;
    }

    public List<GdsBaseInfo> getGdsList() {
        return gdsList;
    }

    public void setGdsList(List<GdsBaseInfo> gdsList) {
        this.gdsList = gdsList;
    }
    

}

