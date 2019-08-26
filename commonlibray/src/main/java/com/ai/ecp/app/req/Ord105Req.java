package com.ai.ecp.app.req;

import java.util.List;

import com.ailk.butterfly.app.model.AppBody;
import com.ailk.butterfly.app.model.IBody;

public class Ord105Req extends AppBody {
    
    /** 
     * ord00801Req:购物车列表. 
     * @since JDK 1.6 
     */ 
    private List<RCrePreOrdReqVO> carList;
    public List<RCrePreOrdReqVO> getCarList() {
        return carList;
    }
    public void setCarList(List<RCrePreOrdReqVO> carList) {
        this.carList = carList;
    }
    
}

