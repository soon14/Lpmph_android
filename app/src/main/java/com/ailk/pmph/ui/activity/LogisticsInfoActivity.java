package com.ailk.pmph.ui.activity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.ailk.pmph.R;
import com.ailk.pmph.base.BaseActivity;

import org.apache.commons.lang.StringUtils;

import butterknife.BindView;

/**
 * 类注释:物流信息
 * 项目名:pmph_android
 * 包名:com.ailk.pmph.ui.activity
 * 作者: Chrizz
 * 时间: 2016/12/14
 */

public class LogisticsInfoActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView mBackIv;
    @BindView(R.id.index_progressBar)
    ProgressBar mProgressBar;
    @BindView(R.id.web_view)
    WebView mWebView;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_logistics;
    }

    @Override
    public void initView() {
        mBackIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String mUrl = bundle.getString("url");
            if (StringUtils.isNotEmpty(mUrl)) {
                WebSettings webSettings = mWebView.getSettings();
                webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
                webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
                webSettings.setUseWideViewPort(true);
                webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
                webSettings.setDisplayZoomControls(false);
                webSettings.setJavaScriptEnabled(true); // 设置支持javascript脚本
                webSettings.setAllowFileAccess(true); // 允许访问文件
                webSettings.setBuiltInZoomControls(true); // 设置显示缩放按钮
                webSettings.setSupportZoom(true); // 支持缩放
                webSettings.setLoadWithOverviewMode(true);

                int screenDensity = getResources().getDisplayMetrics().densityDpi ;
                WebSettings.ZoomDensity zoomDensity = WebSettings.ZoomDensity.MEDIUM ;
                switch (screenDensity){
                    case DisplayMetrics.DENSITY_LOW :
                        zoomDensity = WebSettings.ZoomDensity.CLOSE;
                        break;
                    case DisplayMetrics.DENSITY_MEDIUM:
                        zoomDensity = WebSettings.ZoomDensity.MEDIUM;
                        break;
                    case DisplayMetrics.DENSITY_HIGH:
                        zoomDensity = WebSettings.ZoomDensity.FAR;
                        break ;
                }
                webSettings.setDefaultZoom(zoomDensity);
                mWebView.loadUrl(mUrl);
            }
        }
        mWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                mProgressBar.setProgress(newProgress);
                if (newProgress >= 100) {
                    mProgressBar.setVisibility(View.GONE);
                }
            }
        });
        initWebViewClient(mWebView);
    }

    public void initWebViewClient(final WebView webView) {
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                webView.loadUrl(url);
                return true;
            }
        });
    }

    @Override
    public void initData() {

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onBackPressed();
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

}
