package com.ai.ecp.app.resp.cms;


import com.ailk.butterfly.app.model.AppBody;

import java.util.List;

public class CategoryRespVO extends AppBody {

    private Long id;

    private String catgName;

    private String catgUrl;

    private String catgCode;

    private String vfsUrl;

    private String childStr;

    private List<CategoryRespVO> childCatg;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCatgName() {
        return catgName;
    }

    public void setCatgName(String catgName) {
        this.catgName = catgName;
    }

    public String getCatgUrl() {
        return catgUrl;
    }

    public void setCatgUrl(String catgUrl) {
        this.catgUrl = catgUrl;
    }

    public String getCatgCode() {
        return catgCode;
    }

    public void setCatgCode(String catgCode) {
        this.catgCode = catgCode;
    }

    public String getVfsUrl() {
        return vfsUrl;
    }

    public void setVfsUrl(String vfsUrl) {
        this.vfsUrl = vfsUrl;
    }

    public List<CategoryRespVO> getChildCatg() {
        return childCatg;
    }

    public void setChildCatg(List<CategoryRespVO> childCatg) {
        this.childCatg = childCatg;
    }

    public String getChildStr() {
        return childStr;
    }

    public void setChildStr(String childStr) {
        this.childStr = childStr;
    }

}

