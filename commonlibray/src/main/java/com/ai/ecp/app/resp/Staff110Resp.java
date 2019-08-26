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
 * Description: 个人中心获取相关数据出参<br>
 * Date:2016-2-22下午6:53:17  <br>
 * Copyright (c) 2016 asia All Rights Reserved <br>
 * 
 * @author huangxl5
 * @version  
 * @since JDK 1.6 
 */
public class Staff110Resp extends AppBody {
    
    private long orderPayCnt;//待付款数量
    
    private long orderSendCnt;//待发货数量
    
    private long orderRecept;//待收货数量
    
    private long orderCommentCnt;//待评价数量
    
    private long acctTotal;//总的资金账户金额
    
    private String custPic;//用户头像id
    
    private String staffCode;//用户登录名
    
    private String custLevelName;//会员等级

    public long getOrderPayCnt() {
        return orderPayCnt;
    }

    public void setOrderPayCnt(long orderPayCnt) {
        this.orderPayCnt = orderPayCnt;
    }

    public long getOrderSendCnt() {
        return orderSendCnt;
    }

    public void setOrderSendCnt(long orderSendCnt) {
        this.orderSendCnt = orderSendCnt;
    }

    public long getOrderRecept() {
        return orderRecept;
    }

    public void setOrderRecept(long orderRecept) {
        this.orderRecept = orderRecept;
    }

    public long getOrderCommentCnt() {
        return orderCommentCnt;
    }

    public void setOrderCommentCnt(long orderCommentCnt) {
        this.orderCommentCnt = orderCommentCnt;
    }

    public long getAcctTotal() {
        return acctTotal;
    }

    public void setAcctTotal(long acctTotal) {
        this.acctTotal = acctTotal;
    }

    public String getCustPic() {
        return custPic;
    }

    public void setCustPic(String custPic) {
        this.custPic = custPic;
    }

    public String getStaffCode() {
        return staffCode;
    }

    public void setStaffCode(String staffCode) {
        this.staffCode = staffCode;
    }

    public String getCustLevelName() {
        return custLevelName;
    }

    public void setCustLevelName(String custLevelName) {
        this.custLevelName = custLevelName;
    }
    
}

