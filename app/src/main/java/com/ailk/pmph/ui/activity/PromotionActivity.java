package com.ailk.pmph.ui.activity;

import android.os.Build;
import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ai.ecp.app.req.Cms007Req;
import com.ai.ecp.app.req.Coup004Req;
import com.ai.ecp.app.resp.Cms007Resp;
import com.ai.ecp.app.resp.Coup004Resp;
import com.ailk.butterfly.app.model.AppHeader;
import com.ailk.butterfly.app.model.JsonService;
import com.ailk.pmph.AppContext;
import com.ailk.pmph.base.BaseWebViewActivity;
import com.ailk.pmph.utils.Constant;
import com.ailk.pmph.utils.ToastUtil;

import org.apache.commons.lang.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * 类注释: 活动促销页面
 * 项目名:pmph_android
 * 包名:com.ailk.pmph.ui.activity
 * 作者: Chrizz
 * 时间: 2016/8/17 9:12
 */
public class PromotionActivity extends BaseWebViewActivity {

    @Override
    public void initWebViewClient(final WebView webView) {
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                String url;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    url = request.getUrl().toString();
                    view.loadUrl(url);
                } else {
                    url = request.toString();
                    view.loadUrl(url);
                }
                //跳转商品详情
                if (StringUtils.contains(url, "gdsdetail")) {
                    Bundle bundle = new Bundle();
                    String tempStr = StringUtils.substringAfterLast(url, "/");
                    String gdsId = StringUtils.substringBefore(tempStr, "-");
                    String skuId = StringUtils.substringAfter(tempStr, "-");
                    if (StringUtils.isNotEmpty(gdsId)) {
                        bundle.putString(Constant.SHOP_GDS_ID, gdsId);
                    }
                    if (StringUtils.isNotEmpty(skuId)) {
                        bundle.putString(Constant.SHOP_SKU_ID, skuId);
                    }
                    launch(ShopDetailActivity.class, bundle);
                    view.stopLoading();
                    return true;
                }
                //跳转搜索列表
                if (StringUtils.contains(url, "search")) {
                    Bundle bundle = new Bundle();
                    String tempStr = StringUtils.substringAfter(url, "?");
                    String cateGory = null;
                    String keyWord = null;
                    if (StringUtils.contains(tempStr, "category")) {
                        cateGory = StringUtils.substringAfter(tempStr, "=");
                    }
                    if (StringUtils.contains(tempStr, "keyword")) {
                        try {
                            keyWord = URLDecoder.decode(StringUtils.substringAfter(tempStr, "="), "UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                    if (StringUtils.isNotEmpty(cateGory)) {
                        bundle.putString(Constant.SHOP_CATG_CODE, cateGory);
                    }
                    if (StringUtils.isNotEmpty(keyWord)) {
                        bundle.putString(Constant.SHOP_KEY_WORD, keyWord);
                    }
                    launch(SearchResultActivity.class, bundle);
                    view.stopLoading();
                    return true;
                }
                //领取优惠券
                if (StringUtils.contains(url, "gaincoup")) {
                    if (!AppContext.isLogin) {
                       showLoginDialog(PromotionActivity.this);
                    } else {
                        String coupId = StringUtils.substringAfter(url, "=");
                        Coup004Req req = new Coup004Req();
                        req.setCoupId(Long.parseLong(coupId));
                        getJsonService().requestCoup004(PromotionActivity.this, req, true, new JsonService.CallBack<Coup004Resp>() {
                            @Override
                            public void oncallback(Coup004Resp coup004Resp) {
                                if (StringUtils.equals(coup004Resp.getExceStatus(), "0")) {
                                    ToastUtil.showCenter(PromotionActivity.this, "领取失败");
                                } else {
                                    ToastUtil.showIconToast(PromotionActivity.this, "领取成功");
                                }
                            }

                            @Override
                            public void onErro(AppHeader header) {

                            }
                        });
                    }
                }
                //跳转主页
                if (StringUtils.contains(url, "homepage")) {
                    launch(MainActivity.class);
                    view.stopLoading();
                    return true;
                }
                //跳转至分类
                if (StringUtils.contains(url, "category")) {
                    Cms007Req cms007Req = new Cms007Req();
                    cms007Req.setPlaceId(Long.valueOf("13"));
                    getJsonService().requestCms007(PromotionActivity.this, cms007Req, false, new JsonService.CallBack<Cms007Resp>() {
                        @Override
                        public void oncallback(Cms007Resp cms007Resp) {
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("cm007", cms007Resp);
                            launch(SortActivity.class, bundle);
                        }

                        @Override
                        public void onErro(AppHeader header) {

                        }
                    });
                    view.stopLoading();
                    return true;
                }
                //跳转至排行榜
                if (StringUtils.contains(url, "ranking")) {
                    launch(RankActivity.class);
                    view.stopLoading();
                    return true;
                }
                //跳转至购物车
                if (StringUtils.contains(url, "cart")) {
                    launch(ShopCartActivity.class);
                    view.stopLoading();
                    return true;
                }
                //跳转至会员中心
                if (StringUtils.contains(url, "member")) {
                    launch(PersonalCenterActivity.class);
                    view.stopLoading();
                    return true;
                }
                return true;
            }
        });
    }

}
