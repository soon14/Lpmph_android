package com.ai.ecp.app.resp;

import com.ai.ecp.search.dubbo.search.result.CollationReuslt;
import com.ailk.butterfly.app.model.AppBody;
import com.ailk.butterfly.app.model.IBody;

import java.util.List;

/**
 * Created by HDF on 2016/6/1.
 */
public class Gds022Resp extends AppBody {

    private List<CollationReuslt> collationReuslts ;

    public List<CollationReuslt> getCollationReuslts() {
        return collationReuslts;
    }

    public void setCollationReuslts(List<CollationReuslt> collationReuslts) {
        this.collationReuslts = collationReuslts;
    }
}
