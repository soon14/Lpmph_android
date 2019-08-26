package com.ai.ecp.app.resp.gds;

import java.util.List;

/**
 * Created by HDF on 2016/6/2.
 */
public class GdsCategoryVO {

    public final static String SUFFIX_IMAGE_SIZE = "_220x220!";

    /**
     * 分类编码
     */
    private String catgCode;

    /**
     * 分类名称
     */
    private String catgName;

    /**
     * 分类排序
     */
    private Integer sortNo;

    /**
     * 分类图片地址
     */
    private String mediaURL;

    /**
     * 二级分类列表
     */
    private List<GdsCategoryVO> children;

    public String getCatgCode() {
        return catgCode;
    }

    public void setCatgCode(String catgCode) {
        this.catgCode = catgCode;
    }

    public String getCatgName() {
        return catgName;
    }

    public void setCatgName(String catgName) {
        this.catgName = catgName;
    }

    public List<GdsCategoryVO> getChildren() {
        return children;
    }

    public void setChildren(List<GdsCategoryVO> children) {
        this.children = children;
    }

    public Integer getSortNo() {
        return sortNo;
    }

    public void setSortNo(Integer sortNo) {
        this.sortNo = sortNo;
    }

    public String getMediaURL() {
        return mediaURL;
    }

    public void setMediaURL(String mediaURL) {
        this.mediaURL = mediaURL;
    }
}
