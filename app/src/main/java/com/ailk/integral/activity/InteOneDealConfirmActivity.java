package com.ailk.integral.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ai.ecp.app.req.Pay101Req;
import com.ai.ecp.app.resp.Pay101Resp;
import com.ailk.butterfly.app.model.AppHeader;
import com.ailk.butterfly.app.model.InteJsonService;
import com.ailk.pmph.R;
import com.ailk.pmph.utils.MoneyUtils;
import com.ailk.pmph.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 类注释:
 * 项目名:pmph_android
 * 包名:com.ailk.integral.act
 * 作者: Chrizz
 * 时间: 2016/5/19 16:00
 */
public class InteOneDealConfirmActivity extends BaseActivity implements View.OnClickListener{

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_order_code)
    TextView tvOrderCode;
    @BindView(R.id.tv_order_price)
    TextView tvOrderPrice;
    @BindView(R.id.btn_pay)
    Button btnPay;

    private String redisKey;

    @Override
    protected int getContentViewId() {
        return R.layout.inte_activity_one_deal_confirm;
    }

    @Override
    public void initView() {

    }

    public void initData(){
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            redisKey = bundle.getString("redisKey");
            Pay101Resp pay101Resp = (Pay101Resp) bundle.get("pay101Resp");
            Long orderScores = bundle.getLong("orderScores");
            if (pay101Resp != null) {
                tvOrderCode.setText("订单编号"+pay101Resp.getOrderId());
                tvOrderPrice.setText(orderScores+"积分+"+MoneyUtils.GoodListPrice(pay101Resp.getRealMoney()));
            }
        }
    }

    @Override
    @OnClick({R.id.iv_back, R.id.btn_pay})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                launch(InteMainActivity.class);
                onBackPressed();
                break;
            case R.id.btn_pay:
                requestPayOneShop();
                break;
        }
    }

    private void requestPayOneShop(){
        Pay101Req req = new Pay101Req();
        req.setRedisKey(redisKey);
        getInteJsonService().requestPay101(this, req, true, new InteJsonService.CallBack<Pay101Resp>() {
            @Override
            public void oncallback(Pay101Resp pay101Resp) {
                Bundle extra = new Bundle();
                extra.putSerializable("pay101Resp", pay101Resp);
                launch(InteOnePayActivity.class, extra);
            }

            @Override
            public void onErro(AppHeader header) {

            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            launch(InteMainActivity.class);
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
