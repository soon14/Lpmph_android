package com.ailk.butterfly.app.model;


import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonTypeInfo;

import java.util.Map;

/**
 * Project : PMPH
 * Created by 王可 on 16/3/4.
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="@class")
@JsonIgnoreProperties(ignoreUnknown = true)
public class AppBody implements IBody{
    protected Map<String, Object> expand;

    public Map<String, Object> getExpand() {
        return expand;
    }

    public void setExpand(Map<String, Object> expand) {
        this.expand = expand;
    }

}
