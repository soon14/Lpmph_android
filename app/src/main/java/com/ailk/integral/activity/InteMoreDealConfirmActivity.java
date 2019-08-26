package com.ailk.integral.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.ai.ecp.app.req.Pay102Req;
import com.ai.ecp.app.resp.Pay102Resp;
import com.ai.ecp.app.resp.Pay10301Resp;
import com.ai.ecp.app.resp.Pay103Resp;
import com.ailk.butterfly.app.model.AppHeader;
import com.ailk.butterfly.app.model.InteJsonService;
import com.ailk.integral.adapter.InteMoreDealConfirmListAdapter;
import com.ailk.pmph.R;
import com.ailk.pmph.utils.ToastUtil;
import com.ailk.pmph.base.BaseActivity;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 类注释:
 * 项目名:pmph_android
 * 包名:com.ailk.integral.act
 * 作者: Chrizz
 * 时间: 2016/5/19 19:05
 */
public class InteMoreDealConfirmActivity extends BaseActivity implements View.OnClickListener,
        InteMoreDealConfirmListAdapter.CheckPayForShopInterface{

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.lv_shop_list)
    ListView lvShopList;
    @BindView(R.id.btn_pay)
    Button btnPay;

    private InteMoreDealConfirmListAdapter adapter;
    private List<Pay10301Resp> payList;
    private long lastRequest = 0;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_deal_confirm_shop;
    }

    public void initView() {
        payList = new ArrayList<>();
        Pay103Resp pay103Resp = (Pay103Resp) getIntent().getExtras().get("pay103Resp");
        if (pay103Resp != null) {
            if (pay103Resp.getPay10301Resps()!=null && pay103Resp.getPay10301Resps().size() != 0) {
                adapter = new InteMoreDealConfirmListAdapter(this, pay103Resp.getPay10301Resps());
                adapter.setCheckPayForShopInterface(this);
                lvShopList.setAdapter(adapter);
                for (int i = 0; i < pay103Resp.getPay10301Resps().size(); i++) {
                    Pay10301Resp bean = pay103Resp.getPay10301Resps().get(i);
                    bean.setIsChoosed(true);
                    payList.add(bean);
                }
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void initData() {

    }

    @Override
    public void checkPayForShop(Pay10301Resp bean, boolean isChecked) {
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
                launch(InteMainActivity.class);
                onBackPressed();
                break;
            case R.id.btn_pay:
                requestPay102(true);
                break;
        }
    }

    private void requestPay102(boolean isShowProgress){
        if (payList.size()==0) {
            ToastUtil.showCenter(this,"亲,您还没有选择要支付的店铺哦");
            return;
        }
        if (System.currentTimeMillis() - lastRequest < 1000) {
            return;
        }
        lastRequest = System.currentTimeMillis();
        StringBuilder builder = new StringBuilder();
        Pay102Req req = new Pay102Req();
        if (payList.size() == 1) {
            req.setOrderId(payList.get(0).getOrderId());
        }
        else if (payList.size() > 1) {
            for (int i = 0; i < payList.size(); i++) {
                Pay10301Resp pay = payList.get(i);
                builder.append(pay.getOrderId()).append(":");
                req.setOrderId(StringUtils.substringBeforeLast(builder.toString(),":"));
            }
        }
        getInteJsonService().requestPay102(this, req, isShowProgress, new InteJsonService.CallBack<Pay102Resp>() {
            @Override
            public void oncallback(Pay102Resp pay102Resp) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("pay102Resp", pay102Resp);
                bundle.putLong("realMoney", pay102Resp.getMergeTotalRealMoney());
                launch(InteMorePayActivity.class,bundle);
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
