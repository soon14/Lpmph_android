package com.ai.ecp.app.resp;

import java.io.Serializable;
import java.sql.Timestamp;

public class Ord10803Resp implements Serializable{
    
    /** 
     * expressNo:物流单号. 
     * @since JDK 1.6 
     */ 
    private String expressNo;
    
    /** 
     * deliveryType:送货方式. 
     * @since JDK 1.6 
     */ 
    private String deliveryType;
    
    /** 
     * sendDate:发货时间. 
     * @since JDK 1.6 
     */ 
    private Timestamp sendDate;
    
    /** 
     * expressName:物流公司. 
     * @since JDK 1.6 
     */ 
    private String expressName;


    public String getExpressNo() {
        return expressNo;
    }

    public void setExpressNo(String expressNo) {
        this.expressNo = expressNo;
    }

    public String getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }

    public Timestamp getSendDate() {
        return sendDate;
    }

    public void setSendDate(Timestamp sendDate) {
        this.sendDate = sendDate;
    }

    public String getExpressName() {
        return expressName;
    }

    public void setExpressName(String expressName) {
        this.expressName = expressName;
    }
    
    
    
}

