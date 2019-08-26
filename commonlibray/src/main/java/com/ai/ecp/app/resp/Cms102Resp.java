package com.ai.ecp.app.resp;

import com.ai.ecp.app.resp.cms.CategoryRespVO;
import com.ailk.butterfly.app.model.AppBody;

import java.util.List;

/**
 * 类注释:
 * 项目名:pmph_android
 * 包名:com.ai.ecp.app.resp
 * 作者: Chrizz
 * 时间: 2016/5/12 23:07
 */
public class Cms102Resp extends AppBody {

    List<CategoryRespVO> catgList;

    public List<CategoryRespVO> getCatgList() {
        return catgList;
    }

    public void setCatgList(List<CategoryRespVO> catgList) {
        this.catgList = catgList;
    }
}
