package com.ai.ecp.app.resp.gds;

import java.sql.Timestamp;

import com.ailk.butterfly.app.model.AppBody;

/**
 * 
 * Title: ECP <br>
 * Project Name:ecp-web-mall <br>
 * Description: 秒杀活动促销信息商品域再封装对象.<br>
 * Date:2016年11月4日上午10:14:52  <br>
 * Copyright (c) 2016 asia All Rights Reserved <br>
 * 
 * @author liyong7
 * @version  
 * @since JDK 1.6
 */
public class GdsSecKillPromInfoDTO extends AppBody {


    private Timestamp startTime;//生效开始时间

    private Timestamp endTime;//生效截止时间

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    
}
