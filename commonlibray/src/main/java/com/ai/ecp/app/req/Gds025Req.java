package com.ai.ecp.app.req;

import com.ailk.butterfly.app.model.AppBody;

/**
 * Created by HDF on 2016/6/1.
 */
public class Gds025Req extends AppBody {

    //店铺id
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
