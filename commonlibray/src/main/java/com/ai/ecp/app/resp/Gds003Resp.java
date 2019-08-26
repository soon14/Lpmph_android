package com.ai.ecp.app.resp;

import java.util.List;

import com.ai.ecp.app.resp.gds.GdsSeckillDiscountDTO;
import com.ai.ecp.app.resp.gds.PromListBaseInfo;
import com.ailk.butterfly.app.model.AppBody;

public class Gds003Resp extends AppBody {

    
    private List<PromListBaseInfo> saleList;

    private GdsSeckillDiscountDTO seckill;

    public List<PromListBaseInfo> getSaleList() {
        return saleList;
    }

    public void setSaleList(List<PromListBaseInfo> saleList) {
        this.saleList = saleList;
    }

    public GdsSeckillDiscountDTO getSeckill() {
        return seckill;
    }

    public void setSeckill(GdsSeckillDiscountDTO seckill) {
        this.seckill = seckill;
    }
}

