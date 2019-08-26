package com.ai.ecp.app.req;

import com.ailk.butterfly.app.model.AppBody;

/**
 * Project : pmph_android
 * Created by 王可 on 2017/3/8.
 */

public class IM007Req extends AppBody{
    private String userCode;

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }
}
