/** 
 * Project Name:ecp-web-mall 
 * File Name:DemoResp.java 
 * Package Name:com.ai.ecp.app.resp 
 * Date:2016-2-22下午6:53:17 
 * Copyright (c) 2016, asia All Rights Reserved. 
 * 
 */ 
package com.ai.ecp.app.resp;

import com.ailk.butterfly.app.model.AppBody;
import com.ailk.butterfly.app.model.IBody;

/**
 * Title: ECP <br>
 * Project Name:ecp-web-mall <br>
 * Description: 用户注册出参<br>
 * Date:2016-2-22下午6:53:17  <br>
 * Copyright (c) 2016 asia All Rights Reserved <br>
 * 
 * @author huangxl5
 * @version  
 * @since JDK 1.6 
 */
public class Staff002Resp extends AppBody {
    
    private Long staffId;
    private String tocken;

    public String getTocken() {
        return tocken;
    }

    public void setTocken(String tocken) {
        this.tocken = tocken;
    }

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }
    
    
}

