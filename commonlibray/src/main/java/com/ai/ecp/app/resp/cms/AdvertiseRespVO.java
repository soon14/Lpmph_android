package com.ai.ecp.app.resp.cms;

import com.ailk.butterfly.app.model.AppBody;
import com.ailk.butterfly.app.model.IBody;
/**
 * 
 * Title: ECP <br>
 * Project Name:ecp-web-mall <br>
 * Description: app广告服务返回的基础VO bean <br>
 * Date:2016年3月11日上午10:32:32  <br>
 * Copyright (c) 2016 asia All Rights Reserved <br>
 * 
 * @author zhanbh
 * @version  
 * @since JDK 1.6
 */
public class AdvertiseRespVO extends AppBody {
    private Long id;

    private String advertiseTitle;

    private String linkUrl;

    private String vfsUrl;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAdvertiseTitle() {
        return advertiseTitle;
    }

    public void setAdvertiseTitle(String advertiseTitle) {
        this.advertiseTitle = advertiseTitle;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public String getVfsUrl() {
        return vfsUrl;
    }

    public void setVfsUrl(String vfsUrl) {
        this.vfsUrl = vfsUrl;
    }
    
}

