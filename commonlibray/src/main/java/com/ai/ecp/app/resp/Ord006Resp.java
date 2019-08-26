package com.ai.ecp.app.resp;

import com.ailk.butterfly.app.model.AppBody;

public class Ord006Resp extends AppBody {

    /**
     * 店铺的优惠信息
     */
    private Ord00401Resp cartPromDTO;
    /**
     * 修改明细的优惠信息
     */
    private Ord00401Resp cartPromItemDTO;

    public Ord00401Resp getCartPromDTO() {
        return cartPromDTO;
    }

    public void setCartPromDTO(Ord00401Resp cartPromDTO) {
        this.cartPromDTO = cartPromDTO;
    }

    public Ord00401Resp getCartPromItemDTO() {
        return cartPromItemDTO;
    }

    public void setCartPromItemDTO(Ord00401Resp cartPromItemDTO) {
        this.cartPromItemDTO = cartPromItemDTO;
    }
}

