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
public class Cms010Req extends AppBody {

    private Long siteId ;

    private Integer channelSize;

    private Integer guessPageNo;//猜你喜欢分页

    private Integer guessPageSize;

    private Integer guessGdsNum;

    public Long getSiteId() {
        return siteId;
    }

    public void setSiteId(Long siteId) {
        this.siteId = siteId;
    }

    public Integer getGuessPageNo() {
        return guessPageNo;
    }

    public void setGuessPageNo(Integer guessPageNo) {
        this.guessPageNo = guessPageNo;
    }

    public Integer getGuessPageSize() {
        return guessPageSize;
    }

    public void setGuessPageSize(Integer guessPageSize) {
        this.guessPageSize = guessPageSize;
    }

    public Integer getGuessGdsNum() {
        return guessGdsNum;
    }

    public void setGuessGdsNum(Integer guessGdsNum) {
        this.guessGdsNum = guessGdsNum;
    }

    public Integer getChannelSize() {
        return channelSize;
    }

    public void setChannelSize(Integer channelSize) {
        this.channelSize = channelSize;
    }

}

