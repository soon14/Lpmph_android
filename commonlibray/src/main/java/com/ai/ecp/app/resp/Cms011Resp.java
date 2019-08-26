package com.ai.ecp.app.resp;

import java.util.List;

import com.ailk.butterfly.app.model.AppBody;

/**
 * Title: ECP <br>
 * Project Name:ecp-web-mall <br>
 * Description: 获取APP首页数据服务-出参<br>
 * Date:2016-3-16下午4:53:17  <br>
 * Copyright (c) 2016 asia All Rights Reserved <br>
 * 
 * @author zhanbh
 * @version  
 * @since JDK 1.6 
 */
public class Cms011Resp extends AppBody {
    
    private List<Item> itemList;


    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }
    public static class Item extends AppBody {
    
        private String imgUrl;
        private String clickUrl;
        private String name;
        private String shareKey;
        private String catgCode;
        private Long guidPrice;
        private Long discountPrice;
        private Boolean isCollect;
        
        public String getShareKey() {
            return shareKey;
        }
    
        public void setShareKey(String shareKey) {
            this.shareKey = shareKey;
        }
    
        public String getImgUrl() {
            return imgUrl;
        }
    
        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }
    
        public String getClickUrl() {
            return clickUrl;
        }
    
        public void setClickUrl(String clickUrl) {
            this.clickUrl = clickUrl;
        }
    
        public String getName() {
            return name;
        }
    
        public void setName(String name) {
            this.name = name;
        }
        
        public Long getGuidPrice() {
            return guidPrice;
        }
    
        public void setGuidPrice(Long guidPrice) {
            this.guidPrice = guidPrice;
        }
    
        public Long getDiscountPrice() {
            return discountPrice;
        }
    
        public void setDiscountPrice(Long discountPrice) {
            this.discountPrice = discountPrice;
        }
    
        public Boolean getIsCollect() {
            return isCollect;
        }
    
        public void setIsCollect(Boolean isCollect) {
            this.isCollect = isCollect;
        }
    
        public String getCatgCode() {
            return catgCode;
        }
    
        public void setCatgCode(String catgCode) {
            this.catgCode = catgCode;
        }
    
        public String toString(){
            imgUrl = imgUrl != null?imgUrl:"";
            clickUrl = clickUrl != null?clickUrl:"";
            name = name != null?name:"";
            catgCode = null != catgCode ?catgCode:"";
            return imgUrl + clickUrl + name  + catgCode;
        }
    }
        
}

