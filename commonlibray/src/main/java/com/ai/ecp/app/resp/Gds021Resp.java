package com.ai.ecp.app.resp;

import com.ai.ecp.app.resp.gds.ShopSearchResultVO;
import com.ailk.butterfly.app.model.AppBody;

import java.util.List;

/**
 * Created by HDF on 2016/6/1.
 */
public class Gds021Resp extends AppBody {

    /**
     * 店铺列表
     */
    private  List<ShopSearchResultVO> pageRespVO ;

    /**
     * 总条数
     */
    private long count = 0;

    /**
     * 页数
     */
    private long pageCount;

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public long getPageCount() {
        return pageCount;
    }

    public void setPageCount(long pageCount) {
        this.pageCount = pageCount;
    }

    public List<ShopSearchResultVO> getPageRespVO() {
        return pageRespVO;
    }

    public void setPageRespVO(List<ShopSearchResultVO> pageRespVO) {
        this.pageRespVO = pageRespVO;
    }
}
