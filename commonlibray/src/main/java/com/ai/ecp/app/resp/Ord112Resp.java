package com.ai.ecp.app.resp;

import com.ailk.butterfly.app.model.AppBody;

import java.util.List;

public class Ord112Resp extends AppBody {
    /** 
     * pageNo:请求查询的页码. 
     * @since JDK 1.6 
     */ 
    private int pageNo = 1;

    /** 
     * pageSize:每页显示条数. 
     * @since JDK 1.6 
     */ 
    private int pageSize;

    /** 
     * count:总条数. 
     * @since JDK 1.6 
     */ 
    private long count = 0;
    /** 
     * Ord10901Resps:订单信息列表. 
     * @since JDK 1.6 
     */ 
    private List<Ord11201Resp> Ord11201Resps;
    public int getPageNo() {
        return pageNo;
    }
    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }
    public int getPageSize() {
        return pageSize;
    }
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    public long getCount() {
        return count;
    }
    public void setCount(long count) {
        this.count = count;
    }

    public List<Ord11201Resp> getOrd11201Resps() {
        return Ord11201Resps;
    }

    public void setOrd11201Resps(List<Ord11201Resp> ord11201Resps) {
        Ord11201Resps = ord11201Resps;
    }
}

