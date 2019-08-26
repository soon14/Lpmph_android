package com.ai.ecp.app.resp;

import java.util.List;

import com.ailk.butterfly.app.model.AppBody;
import com.ailk.butterfly.app.model.IBody;

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
public class Cms008Resp extends AppBody {
    
    private List<Model> datas;


    public List<Model> getDatas() {
        return datas;
    }

    public void setDatas(List<Model> datas) {
        this.datas = datas;
    }

    public static class Model extends AppBody {
        private Integer type;
        private String imgUrl;
        private String name;
        private String clickUrl;
        private String color;

        private List<Item> itemList;

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getClickUrl() {
            return clickUrl;
        }

        public void setClickUrl(String clickUrl) {
            this.clickUrl = clickUrl;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public List<Item> getItemList() {
            return itemList;
        }

        public void setItemList(List<Item> itemList) {
            this.itemList = itemList;
        }
    }

    public static class Item extends AppBody {

        private String imgUrl;
        private String clickUrl;
        private String name;
        private String shareKey;

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
        
        public String toString(){
            imgUrl = imgUrl != null?imgUrl:"";
            clickUrl = clickUrl != null?clickUrl:"";
            name = name != null?name:"";
            shareKey = shareKey != null?shareKey:"";
            return imgUrl + clickUrl + name +shareKey;
        }
        
        
        
    }
}

