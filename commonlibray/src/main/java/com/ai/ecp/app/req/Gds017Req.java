package com.ai.ecp.app.req;

import com.ailk.butterfly.app.model.AppBody;

/**
 * 类注释:
 * 项目名:pmph_android
 * 包名:com.ai.ecp.app.req
 * 作者: Chrizz
 * 时间: 2016/4/26 10:14
 */
public class Gds017Req extends AppBody {

    //版本系统类型
    private String verOs;
    //版本归属项目
    private String verProgram;
    //当前版本编号
    private Long verNo;

    public String getVerOs() {
        return verOs;
    }

    public void setVerOs(String verOs) {
        this.verOs = verOs;
    }

    public String getVerProgram() {
        return verProgram;
    }

    public void setVerProgram(String verProgram) {
        this.verProgram = verProgram;
    }

    public Long getVerNo() {
        return verNo;
    }

    public void setVerNo(Long verNo) {
        this.verNo = verNo;
    }
}
