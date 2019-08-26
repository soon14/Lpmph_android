package com.ailk.butterfly.app.model;

import org.codehaus.jackson.annotate.JsonTypeInfo;

/**
 * Project : PMPH
 * Created by 王可 on 16/3/4.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public interface IHeader {
//    public String get(String key, String value);

    public String getBizCode();

    public String getIdentityId();

    public void setRespCode(String respCode);

    public void setRespMsg(String respMsg);
}
