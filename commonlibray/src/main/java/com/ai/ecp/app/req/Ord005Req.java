package com.ai.ecp.app.req;

import com.ailk.butterfly.app.model.AppBody;

/**
 * 类注释:
 * 项目名:PMPH
 * 包名:com.ai.ecp.app.req
 * 作者: Chrizz
 * 时间: 2016/3/25 21:40
 */
public class Ord005Req extends AppBody {

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
