package com.ai.ecp.app.resp;

import com.ailk.butterfly.app.model.AppBody;

import java.util.List;

public class Ord012Resp extends AppBody {
    /** 
     * pageNo:请求查询的页码. 
     * @since JDK 1.6 
     */ 
    private Integer pageNo = 1;

    /** 
     * pageSize:每页显示条数. 
     * @since JDK 1.6 
     */ 
    private Integer pageSize;

    /** 
     * count:总条数. 
     * @since JDK 1.6 
     */ 
    private long count = 0;
    /** 
     * Ord01201Resps:订单信息列表. 
     * @since JDK 1.6 
     */ 
    private List<Ord01201Resp> Ord01201Resps;
    public Integer getPageNo() {
        return pageNo;
    }
    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }
    public Integer getPageSize() {
        return pageSize;
    }
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
    public long getCount() {
        return count;
    }
    public void setCount(long count) {
        this.count = count;
    }
    public List<Ord01201Resp> getOrd01201Resps() {
        return Ord01201Resps;
    }
    public void setOrd01201Resps(List<Ord01201Resp> ord01201Resps) {
        Ord01201Resps = ord01201Resps;
    }

}

