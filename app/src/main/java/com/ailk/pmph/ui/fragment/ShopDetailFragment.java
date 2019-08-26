package com.ailk.pmph.ui.fragment;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ai.ecp.app.req.Gds001Req;
import com.ai.ecp.app.resp.Gds001Resp;
import com.ailk.butterfly.app.model.AppHeader;
import com.ailk.butterfly.app.model.JsonService;
import com.ailk.pmph.R;
import com.ailk.pmph.base.BaseFragment;
import com.ailk.pmph.utils.Constant;
import com.ailk.pmph.utils.DialogUtil;
import com.ailk.pmph.utils.LogUtil;
import com.ailk.pmph.utils.StringUtil;
import com.ailk.pmph.utils.ToastUtil;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ShopDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShopDetailFragment extends BaseFragment implements View.OnClickListener{

    private static final String ARG_URL = "url";

    private String mUrl;

    private TextView tvDesc;
    private View descLine;
    private TextView tvInfo;
    private View infoLine;
    private WebView webView;
    private ImageView noContent;

    private long skuId;
    private long gdsId;


    public static ShopDetailFragment newInstance() {
        ShopDetailFragment fragment = new ShopDetailFragment();
        return fragment;
    }

    public ShopDetailFragment() {
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_web_view;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView(View view) {
        tvDesc = (TextView)view.findViewById(R.id.tv_desc);
        descLine = view.findViewById(R.id.desc_line);
        tvInfo = (TextView)view.findViewById(R.id.tv_info);
        infoLine = view.findViewById(R.id.info_line);
        webView = (WebView)view.findViewById(R.id.webview);
        noContent = (ImageView) view.findViewById(R.id.detail_nocontent);
        setGone(noContent);
        if (getArguments() != null) {
            if (StringUtil.isNotEmpty(getArguments().getString(Constant.SHOP_SKU_ID))){
                skuId = Long.parseLong(getArguments().getString(Constant.SHOP_SKU_ID));
            }
            if (StringUtil.isNotEmpty(getArguments().getString(Constant.SHOP_GDS_ID))){
                gdsId = Long.parseLong(getArguments().getString(Constant.SHOP_GDS_ID));
            }
        }

        tvDesc.setOnClickListener(this);
        tvInfo.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        getDescUrl();
        initWebView();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_desc:
                tvDesc.setTextColor(ContextCompat.getColor(getActivity(), R.color.orange_ff6a00));
                descLine.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.orange_ff6a00));
                tvInfo.setTextColor(ContextCompat.getColor(getActivity(), R.color.black));
                infoLine.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.black));
                setVisible(descLine);
                setGone(infoLine);
                getDescUrl();
                break;
            case R.id.tv_info:
                tvInfo.setTextColor(ContextCompat.getColor(getActivity(), R.color.orange_ff6a00));
                infoLine.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.orange_ff6a00));
                tvDesc.setTextColor(ContextCompat.getColor(getActivity(), R.color.black));
                descLine.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.black));
                setVisible(infoLine);
                setGone(descLine);
                getBaseInfoUrl();
                break;
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void getDescUrl() {
        Gds001Req req = new Gds001Req();
        req.setGdsId(gdsId);
        req.setSkuId(skuId);
        getJsonService().requestGds001(getActivity(), req, true, new JsonService.CallBack<Gds001Resp>() {
            @Override
            public void oncallback(Gds001Resp gds001Resp) {
                if (StringUtil.isNotEmpty(gds001Resp.getGdsDetailBaseInfo().getContentInfoUrl())){
                    setGone(noContent);
                    setVisible(webView);
                    webView.loadUrl(gds001Resp.getGdsDetailBaseInfo().getContentInfoUrl());
                } else {
                    setGone(webView);
                    setVisible(noContent);
                }
            }

            @Override
            public void onErro(AppHeader header) {

            }
        });
    }

    private void getBaseInfoUrl() {
        Gds001Req req = new Gds001Req();
        req.setGdsId(gdsId);
        req.setSkuId(skuId);
        getJsonService().requestGds001(getActivity(), req, true, new JsonService.CallBack<Gds001Resp>() {
            @Override
            public void oncallback(Gds001Resp gds001Resp) {
                if (StringUtil.isNotEmpty(gds001Resp.getGdsDetailBaseInfo().getBaseInfoUrl())){
                    setGone(noContent);
                    setVisible(webView);
                    webView.loadUrl(gds001Resp.getGdsDetailBaseInfo().getBaseInfoUrl());
                } else {
                    setGone(webView);
                    setVisible(noContent);
                }
            }

            @Override
            public void onErro(AppHeader header) {

            }
        });
    }

    @SuppressLint({ "NewApi" })
    private void initWebView() {
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setUseWideViewPort(true);//关键点

        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

        webSettings.setDisplayZoomControls(false);
        webSettings.setJavaScriptEnabled(true); // 设置支持javascript脚本
        webSettings.setAllowFileAccess(true); // 允许访问文件
        webSettings.setBuiltInZoomControls(true); // 设置显示缩放按钮
        webSettings.setSupportZoom(true); // 支持缩放

        webSettings.setLoadWithOverviewMode(true);

        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int mDensity = metrics.densityDpi;
        if (mDensity == 240) {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
        } else if (mDensity == 160) {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
        } else if(mDensity == 120) {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.CLOSE);
        }else if(mDensity == DisplayMetrics.DENSITY_XHIGH){
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
        }else if (mDensity == DisplayMetrics.DENSITY_TV){
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
        }else{
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
        }

/**
 * 用WebView显示图片，可使用这个参数 设置网页布局类型： 1、LayoutAlgorithm.NARROW_COLUMNS ：
 * 适应内容大小 2、LayoutAlgorithm.SINGLE_COLUMN:适应屏幕，内容将自动缩放
 */
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webView.setWebViewClient(new WebViewClient() {
            // 设置使用当前webview加载url
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                LogUtil.e("on_loadUrl --> " + url);
                // 调用拨号程序
                if (url.startsWith("mailto:") || url.startsWith("geo:")
                        || url.startsWith("tel:") || url.startsWith("sms:")
                        || url.startsWith("tencent:")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri
                            .parse(url));
                    try {
                        startActivity(intent);
                    } catch (Exception e) {
                        DialogUtil.showOkAlertDialog(getActivity(), "提示", "没有能应用打开链接", null);
                    }
                    return true;
                }

                webView.loadUrl(url);
                return true;
            }

            // 加载网页错误时调用
            @Override
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
                ToastUtil.show(getActivity(), errorCode + "/"
                        + description);
                webView.stopLoading();
                super.onReceivedError(view, errorCode, description, failingUrl);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }


            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

        });

    }

    private void loadUrl() {
        webView.loadUrl(mUrl);
    }

    public void setUrl(String url) {
        saveUrl(url);
        loadUrl();
    }

    private void saveUrl(String url) {
        mUrl = url;
//        Bundle args = new Bundle();
//        args.putString(ARG_URL, url);
//        setArguments(args);
    }
}
