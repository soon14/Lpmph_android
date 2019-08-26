package com.ai.ecp.app.resp.ord;

import java.io.Serializable;
import java.util.Map;

/**
 * 类注释:
 * 项目名:PMPH
 * 包名:com.ai.ecp.app.resp.ord
 * 作者: Chrizz
 * 时间: 2016/3/28 16:54
 */
public class DeliverTypesMap implements Serializable {

    private Map<Long, String> deliverTypes;

    public Map<Long, String> getDeliverTypes() {
        return deliverTypes;
    }

    public void setDeliverTypes(Map<Long, String> deliverTypes) {
        this.deliverTypes = deliverTypes;
    }
}
