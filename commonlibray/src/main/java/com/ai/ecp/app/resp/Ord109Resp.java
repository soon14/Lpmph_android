package com.ai.ecp.app.resp;

import com.ailk.butterfly.app.model.AppBody;

import java.util.List;

/**
 * 类注释:
 * 项目名:pmph_android
 * 包名:com.ai.ecp.app.resp
 * 作者: Chrizz
 * 时间: 2016/5/11 19:13
 */
public class Ord109Resp extends AppBody {

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
    private List<Ord10901Resp> Ord10901Resps;
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
    public List<Ord10901Resp> getOrd10901Resps() {
        return Ord10901Resps;
    }
    public void setOrd10901Resps(List<Ord10901Resp> ord10901Resps) {
        Ord10901Resps = ord10901Resps;
    }

}
