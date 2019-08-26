package com.ai.ecp.app.resp;

import java.io.Serializable;
import java.util.List;

/**
 * 类注释:
 * 项目名:pmph_android
 * 包名:com.ai.ecp.app.resp
 * 作者: Chrizz
 * 时间: 2016/5/13 22:40
 */
public class Ord10201Resp implements Serializable{

    Long cartId;
    Long staffId;
    Long shopId;
    String shopName;
    Long promId;
    List<Ord10202Resp> ordCartItemList;
    boolean isChoosed;

    public boolean isChoosed() {
        return isChoosed;
    }

    public void setIsChoosed(boolean isChoosed) {
        this.isChoosed = isChoosed;
    }

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public Long getPromId() {
        return promId;
    }

    public void setPromId(Long promId) {
        this.promId = promId;
    }

    public List<Ord10202Resp> getOrdCartItemList() {
        return ordCartItemList;
    }

    public void setOrdCartItemList(List<Ord10202Resp> ordCartItemList) {
        this.ordCartItemList = ordCartItemList;
    }
}
