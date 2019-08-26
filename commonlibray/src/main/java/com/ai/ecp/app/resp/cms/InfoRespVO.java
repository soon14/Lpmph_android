/** 
 * Project Name:ecp-web-manage Maven Webapp 
 * File Name:CmsInfoVO.java 
 * Package Name:com.ai.ecp.busi.cms.vo 
 * Date:2015-8-14下午3:14:04 
 * Copyright (c) 2015, asia All Rights Reserved. 
 * 
 */ 
package com.ai.ecp.app.resp.cms;

import com.ailk.butterfly.app.model.AppBody;

/**
 * Title: ECP <br>
 * Project Name:ecp-web-manage Maven Webapp <br>
 * Description:页面信息VO <br>
 * Date:2015-8-14下午3:14:04  <br>
 * 
 * @author wenyf
 * @version  
 * @since JDK 1.7
 */
public class InfoRespVO extends AppBody {
    
    private Long id;

    private String infoTitle;

    private String typeName;
    
    private String pubTime;

    private Long siteId;

    private String clickUrl;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInfoTitle() {
        return infoTitle;
    }

    public void setInfoTitle(String infoTitle) {
        this.infoTitle = infoTitle == null ? null : infoTitle.trim();
    }

    public String getPubTime() {
        return pubTime;
    }

    public void setPubTime(String pubTime) {
        this.pubTime = pubTime;
    }

    public Long getSiteId() {
        return siteId;
    }

    public void setSiteId(Long siteId) {
        this.siteId = siteId;
    }
    
    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getClickUrl() {
        return clickUrl;
    }

    public void setClickUrl(String clickUrl) {
        this.clickUrl = clickUrl;
    }
}

