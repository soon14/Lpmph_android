package com.ailk.pmph.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

import com.ai.ecp.app.req.Gds026Req;
import com.ai.ecp.app.resp.Gds026Resp;
import com.ailk.butterfly.app.model.AppHeader;
import com.ailk.butterfly.app.model.JsonService;
import com.ailk.pmph.R;
import com.ailk.pmph.base.BaseActivity;
import com.ailk.pmph.utils.Constant;
import com.ailk.pmph.utils.LogUtil;
import com.ailk.pmph.utils.ToastUtil;

import org.apache.commons.lang.StringUtils;

import butterknife.BindView;
import cn.bingoogolapple.qrcode.core.QRCodeView;

/**
 * 类注释:扫一扫
 * 项目名:pmph_android
 * 包名:com.ailk.pmph.ui.activity
 * 作者: Chrizz
 * 时间: 2016/10/5 14:49
 */

public class ScanActivity extends BaseActivity implements QRCodeView.Delegate{

    @BindView(R.id.iv_back)
    ImageView mBackIv;
    @BindView(R.id.zxingview)
    QRCodeView mQRCodeView;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_scan;
    }

    @Override
    public void initView() {
        mBackIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mQRCodeView.setDelegate(this);
    }

    @Override
    public void initData() {

    }

    @Override
    protected void onStart() {
        super.onStart();
        mQRCodeView.startCamera();
        mQRCodeView.startSpot();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mQRCodeView.stopCamera();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mQRCodeView.onDestroy();
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        vibrate();
        mQRCodeView.startSpot();
        handleResult(result);
    }

    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }

    private void handleResult(final String result) {
        if (result.startsWith("http") || result.startsWith("https")) {
            Uri uri = Uri.parse(result);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        } else {
            Gds026Req req = new Gds026Req();
            req.setBarcode(result);
            getJsonService().requestGds026(ScanActivity.this, req, true, new JsonService.CallBack<Gds026Resp>() {
                @Override
                public void oncallback(Gds026Resp gds026Resp) {
                    if (gds026Resp != null) {
                        Long gdsId = gds026Resp.getGdsId();
                        Long skuId = gds026Resp.getSkuId();
                        Bundle bundle = new Bundle();
                        if (gdsId != null) {
                            bundle.putString(Constant.SHOP_GDS_ID, String.valueOf(gdsId));
                            if (skuId != null) {
                                bundle.putString(Constant.SHOP_SKU_ID, String.valueOf(skuId));
                            }
                            launch(ShopDetailActivity.class, bundle);
                        } else {
                            ToastUtil.showCenter(ScanActivity.this, "该商品不存在");
                        }
                    }
                }

                @Override
                public void onErro(AppHeader header) {

                }
            });
        }
    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        ToastUtil.showCenter(this, "打开相机出错");
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
