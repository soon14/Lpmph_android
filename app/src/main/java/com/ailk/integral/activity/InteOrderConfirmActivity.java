package com.ailk.integral.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ai.ecp.app.req.Ord106Req;
import com.ai.ecp.app.req.Ord111Req;
import com.ai.ecp.app.req.Pay101Req;
import com.ai.ecp.app.req.Pay103Req;
import com.ai.ecp.app.req.ord.RSumbitMainReqVO;
import com.ai.ecp.app.resp.CustAddrResDTO;
import com.ai.ecp.app.resp.Ord10201Resp;
import com.ai.ecp.app.resp.Ord10202Resp;
import com.ai.ecp.app.resp.Ord106Resp;
import com.ai.ecp.app.resp.Ord111Resp;
import com.ai.ecp.app.resp.Pay101Resp;
import com.ai.ecp.app.resp.Pay103Resp;
import com.ailk.butterfly.app.model.AppHeader;
import com.ailk.butterfly.app.model.InteJsonService;
import com.ailk.integral.adapter.InteOrderConfirmShopListAdapter;
import com.ailk.pmph.R;
import com.ailk.pmph.utils.Constant;
import com.ailk.pmph.utils.DialogAnotherUtil;
import com.ailk.pmph.utils.DialogUtil;
import com.ailk.pmph.utils.MoneyUtils;
import com.ailk.pmph.utils.StringUtil;
import com.ailk.pmph.ui.activity.AddressManagerActivity;
import com.ailk.pmph.base.BaseActivity;
import com.ailk.pmph.ui.view.CustomExpandableListView;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 类注释:
 * 项目名:pmph_android
 * 包名:com.ailk.integral.act
 * 作者: Chrizz
 * 时间: 2016/5/19 14:19
 */
public class InteOrderConfirmActivity extends BaseActivity implements View.OnClickListener,
        InteOrderConfirmShopListAdapter.RedirectToDetail{

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_contactName)
    TextView tvName;
    @BindView(R.id.tv_contactPhone)
    TextView tvPhone;
    @BindView(R.id.ll_address_layout)
    LinearLayout llAddressLayout;
    @BindView(R.id.tv_chnlAddress)
    TextView tvAddress;
    @BindView(R.id.tv_shop_num)
    TextView tvNum;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_total_price)
    TextView tvTotalPrice;
    @BindView(R.id.tv_pay)
    Button tvPay;
    @BindView(R.id.lv_shop_list)
    CustomExpandableListView lvShopList;

    private List<Ord10201Resp> groups = new ArrayList<>();
    private Map<Long, List<Ord10202Resp>> productsMap = new HashMap<>();
    private String redisKey;
    private Long orderMoneys;
    private Long orderScores;
    private Long orderAmounts;
    private List<CustAddrResDTO> addrs;
    private Long addrId;
    private Map<Long,Long> orderMoneyMap;
    private Map<Long,Long> orderScoreMap;
    private InteOrderConfirmShopListAdapter adapter;

    @Override
    protected int getContentViewId() {
        return R.layout.inte_activity_order_confirm;
    }

    public void initView(){
        Bundle bundle = getIntent().getExtras();
        groups = (List<Ord10201Resp>) bundle.get("groups");
        redisKey = bundle.getString("redisKey");
        orderMoneys = bundle.getLong("orderMoneys");
        orderScores = bundle.getLong("orderScores");
        orderAmounts = bundle.getLong("orderAmounts");
        addrs = (List<CustAddrResDTO>) bundle.get("addrs");
        orderMoneyMap = (Map<Long, Long>) bundle.get("orderMoneyMap");
        orderScoreMap = (Map<Long, Long>) bundle.get("orderScoreMap");
    }

    public void initData() {
        if (addrs != null) {
            for (int i = 0 ; i < addrs.size(); i++) {
                CustAddrResDTO addr = addrs.get(i);
                if (StringUtil.equals("1", addr.getUsingFlag())) {
                    tvName.setText(addr.getContactName());
                    tvPhone.setText(addr.getContactPhone());
                    tvAddress.setText(addr.getPccName() + addr.getChnlAddress());
                    addrId = addr.getId();
                }
            }
        }
        else {
            tvName.setText("");
            tvPhone.setText("");
            tvAddress.setText("");
            addrId = 0L;
        }
        for (int i = 0; i < groups.size(); i++) {
            Ord10201Resp bean = groups.get(i);
            productsMap.put(bean.getShopId(), bean.getOrdCartItemList());
            adapter = new InteOrderConfirmShopListAdapter(this, groups, productsMap);
        }
        adapter.setRedirectToDetail(InteOrderConfirmActivity.this);
        lvShopList.setAdapter(adapter);
        for (int i = 0; i < adapter.getGroupCount(); i++) {
            lvShopList.expandGroup(i);
        }
        lvShopList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return true;
            }
        });
        tvNum.setText("共" + orderAmounts + "件商品");
        if (orderMoneys==0) {
            tvPrice.setText(orderScores+"积分 + "+"￥0.00");
            tvTotalPrice.setText(orderScores+"积分 + "+"￥0.00");
        } else {
            tvPrice.setText(orderScores+"积分 + "+MoneyUtils.GoodListPrice(orderMoneys));
            tvTotalPrice.setText(orderScores+"积分 + "+MoneyUtils.GoodListPrice(orderMoneys));
        }
    }

    @Override
    public void toDetail(Ord10202Resp product) {
        Bundle bundle = new Bundle();
        bundle.putString(Constant.SHOP_GDS_ID, String.valueOf(product.getGdsId()));
        bundle.putString(Constant.SHOP_SKU_ID, String.valueOf(product.getSkuId()));
        launch(InteShopDetailActivity.class, bundle);
    }

    @Override
    @OnClick({R.id.iv_back,R.id.ll_address_layout,R.id.tv_pay})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.ll_address_layout:
                Intent intent = new Intent(this, AddressManagerActivity.class);
                startActivityForResult(intent, 10000);
                break;
            case R.id.tv_pay:
                requestCommitOrder(true);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10000 && resultCode == 10001) {
            if (data != null) {
                String contactName = data.getExtras().getString("contactName");
                String contactPhone = data.getExtras().getString("contactPhone");
                String address = data.getExtras().getString("address");
                addrId = data.getExtras().getLong("addrId");
                tvName.setText(contactName);
                tvPhone.setText(contactPhone);
                tvAddress.setText(address);
            }
        }
    }

    private void requestCommitOrder(boolean isShowProgress){
        tvPay.setText("正在提交...");
        tvPay.setClickable(false);
        Ord106Req req = new Ord106Req();
        req.setRedisKey(redisKey);
        req.setAddrId(addrId);
        req.setPayType("0");
        List<com.ai.ecp.app.req.ord.RSumbitMainReqVO> sumbitMainList = new ArrayList<>();
        for (int i = 0; i < groups.size(); i++) {
            Ord10201Resp shop = groups.get(i);
            for (Long key : orderMoneyMap.keySet()) {
                if (key.equals(shop.getShopId())) {
                    RSumbitMainReqVO rSumbitMainReqVO = new RSumbitMainReqVO();
                    rSumbitMainReqVO.setCartId(shop.getCartId());
                    rSumbitMainReqVO.setRealExpressFee(0L);
                    rSumbitMainReqVO.setRealMoney(orderMoneyMap.get(key));
                    rSumbitMainReqVO.setDeliverType("1");
                    sumbitMainList.add(rSumbitMainReqVO);
                }
            }
        }
        req.setSumbitMainList(sumbitMainList);
        getInteJsonService().requestOrd106(this, req, isShowProgress, new InteJsonService.CallBack<Ord106Resp>() {
            @Override
            public void oncallback(Ord106Resp ord106Resp) {
                tvPay.setText("提交订单");
                tvPay.setClickable(true);
                if (ord106Resp != null) {
                    String flag = ord106Resp.getExceptionFlag();
                    String errorContent = ord106Resp.getExceptionContent();
                    if (StringUtils.equals("1", flag)) {
                        if (StringUtils.isNotEmpty(errorContent)) {
                            DialogAnotherUtil.showCustomAlertDialogWithMessage(InteOrderConfirmActivity.this, null,
                                    errorContent, "确定", "取消",
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
                        }
                    } else {
                        if (orderMoneys > 0) {
                            if (groups.size() == 1) {
                                requestPayOne(ord106Resp);
                            }
                            if (groups.size() > 1) {
                                requestPayMore(ord106Resp);
                            }
                        } else {
                            launch(InteOrdersActivity.class);
                            finish();
                        }
                        refreshCartNum();
                    }
                }
            }

            @Override
            public void onErro(AppHeader header) {

            }
        });
    }

    private void refreshCartNum() {
        Ord111Req req = new Ord111Req();
        getInteJsonService().requestOrd111(this, req, false, new InteJsonService.CallBack<Ord111Resp>() {
            @Override
            public void oncallback(Ord111Resp ord111Resp) {
                if (ord111Resp != null) {
                    Intent intent = new Intent(InteMainActivity.INTE_CART_GOODS_NUM);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("ord111Resp", ord111Resp);
                    intent.putExtras(bundle);
                    sendBroadcast(intent);
                }
            }

            @Override
            public void onErro(AppHeader header) {

            }
        });
    }

    private void requestPayOne(final Ord106Resp ord106Resp){
        Pay101Req req = new Pay101Req();
        req.setRedisKey(ord106Resp.getRedisKey());
        getInteJsonService().requestPay101(this, req, true, new InteJsonService.CallBack<Pay101Resp>() {
            @Override
            public void oncallback(Pay101Resp pay101Resp) {
                Bundle bundle = new Bundle();
                bundle.putString("redisKey", ord106Resp.getRedisKey());
                bundle.putLong("orderScores", orderScores);
                bundle.putSerializable("pay101Resp", pay101Resp);
                launch(InteOneDealConfirmActivity.class, bundle);
            }

            @Override
            public void onErro(AppHeader header) {

            }
        });
    }

    private void requestPayMore(final Ord106Resp ord106Resp){
        Pay103Req req = new Pay103Req();
        req.setRedisKey(ord106Resp.getRedisKey());
        getInteJsonService().requestPay103(this, req, true, new InteJsonService.CallBack<Pay103Resp>() {
            @Override
            public void oncallback(Pay103Resp pay103Resp) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("pay103Resp", pay103Resp);
                launch(InteMoreDealConfirmActivity.class, bundle);
            }

            @Override
            public void onErro(AppHeader header) {

            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
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
