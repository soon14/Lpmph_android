package com.ai.ecp.app.req;

import com.ailk.butterfly.app.model.AppBody;
/**
 * 
 * Title: ECP <br>
 * Project Name:ecp-web-mall <br>
 * Description: 条形码扫描对象入参<br>
 * Date:2016年10月9日下午5:56:46  <br>
 * Copyright (c) 2016 asia All Rights Reserved <br>
 * 
 * @author liyong7
 * @version  
 * @since JDK 1.6
 */
public class Gds026Req extends AppBody {

    private String barcode;

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

   
    
    
    

}
