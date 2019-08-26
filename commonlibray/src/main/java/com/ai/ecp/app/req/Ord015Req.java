package com.ai.ecp.app.req;

import com.ailk.butterfly.app.model.AppBody;

import java.util.List;

/**
 * 类注释:
 * 项目名:pmph_android
 * 包名:com.ai.ecp.app.req
 * 作者: Chrizz
 * 时间: 2016/4/14 20:41
 */
public class Ord015Req extends AppBody {

    private List<ROrdCartItemRequest> ordCartItems;

    public List<ROrdCartItemRequest> getOrdCartItems() {
        return ordCartItems;
    }

    public void setOrdCartItems(List<ROrdCartItemRequest> ordCartItems) {
        this.ordCartItems = ordCartItems;
    }
}
