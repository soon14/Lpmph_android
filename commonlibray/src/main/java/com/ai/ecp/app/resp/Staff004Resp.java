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
 * Description: 用户修改密码出参<br>
 * Date:2016-2-22下午6:53:17  <br>
 * Copyright (c) 2016 asia All Rights Reserved <br>
 * 
 * @author huangxl5
 * @version  
 * @since JDK 1.6 
 */
public class Staff004Resp extends AppBody {
    
    private boolean flag;

    public boolean isFlag() {
        return flag;
    }
    
    public void setFlag(boolean flag) {
        this.flag = flag;
    }
    
}

