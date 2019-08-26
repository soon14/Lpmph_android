package com.ai.ecp.app.resp;

import com.ailk.butterfly.app.model.AppBody;

import java.util.List;

/**
 * 类注释:
 * 项目名:pmph_android
 * 包名:com.ai.ecp.app.resp
 * 作者: Chrizz
 * 时间: 2016/4/3 15:32
 */
public class Pay003Resp extends AppBody {

    private String redisKey;
    private List<Pay00301Resp> pay00301Resps;

    public String getRedisKey() {
        return redisKey;
    }

    public void setRedisKey(String redisKey) {
        this.redisKey = redisKey;
    }

    public List<Pay00301Resp> getPay00301Resps() {
        return pay00301Resps;
    }

    public void setPay00301Resps(List<Pay00301Resp> pay00301Resps) {
        this.pay00301Resps = pay00301Resps;
    }
}
