package com.ai.ecp.app.resp;

import com.ai.ecp.app.resp.gds.AppVersionInfo;
import com.ailk.butterfly.app.model.AppBody;

/**
 * 类注释:
 * 项目名:pmph_android
 * 包名:com.ai.ecp.app.resp
 * 作者: Chrizz
 * 时间: 2016/4/26 10:15
 */
public class Gds017Resp extends AppBody {

    private AppVersionInfo appVersionInfo;

    public AppVersionInfo getAppVersionInfo() {
        return appVersionInfo;
    }

    public void setAppVersionInfo(AppVersionInfo appVersionInfo) {
        this.appVersionInfo = appVersionInfo;
    }
}
