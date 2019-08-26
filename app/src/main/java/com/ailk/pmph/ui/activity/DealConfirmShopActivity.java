package com.ailk.pmph.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.ai.ecp.app.req.Pay002Req;
import com.ai.ecp.app.resp.Pay002Resp;
import com.ai.ecp.app.resp.Pay00301Resp;
import com.ai.ecp.app.resp.Pay003Resp;
import com.ailk.butterfly.app.model.AppHeader;
import com.ailk.butterfly.app.model.JsonService;
import com.ailk.pmph.AppManager;
import com.ailk.pmph.R;
import com.ailk.pmph.base.BaseActivity;
import com.ailk.pmph.utils.ToastUtil;
import com.ailk.pmph.ui.adapter.DealShopListAdapter;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * 类注释:确认交易（多店铺）
 * 项目名:pmph_android
 * 包名:com.ailk.pmph.ui.activity
 * 作者: Chrizz
 * 时间: 2016/4/10 16:21
 */
public class DealConfirmShopActivity extends BaseActivity implements View.OnClickListener,
        DealShopListAdapter.CheckPayForShopInterface{

    @BindView(R.id.iv_back)           ImageView ivBack;
    @BindView(R.id.lv_shop_list)      ListView lvShopList;
    @BindView(R.id.btn_pay)           Button btnPay;

    private DealShopListAdapter adapter;
    private long lastRequest = 0;
    private List<Pay00301Resp> list;
    private List<Pay00301Resp> payList;


    @Override
    protected int getContentViewId() {
        return R.layout.activity_deal_confirm_shop;
    }

    @Override
    public void initView() {
        Pay003Resp pay003Resp = (Pay003Resp) getIntent().getExtras().get("pay003Resp");
        if (pay003Resp != null) {
            list = pay003Resp.getPay00301Resps();
        }
        payList = new ArrayList<>();
        if (list != null && list.size() != 0) {
            adapter = new DealShopListAdapter(this, list);
            adapter.setCheckPayForShopInterface(this);
            lvShopList.setAdapter(adapter);
            for (int i = 0; i < list.size(); i++) {
                Pay00301Resp bean = list.get(i);
                bean.setIsChoosed(true);
                payList.add(bean);
            }
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void initData() {

    }

    @Override
    public void checkPayForShop(Pay00301Resp bean, boolean isChecked) {
        if (isChecked) {
            payList.add(bean);
            adapter.notifyDataSetChanged();
        } else {
            payList.remove(bean);
            adapter.notifyDataSetChanged();
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
                requestPay002(true);
                break;
        }
    }

    private void requestPay002(boolean isShowProgress){
        if (payList.size()==0) {
            ToastUtil.showCenter(this,"亲,您还没有选择要支付的店铺哦");
            return;
        }
        if (System.currentTimeMillis() - lastRequest < 1000) {
            return;
        }
        lastRequest = System.currentTimeMillis();
        StringBuilder builder = new StringBuilder();
        Pay002Req req = new Pay002Req();
        if (payList.size() == 1) {
            req.setOrderId(payList.get(0).getOrderId());
        }
        else if (payList.size() > 1) {
            for (int i = 0; i < payList.size(); i++) {
                Pay00301Resp pay = payList.get(i);
                builder.append(pay.getOrderId()).append(":");
                req.setOrderId(StringUtils.substringBeforeLast(builder.toString(),":"));
            }
        }
        getJsonService().requestPay002(this, req, isShowProgress, new JsonService.CallBack<Pay002Resp>() {
            @Override
            public void oncallback(Pay002Resp pay002Resp) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("pay002Resp", pay002Resp);
                bundle.putLong("realMoney", pay002Resp.getMergeTotalRealMoney());
                launch(OrderPayShopActivity.class,bundle);
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
