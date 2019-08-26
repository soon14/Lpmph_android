package com.ai.ecp.app.req;

import java.util.List;

/**
 * 类注释:
 * 项目名:PMPH
 * 包名:com.ai.ecp.app.req
 * 作者: Chrizz
 * 时间: 2016/3/24 21:26
 */
public class RCrePreOrdReqVO {

    /**
     * 购物车ID
     */
    private Long cartId;
    /**
     * 店铺促销ID
     */
    private Long promId;
    /**
     * 明细列表
     */
    private List<RCrePreOrdItemReqVO> cartItemIdList;

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public Long getPromId() {
        return promId;
    }

    public void setPromId(Long promId) {
        this.promId = promId;
    }

    public List<RCrePreOrdItemReqVO> getCartItemIdList() {
        return cartItemIdList;
    }

    public void setCartItemIdList(List<RCrePreOrdItemReqVO> cartItemIdList) {
        this.cartItemIdList = cartItemIdList;
    }
}
