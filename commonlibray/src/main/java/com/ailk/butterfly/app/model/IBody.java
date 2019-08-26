package com.ailk.butterfly.app.model;

import org.codehaus.jackson.annotate.JsonTypeInfo;

import java.io.Serializable;

/**
 * Project : PMPH
 * Created by 王可 on 16/3/4.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public interface IBody extends Serializable{

}
