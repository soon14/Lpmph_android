package com.ailk.integral.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ai.ecp.app.req.Ord107Req;
import com.ai.ecp.app.req.Ord110Req;
import com.ai.ecp.app.req.Pay102Req;
import com.ai.ecp.app.req.Staff116Req;
import com.ai.ecp.app.resp.Ord107Resp;
import com.ai.ecp.app.resp.Ord10801Resp;
import com.ai.ecp.app.resp.Ord108Resp;
import com.ai.ecp.app.resp.Ord110Resp;
import com.ai.ecp.app.resp.Pay102Resp;
import com.ai.ecp.app.resp.Staff116Resp;
import com.ailk.butterfly.app.model.AppHeader;
import com.ailk.butterfly.app.model.InteJsonService;
import com.ailk.butterfly.app.model.JsonService;
import com.ailk.integral.adapter.InteLogisticsListAdapter;
import com.ailk.integral.adapter.InteOrderDetailShopListAdapter;
import com.ailk.pmph.BuildConfig;
import com.ailk.pmph.R;
import com.ailk.pmph.thirdstore.activity.StoreActivity;
import com.ailk.pmph.utils.Constant;
import com.ailk.pmph.utils.DialogAnotherUtil;
import com.ailk.pmph.utils.DialogUtil;
import com.ailk.pmph.utils.MoneyUtils;
import com.ailk.pmph.utils.ToastUtil;
import com.ailk.pmph.base.BaseActivity;
import com.ailk.pmph.ui.view.CustomListView;

import org.apache.commons.lang.StringUtils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 类注释:订单详情
 * 项目名:pmph_android
 * 包名:com.ailk.pmph.ui.activity
 * 作者: Chrizz
 * 时间: 2016/3/29 23:23
 */
public class InteOrderDetailActivity extends BaseActivity implements View.OnClickListener,
        InteOrderDetailShopListAdapter.RedirectToShopDetail{

    @BindView(R.id.iv_back)               ImageView ivBack;
    @BindView(R.id.tv_order_num)          TextView tvOrderCode;
    @BindView(R.id.tv_name)               TextView tvContactName;
    @BindView(R.id.tv_phone)              TextView tvContactPhone;
    @BindView(R.id.tv_address)            TextView tvAddress;
    @BindView(R.id.lv_shop_list)          CustomListView lvShopList;
    @BindView(R.id.tv_pay_type)           TextView tvPayType;
    @BindView(R.id.tv_deliver_type)       TextView tvDeliverType;
    @BindView(R.id.ll_info_layout)        LinearLayout llInfoLayout;
    @BindView(R.id.tv_info)               TextView tvInfo;
    @BindView(R.id.iv_arrow)              ImageView ivArrow;
    @BindView(R.id.lv_logistics_info)     CustomListView lvLogisticsList;
    @BindView(R.id.tv_pay_price)          TextView tvPayPrice;
    @BindView(R.id.tv_order_time)         TextView tvOrderTime;
    @BindView(R.id.tv_cancel)             TextView tvCancel;
    @BindView(R.id.tv_go_pay)             TextView tvPay;
    @BindView(R.id.ll_detail_bottom)      LinearLayout llDetailBottomLayout;
    @BindView(R.id.ll_detail_time)        LinearLayout llDetailTimeLayout;
    @BindView(R.id.tv_shop_name)          TextView tvShopName;
    @BindView(R.id.store_layout)          LinearLayout storeLayout;

    private Ord108Resp ord108Resp;
    private List<Ord10801Resp> ord01101RespList;
    private String orderStatus;

    @Override
    protected int getContentViewId() {
        return R.layout.inte_activity_order_detail;
    }

    public void initView() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            ord108Resp = (Ord108Resp) bundle.get("ord108Resp");
        }
        if (ord108Resp != null) {
            ord01101RespList = ord108Resp.getOrd10801Resps();
            orderStatus = ord108Resp.getStatus();
        }
    }

    public void initData() {
        tvOrderCode.setText("订单号:"+ord108Resp.getOrderId());
        tvContactName.setText(ord108Resp.getContactName());
        tvContactPhone.setText(ord108Resp.getContactPhone());
        tvAddress.setText(ord108Resp.getChnlAddress());
        getShopName();
        InteOrderDetailShopListAdapter adapter = new InteOrderDetailShopListAdapter(this, ord01101RespList);
        adapter.setRedirectToShopDetail(InteOrderDetailActivity.this);
        lvShopList.setAdapter(adapter);

        if (StringUtils.equals("0",ord108Resp.getPayType()))
        {
            tvPayType.setText("线上支付");
        }
        else if (StringUtils.equals("1",ord108Resp.getPayType()))
        {
            tvPayType.setText("上门支付");
        }
        else if (StringUtils.equals("2",ord108Resp.getPayType()))
        {
            tvPayType.setText("邮局汇款");
        }
        else if (StringUtils.equals("3",ord108Resp.getPayType()))
        {
            tvPayType.setText("银行转账");
        }

        if (StringUtils.equals("0",ord108Resp.getDispatchType()))
        {
            tvDeliverType.setText("邮局挂号");
        }
        else if (StringUtils.equals("1",ord108Resp.getDispatchType()))
        {
            tvDeliverType.setText("快递");
        }
        else if (StringUtils.equals("2",ord108Resp.getDispatchType()))
        {
            tvDeliverType.setText("自提");
        }

        if (ord108Resp.getOrd10803Resps().size() > 0 && StringUtils.equals("1",ord108Resp.getDispatchType())) {
            tvInfo.setText("");
            ivArrow.setImageResource(R.drawable.arrow_down);
            setVisible(lvLogisticsList);
            InteLogisticsListAdapter logisticsListAdapter = new InteLogisticsListAdapter(this, ord108Resp.getOrd10803Resps());
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
            if (!StringUtils.equals("1",ord108Resp.getDispatchType())) {
                tvInfo.setText("暂无物流信息");
                setGone(ivArrow, lvLogisticsList);
            } else {
                tvInfo.setText("暂未发货");
                setGone(ivArrow, lvLogisticsList);
            }
        }

        tvPayPrice.setText(ord108Resp.getOrderScore() + "积分 + " + MoneyUtils.GoodListPrice(ord108Resp.getRealMoney()));
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Timestamp orderTime = new Timestamp(ord108Resp.getOrderTime().getTime());
        tvOrderTime.setText("下单时间："+df.format(orderTime));
        /**
         * 订单状态 01:待支付;02:已支付;04:部分发货;05:已发货;06:已收货;07:已退货;08:已退款;80:已关闭;99:已取消;
         */
        if (StringUtils.equals("01", orderStatus))
        {
            if (StringUtils.equals("0",ord108Resp.getPayType())) //线上支付
            {
                setVisible(llDetailBottomLayout);
                tvPay.setText("去支付");
            }
            else
            {
                setInvisible(llDetailTimeLayout);
                setGone(tvPay);
            }
        }
        else if (StringUtils.equals("02", orderStatus))
        {
            setGone(llDetailBottomLayout);
        }
        else if (StringUtils.equals("04", orderStatus))
        {
            setGone(llDetailBottomLayout);
        }
        else if (StringUtils.equals("05", orderStatus))
        {
            setVisible(llDetailBottomLayout);
            setInvisible(llDetailTimeLayout);
            setGone(tvCancel);
            tvPay.setText("确认收货");
        }
        else if (StringUtils.equals("06", orderStatus))
        {
            setGone(llDetailBottomLayout);
        }
        else if (StringUtils.equals("07", orderStatus))
        {
            setGone(llDetailBottomLayout);
        }
        else if (StringUtils.equals("08", orderStatus))
        {
            setGone(llDetailBottomLayout);
        }
        else if (StringUtils.equals("80", orderStatus))
        {
            setGone(llDetailBottomLayout);
        }
        else if (StringUtils.equals("99", orderStatus))
        {
            setGone(llDetailBottomLayout);
        }
    }

    private void getShopName() {
        Staff116Req req = new Staff116Req();
        req.setShopId(ord108Resp.getShopId());
        getJsonService().requestStaff116(this, req, false, new JsonService.CallBack<Staff116Resp>() {
            @Override
            public void oncallback(Staff116Resp staff116Resp) {
                tvShopName.setText(staff116Resp.getShopName());
            }

            @Override
            public void onErro(AppHeader header) {

            }
        });
    }

    @Override
    public void redirectToDetail(Ord10801Resp product) {
        Bundle bundle = new Bundle();
        bundle.putString(Constant.SHOP_GDS_ID, String.valueOf(product.getGdsId()));
        bundle.putString(Constant.SHOP_SKU_ID, String.valueOf(product.getSkuId()));
        launch(InteShopDetailActivity.class, bundle);
    }

    @Override
    @OnClick({R.id.iv_back, R.id.store_layout, R.id.tv_cancel, R.id.tv_go_pay})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.store_layout:
                if (!StringUtils.equals(BuildConfig.BUILD_TYPE, Constant.BUILD_TYPE_PMPH)) {
                    if (ord108Resp.getShopId() != null) {
                        Bundle bundle = new Bundle();
                        bundle.putString(Constant.STORE_ID, String.valueOf(ord108Resp.getShopId()));
                        launch(StoreActivity.class, bundle);
                    }
                }
                break;
            case R.id.tv_cancel:
                DialogAnotherUtil.showCustomAlertDialogWithMessage(this, null,
                        "确认取消订单吗？", "确定", "取消",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                requestCancelOrder(true);
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
                if (StringUtils.equals("01", orderStatus) && StringUtils.equals("0",ord108Resp.getPayType()))
                {
                    requestPayMoney(ord108Resp);
                }
                //确认收货
                if (StringUtils.equals("05", orderStatus))
                {
                    DialogAnotherUtil.showCustomAlertDialogWithMessage(this, null,
                            "确认收货吗？", "确定", "取消",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    DialogUtil.dismissDialog();
                                    requestConfirmGoods(ord108Resp.getOrderId());
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
            default:
                break;
        }
    }

    /**
     * 取消订单
     * @param isShowProgress
     */
    private void requestCancelOrder(boolean isShowProgress){
        Ord107Req request = new Ord107Req();
        request.setOrderId(ord108Resp.getOrderId());
        getInteJsonService().requestOrd107(this, request, isShowProgress, new InteJsonService.CallBack<Ord107Resp>() {
            @Override
            public void oncallback(Ord107Resp ord107Resp) {
                ToastUtil.showCenter(InteOrderDetailActivity.this, "订单取消成功");
                onBackPressed();
            }

            @Override
            public void onErro(AppHeader header) {

            }
        });
    }

    /**
     * 去支付
     * @param ord108Resp
     */
    private void requestPayMoney(final Ord108Resp ord108Resp){
        Pay102Req request = new Pay102Req();
        request.setOrderId(ord108Resp.getOrderId());
        request.setShopId(ord108Resp.getShopId());
        getInteJsonService().requestPay102(this, request, true, new InteJsonService.CallBack<Pay102Resp>() {
            @Override
            public void oncallback(Pay102Resp pay102Resp) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("pay102Resp", pay102Resp);
                bundle.putLong("realMoney", ord108Resp.getRealMoney());
                launch(InteMorePayActivity.class,bundle);
            }

            @Override
            public void onErro(AppHeader header) {

            }
        });
    }

    private void requestConfirmGoods(String orderId) {
        Ord110Req request = new Ord110Req();
        request.setOrderId(orderId);
        getInteJsonService().requestOrd110(this, request, false, new InteJsonService.CallBack<Ord110Resp>() {
            @Override
            public void oncallback(Ord110Resp ord110Resp) {
                ToastUtil.showCenter(InteOrderDetailActivity.this, "您已确认收货");
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
