package com.ai.ecp.app.resp.ord;

import java.io.Serializable;
import java.util.Map;

/**
 * 类注释:
 * 项目名:PMPH
 * 包名:com.ai.ecp.app.resp.ord
 * 作者: Chrizz
 * 时间: 2016/3/28 16:53
 */
public class RealExpressFeesMap implements Serializable {

    private Map<Long, Long> realExpressFeesMap;

    public Map<Long, Long> getRealExpressFeesMap() {
        return realExpressFeesMap;
    }

    public void setRealExpressFeesMap(Map<Long, Long> realExpressFeesMap) {
        this.realExpressFeesMap = realExpressFeesMap;
    }
}
