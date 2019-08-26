package com.ai.ecp.app.resp;

import java.io.Serializable;

/**
 * Created by wang on 16/3/14.
 */
public class Ord00203Resp implements Serializable{

    private boolean isChoosed;
    /**
     * 促销id
     */
    private Long id;
    /**
     * 促销简称
     */
    private String nameShort;
    /**
     * 促销名称
     */
    private String promTheme;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameShort() {
        return nameShort;
    }

    public void setNameShort(String nameShort) {
        this.nameShort = nameShort;
    }

    public String getPromTheme() {
        return promTheme;
    }

    public void setPromTheme(String promTheme) {
        this.promTheme = promTheme;
    }

    public boolean isChoosed() {
        return isChoosed;
    }

    public void setIsChoosed(boolean isChoosed) {
        this.isChoosed = isChoosed;
    }
}
