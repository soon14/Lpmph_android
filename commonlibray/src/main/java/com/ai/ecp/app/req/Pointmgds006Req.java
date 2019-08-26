package com.ai.ecp.app.req;

import java.util.List;

import com.ai.ecp.app.req.gds.PointSearchPropReqInfo;
import com.ailk.butterfly.app.model.AppBody;

public class Pointmgds006Req extends AppBody {

   private String keyword;
   
   private String category;
   
   private int pageSize;
   
   private int pageNumber;
   
   private String field;
   
   private String sort;
   
   private String priceStart;
   
   private String priceEnd;
   
   private String gdsTypeId;
  /**
   * 价格范围 
   */
   private String rangeType;
   
   /**
    * 单个过滤和多个属性过滤都使用属性值组统一处理
    */
   private List<PointSearchPropReqInfo> propertyGroup;

public String getKeyword() {
    return keyword;
}

public void setKeyword(String keyWord) {
    this.keyword = keyWord;
}

public String getCategory() {
    return category;
}

public void setCategory(String category) {
    this.category = category;
}

public int getPageSize() {
    return pageSize;
}

public void setPageSize(int pageSize) {
    this.pageSize = pageSize;
}

public int getPageNumber() {
    return pageNumber;
}

public void setPageNumber(int pageNumber) {
    this.pageNumber = pageNumber;
}

public String getField() {
    return field;
}

public void setField(String field) {
    this.field = field;
}

public String getSort() {
    return sort;
}

public void setSort(String sort) {
    this.sort = sort;
}

public String getPriceStart() {
	return priceStart;
}

public void setPriceStart(String priceStart) {
	this.priceStart = priceStart;
}

public String getPriceEnd() {
	return priceEnd;
}

public void setPriceEnd(String priceEnd) {
	this.priceEnd = priceEnd;
}



public void setPropertyGroup(List<PointSearchPropReqInfo> propertyGroup) {
	this.propertyGroup = propertyGroup;
}

public List<PointSearchPropReqInfo> getPropertyGroup() {
	return propertyGroup;
}

public String getGdsTypeId() {
	return gdsTypeId;
}

public void setGdsTypeId(String gdsTypeId) {
	this.gdsTypeId = gdsTypeId;
}

public String getRangeType() {
	return rangeType;
}

public void setRangeType(String rangeType) {
	this.rangeType = rangeType;
}

  
}

