package com.ailk.butterfly.app.model;

/**
 * 类注释:
 * 项目名:pmph_android
 * 包名:com.ailk.butterfly.app.model
 * 作者: Chrizz
 * 时间: 2016/7/5 10:19
 */
public class RequestURL {

//    public static String URL_HEADER = "http://192.168.1.200:18080/ecp-web-mall/";//开发环境
//    public static String URL_HEADER = "http://192.168.1.104:8080/pmph-web-mall/";//测试环境
//    public static String URL_HEADER = "http://192.168.1.101:8080/";//程保兰
//    public static String URL_HEADER = "http://192.168.1.122:8080/ecp-web-mall/";//张晋辉
//    public static String URL_HEADER = "http://192.168.1.108:8282/ecp-web-mall/";//詹宝煌
//    public static String URL_HEADER = "http://192.168.1.127:8282/ecp-web-mall/";//詹宝煌
//    public static String URL_HEADER = "http://192.168.1.107:8080/ecp-web-mall/";//黄先龙
//    public static String URL_HEADER = "http://192.168.1.117:8080/ecp-web-mall/";//李涌
//    public static String URL_HEADER = "http://192.168.1.121:8080/ecp-web-mall/";//林伟
//    public static String URL_HEADER = "http://192.168.1.137:8080/ecp-web-mall/";//姜宗辉

//    public static String URL_HEADER = "http://shoptest1.pmph.com:19419/ecp-web-mall/";//北京测试环境
//    public static String URL_HEADER = "http://192.168.1.147:38083/pmph-web-mall/";//147服务器
//    public static String URL_HEADER = "http://124.207.3.67:11800/ecp-web-mall/";//公司云服务器
//    public static String URL_HEADER = "http://192.168.1.147:13080/ecp-web-mall/";//轻量电商服务器
//    public static String URL_HEADER = "http://124.207.3.67:11800/ecp-web-mall/";//演示服务器
//    public static String URL_HEADER = "http://10.1.235.158:9080/ecp-web-mall/";//演示内网服务器
//    public static String URL_HEADER = "http://120.132.0.66:19900/";//农村电商
    public static String URL_HEADER = "http://192.168.1.104:18020/ecp-web-mall/";
    public static String getURL() {
        return URL_HEADER + "app/json.do";
    }

}
