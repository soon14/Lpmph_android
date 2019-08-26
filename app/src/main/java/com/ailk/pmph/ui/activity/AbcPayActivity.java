package com.ailk.pmph.ui.activity;

import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ailk.pmph.base.BaseHtmlActivity;

import org.apache.commons.lang.StringUtils;

/**
 * 类注释: 农行支付页面
 * 项目名:pmph_android
 * 包名:com.ailk.pmph.ui.activity
 * 作者: Chrizz
 * 时间: 2016/9/26 16:25
 */

public class AbcPayActivity extends BaseHtmlActivity {

    public void initWebViewClient(final WebView webView) {
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                webView.loadUrl(url);
                if (StringUtils.contains(url, "payresult/9004")) {
                    launch(MainActivity.class);
                }
                return true;
            }
        });
    }


}
