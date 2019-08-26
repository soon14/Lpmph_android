package com.ai.ecp.app.resp;


import com.ai.ecp.app.resp.cms.AdvertiseRespVO;
import com.ailk.butterfly.app.model.AppBody;

import java.util.List;

/**
 * Title: ECP <br>
 * Project Name:ecp-web-mall <br>
 * Description: 获取APP广告服务-出参<br>
 * Date:2016-2-22下午6:53:17  <br>
 * Copyright (c) 2016 asia All Rights Reserved <br>
 * 
 * @author jiangzh
 * @version  
 * @since JDK 1.6 
 */
public class Cms001Resp extends AppBody {
    
    List<AdvertiseRespVO> advertiseList ;

    public List<AdvertiseRespVO> getAdvertiseList() {
        return advertiseList;
    }

    public void setAdvertiseList(List<AdvertiseRespVO> advertiseList) {
        this.advertiseList = advertiseList;
    }
    
}

