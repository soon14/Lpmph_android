package com.ai.ecp.app.resp.ord;

import java.io.Serializable;
import java.util.Map;

/**
 * 类注释:
 * 项目名:PMPH
 * 包名:com.ai.ecp.app.resp.ord
 * 作者: Chrizz
 * 时间: 2016/3/28 16:55
 */
public class PayListMap implements Serializable {
    private Map<String, String> payList;

    public Map<String, String> getPayList() {
        return payList;
    }

    public void setPayList(Map<String, String> payList) {
        this.payList = payList;
    }
}
