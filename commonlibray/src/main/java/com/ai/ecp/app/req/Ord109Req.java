package com.ai.ecp.app.req;

import com.ailk.butterfly.app.model.AppBody;

import java.sql.Date;

/**
 * 类注释:
 * 项目名:pmph_android
 * 包名:com.ai.ecp.app.req
 * 作者: Chrizz
 * 时间: 2016/5/11 19:12
 */
public class Ord109Req extends AppBody {

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
     * begDate:查询开始时间.
     * @since JDK 1.6
     */
    private Date begDate;

    /**
     * endDate:查询结束时间.
     * @since JDK 1.6
     */
    private Date endDate;

    /**
     * status:订单状态.  // 00-全部01-待付款02-待发货03-待收货04-已收货05-已取消
     * @since JDK 1.6
     */
    private String status;

    /**
     * siteId:站点ID.
     * @since JDK 1.6
     */
    private Long siteId;


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

    public Date getBegDate() {
        return begDate;
    }

    public void setBegDate(Date begDate) {
        this.begDate = begDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getSiteId() {
        return siteId;
    }

    public void setSiteId(Long siteId) {
        this.siteId = siteId;
    }
}
