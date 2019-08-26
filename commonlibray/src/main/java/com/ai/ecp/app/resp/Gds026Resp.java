package com.ai.ecp.app.resp;

import com.ailk.butterfly.app.model.AppBody;

/**
 * 
 * Title: ECP <br>
 * Project Name:ecp-web-mall <br>
 * Description: 条形码查询返回对象.<br>
 * Date:2016年10月9日下午5:55:11  <br>
 * Copyright (c) 2016 asia All Rights Reserved <br>
 * 
 * @author liyong7
 * @version  
 * @since JDK 1.6
 */
public class Gds026Resp extends AppBody {

    /**
     * 商品编号
     */
    private Long gdsId;
    /**
     * 单品编号
     */
    private Long skuId;

    public Long getGdsId() {
        return gdsId;
    }
    public void setGdsId(Long gdsId) {
        this.gdsId = gdsId;
    }
    public Long getSkuId() {
        return skuId;
    }
    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }
    
}
