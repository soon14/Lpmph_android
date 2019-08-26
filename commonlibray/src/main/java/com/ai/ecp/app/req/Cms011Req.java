package com.ai.ecp.app.req;

import com.ailk.butterfly.app.model.AppBody;

/**
 * Title: ECP <br>
 * Project Name:ecp-web-mall <br>
 * Description: 获取APP首页数据服务-入参<br>
 * Date:2016-2-22下午6:52:57  <br>
 * Copyright (c) 2016 asia All Rights Reserved <br>
 * 
 * @author jiangzh
 * @version  
 * @since JDK 1.6 
 */
public class Cms011Req extends AppBody {
    
    private Long siteId ;
    
    private Integer pageNo;
    
    private Integer pageSize;
    
    private Integer guessGdsNum;

    public Long getSiteId() {
        return siteId;
    }

    public void setSiteId(Long siteId) {
        this.siteId = siteId;
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

    public Integer getGuessGdsNum() {
        return guessGdsNum;
    }

    public void setGuessGdsNum(Integer guessGdsNum) {
        this.guessGdsNum = guessGdsNum;
    }

}

