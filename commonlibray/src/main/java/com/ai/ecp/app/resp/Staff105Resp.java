package com.ai.ecp.app.resp;

import com.ailk.butterfly.app.model.AppBody;

/**
 * 类注释:
 * 项目名:pmph_android
 * 包名:com.ai.ecp.app.resp
 * 作者: Chrizz
 * 时间: 2016/4/6 21:18
 */
public class Staff105Resp extends AppBody {

    private Long id;
    private boolean flag;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
