package com.ai.ecp.app.req;

import com.ailk.butterfly.app.model.AppBody;

import java.util.List;

public class Ord008Req extends AppBody {
    /**
     * 购物车列表
     */
    private List<RCrePreOrdReqVO> carList;

    public List<RCrePreOrdReqVO> getCarList() {
        return carList;
    }

    public void setCarList(List<RCrePreOrdReqVO> carList) {
        this.carList = carList;
    }
}

