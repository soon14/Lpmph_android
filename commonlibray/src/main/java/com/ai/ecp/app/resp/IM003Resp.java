package com.ai.ecp.app.resp;

import com.ai.ecp.app.resp.vo.IM00301VO;
import com.ailk.butterfly.app.model.AppBody;

import java.util.List;

/**
 * 类注释:
 * 项目名:pmph_android
 * 包名:com.ai.ecp.app.resp
 * 作者: Chrizz
 * 时间: 2017/2/17
 */

public class IM003Resp extends AppBody{

    private List<IM00301VO> pageResp;
    private Long count;
    private Long pageCount;

    public List<IM00301VO> getPageResp() {
        return pageResp;
    }

    public void setPageResp(List<IM00301VO> pageResp) {
        this.pageResp = pageResp;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Long getPageCount() {
        return pageCount;
    }

    public void setPageCount(Long pageCount) {
        this.pageCount = pageCount;
    }

}
