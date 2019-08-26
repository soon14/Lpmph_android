package com.ai.ecp.app.resp.gds;

import com.ailk.butterfly.app.model.AppBody;

import java.sql.Timestamp;


public class GdsSeckillDiscountDTO extends AppBody {

    private boolean exist = false;
    private boolean start = false;
    private Timestamp systemTime;
    private GdsSecKillPromInfoDTO seckillProm;
    public boolean isExist() {
        return exist;
    }
    public void setExist(boolean exist) {
        this.exist = exist;
    }
    public boolean isStart() {
        return start;
    }
    public void setStart(boolean start) {
        this.start = start;
    }
    public GdsSecKillPromInfoDTO getSeckillProm() {
        if (seckillProm == null) {
            return new GdsSecKillPromInfoDTO(); 
        }
        return seckillProm;
    }
    public void setSeckillProm(GdsSecKillPromInfoDTO seckillProm) {
        this.seckillProm = seckillProm;
    }

    public Timestamp getSystemTime() {
        return systemTime;
    }

    public void setSystemTime(Timestamp systemTime) {
        this.systemTime = systemTime;
    }
}
