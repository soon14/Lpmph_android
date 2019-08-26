package com.ailk.pmph.ui.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ai.ecp.app.req.Pay001Req;
import com.ai.ecp.app.resp.Pay001Resp;
import com.ailk.butterfly.app.model.AppHeader;
import com.ailk.butterfly.app.model.JsonService;
import com.ailk.pmph.AppManager;
import com.ailk.pmph.R;
import com.ailk.pmph.base.BaseActivity;
import com.ailk.pmph.utils.MoneyUtils;

import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * 类注释:确认交易(单店铺正常支付)
 * 项目名:pmph_android
 * 包名:com.ailk.pmph.ui.activity
 * 作者: Chrizz
 * 时间: 2016/4/3 15:13
 */
public class DealConfirmActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.iv_back)               ImageView ivBack;
    @BindView(R.id.tv_order_code)         TextView tvOrderCode;
    @BindView(R.id.tv_order_price)        TextView tvOrderPrice;
    @BindView(R.id.btn_pay)               Button btnPay;

    private Long staffId;
    private String redisKey;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_deal_confirm;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        Bundle bundle = getIntent().getExtras();
        staffId = bundle.getLong("staffId");
        redisKey = bundle.getString("redisKey");
        Pay001Resp pay001Resp = (Pay001Resp) bundle.get("pay001Resp");
        if (pay001Resp != null) {
            if (pay001Resp.getOrderId() != null) {
                tvOrderCode.setText("订单编号"+pay001Resp.getOrderId());
            }
            if (pay001Resp.getRealMoney() != null) {
                tvOrderPrice.setText(MoneyUtils.GoodListPrice(pay001Resp.getRealMoney()));
            }
        }
    }

    @Override
    @OnClick({R.id.iv_back, R.id.btn_pay})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                launch(MainActivity.class);
                onBackPressed();
                break;
            case R.id.btn_pay:
                requestPay001Req();
                break;
            default:
                break;
        }
    }

    private void requestPay001Req() {
        Pay001Req request = new Pay001Req();
        request.setRedisKey(redisKey);
        getJsonService().requestPay001(this, request, true, new JsonService.CallBack<Pay001Resp>() {
            @Override
            public void oncallback(Pay001Resp pay001Resp) {
                Bundle extra = new Bundle();
                extra.putSerializable("pay001Resp", pay001Resp);
                launch(OrderPayActivity.class, extra);
            }

            @Override
            public void onErro(AppHeader header) {

            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            launch(MainActivity.class);
            onBackPressed();
            return false;
        }else {
            return super.onKeyDown(keyCode, event);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
