package com.ailk.integral.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.ai.aipay.AiPayActivity;
import com.ai.ecp.app.req.Pay004Req;
import com.ai.ecp.app.req.Pay104Req;
import com.ai.ecp.app.resp.Pay004Resp;
import com.ai.ecp.app.resp.Pay101Resp;
import com.ai.ecp.app.resp.Pay102Resp;
import com.ai.ecp.app.resp.Pay104Resp;
import com.ai.ecp.app.resp.PayWayItem;
import com.ai.ecp.app.resp.PayWayResponse;
import com.ailk.butterfly.app.model.AppBody;
import com.ailk.butterfly.app.model.AppHeader;
import com.ailk.butterfly.app.model.InteJsonService;
import com.ailk.butterfly.app.model.JsonService;
import com.ailk.im.net.NetCenter;
import com.ailk.pmph.AppContext;
import com.ailk.pmph.BuildConfig;
import com.ailk.pmph.R;
import com.ailk.pmph.ui.activity.AbcPayActivity;
import com.ailk.pmph.utils.Constant;
import com.ailk.pmph.utils.AliPayUtils;
import com.ailk.pmph.utils.DialogAnotherUtil;
import com.ailk.pmph.utils.DialogUtil;
import com.ailk.pmph.utils.AiPayResult;
import com.ailk.pmph.utils.LogUtil;
import com.ailk.pmph.utils.MoneyUtils;
import com.ailk.pmph.utils.TDevice;
import com.ailk.pmph.utils.ToastUtil;
import com.ailk.pmph.base.BaseActivity;
import com.ailk.pmph.ui.activity.MainActivity;
import com.ailk.tool.GlideUtil;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * 类注释:
 * 项目名:pmph_android
 * 包名:com.ailk.integral.act
 * 作者: Chrizz
 * 时间: 2016/5/19 19:15
 */
public class InteMorePayActivity extends BaseActivity implements View.OnClickListener{

    @BindView(R.id.iv_back)               ImageView ivBack;
    @BindView(R.id.tv_real_money)         TextView tvRealMoney;
    @BindView(R.id.ll_hong_layout)        LinearLayout llHongLayout;
    @BindView(R.id.iv_hong_pay)           ImageView ivHongImg;
    @BindView(R.id.tv_hong_pay_name)      TextView tvHongPayName;
    @BindView(R.id.rb_hong)               RadioButton rbHong;
    @BindView(R.id.ll_ali_layout)         LinearLayout llAliLayout;
    @BindView(R.id.iv_ali_pay)            ImageView ivAliImg;
    @BindView(R.id.tv_ali_pay)            TextView tvAliPayName;
    @BindView(R.id.rb_ali)                RadioButton rbAli;
    @BindView(R.id.ll_abc_layout)         LinearLayout llAbcLayout;
    @BindView(R.id.iv_abc_pay)            ImageView ivAbcImg;
    @BindView(R.id.tv_abc_pay)            TextView tvAbcPayName;
    @BindView(R.id.rb_abc)                RadioButton rbAbc;
    @BindView(R.id.ll_wechat_layout)      LinearLayout llWechatLayout;
    @BindView(R.id.iv_wechat_pay)         ImageView ivWechatImg;
    @BindView(R.id.tv_wevchat_pay)        TextView tvWechatPayName;
    @BindView(R.id.rb_wechat)             RadioButton rbWechat;
    @BindView(R.id.btn_confirm)           Button btnConfirm;

    private Pay102Resp pay102Resp;
    private Long orderScore;
    private String abcPayWayType;
    List<PayWayResponse> wayList = new ArrayList<>();
    Map<Object,Object> formMap;
    private long lastRequest = 0;
    private static final int CLICK_INTERVAL = 1000;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_orderpay;
    }

    public void initView() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            pay102Resp = (Pay102Resp) bundle.get("pay102Resp");
            Long realMoney = bundle.getLong("realMoney");
            orderScore = bundle.getLong("orderScore");
            tvRealMoney.setText(MoneyUtils.GoodListPrice(realMoney));
        }
        if (pay102Resp != null) {
            wayList = pay102Resp.getWayList();
        }
        if (wayList.size() > 0) {
            for (int i = 0; i < wayList.size(); i++) {
                PayWayResponse payWayResponse = wayList.get(i);
                for (int j = 0; j < payWayResponse.getPayWayList().size(); j++) {
                    PayWayItem payWayItem = payWayResponse.getPayWayList().get(j);
                    if (StringUtils.equals(Constant.UNION_PAY_ID, payWayItem.getId())) {
                        setVisible(llHongLayout);
                        GlideUtil.loadImg(this, payWayItem.getPayImage(), ivHongImg);
                        tvHongPayName.setText(Constant.UNION_PAY_WAY_NAME);
                    }
                    if (StringUtils.equalsIgnoreCase(Constant.ALI_PAY_ID, payWayItem.getId())) {
                        setVisible(llAliLayout);
                        GlideUtil.loadImg(this, payWayItem.getPayImage(), ivAliImg);
                        tvAliPayName.setText(payWayItem.getPayWayName());
                    }
                    if (StringUtils.equalsIgnoreCase(Constant.ABC_PAY_ID, payWayItem.getId())) {
                        setVisible(llAbcLayout);
                        abcPayWayType = payWayItem.getId();
                        GlideUtil.loadImg(this, payWayItem.getPayImage(), ivAbcImg);
                        tvAbcPayName.setText(payWayItem.getPayWayName());
                    }
                    if (StringUtils.equalsIgnoreCase(Constant.WECHAT_PAY_ID, payWayItem.getId())) {
                        setVisible(llWechatLayout);
                        GlideUtil.loadImg(this, payWayItem.getPayImage(), ivWechatImg);
                        tvWechatPayName.setText("微信支付");
                    }
                }
            }
        }
        formMap = new HashMap<>();
    }

    @Override
    public void initData() {

    }

    @Override
    @OnClick({R.id.iv_back, R.id.rb_hong, R.id.ll_hong_layout, R.id.rb_ali, R.id.ll_ali_layout,
            R.id.rb_abc, R.id.ll_abc_layout, R.id.rb_wechat, R.id.ll_wechat_layout, R.id.btn_confirm})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                DialogAnotherUtil.showCustomAlertDialogWithMessage(this, null,
                        "确认放弃支付？", "确认离开", "继续支付",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DialogUtil.dismissDialog();
                                onBackPressed();
                            }
                        },
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DialogUtil.dismissDialog();
                            }
                        });
                break;
            case R.id.rb_hong:
                rbHong.setChecked(true);
                rbAli.setChecked(false);
                rbAbc.setChecked(false);
                rbWechat.setChecked(false);
                rbAli.setBackgroundResource(R.drawable.radio_normal);
                rbAbc.setBackgroundResource(R.drawable.radio_normal);
                rbWechat.setBackgroundResource(R.drawable.radio_normal);
                break;
            case R.id.ll_hong_layout:
                rbHong.setChecked(true);
                rbAli.setChecked(false);
                rbAbc.setChecked(false);
                rbWechat.setChecked(false);
                rbAli.setBackgroundResource(R.drawable.radio_normal);
                rbAbc.setBackgroundResource(R.drawable.radio_normal);
                rbWechat.setBackgroundResource(R.drawable.radio_normal);
                break;
            case R.id.rb_ali:
                rbHong.setChecked(false);
                rbAli.setChecked(true);
                rbAbc.setChecked(false);
                rbWechat.setChecked(false);
                rbHong.setBackgroundResource(R.drawable.radio_normal);
                rbAbc.setBackgroundResource(R.drawable.radio_normal);
                rbWechat.setBackgroundResource(R.drawable.radio_normal);
                break;
            case R.id.ll_ali_layout:
                rbHong.setChecked(false);
                rbAli.setChecked(true);
                rbAbc.setChecked(false);
                rbWechat.setChecked(false);
                rbHong.setBackgroundResource(R.drawable.radio_normal);
                rbAbc.setBackgroundResource(R.drawable.radio_normal);
                rbWechat.setBackgroundResource(R.drawable.radio_normal);
                break;
            case R.id.rb_abc:
                rbHong.setChecked(false);
                rbAli.setChecked(false);
                rbAbc.setChecked(true);
                rbWechat.setChecked(false);
                rbAli.setBackgroundResource(R.drawable.radio_normal);
                rbHong.setBackgroundResource(R.drawable.radio_normal);
                rbWechat.setBackgroundResource(R.drawable.radio_normal);
                break;
            case R.id.ll_abc_layout:
                rbHong.setChecked(false);
                rbAli.setChecked(false);
                rbAbc.setChecked(true);
                rbWechat.setChecked(false);
                rbAli.setBackgroundResource(R.drawable.radio_normal);
                rbHong.setBackgroundResource(R.drawable.radio_normal);
                rbWechat.setBackgroundResource(R.drawable.radio_normal);
                break;
            case R.id.rb_wechat:
                rbHong.setChecked(false);
                rbAli.setChecked(false);
                rbAbc.setChecked(false);
                rbWechat.setChecked(true);
                rbAli.setBackgroundResource(R.drawable.radio_normal);
                rbHong.setBackgroundResource(R.drawable.radio_normal);
                rbAbc.setBackgroundResource(R.drawable.radio_normal);
                break;
            case R.id.ll_wechat_layout:
                rbHong.setChecked(false);
                rbAli.setChecked(false);
                rbAbc.setChecked(false);
                rbWechat.setChecked(true);
                rbAli.setBackgroundResource(R.drawable.radio_normal);
                rbHong.setBackgroundResource(R.drawable.radio_normal);
                rbAbc.setBackgroundResource(R.drawable.radio_normal);
                break;
            case R.id.btn_confirm:
                if (!rbHong.isChecked() && !rbAli.isChecked() && !rbAbc.isChecked() && !rbWechat.isChecked()) {
                    ToastUtil.showCenter(this, "请选择支付通道再提交");
                    return;
                }
                if (wayList.size() == 0) {
                    ToastUtil.showCenter(this, "请选择支付通道再提交");
                    return;
                }
                if (rbHong.isChecked()) {
                    requestAiPayInfo(pay102Resp);
                }
                if (rbAli.isChecked()) {
                    requestAliPayInfo(pay102Resp);
                }
                if (rbAbc.isChecked()) {
                    requestAbcPayInfo(pay102Resp);
                }
                if (rbWechat.isChecked()) {
                    requestWechatPayInfo(pay102Resp);
                }
                break;
        }
    }

    /**
     * 发起农行支付请求
     * @param resp
     */
    private void requestAbcPayInfo(Pay102Resp resp) {
        if (System.currentTimeMillis() - lastRequest < CLICK_INTERVAL) {
            return;
        }
        lastRequest = System.currentTimeMillis();
        for (int i = 0; i < wayList.size(); i++) {
            PayWayResponse payWayResponse = wayList.get(i);
            for (int j = 0; j < payWayResponse.getPayWayList().size(); j++) {
                final PayWayItem payWayItem = payWayResponse.getPayWayList().get(j);
                if (StringUtils.equalsIgnoreCase(Constant.ABC_PAY_ID, payWayItem.getId())) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(Constant.ABC_TEST_PAY_URL).append("?");
                    sb.append("orderId").append("=").append(resp.getOrderId());
                    sb.append("&").append("payWay").append("=").append(abcPayWayType);
                    Bundle bundle = new Bundle();
                    bundle.putString("url", sb.toString());
                    launch(AbcPayActivity.class, bundle);
                }
            }
        }
    }

    /**
     * 发起鸿支付请求
     * @param resp
     */
    private void requestAiPayInfo(Pay102Resp resp) {
        if (System.currentTimeMillis() - lastRequest < 1000) {
            return;
        }
        lastRequest = System.currentTimeMillis();
        for (int i = 0; i < wayList.size(); i++) {
            PayWayResponse payWayResponse = wayList.get(i);
            for (int j = 0; j < payWayResponse.getPayWayList().size(); j++) {
                PayWayItem payWayItem = payWayResponse.getPayWayList().get(j);
                if (StringUtils.equalsIgnoreCase(Constant.UNION_PAY_ID, payWayItem.getId())) {
                    Pay104Req req = new Pay104Req();
                    req.setOrderId(resp.getOrderId());
                    req.setPayWay(payWayItem.getId());
                    if (TDevice.getNetworkType(this) == TDevice.NetTypeWifi) {
                        req.setClientIp(TDevice.getWIFILocalIpAddress(this));
                    } else {
                        req.setClientIp(TDevice.getHostIp());
                    }
                    NetCenter.build(this)
                            .requestDefault(req, "pay104")
                            .map(new Func1<AppBody, Pay104Resp>() {
                                @Override
                                public Pay104Resp call(AppBody appBody) {
                                    return null == appBody ? null : (Pay104Resp) appBody;
                                }
                            }).subscribe(new Action1<Pay104Resp>() {
                        @Override
                        public void call(Pay104Resp pay104Resp) {
                            String formData = pay104Resp.getFormData().get("requestPacket");
                            String newXmlStr = StringUtils.replace(formData, "&quot;", "\"");
                            Intent intent = new Intent(InteMorePayActivity.this, AiPayActivity.class);
                            intent.putExtra("postData", newXmlStr);
                            if (StringUtils.equals(BuildConfig.BUILD_TYPE, Constant.BUILD_TYPE_PMPH)) {
                                intent.putExtra("url", Constant.AI_PAY_URL);
                            } else {
                                intent.putExtra("url", Constant.AI_TEST_PAY_URL);
                            }
                            intent.putExtra("payType", Constant.AI_PAY_TYPE);
                            startActivityForResult(intent, 0);
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            ToastUtil.show(InteMorePayActivity.this, throwable.getMessage());
                        }
                    });
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                String params = data.getStringExtra("params");
                try {
                    ObjectMapper mapper = new ObjectMapper();
                    AiPayResult result = mapper.readValue(params, AiPayResult.class);
                    if (StringUtils.equals("00", result.getResultCode())) {
                        ToastUtil.showCenter(this, "支付成功");
                        launch(MainActivity.class);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (resultCode == RESULT_FIRST_USER) {
                ToastUtil.showCenter(this,"未支付返回");
            } else if (resultCode == RESULT_CANCELED) {
                ToastUtil.showCenter(this,"用户返回");
            }

        }
    }

    /**
     * 发起支付宝请求
     * @param resp
     */
    private void requestAliPayInfo(final Pay102Resp resp) {
        if (System.currentTimeMillis() - lastRequest < 1000) {
            return;
        }
        lastRequest = System.currentTimeMillis();
        for (int i = 0; i < wayList.size(); i++) {
            PayWayResponse payWayResponse = wayList.get(i);
            for (int j = 0; j < payWayResponse.getPayWayList().size(); j++) {
                PayWayItem payWayItem = payWayResponse.getPayWayList().get(j);
                if (StringUtils.equalsIgnoreCase(Constant.ALI_PAY_ID, payWayItem.getId())) {
                    Pay104Req req = new Pay104Req();
                    req.setOrderId(resp.getOrderId());
                    req.setPayWay(payWayItem.getId());
                    getInteJsonService().requestPay104(this, req, true, new InteJsonService.CallBack<Pay104Resp>() {
                        @Override
                        public void oncallback(Pay104Resp pay104Resp) {
                            Map<String, String> formData =  pay104Resp.getFormData();
                            AliPayUtils aliPayUtils = new AliPayUtils(InteMorePayActivity.this);
                            String privateKey = pay104Resp.getCerPassword();
                            String seller = formData.get("seller_id");
                            String partner = formData.get("partner");
                            String notifyUrl = formData.get("notify_url");
                            String subject = formData.get("subject");
                            String price = formData.get("total_fee");
                            String orderCode = formData.get("out_trade_no");
                            aliPayUtils.pay(privateKey,seller,partner,notifyUrl,subject,null,price,orderCode);
                        }

                        @Override
                        public void onErro(AppHeader header) {
                        }
                    });
                    break;
                }
            }
        }
    }

    private void requestWechatPayInfo(Pay102Resp resp) {
        if (System.currentTimeMillis() - lastRequest < CLICK_INTERVAL) {
            return;
        }
        lastRequest = System.currentTimeMillis();
        for (int i = 0; i < wayList.size(); i++) {
            PayWayResponse payWayResponse = wayList.get(i);
            for (int j = 0; j < payWayResponse.getPayWayList().size(); j++) {
                final PayWayItem payWayItem = payWayResponse.getPayWayList().get(j);
                if (StringUtils.equalsIgnoreCase(Constant.WECHAT_PAY_ID, payWayItem.getId())) {
                    Pay104Req req = new Pay104Req();
                    req.setOrderId(resp.getOrderId());
                    req.setPayWay(payWayItem.getId());
                    getInteJsonService().requestPay104(this, req, true, new InteJsonService.CallBack<Pay104Resp>() {
                        @Override
                        public void oncallback(Pay104Resp pay104Resp) {
                            if (pay104Resp != null) {
                                Map<String, String> formData = pay104Resp.getFormData();
                                IWXAPI iwxapi = WXAPIFactory.createWXAPI(InteMorePayActivity.this, null);
                                iwxapi.registerApp(Constant.WECHAT_PAY_APPID);
                                PayReq payReq = new PayReq();
                                payReq.appId = Constant.WECHAT_PAY_APPID;
                                payReq.partnerId = formData.get("partnerid");
                                payReq.prepayId = formData.get("prepayid");
                                payReq.packageValue = "Sign=WXPay";
                                payReq.nonceStr = formData.get("noncestr");
                                payReq.timeStamp = formData.get("timestamp");
                                payReq.sign = formData.get("sign");
                                iwxapi.sendReq(payReq);
                            }
                        }

                        @Override
                        public void onErro(AppHeader header) {

                        }
                    });
                }
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            DialogAnotherUtil.showCustomAlertDialogWithMessage(this, null,
                    "确认放弃支付？", "确认离开", "继续支付",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            DialogUtil.dismissDialog();
                            onBackPressed();
                        }
                    },
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            DialogUtil.dismissDialog();
                        }
                    });
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
