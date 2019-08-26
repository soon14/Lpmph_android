package com.ai.ecp.app.resp;

import java.util.List;

import com.ailk.butterfly.app.model.AppBody;

public class Gds007Resp extends AppBody {
    private List<String> hotkeywords;

    public List<String> getHotkeywords() {
        return hotkeywords;
    }

    public void setHotkeywords(List<String> hotkeywords) {
        this.hotkeywords = hotkeywords;
    }
     
}

