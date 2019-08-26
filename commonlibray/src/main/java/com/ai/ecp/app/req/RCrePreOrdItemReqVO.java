package com.ai.ecp.app.req;

/**
 * 类注释:
 * 项目名:PMPH
 * 包名:com.ai.ecp.app.req
 * 作者: Chrizz
 * 时间: 2016/3/24 21:25
 */
public class RCrePreOrdItemReqVO {

    /**
     * 明细ID
     */
    private Long cartItemId;
    /**
     * 促销ID
     */
    private Long promId;

    public Long getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(Long cartItemId) {
        this.cartItemId = cartItemId;
    }

    public Long getPromId() {
        return promId;
    }

    public void setPromId(Long promId) {
        this.promId = promId;
    }
}
