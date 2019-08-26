package com.ailk.butterfly.app.model;

/**
 * 类注释:
 * 项目名:pmph_android
 * 包名:com.ailk.butterfly.app.model
 * 作者: Chrizz
 * 时间: 2016/7/5 10:20
 */
public class InteRequestURL {

//    public static String URL_HEADER = "http://192.168.1.200:16084/ecp-web-mall-point/";//开发环境
    //积分商城测试环境新地址
//    public static String URL_HEADER = "http://192.168.0.100:8081/pmph-web-mall-point/";//测试环境
//    public static String URL_HEADER = "http://shoptest1.pmph.com/ecp-web-point/";//北京测试环境
    public static String URL_HEADER = "http://jifen.pmphmall.com/";//人卫积分商城现网
    public static String getURL() {
        return URL_HEADER + "app/json.do";
    }

}
