package com.ai.ecp.app.resp;

import java.io.Serializable;
import java.util.List;

/**
 * 类注释:
 * 项目名:pmph_android
 * 包名:com.ai.ecp.app.resp
 * 作者: Chrizz
 * 时间: 2016/4/19 13:56
 */
public class Ord00205Resp implements Serializable {

    private boolean isChoosed;
    private boolean isGdsStatus;
    private List<Ord00202Resp> groupSkus;

    public boolean isGdsStatus() {
        return isGdsStatus;
    }

    public void setGdsStatus(boolean gdsStatus) {
        isGdsStatus = gdsStatus;
    }

    public List<Ord00202Resp> getGroupSkus() {
        return groupSkus;
    }

    public void setGroupSkus(List<Ord00202Resp> groupSkus) {
        this.groupSkus = groupSkus;
    }

    public boolean isChoosed() {
        return isChoosed;
    }

    public void setIsChoosed(boolean isChoosed) {
        this.isChoosed = isChoosed;
    }
}
