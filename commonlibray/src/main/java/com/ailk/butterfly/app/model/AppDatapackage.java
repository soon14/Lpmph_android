package com.ailk.butterfly.app.model;

import org.codehaus.jackson.annotate.JsonTypeInfo;

/**
 * Project : PMPH
 * Created by 王可 on 16/3/4.
 * 由AppHeader与报体部分拼接而成
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public class AppDatapackage {
    private IHeader header;

    private IBody body;

    public IHeader getHeader() {
        return header;
    }

    public void setHeader(IHeader header) {
        this.header = header;
    }

    public IBody getBody() {
        return body;
    }

    public void setBody(IBody body) {
        this.body = body;
    }
}
