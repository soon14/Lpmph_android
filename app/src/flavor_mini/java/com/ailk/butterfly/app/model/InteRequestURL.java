package com.ailk.butterfly.app.model;

/**
 * 类注释:
 * 项目名:pmph_android
 * 包名:com.ailk.butterfly.app.model
 * 作者: Chrizz
 * 时间: 2016/7/5 10:23
 */
public class InteRequestURL {

    //    public static String URL_HEADER = "http://192.168.1.147:38083/pmph-web-mall-point/";//147服务器
//    public static String URL_HEADER = "http://10.1.235.158:9080/ecp-web-mall-point/";//公司云服务器
//    public static String URL_HEADER = "http://120.132.0.66:19910/";//农村积分商城服务器
//    public static String URL_HEADER = "http://124.207.3.67:11800/ecp-web-mall-point/";//演示积分商城服务器
        public static String URL_HEADER = "http://192.168.1.147:12080/ecp-web-mall-point/";//轻量电商积分商城服务器
//    public static String URL_HEADER = "http://221.7.252.48:14411/point/";//轻量电商外网积分商城
    public static String getURL() {
        return URL_HEADER + "app/json.do";
    }

}
