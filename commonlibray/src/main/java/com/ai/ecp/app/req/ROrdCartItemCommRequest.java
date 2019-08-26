package com.ai.ecp.app.req;

/**
 * 类注释:
 * 项目名:PMPH
 * 包名:com.ai.ecp.app.req
 * 作者: Chrizz
 * 时间: 2016/3/23 10:06
 */
public class ROrdCartItemCommRequest {

    private Long id;
    private Long promId;
    private Long staffId;
    private Long cartId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPromId() {
        return promId;
    }

    public void setPromId(Long promId) {
        this.promId = promId;
    }

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }
}
