package com.ai.ecp.app.resp;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wang on 16/3/14.
 */
public class Ord00204Resp implements Serializable{

    private boolean isChoosed;

    private List<Ord00205Resp> groupLists;

    public List<Ord00205Resp> getGroupLists() {
        return groupLists;
    }

    public void setGroupLists(List<Ord00205Resp> groupLists) {
        this.groupLists = groupLists;
    }

    public boolean isChoosed() {
        return isChoosed;
    }

    public void setIsChoosed(boolean isChoosed) {
        this.isChoosed = isChoosed;
    }
}
