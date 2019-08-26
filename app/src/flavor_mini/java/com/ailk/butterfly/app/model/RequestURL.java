package com.ailk.butterfly.app.model;

/**
 * 类注释:
 * 项目名:pmph_android
 * 包名:com.ailk.butterfly.app.model
 * 作者: Chrizz
 * 时间: 2016/7/5 10:22
 */
public class RequestURL {

//    public static String URL_HEADER = "http://192.168.1.101:8080/";//程保兰
//    public static String URL_HEADER = "http://192.168.1.123:8080/ecp-web-mall/";//詹宝煌
//    public static String URL_HEADER = "http://192.168.1.107:8080/ecp-web-mall/";//黄先龙
//    public static String URL_HEADER = "http://192.168.1.121:8080/ecp-web-mall/";//李涌
//    public static String URL_HEADER = "http://192.168.1.121:8080/ecp-web-mall/";//林伟
//    public static String URL_HEADER = "http://chenjftest.ittun.com/ecp-web-mall/";//陈聚福

    //    public static String URL_HEADER = "http://shoptest1.pmph.com:19419/ecp-web-mall/";//北京测试环境
//    public static String URL_HEADER = "http://192.168.1.147:38083/pmph-web-mall/";//147服务器
//    public static String URL_HEADER = "http://124.207.3.67:11800/ecp-web-mall/";//公司云服务器
    public static String URL_HEADER = "http://192.168.1.147:13080/ecp-web-mall/";//轻量电商服务器
//    public static String URL_HEADER = "http://124.207.3.67:11800/ecp-web-mall/";//演示服务器
    //    public static String URL_HEADER = "http://10.1.235.158:9080/ecp-web-mall/";//演示内网服务器
//    public static String URL_HEADER = "http://120.132.0.66:19900/";//农村电商
//    public static String URL_HEADER = "http://221.7.252.48:14411/mall/";//轻量电商外网
//    public static String URL_HEADER = "http://112.74.184.23/mall/";//全州环境
    public static String getURL() {
        return URL_HEADER + "app/json.do";
    }

}
