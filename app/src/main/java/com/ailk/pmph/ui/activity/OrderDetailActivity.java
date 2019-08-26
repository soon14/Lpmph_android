package com.ailk.pmph.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ai.ecp.app.req.IM001Req;
import com.ai.ecp.app.req.Ord010Req;
import com.ai.ecp.app.req.Ord017Req;
import com.ai.ecp.app.req.Pay002Req;
import com.ai.ecp.app.req.Staff116Req;
import com.ai.ecp.app.resp.IM001Resp;
import com.ai.ecp.app.resp.Ord010Resp;
import com.ai.ecp.app.resp.Ord01101Resp;
import com.ai.ecp.app.resp.Ord011Resp;
import com.ai.ecp.app.resp.Ord017Resp;
import com.ai.ecp.app.resp.Pay002Resp;
import com.ai.ecp.app.resp.Staff116Resp;
import com.ailk.butterfly.app.model.AppBody;
import com.ailk.im.net.NetCenter;
import com.ailk.im.ui.activity.MessageActivity;
import com.ailk.pmph.AppManager;
import com.ailk.pmph.BuildConfig;
import com.ailk.pmph.R;
import com.ailk.pmph.base.BaseActivity;
import com.ailk.pmph.thirdstore.activity.StoreActivity;
import com.ailk.pmph.ui.adapter.LogisticsListAdapter;
import com.ailk.pmph.ui.adapter.OrderDetailShopListAdapter;
import com.ailk.pmph.ui.view.CustomListView;
import com.ailk.pmph.utils.Constant;
import com.ailk.pmph.utils.DialogUtil;
import com.ailk.pmph.utils.LogUtil;
import com.ailk.pmph.utils.MoneyUtils;
import com.ailk.pmph.utils.ToastUtil;
import com.bigkoo.alertview.AlertView;

import org.apache.commons.lang.StringUtils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * 类注释:订单详情
 * 项目名:pmph_android
 * 包名:com.ailk.pmph.ui.activity
 * 作者: Chrizz
 * 时间: 2016/3/29 23:23
 */
public class OrderDetailActivity extends BaseActivity implements View.OnClickListener,
        OrderDetailShopListAdapter.RedirectToShopDetail {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_order_num)
    TextView tvOrderCode;
    @BindView(R.id.tv_name)
    TextView tvContactName;
    @BindView(R.id.tv_phone)
    TextView tvContactPhone;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.lv_shop_list)
    CustomListView lvShopList;
    @BindView(R.id.tv_pay_type)
    TextView tvPayType;
    @BindView(R.id.tv_deliver_type)
    TextView tvDeliverType;
    @BindView(R.id.ll_info_layout)
    LinearLayout llInfoLayout;
    @BindView(R.id.tv_info)
    TextView tvInfo;
    @BindView(R.id.iv_arrow)
    ImageView ivArrow;
    @BindView(R.id.lv_logistics_info)
    CustomListView lvLogisticsList;
    @BindView(R.id.tv_bill_type)
    TextView tvBillType;
    @BindView(R.id.ll_bill_detail)
    LinearLayout llBillDetail;
    @BindView(R.id.tv_bill_name)
    TextView tvBillTitle;
    @BindView(R.id.tv_bill_content)
    TextView tvBillContent;
    @BindView(R.id.tv_shop_price)
    TextView tvShopPrice;
    @BindView(R.id.tv_cash_price)
    TextView tvCashPrice;
    @BindView(R.id.tv_express_price)
    TextView tvExpressPrice;
    @BindView(R.id.tv_pay_price)
    TextView tvPayPrice;
    @BindView(R.id.tv_order_time)
    TextView tvOrderTime;
    @BindView(R.id.tv_time)
    TextView tvLeftTime;
    @BindView(R.id.tv_cancel)
    TextView tvCancel;
    @BindView(R.id.tv_go_pay)
    TextView tvPay;
    @BindView(R.id.ll_detail_bottom)
    LinearLayout llDetailBottomLayout;
    @BindView(R.id.ll_detail_time)
    LinearLayout llDetailTimeLayout;
    @BindView(R.id.store_layout)
    LinearLayout storeLayout;
    @BindView(R.id.iv_shop_logo)
    ImageView ivShopLogo;
    @BindView(R.id.tv_shop_name)
    TextView tvShopName;
    @BindView(R.id.rl_online_service_layout)
    RelativeLayout onlineServiceLayout;
    @BindView(R.id.buy_info_layout)
    LinearLayout buyInfoLayout;
    @BindView(R.id.tv_buy_info)
    TextView tvBuyInfo;
    @BindView(R.id.tv_taxpayer_number)
    TextView tvTaxpayerNumber;

    private Ord011Resp ord011Resp;
    private List<Ord01101Resp> ord01101RespList;
    private String orderStatus;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_order_detail;
    }

    public void initView() {
        Bundle bundle = getIntent().getExtras();
        ord011Resp = (Ord011Resp) bundle.get("ord011Resp");
        if (ord011Resp != null) {
            ord01101RespList = ord011Resp.getOrd01101Resps();
            orderStatus = ord011Resp.getStatus();
        }
    }

    public void initData() {
        tvOrderCode.setText("订单号:" + ord011Resp.getOrderId());
        tvContactName.setText(ord011Resp.getContactName());
        tvContactPhone.setText(ord011Resp.getContactPhone());
        tvAddress.setText(ord011Resp.getChnlAddress());
        getShopName();
        OrderDetailShopListAdapter adapter = new OrderDetailShopListAdapter(this, ord01101RespList);
        adapter.setRedirectToShopDetail(OrderDetailActivity.this);
        lvShopList.setAdapter(adapter);

        if (StringUtils.equals("0", ord011Resp.getPayType())) {
            tvPayType.setText("线上支付");
        } else if (StringUtils.equals("1", ord011Resp.getPayType())) {
            tvPayType.setText("上门支付");
        } else if (StringUtils.equals("2", ord011Resp.getPayType())) {
            tvPayType.setText("邮局汇款");
        } else if (StringUtils.equals("3", ord011Resp.getPayType())) {
            tvPayType.setText("银行转账");
        }

        if (StringUtils.equals("0", ord011Resp.getDispatchType())) {
            tvDeliverType.setText("邮局挂号");
        } else if (StringUtils.equals("1", ord011Resp.getDispatchType())) {
            tvDeliverType.setText("快递");
        } else if (StringUtils.equals("2", ord011Resp.getDispatchType())) {
            tvDeliverType.setText("自提");
        }

        if (ord011Resp.getOrd01103Resps().size() > 0 && StringUtils.equals("1", ord011Resp.getDispatchType())) {
            tvInfo.setText("");
            ivArrow.setImageResource(R.drawable.arrow_down);
            setVisible(lvLogisticsList);
            LogisticsListAdapter logisticsListAdapter = new LogisticsListAdapter(this, ord011Resp.getOrd01103Resps());
            lvLogisticsList.setAdapter(logisticsListAdapter);
            llInfoLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (lvLogisticsList.getVisibility() == View.VISIBLE) {
                        setGone(lvLogisticsList);
                        ivArrow.setImageResource(R.drawable.arrow_right);
                    } else {
                        setVisible(lvLogisticsList);
                        ivArrow.setImageResource(R.drawable.arrow_down);
                    }
                }
            });
        } else {
            if (!StringUtils.equals("1", ord011Resp.getDispatchType())) {
                tvInfo.setText("暂无物流信息");
                setGone(ivArrow, lvLogisticsList);
            } else {
                tvInfo.setText("暂未发货");
                setGone(ivArrow, lvLogisticsList);
            }
        }
        //发票
        if (StringUtils.equals("0", ord011Resp.getInvoiceType())) {
            tvBillType.setText("普票");
            llBillDetail.setVisibility(View.VISIBLE);
            tvBillTitle.setText("发票抬头：" + ord011Resp.getInvoiceTitle());
            tvBillContent.setText("发票内容：" + ord011Resp.getInvoiceContent());

            if (TextUtils.isEmpty(ord011Resp.getTaxpayerNo())) {
                //个人发票
                tvTaxpayerNumber.setVisibility(View.GONE);
            } else {
                //公司发票，展示纳税人识别号
                tvTaxpayerNumber.setVisibility(View.VISIBLE);
                tvTaxpayerNumber.setText("纳税人识别号：" + ord011Resp.getTaxpayerNo());
            }
        } else if (StringUtils.equals("1", ord011Resp.getInvoiceType())) {
            tvTaxpayerNumber.setVisibility(View.GONE);
            tvBillType.setText("增票");
        } else if (StringUtils.equals("2", ord011Resp.getInvoiceType())) {
            tvTaxpayerNumber.setVisibility(View.GONE);
            tvBillType.setText("不开发票");
            setGone(llBillDetail);
        }
        tvShopPrice.setText(MoneyUtils.GoodListPrice(ord011Resp.getOrderMoney()));
        tvCashPrice.setText(MoneyUtils.GoodListPrice(ord011Resp.getDiscountOrderSum() + ord011Resp.getDiscountGdsSum()
                + ord011Resp.getDiscountCoupSum() + ord011Resp.getDiscountCoupCodeSum()));
        tvExpressPrice.setText(MoneyUtils.GoodListPrice(ord011Resp.getRealExpressFee()));
        tvPayPrice.setText(MoneyUtils.GoodListPrice(ord011Resp.getRealMoney()));
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        Timestamp orderTime = new Timestamp(ord011Resp.getOrderTime().getTime());
        tvOrderTime.setText("下单时间：" + df.format(orderTime));
        // 订单状态 01:待支付;02:已支付;04:部分发货;05:已发货;06:已收货;07:已退货;08:已退款;80:已关闭;99:已取消;
        if (StringUtils.equals("01", orderStatus)) {
            if (StringUtils.equals("0", ord011Resp.getPayType())) //线上支付
            {
                setVisible(llDetailBottomLayout);
                showLeftTime(ord011Resp, tvLeftTime);
            } else {
                setInvisible(llDetailTimeLayout);
                setGone(tvPay);
            }
        } else if (StringUtils.equals("02", orderStatus)) {
            setGone(llDetailBottomLayout);
        } else if (StringUtils.equals("04", orderStatus)) {
            setGone(llDetailBottomLayout);
        } else if (StringUtils.equals("05", orderStatus)) {
            setVisible(llDetailBottomLayout);
            setInvisible(llDetailTimeLayout);
            setGone(tvCancel);
            tvPay.setText("确认收货");
        } else if (StringUtils.equals("06", orderStatus)) {
            setGone(llDetailBottomLayout);
        } else if (StringUtils.equals("07", orderStatus)) {
            setGone(llDetailBottomLayout);
        } else if (StringUtils.equals("08", orderStatus)) {
            setGone(llDetailBottomLayout);
        } else if (StringUtils.equals("80", orderStatus)) {
            setGone(llDetailBottomLayout);
        } else if (StringUtils.equals("99", orderStatus)) {
            setGone(llDetailBottomLayout);
        }

        String buyerMsg = ord011Resp.getBuyerMsg();
        if (StringUtils.isNotEmpty(buyerMsg)) {
            setVisible(buyInfoLayout);
            tvBuyInfo.setText(buyerMsg);
        } else {
            setGone(buyInfoLayout);
        }
    }

    /**
     * 获取店铺名字
     */
    private void getShopName() {
        Staff116Req req = new Staff116Req();
        req.setShopId(ord011Resp.getShopId());
        DialogUtil.showCustomerProgressDialog(this);
        NetCenter.build(this)
                .requestDefault(req, "staff116")
                .map(new Func1<AppBody, Staff116Resp>() {
                    @Override
                    public Staff116Resp call(AppBody appBody) {
                        return null == appBody ? null : (Staff116Resp) appBody;
                    }
                }).subscribe(new Action1<Staff116Resp>() {
            @Override
            public void call(Staff116Resp staff116Resp) {
                DialogUtil.dismissDialog();
                tvShopName.setText(staff116Resp.getShopName());
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                DialogUtil.dismissDialog();
                LogUtil.e(throwable);
            }
        });
    }

    /**
     * 显示付款剩余时间
     *
     * @param ord011Resp
     * @param textView
     */
    private void showLeftTime(Ord011Resp ord011Resp, TextView textView) {
        long hour = ord011Resp.getLimitHour();
        long minute = ord011Resp.getLimitMinutes();
        if (hour != 0 && minute != 0) {
            textView.setText(hour + "小时" + minute + "分钟");
            tvPay.setText("去支付");
        } else if (hour == 0 && minute != 0) {
            textView.setText(minute + "分钟");
            tvPay.setText("去支付");
        } else {
            setInvisible(llDetailTimeLayout);
            setGone(tvPay);
        }
    }

    @Override
    public void redirectToDetail(Ord01101Resp product) {
        Bundle bundle = new Bundle();
        bundle.putString(Constant.SHOP_GDS_ID, String.valueOf(product.getGdsId()));
        bundle.putString(Constant.SHOP_SKU_ID, String.valueOf(product.getSkuId()));
        launch(ShopDetailActivity.class, bundle);
    }

    @Override
    @OnClick({R.id.iv_back, R.id.store_layout, R.id.tv_cancel, R.id.tv_go_pay, R.id.rl_online_service_layout})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.store_layout:
                if (!StringUtils.equals(BuildConfig.BUILD_TYPE, Constant.BUILD_TYPE_PMPH)) {
                    if (ord011Resp.getShopId() != null) {
                        Bundle bundle = new Bundle();
                        bundle.putString(Constant.STORE_ID, String.valueOf(ord011Resp.getShopId()));
                        launch(StoreActivity.class, bundle);
                    }
                }
                break;
            case R.id.tv_cancel:
                DialogUtil.showCustomAlertDialogWithMessage(this, null,
                        "确认取消订单吗？", "确定", "取消",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                requestCancelOrder();
                                DialogUtil.dismissDialog();
                            }
                        },
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DialogUtil.dismissDialog();
                            }
                        });
                break;
            case R.id.tv_go_pay:
                //线上支付
                if (StringUtils.equals("01", orderStatus) && StringUtils.equals("0", ord011Resp.getPayType())) {
                    requestPayMoney(ord011Resp);
                }
                //确认收货
                if (StringUtils.equals("05", orderStatus)) {
                    DialogUtil.showCustomAlertDialogWithMessage(this, null,
                            "确认收货吗？", "确定", "取消",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    DialogUtil.dismissDialog();
                                    requestConfirmGoods(ord011Resp.getOrderId());
                                }
                            },
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    DialogUtil.dismissDialog();
                                }
                            });
                }
                break;
            case R.id.rl_online_service_layout:
                connectIM();
                break;
        }
    }

    /**
     * 取消订单
     */
    private void requestCancelOrder() {
        Ord010Req request = new Ord010Req();
        request.setOrderId(ord011Resp.getOrderId());
        DialogUtil.showCustomerProgressDialog(this);
        NetCenter.build(this)
                .requestDefault(request, "ord010")
                .map(new Func1<AppBody, Ord010Resp>() {
                    @Override
                    public Ord010Resp call(AppBody appBody) {
                        return null == appBody ? null : (Ord010Resp) appBody;
                    }
                }).subscribe(new Action1<Ord010Resp>() {
            @Override
            public void call(Ord010Resp ord010Resp) {
                DialogUtil.dismissDialog();
                ToastUtil.showIconToast(OrderDetailActivity.this, "订单取消成功");
                onBackPressed();
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                DialogUtil.dismissDialog();
                LogUtil.e(throwable);
            }
        });
    }

    /**
     * 去支付
     *
     * @param ord011Resp
     */
    private void requestPayMoney(final Ord011Resp ord011Resp) {
        Pay002Req request = new Pay002Req();
        request.setOrderId(ord011Resp.getOrderId());
        request.setShopId(ord011Resp.getShopId());
        DialogUtil.showCustomerProgressDialog(this);
        NetCenter.build(this)
                .requestDefault(request, "pay002")
                .map(new Func1<AppBody, Pay002Resp>() {
                    @Override
                    public Pay002Resp call(AppBody appBody) {
                        return null == appBody ? null : (Pay002Resp) appBody;
                    }
                }).subscribe(new Action1<Pay002Resp>() {
            @Override
            public void call(Pay002Resp pay002Resp) {
                DialogUtil.dismissDialog();
                Bundle extra = new Bundle();
                extra.putLong("realMoney", ord011Resp.getRealMoney());
                extra.putSerializable("pay002Resp", pay002Resp);
                launch(OrderListPayActivity.class, extra);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                DialogUtil.dismissDialog();
                LogUtil.e(throwable);
            }
        });
    }

    private void requestConfirmGoods(String orderId) {
        Ord017Req request = new Ord017Req();
        request.setOrderId(orderId);
        DialogUtil.showCustomerProgressDialog(this);
        NetCenter.build(this)
                .requestDefault(request, "ord017")
                .map(new Func1<AppBody, Ord017Resp>() {
                    @Override
                    public Ord017Resp call(AppBody appBody) {
                        return null == appBody ? null : (Ord017Resp) appBody;
                    }
                }).subscribe(new Action1<Ord017Resp>() {
            @Override
            public void call(Ord017Resp ord017Resp) {
                DialogUtil.dismissDialog();
                ToastUtil.show(OrderDetailActivity.this, "您已确认收货");
                launch(UnCommentActivity.class);
                AppManager.getAppManager().finishActivity();
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                DialogUtil.dismissDialog();
                LogUtil.e(throwable);
            }
        });
    }

    private void connectIM() {
        IM001Req req = new IM001Req();
        if (ord011Resp.getShopId() != null) {
            req.setShopId(ord011Resp.getShopId());
        }
        final Long businessType = 1L;
        req.setBusinessType(businessType);
        if (StringUtils.isNotEmpty(ord011Resp.getOrderId())) {
            req.setBusinessId(ord011Resp.getOrderId());
        }
        DialogUtil.showCustomerProgressDialog(this);
        NetCenter.build(this)
                .requestDefault(req, "im001")
                .map(new Func1<AppBody, IM001Resp>() {
                    @Override
                    public IM001Resp call(AppBody appBody) {
                        return null == appBody ? null : (IM001Resp) appBody;
                    }
                }).subscribe(new Action1<IM001Resp>() {
            @Override
            public void call(IM001Resp im001Resp) {
                DialogUtil.dismissDialog();
                if (im001Resp != null) {
                    String account = im001Resp.getUserCode();
                    String serviceAccount = im001Resp.getCsaCode();
                    String serviceName = im001Resp.getHotlinePerson();
                    String servicePhoto = im001Resp.getHotlinePhoto();
                    String sessionId = im001Resp.getSessionId();
                    String photo = im001Resp.getCustPic();
                    int waitCount = im001Resp.getWaitCount();

                    if (waitCount == 0) {
                        String description;
                        description = "用户" + account + "第" + (waitCount + 1) + "次接入";
                        Intent intent = new Intent(OrderDetailActivity.this, MessageActivity.class);
                        if (StringUtils.isNotEmpty(account)) {
                            intent.putExtra("account", account);
                        }
                        if (StringUtils.isNotEmpty(serviceAccount)) {
                            intent.putExtra("serviceAccount", serviceAccount);
                        }
                        if (StringUtils.isNotEmpty(serviceName)) {
                            intent.putExtra("serviceName", serviceName);
                        }
                        if (StringUtils.isNotEmpty(sessionId)) {
                            intent.putExtra("sessionId", sessionId);
                        }
                        intent.putExtra("servicePhoto", servicePhoto);
                        intent.putExtra("photo", photo);
                        intent.putExtra("businessType", businessType);
                        intent.putExtra("businessId", ord011Resp.getOrderId());
                        intent.putExtra("waitCount", waitCount);
                        intent.putExtra("shopId", ord011Resp.getShopId());
                        intent.putExtra("description", description);
                        launch(intent);
                    } else if (waitCount > 0) {
                        new AlertView("提示", "客服正忙，请稍候再试",
                                null, new String[]{"确定"},
                                null, OrderDetailActivity.this, AlertView.Style.Alert, null).show();
                    } else {
                        new AlertView("提示", "对不起，客服还没有上线 \n" + "请在正常工作时间内联系客服人员",
                                null, new String[]{"确定"},
                                null, OrderDetailActivity.this, AlertView.Style.Alert, null).show();
                    }
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                DialogUtil.dismissDialog();
                LogUtil.e(throwable);
            }
        });
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
