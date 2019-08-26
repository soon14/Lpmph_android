package com.ailk.pmph.base;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ailk.pmph.AppContext;
import com.ailk.pmph.BuildConfig;
import com.ailk.pmph.R;
import com.ailk.pmph.ui.activity.MainActivity;
import com.ailk.pmph.ui.activity.MessageActivity;
import com.ailk.pmph.ui.activity.MyCollectionActivity;
import com.ailk.pmph.ui.activity.PmphCollectionActivity;
import com.ailk.pmph.ui.activity.SearchActivity;
import com.ailk.pmph.utils.Constant;
import com.ailk.pmph.utils.PrefUtility;

import org.apache.commons.lang.StringUtils;


import butterknife.BindView;

/**
 * 类注释:webview的基类
 * 项目名:pmph_android
 * 包名:com.ailk.pmph.base
 * 作者: Chrizz
 * 时间: 2016/9/27 14:29
 */

public class BaseWebViewActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView mBackIv;
    @BindView(R.id.tv_title)
    TextView mTitleTv;
    @BindView(R.id.index_progressBar)
    ProgressBar mProgressBar;
    @BindView(R.id.web_view)
    WebView mWebView;
    @BindView(R.id.iv_more)
    ImageView mMoreIv;

    private PopupWindow mPopupWindow;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_web_view;
    }

    @Override
    public void initView() {
        mBackIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mMoreIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupWindow(v);
            }
        });
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String mUrl = bundle.getString("url");
            Long staffId = PrefUtility.getLong("staffId", -1L);
            if (staffId > 0) {
                if (mUrl != null && mUrl.contains("?")) {
                    mUrl += "&staffId=" + staffId;
                } else {
                    mUrl += "?staffId=" + staffId;
                }
            }
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

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                mTitleTv.setText(title);
            }
        });
        initWebViewClient(mWebView);
    }

    public void initWebViewClient(WebView webView) {

    }

    @Override
    public void initData() {

    }

    private void showPopupWindow(View anchor) {
        if (null != mPopupWindow && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
            mPopupWindow = null;
        } else {
            View popView = LayoutInflater.from(this).inflate(R.layout.pop_message_layout, null);
            LinearLayout messageLayout = (LinearLayout) popView.findViewById(R.id.message_layout);
            LinearLayout homeLayout = (LinearLayout) popView.findViewById(R.id.home_layout);
            LinearLayout searchLayout = (LinearLayout) popView.findViewById(R.id.search_layout);
            LinearLayout collectLayout = (LinearLayout) popView.findViewById(R.id.collect_layout);
            mPopupWindow = new PopupWindow(popView, 400, LinearLayout.LayoutParams.WRAP_CONTENT);
            mPopupWindow.setAnimationStyle(R.style.popup_window_animation);
            mPopupWindow.setFocusable(true);
            mPopupWindow.setOutsideTouchable(true);
            mPopupWindow.setBackgroundDrawable(new ColorDrawable());
            mPopupWindow.showAsDropDown(anchor, -(mPopupWindow.getWidth()/2+100), 0);
            messageLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mPopupWindow != null) {
                        mPopupWindow.dismiss();
                    }
                    launch(MessageActivity.class);
                }
            });
            homeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mPopupWindow != null) {
                        mPopupWindow.dismiss();
                    }
                    launch(MainActivity.class);
                }
            });
            searchLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mPopupWindow != null) {
                        mPopupWindow.dismiss();
                    }
                    launch(SearchActivity.class);
                }
            });
            collectLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mPopupWindow != null) {
                        mPopupWindow.dismiss();
                    }
                    if (StringUtils.equals(BuildConfig.BUILD_TYPE, Constant.BUILD_TYPE_PMPH)) {
                        if (!AppContext.isLogin) {
                            showLoginDialog(BaseWebViewActivity.this);
                        } else {
                            launch(PmphCollectionActivity.class);
                        }
                    }
                    else {
                        if (!AppContext.isLogin) {
                            showLoginDialog(BaseWebViewActivity.this);
                        } else {
                            launch(MyCollectionActivity.class);
                        }
                    }
                }
            });
        }
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
