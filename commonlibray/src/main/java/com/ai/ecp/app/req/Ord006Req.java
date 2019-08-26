package com.ai.ecp.app.req;

import com.ailk.butterfly.app.model.AppBody;

public class Ord006Req extends AppBody {

    private ROrdCartItemRequest ordCartItem;
    private ROrdCartChangeRequest ordCartChg;

    public ROrdCartItemRequest getOrdCartItem() {
        return ordCartItem;
    }

    public void setOrdCartItem(ROrdCartItemRequest ordCartItem) {
        this.ordCartItem = ordCartItem;
    }

    public ROrdCartChangeRequest getOrdCartChg() {
        return ordCartChg;
    }

    public void setOrdCartChg(ROrdCartChangeRequest ordCartChg) {
        this.ordCartChg = ordCartChg;
    }
}

