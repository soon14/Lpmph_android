package com.ailk.im.tool;


import android.support.annotation.NonNull;

import com.ailk.butterfly.app.model.BaseConstant;

/**
 * Project : XHS
 * Created by 王可 on 2016/11/1.
 */

public class IMConstant extends BaseConstant{
//http://host:port/ecp-web-im/app/upload/image
    public static final String UPLOAD_IMG_URL = URL_HEADER + "app/upload/image";
//    public static final String IMG_URL_HEADER = "http://192.168.1.104:9000/imageServer/image/";//福州104
//    public static final String IMG_URL_HEADER = "http://shoptest1.pmph.com/imageServer/image/";//北京测试
    public static final String IMG_URL_HEADER = "http://image.pmph.com/imageServer/image/";//现网
    public static final int SELECT_PICTURE = 101;
    public static final int SELECT_PICTURE_KITKAT = 102;
    public static final int TAKE_PICTURE = 103;
    public static final int PICK_LOCATION = 104;
    public static final int REQUEST_TAKE_PHOTO = 105;

//    public static String openfireHost = "192.168.1.104";//福州104
//    public static String openfireHost = "119.254.225.94";//北京测试
    public static String openfireHost = "119.254.226.96";//现网

//    public static String domain = "imserver";//福州104
//    public static String domain = "imsever";//北京测试
    public static String domain = "pmph1web2";//现网
    public static String resource = "APP";
    public static String otherResource = "WEB";

    public static String makeMyDomainResource(){
        return "@" + IMConstant.domain + "/" + IMConstant.resource;
    }
    public static String makeOtherDomainResource(){
        return "@" + IMConstant.domain + "/" + IMConstant.otherResource;
    }

    public static String makeImageUrl(@NonNull String fileId){
        return IMG_URL_HEADER + fileId + ".jpg";
    }




}
