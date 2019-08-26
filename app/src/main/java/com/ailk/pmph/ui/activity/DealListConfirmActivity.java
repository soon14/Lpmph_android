package com.ailk.pmph.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ai.ecp.app.req.Pay002Req;
import com.ai.ecp.app.resp.Ord01201Resp;
import com.ai.ecp.app.resp.Pay002Resp;
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
 * 类注释:确认交易（从我的订单进入）
 * 项目名:pmph_android
 * 包名:com.ailk.pmph.ui.activity
 * 作者: Chrizz
 * 时间: 2016/4/8 23:48
 */
public class DealListConfirmActivity extends BaseActivity implements View.OnClickListener{

    @BindView(R.id.iv_back)               ImageView ivBack;
    @BindView(R.id.tv_order_code)         TextView tvOrderCode;
    @BindView(R.id.tv_order_price)        TextView tvOrderPrice;
    @BindView(R.id.btn_pay)               Button btnPay;

    private Ord01201Resp shop;

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
        shop = (Ord01201Resp) bundle.get("shop");
        if (shop != null) {
            if (shop.getOrderId() != null) {
                tvOrderCode.setText("订单编号"+shop.getOrderId());
            }
            if (shop.getRealMoney() != null) {
                tvOrderPrice.setText(MoneyUtils.GoodListPrice(shop.getRealMoney()));
            }
        }
    }

    @Override
    @OnClick({R.id.iv_back, R.id.btn_pay})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.btn_pay:
                requestPayMoney(shop);
                break;
            default:
                break;
        }
    }

    private void requestPayMoney(final Ord01201Resp shop){
        Pay002Req request = new Pay002Req();
        request.setOrderId(shop.getOrderId());
        request.setShopId(shop.getShopId());
        getJsonService().requestPay002(this, request, true, new JsonService.CallBack<Pay002Resp>() {
            @Override
            public void oncallback(Pay002Resp pay002Resp) {
                Bundle extra = new Bundle();
                extra.putLong("realMoney",shop.getRealMoney());
                extra.putSerializable("pay002Resp", pay002Resp);
                launch(OrderListPayActivity.class, extra);
            }

            @Override
            public void onErro(AppHeader header) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
