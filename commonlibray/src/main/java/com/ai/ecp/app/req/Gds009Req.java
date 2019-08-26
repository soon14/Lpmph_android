package com.ai.ecp.app.req;

import java.util.List;

import com.ailk.butterfly.app.model.AppBody;

public class Gds009Req extends AppBody {

  
    
    private Long staffId;
    
    private int pageNumber;
    
    private int pageSize;


    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }


}

