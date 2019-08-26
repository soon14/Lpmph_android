package com.ai.ecp.app.req;

import java.util.List;

import com.ailk.butterfly.app.model.AppBody;

public class Ord104Req extends AppBody {
    private List<ROrdCartItemRequest> ordCartItems;

    public List<ROrdCartItemRequest> getOrdCartItems() {
        return ordCartItems;
    }

    public void setOrdCartItems(List<ROrdCartItemRequest> ordCartItems) {
        this.ordCartItems = ordCartItems;
    }
}

