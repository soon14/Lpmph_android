package com.ai.ecp.app.req;

import com.ailk.butterfly.app.model.AppBody;

public class Ord103Req extends AppBody{
    
    /** 
     * cartItemChg:变更数量的明细. 
     * @since JDK 1.6 
     */ 
    private ROrdCartItemRequest ordCartItem;

    public ROrdCartItemRequest getOrdCartItem() {
        return ordCartItem;
    }

    public void setOrdCartItem(ROrdCartItemRequest ordCartItem) {
        this.ordCartItem = ordCartItem;
    }



}
