package com.ai.ecp.app.resp;

import java.util.List;

import com.ai.ecp.app.resp.cms.CategoryRespVO;
import com.ailk.butterfly.app.model.AppBody;

/**
 * Title: ECP <br>
 * Project Name:ecp-web-mall <br>
 * Description: 获取APP全部分类服务-出参<br>
 * Date:2016-2-22下午6:53:17  <br>
 * Copyright (c) 2016 asia All Rights Reserved <br>
 * 
 * @author jiangzh
 * @version  
 * @since JDK 1.6 
 */
public class Cms007Resp extends AppBody {
    
    List<CategoryRespVO> catgList;

    public List<CategoryRespVO> getCatgList() {
        return catgList;
    }

    public void setCatgList(List<CategoryRespVO> catgList) {
        this.catgList = catgList;
    }

}

