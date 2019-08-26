package com.ailk.pmph.utils;


import com.ailk.butterfly.app.model.RequestURL;

/**
 * 类描述：常量配置
 * 项目名称： pmph_android
 * Package:  com.ailk.pmph.tool
 * 创建人：   Nzke
 * 创建时间： 2016/6/6
 * 修改人：   Nzke
 * 修改时间： 2016/6/6
 * 修改备注： 2016/6/6
 * version   v1.0
 */
public class Constant {

    public static final String BUILD_TYPE_ASIA_INFO = "asiainfo";
    public static final String BUILD_TYPE_MINI = "mini";
    public static final String BUILD_TYPE_PMPH = "pmph";

    public static final String SHOP_KEY_WORD= "keyWord";
    public static final String SHOP_CATG_CODE = "catgCode";
    public static final String SHOP_CATG_NAME = "catgName";
    public static final String SHOP_ADID = "adid";
    public static final String SHOP_LINK_TYPE = "linkType";

    public static final String SHOP_GDS_ID = "gdsId";
    public static final String SHOP_SKU_ID = "skuId";

    public static final String AUTHOR_ID = "authorNameService";//查看更多作品
    public static final String AUTHOR_BOOKS = "authorBooks";

    public static final String SHOP_ID = "shopId";
    public static final String STORE_ID = "STORE_ID";
    public static final String STORE_LOGO = "STORE_LOGO";
    public static final String STORE_NAME = "STORE_NAME";
    public static final String STORE_KEY_WORDS = "STORE_KEY_WORDS";
    public static final String STORE_SOURCE = "STORE_SOURCE";//跳转来源的标识
    public static final String STORE_CLASSIFY = "STORE_CLASSIFY";//来源以分类
    public static final String STORE_SEARCH = "STORE_SEARCH";//来源以搜索

    public static final int PAGE_SIZE = 10;//每次加载更多请求的数据长度
    public static final int MAX_NAME_COUNT = 20;//收货人姓名字数限制
    public static final int MAX_PHONE_COUNT = 11;//手机号字数限制
    public static final int MAX_POST_COUNT = 6;//邮编字数限制

    public static final String SP_CODE = "SWITCH_PAY_MERGE";//合并支付开关入参
    public static final String SINA_WEIBO_APP_KEY = "1079499280";

    //==========================支付通道=============================//

    public static final String UNION_PAY_WAY_NAME = "银联商务";
    public static final String ALI_PAY_WAY_NAME = "支付宝";
    public static final String ABC_PAY_WAY_NAME = "农行支付";
    public static final String WECHAT_PAY_WAY_NAME = "微信支付";

    public static final String UNION_PAY_ID = "9002";
    public static final String ALI_PAY_ID = "9003";
    public static final String ABC_PAY_ID = "9004";
    public static final String WECHAT_PAY_ID = "9008";
    public static final String WECHAT_PAY_APPID = "wx075d4f0451b8416c";

    //==========================优惠券状态=============================//

    public static final String OPE_TYPE_OLD = "0";//已过期
    public static final String OPE_TYPE_UN_USE = "1";//未使用
    public static final String OPE_TYPE_USE = "2";//已使用

    //==========================支付地址==============================//

    public static final String ABC_TEST_PAY_URL = RequestURL.URL_HEADER + "app/pay/transfer";//农行支付url
    public static final String ABC_INT_TEST_PAY_URL = "http://192.168.1.147:38083/pmph-web-mall-point/app/pay/transfer";//农行积分商城支付url
    public static final String AI_TEST_PAY_URL = "https://121.31.32.100:8443/aipay_web/aiPay.do";//鸿支付测试环境
    public static final String AI_PAY_URL = "https://www.hongpay.com.cn/hongpay/aiPay.do";//鸿支付生产环境
    public static final String AI_NOTIFY_URL = "https://121.31.32.100:8443/aipay_web/notify.do";//鸿支付测试响应地址
    public static final String AI_PAY_TYPE = "11";

    //============================订单状态============================//

    public static final String ALL = "00";//全部
    public static final String UN_PAY = "01";//待付款
    public static final String UN_SEND = "02";//待发货
    public static final String UN_RECEIVE = "03";//待收货
    public static final String RECEIVE = "04";//已收货
    public static final String CANCEL = "05";//已取消
    public static final String SWITCH_PAY_MERGE = "SWITCH_PAY_MERGE";//合并支付开关

    //===========================SQlite==============================//

    public static final String DB_NAME = "pmph.db";
    public static final String HISTORY_TABLENAME = "searchhistory";
    public static final String CREAT_HISTORY = "create table " + HISTORY_TABLENAME
            + "(_id integer primary key autoincrement, h_name text not null unique)";

}
