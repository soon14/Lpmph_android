package com.ailk.pmph.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.ailk.integral.activity.InteMainActivity;
import com.ailk.pmph.base.BaseActivity;
import com.ailk.pmph.ui.activity.SecondKillActivity;
import com.ailk.pmph.ui.activity.SignInActivity;
import com.ailk.pmph.ui.activity.SortActivity;
import com.ailk.pmph.ui.activity.SearchResultActivity;
import com.ailk.pmph.ui.activity.ShopDetailActivity;
import com.ailk.pmph.ui.activity.PromotionActivity;

import org.apache.commons.lang.StringUtils;

import java.util.HashMap;

/**
 * Project : PMPH
 * Created by 王可 on 16/3/18.
 * 用于处理clickUrl
 */
public class PromotionParseUtil {
    public static boolean parsePromotionUrl(Context context, String urlClick) {
        //默认直接消费掉事件
        return parsePromotionUrl(context, urlClick, true,true,null);
    }

    public static boolean parsePromotionUrl(Context context, String urlClick, boolean newPage, boolean nested,String shareKey) {
        if (StringUtil.isNullString(urlClick) || "null".equals(urlClick)) {
            DialogUtil.showOkAlertDialog(context, "当前未配置！", null);
            return false;
        }
        LogUtil.e("parseUrl = " + urlClick);
        // 将分割后的url,写入键值对中
        HashMap<String, String> paramsMap = paraseParams(urlClick);
        // 解析数据
        return paraseParamMap(context, paramsMap, urlClick, newPage, nested);
    }

    public static boolean paraseParamMap(Context context,
                                         HashMap<String, String> paramsMap, String urlClick, boolean newPage, boolean nested) {
        String goPage = String.valueOf(paramsMap.get("gopage"));
        if (StringUtils.equals(goPage, "goodInfo")) {
            goToGoodDetail(context, paramsMap, urlClick, goPage);
            return true;
        }
        if (StringUtils.equals(goPage, "goodList")) {
            goToGoodList(context, paramsMap, urlClick, goPage);
            return true;
        }
        if (StringUtils.equals(goPage, "categories")) {
            goToCategories(context, paramsMap, urlClick, goPage);
            return true;
        }
        if (StringUtils.equals(goPage, "webview")) {
            goToWebView(context, urlClick);
            return true;
        }
        if (StringUtils.equals(goPage, "jfmall")) {
            launch(context, InteMainActivity.class, null);
            return true;
        }
        if (StringUtils.equals(goPage, "signIn")) {
            launch(context, SignInActivity.class, null);
        }
        if (StringUtils.equals(goPage, "seckill")) {
            launch(context, SecondKillActivity.class, null);
        }
        return false;
    }

    private static void goToGoodList(Context context, HashMap<String, String> paramsMap, String urlClick, String goPage) {
        String catgCode = paramsMap.get("catgCode");
        String keyWord = paramsMap.get("keyWord");
        String adid = paramsMap.get("adid");
        Bundle bundle = new Bundle();
        if (StringUtils.isNotEmpty(catgCode)) {
            bundle.putString(Constant.SHOP_CATG_CODE, catgCode);
        }
        if (StringUtils.isNotEmpty(keyWord)) {
            bundle.putString(Constant.SHOP_KEY_WORD, keyWord);
        }
        if (StringUtils.isNotEmpty(adid)) {
            bundle.putString(Constant.SHOP_ADID, adid);
        }
        bundle.putString(Constant.SHOP_LINK_TYPE, "1");
        launch(context, SearchResultActivity.class, bundle);
    }

    private static void goToGoodDetail(Context context, HashMap<String, String> paramsMap, String urlClick, String goPage) {
        String gdsId = paramsMap.get("gdsId");
        String adid = paramsMap.get("adid");
        Bundle bundle = new Bundle();
        if (StringUtils.isNotEmpty(gdsId)) {
            bundle.putString(Constant.SHOP_GDS_ID, gdsId);
        }
        if (StringUtils.isNotEmpty(adid)) {
            bundle.putString(Constant.SHOP_ADID, adid);
        }
        bundle.putString(Constant.SHOP_LINK_TYPE, "");
        launch(context, ShopDetailActivity.class, bundle);
    }

    private static void goToCategories(Context context, HashMap<String, String> paramsMap, String urlClick, String goPage) {
        launch(context, SortActivity.class, null);
    }

    private static void goToWebView(Context context, String urlClick) {
        Bundle bundle = new Bundle();
        if (StringUtils.isNotEmpty(urlClick)) {
            bundle.putString("url", StringUtils.substringAfterLast(urlClick,"url="));
        }
        launch(context, PromotionActivity.class, bundle);
    }

    /**
     * 将数据解析添加到map里面 将字段已"="分割，前面为键，后面为值 如果没有"=",则，已"gopage"为键，字段为值
     * @param urlClick
     * @return
     */
    public static HashMap<String, String> paraseParams(String urlClick) {
        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put("gopage", UrlParamsDecoder.getWocfPage(UrlParamsDecoder.UrlPage(urlClick)));
        paramsMap.putAll(UrlParamsDecoder.URLRequest(urlClick));
        return paramsMap;
    }

    public static boolean paraseParamMap(Context context,
                                         HashMap<String, String> paramsMap, String urlClick, boolean newPage) {
        String goPage = String.valueOf(paramsMap.get("gopage"));
        LogUtil.e("goPage ->" + goPage);
        return false;
    }

    /**
     * 带参数，打开活动
     * @param context
     * @param clazz
     * @param bundle
     */
    public static void launch(Context context, Class<? extends Activity> clazz, Bundle bundle) {
        Intent intent = new Intent(context, clazz);
        if (bundle != null)
            intent.putExtras(bundle);
        if (context instanceof BaseActivity) {
            ((BaseActivity) context).launch(intent);
        } else {
            context.startActivity(intent);
        }
    }
}
