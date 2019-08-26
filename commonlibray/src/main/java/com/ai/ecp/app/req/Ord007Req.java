package com.ai.ecp.app.req;

import com.ailk.butterfly.app.model.AppBody;

import java.util.List;

public class Ord007Req extends AppBody {

    private List<ROrdCartItemRequest> ordCartItems;

    public List<ROrdCartItemRequest> getOrdCartItems() {
        return ordCartItems;
    }

    public void setOrdCartItems(List<ROrdCartItemRequest> ordCartItems) {
        this.ordCartItems = ordCartItems;
    }
}

