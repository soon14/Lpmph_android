package com.ai.ecp.app.req;

import com.ailk.butterfly.app.model.AppBody;

public class Ord003Req extends AppBody {

    /**
     * 当前修改的明细
     */
    private ROrdCartItemRequest ordCartItem;

    public ROrdCartItemRequest getOrdCartItem() {
        return ordCartItem;
    }

    public void setOrdCartItem(ROrdCartItemRequest ordCartItem) {
        this.ordCartItem = ordCartItem;
    }
}

