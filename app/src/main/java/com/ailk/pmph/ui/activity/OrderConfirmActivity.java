package com.ailk.pmph.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.ai.ecp.app.req.CoupCheckBeanRespVO;
import com.ai.ecp.app.req.CoupDetailRespVO;
import com.ai.ecp.app.req.Ord009Req;
import com.ai.ecp.app.req.Ord018Req;
import com.ai.ecp.app.req.Ord019Req;
import com.ai.ecp.app.req.Pay001Req;
import com.ai.ecp.app.req.Pay003Req;
import com.ai.ecp.app.req.ROrdInvoiceCommRequest;
import com.ai.ecp.app.req.RSumbitMainReqVO;
import com.ai.ecp.app.resp.AcctInfoResDTO;
import com.ai.ecp.app.resp.CoupCheckBeanRespDTO;
import com.ai.ecp.app.resp.CoupDetailRespDTO;
import com.ai.ecp.app.resp.CustAddrResDTO;
import com.ai.ecp.app.resp.Ord00201Resp;
import com.ai.ecp.app.resp.Ord00202Resp;
import com.ai.ecp.app.resp.Ord00801Resp;
import com.ai.ecp.app.resp.Ord009Resp;
import com.ai.ecp.app.resp.Ord018Resp;
import com.ai.ecp.app.resp.Ord019Resp;
import com.ai.ecp.app.resp.Pay001Resp;
import com.ai.ecp.app.resp.Pay003Resp;
import com.ai.ecp.app.resp.ord.DeliverTypesMap;
import com.ai.ecp.app.resp.ord.DiscountPriceMoneyMap;
import com.ai.ecp.app.resp.ord.OrderMoneyMap;
import com.ai.ecp.app.resp.ord.RealExpressFeesMap;
import com.ailk.butterfly.app.model.AppBody;
import com.ailk.butterfly.app.model.AppHeader;
import com.ailk.butterfly.app.model.JsonService;
import com.ailk.im.net.NetCenter;
import com.ailk.pmph.BuildConfig;
import com.ailk.pmph.R;
import com.ailk.pmph.base.BaseActivity;
import com.ailk.pmph.ui.view.ClearEditText;
import com.ailk.pmph.utils.Constant;
import com.ailk.pmph.utils.DialogUtil;
import com.ailk.pmph.utils.InputUtil;
import com.ailk.pmph.utils.LogUtil;
import com.ailk.pmph.utils.MoneyUtils;
import com.ailk.pmph.utils.StringUtil;
import com.ailk.pmph.utils.ToastUtil;
import com.ailk.pmph.ui.adapter.OrderShopListAdapter;
import com.ailk.pmph.ui.view.CustomExpandableListView;
import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;

import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * 类注释:确认订单
 * 项目名:PMPH
 * 包名:com.ailk.pmph.ui.activity
 * 作者: Chrizz
 * 时间: 2016/3/25 22:24
 */
public class OrderConfirmActivity extends BaseActivity implements View.OnClickListener,
        OrderShopListAdapter.StartBillInterface,OrderShopListAdapter.StartCouponInterface,
        OrderShopListAdapter.UseCashInterface,OrderShopListAdapter.UseContractInterface,
        OrderShopListAdapter.SendDeliverTypeInterface,OrderShopListAdapter.RedirectToShopDetailInterface,
        OrderShopListAdapter.SendBillInterface,OrderShopListAdapter.PromotionCodeInterface{

    @BindView(R.id.iv_back)                     ImageView ivBack;
    @BindView(R.id.sv_container)                ScrollView container;
    @BindView(R.id.tv_contactName)              TextView tvContactName;        //收货人姓名
    @BindView(R.id.tv_contactPhone)             TextView tvContactPhone;       //收货人手机号
    @BindView(R.id.ll_info_layout)              LinearLayout llInfoLayout;
    @BindView(R.id.ll_address_layout)           LinearLayout llAddressLayout;
    @BindView(R.id.tv_chnlAddress)              TextView tvAddress;            //收货人地址
    @BindView(R.id.lv_shop_list)                CustomExpandableListView lvShopList; //购买商品列表
    @BindView(R.id.tv_online)                   TextView tvOnline;             //线上支付
    @BindView(R.id.tv_bank)                     TextView tvBank;               //银行转账
    @BindView(R.id.tv_post_office)              TextView tvPostOffice;         //邮局汇款
    @BindView(R.id.tv_door)                     TextView tvDoor;               //上门支付
    @BindView(R.id.tv_shop_num)                 TextView tvShopNum;            //商品数量
    @BindView(R.id.tv_sub_total_price)          TextView tvSubTotalPrice;      //总商品金额
    @BindView(R.id.tv_express)                  TextView tvExpress;            //运费
    @BindView(R.id.tv_cash_price)               TextView tvCashPrice;          //资金账户
    @BindView(R.id.tv_coupon_price)             TextView tvCouponPrice;        //优惠券
    @BindView(R.id.tv_promotion_code_price)     TextView tvPromotionCodePrice; //优惠码
    @BindView(R.id.tv_prom_price)               TextView tvPromPrice;          //促销金额
    @BindView(R.id.tv_total_payprice)           TextView tvTotalPayPrice;      //应付金额
    @BindView(R.id.tv_total_price)              TextView tvTotalPrice;         //合计金额
    @BindView(R.id.et_buy_info)                 ClearEditText etBuyInfo;       //卖家留言
    @BindView(R.id.tv_pay)                      Button tvPay;                  //去付款

    private List<Ord00201Resp> groups;
    private String redisKey;
    private Long orderMoneys;
    private Long realExpressFees;
    private Long allMoney;
    private Long orderAmounts;
    private Long discountMoneys;
    private Map<Long, Long> orderMoneyMap;
    private Map<Long, Long> realExpressFeesMap;
    private Map<Long, Long> discountPriceMoneyMap;
    private Map<Long, String> deliverTypes;
    private List<CustAddrResDTO> addrs;
    private List<Ord00801Resp> ord00801Resps;
    private List<String> invoiceConList;
    private String ifcoupCodeOpen;

    private OrderShopListAdapter adapter;
    private Map<Long, List<Ord00202Resp>> children = new HashMap<>();
    private Long addrId;
    private String payType = "0";//默认线上支付
    private String billType = "2";//默认不开票,0为普票,1为增票
    private Long cashBalance = 0L;
    private Long contractBalance = 0L;
    private Long totalCoupValue=0L;
    private Long totalPromotionCodeValue=0L;

    private String billTypeText = "普通发票";//发票类型
    private String billTitle = "";//发票抬头
    private String detailFlag = "0";//是否附加明细 0为否 1为是
    private String billContent = "";//发票内容

    private Long totalCashCoup = 0L;
    private Long totalContractCoup = 0L;
    List<Long> checkCoupIds = new ArrayList<>();

    Bundle bundle;

    Map<Long, List<CoupCheckBeanRespDTO>> checkCoupCheckBeansMap = new HashMap<>();
    Map<Long, List<CoupDetailRespDTO>> checkCoupDetailsMap = new HashMap<>();
    Map<Long, List<Long>> checkCoupValuesMap = new HashMap<>();
    Map<Long, List<Long>> checkCoupIdsMap = new HashMap<>();
    Map<Long, List<Long>> detailCoupIdsMap = new HashMap<>();
    List<Map<Long, List<Long>>> checkCoupIdsMapList = new ArrayList<>();

    public static final String REFRESH_ADDRESS = "refresh_address";

    @Override
    protected int getContentViewId() {
        return R.layout.activity_order_confirm;
    }

    public BroadcastReceiver refreshReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (StringUtils.equals(intent.getAction(), REFRESH_ADDRESS)) {
                Bundle extra = intent.getBundleExtra("adddata");
                addrId = extra.getLong("addrId");
                tvContactName.setText(extra.getString("name"));
                tvContactPhone.setText(extra.getString("phone"));
                tvAddress.setText(extra.getString("address"));
            }
        }
    };

    @Override
    public void initView() {
        bundle = getIntent().getExtras();
        if (bundle!=null) {
            groups = (List<Ord00201Resp>) bundle.get("groups");
            redisKey = bundle.getString("redisKey");
            orderMoneys = bundle.getLong("orderMoneys");
            realExpressFees = bundle.getLong("realExpressFees");
            allMoney = bundle.getLong("allMoney");
            orderAmounts = bundle.getLong("orderAmounts");
            discountMoneys = bundle.getLong("discountMoneys");
            addrs = (List<CustAddrResDTO>) bundle.get("addrs");
            ifcoupCodeOpen = bundle.getString("ifcoupCodeOpen");
            OrderMoneyMap orderMoneyMapCls = (OrderMoneyMap) bundle.get("orderMoneyMap");
            if (orderMoneyMapCls != null) {
                orderMoneyMap = orderMoneyMapCls.getOrderMoneyMap();
            }
            RealExpressFeesMap realExpressFeesMapCls = (RealExpressFeesMap) bundle.get("realExpressFeesMap");
            if (realExpressFeesMapCls != null) {
                realExpressFeesMap = realExpressFeesMapCls.getRealExpressFeesMap();
            }
            DiscountPriceMoneyMap discountPriceMoneyMapCls = (DiscountPriceMoneyMap) bundle.get("discountPriceMoneyMap");
            if (discountPriceMoneyMapCls != null) {
                discountPriceMoneyMap = discountPriceMoneyMapCls.getDiscountPriceMoneyMap();
            }
            DeliverTypesMap deliverTypesMapCls = (DeliverTypesMap) bundle.get("deliverTypes");
            if (deliverTypesMapCls != null) {
                deliverTypes = deliverTypesMapCls.getDeliverTypes();
            }
            ord00801Resps = (List<Ord00801Resp>) bundle.get("Ord00801Resps");
            invoiceConList = (List<String>) bundle.get("invoiceConList");
        }
    }

    @Override
    public void initData() {
        if (addrs != null)
        {
            for (int i = 0; i < addrs.size(); i++) {
                CustAddrResDTO addr = addrs.get(i);
                if (StringUtil.equals("1", addr.getUsingFlag()))
                {
                    tvContactName.setText(addr.getContactName());
                    tvContactPhone.setText(addr.getContactPhone());
                    tvAddress.setText(addr.getPccName() + addr.getChnlAddress());
                    addrId = addr.getId();
                }
            }
        }
        else
        {
            tvContactName.setText("");
            tvContactPhone.setText("");
            tvAddress.setText("");
            addrId = 0L;
        }
        for (int i = 0; i < groups.size(); i++) {
            Ord00201Resp bean = groups.get(i);
            bean.setDeliverTypes(deliverTypes.get(bean.getShopId()));
            bean.setExpressMoney(realExpressFeesMap.get(bean.getShopId()));
            children.put(bean.getShopId(), bean.getOrdCartItemList());
            adapter = new OrderShopListAdapter(this, groups,children);
            adapter.setStartCouponInterface(this);
            adapter.setStartBillInterface(this);
            adapter.setUseCashInterface(this);
            adapter.setUseContractInterface(this);
            adapter.setPromotionCodeInterface(this);
            adapter.setSendDeliverTypeInterface(this);
            adapter.setRedirectToShopDetailInterface(this);
            adapter.setSendBillInterface(this);
            adapter.setIfcoupCodeOpen(ifcoupCodeOpen);
            adapter.setOrd00801Resps(ord00801Resps);
            adapter.setCheckCoupIds(checkCoupIds);
        }

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

        tvShopNum.setText("共计" + orderAmounts + "件商品");
        tvSubTotalPrice.setText(MoneyUtils.GoodListPrice(orderMoneys));
        tvExpress.setText(MoneyUtils.GoodListPrice(realExpressFees));
        tvPromPrice.setText("-" + MoneyUtils.GoodListPrice(discountMoneys));
        tvTotalPayPrice.setText(MoneyUtils.GoodListPrice(allMoney));
        MoneyUtils.showSpannedPrice(tvTotalPrice, MoneyUtils.GoodListPrice(allMoney));

        IntentFilter refreshList = new IntentFilter();
        refreshList.addAction(REFRESH_ADDRESS);
        registerReceiver(refreshReceiver,refreshList);
    }

    @Override
    public void redirectToShopDetail(Ord00202Resp product) {
        Bundle bundle = new Bundle();
        bundle.putString(Constant.SHOP_GDS_ID, String.valueOf(product.getGdsId()));
        bundle.putString(Constant.SHOP_SKU_ID, String.valueOf(product.getSkuId()));
        launch(ShopDetailActivity.class, bundle);
    }

    @Override
    public void startCoupon(Ord00201Resp group, List<CoupCheckBeanRespDTO> coupOrdSkuList, List<Long> _checkCoupIds) {
        checkCoupIds = _checkCoupIds;
        Bundle bundle = new Bundle();
        bundle.putSerializable("coupOrdSkuList", (Serializable) coupOrdSkuList);
        bundle.putSerializable("group", group);
        Map<Long, List<Long>> checkCoupIdsMap = new HashMap<>();
        if (checkCoupIds!=null && checkCoupIds.size() > 0) {
            checkCoupIdsMap.put(group.getShopId(),checkCoupIds);
            checkCoupIdsMapList.add(checkCoupIdsMap);
        }
        bundle.putSerializable("checkCoupIdsMapList", (Serializable) checkCoupIdsMapList);
        Intent intent = new Intent(this, CouponUseActivity.class);
        intent.putExtras(bundle);
        startActivityForResult(intent, 100);
    }

    @Override
    public void usePromotionCode(final Ord00201Resp shop, final TextView textView, final EditText inputView, final TextView showView, final TextView clickView) {
        if (StringUtils.equals(clickView.getText().toString(),"使用")) {
            final Long shopPrice;
            if (shop.getAcctInfoResDTO().getDeductOrderMoney() != null) {
                shopPrice = orderMoneyMap.get(shop.getShopId()) - discountPriceMoneyMap.get(shop.getCartId()) - shop.getAcctInfoResDTO().getDeductOrderMoney();
            } else {
                shopPrice = orderMoneyMap.get(shop.getShopId()) - discountPriceMoneyMap.get(shop.getCartId());
            }
            Ord019Req req = new Ord019Req();
            req.setShopId(shop.getShopId());
            req.setCoupCode(inputView.getText().toString());
            req.setRedisKey(redisKey);
            req.setSourceKey(null);
            getJsonService().requestOrd019(this, req, true, new JsonService.CallBack<Ord019Resp>() {
                @Override
                public void oncallback(Ord019Resp ord019Resp) {
                    if (ord019Resp != null) {
                        String ifCanUse = ord019Resp.getIfCanUse();
                        String resultMsg = ord019Resp.getResultMsg();
                        if (StringUtils.isNotEmpty(ifCanUse)) {
                            if (StringUtils.equals("1", ifCanUse)) {
                                Long promotionValue = ord019Resp.getCoupValue();
                                String promotionCode = inputView.getText().toString();
                                if (promotionValue > shopPrice) {
                                    ToastUtil.showCenter(OrderConfirmActivity.this, "优惠金额不能大于订单金额");
                                } else {
                                    clickView.setText("取消");
                                    hideCashInput(inputView, showView);
                                    textView.setText("您使用的优惠码为" + promotionCode);
                                    showView.setText("，已优惠" + StringUtils.remove(MoneyUtils.GoodListPrice(ord019Resp.getCoupValue()), "￥") + "元");
                                    shop.setPromotionCodeValue(promotionValue);
                                    shop.setPromotionCode(promotionCode);
                                    shop.setPromotionHashKey(ord019Resp.getHashKey());
                                    totalPromotionCodeValue += promotionValue;
                                    calculate();
                                }
                            } else {
                                if (StringUtils.isNotEmpty(resultMsg)) {
                                    ToastUtil.showCenter(OrderConfirmActivity.this, resultMsg);
                                }
                            }
                        }
                    }
                }

                @Override
                public void onErro(AppHeader header) {

                }
            });
        } else if (StringUtils.equals(clickView.getText().toString(),"取消")) {
            clickView.setText("使用");
            hideCashShow(inputView, showView);
            textView.setText("请输入您的优惠码：");
            inputView.setText(null);
            totalPromotionCodeValue -= shop.getPromotionCodeValue();
            shop.setPromotionCodeValue(0L);
            shop.setPromotionCode(null);
            shop.setPromotionHashKey(null);
            calculate();
        }
    }

    @Override
    public void startBill(String text1, String text2, String text3, Ord00201Resp shop) {
        Bundle bundle = new Bundle();
        if (shop.getBillTypeText()==null) {
            shop.setBillTypeText(billTypeText);
        }
        if (shop.getBillTitle()==null) {
            shop.setBillTitle(billTitle);
        }
        if (shop.getDetailFlag()==null) {
            shop.setDetailFlag(detailFlag);
        }
        if (TextUtils.isEmpty(shop.getBillContent())) {
            shop.setBillContent(invoiceConList.get(0));
        }
        bundle.putSerializable("shop", shop);
        bundle.putSerializable("invoiceConList",(Serializable) invoiceConList);
        Intent intent = new Intent(this, InvoiceActivity.class);
        intent.putExtras(bundle);
        startActivityForResult(intent, 1000);
    }

    @Override
    public void sendBill(String invoiceType, Long shopId) {
        billType = invoiceType;

        for (Ord00201Resp item :groups){
            if (item.getShopId() == shopId){
                item.setBillType(billType);

            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //==============================================收货地址===================================================//
        if (requestCode == 10000 && resultCode == 10001) {
            if (data != null) {
                String contactName = data.getExtras().getString("contactName");
                String contactPhone = data.getExtras().getString("contactPhone");
                String address = data.getExtras().getString("address");
                addrId = data.getExtras().getLong("addrId");
                tvContactName.setText(contactName);
                tvContactPhone.setText(contactPhone);
                tvAddress.setText(address);
            }
        }

        //================================================优惠券===================================================//
        if (requestCode == 100 && resultCode == 101) {
            if (data != null) {
                checkCoupCheckBeansMap = (Map<Long, List<CoupCheckBeanRespDTO>>) data.getExtras().get("checkCoupBeansMap");
                checkCoupDetailsMap = (Map<Long, List<CoupDetailRespDTO>>) data.getExtras().get("checkCoupDetailsMap");
                checkCoupValuesMap = (Map<Long, List<Long>>) data.getExtras().get("checkCoupValuesMap");
                checkCoupIdsMap = (Map<Long, List<Long>>) data.getExtras().get("checkCoupIdsMap");
                detailCoupIdsMap = (Map<Long, List<Long>>) data.getExtras().get("detailCoupIdsMap");
                Long shopId = data.getExtras().getLong("shopId");
                String noExpress = data.getExtras().getString("noExpress");

                for (int i = 0; i < groups.size(); i++) {
                    Ord00201Resp group = groups.get(i);
                    if (shopId.equals(group.getShopId())) {
                        Long expressMoney = realExpressFeesMap.get(shopId);
                        group.setCoupIdList(checkCoupIdsMap.get(shopId));
                        group.setDetailCoupIdList(detailCoupIdsMap.get(shopId));
                        group.setCoupValueList(checkCoupValuesMap.get(shopId));
                        group.setCoupDetails(checkCoupDetailsMap.get(shopId));
                        group.setCoupCheckBeans(checkCoupCheckBeansMap.get(shopId));
                        group.setNoExpress(noExpress);
                        if (StringUtils.equals("1", noExpress)) {
                            group.setExpressMoney(0L);
                        } else {
                            if (StringUtils.equals("1", group.getCheckDeliverType())
                                    && !StringUtils.equals("1", group.getNoExpress()))
                            {
                                group.setExpressMoney(expressMoney);
                            }
                            else
                            {
                                group.setExpressMoney(0L);
                            }
                        }
                        showExpressMoney();
                        adapter.notifyDataSetChanged();
                        totalCoupValue = 0L;
                        for (int j = 0; j < groups.size(); j++) {
                            Ord00201Resp shop = groups.get(j);
                            List<Long> coupValues = shop.getCoupValueList();
                            if (coupValues!=null && coupValues.size()>0) {
                                for (int m = 0; m < coupValues.size(); m++) {
                                    Long coupValue = coupValues.get(m);
                                    totalCoupValue +=coupValue;
                                }
                            }
                            tvCouponPrice.setText("-"+MoneyUtils.GoodListPrice(totalCoupValue));
                            if (StringUtils.equals("1", shop.getNoExpress())) {
                                tvTotalPayPrice.setText(MoneyUtils.GoodListPrice(allMoney - totalCashCoup - totalContractCoup - totalCoupValue - expressMoney));
                                String price = MoneyUtils.GoodListPrice(allMoney - totalCashCoup - totalContractCoup - totalCoupValue - expressMoney);
                                MoneyUtils.showSpannedPrice(tvTotalPrice, price);
                            } else {
                                tvTotalPayPrice.setText(MoneyUtils.GoodListPrice(allMoney - totalCashCoup - totalContractCoup - totalCoupValue));
                                String price = MoneyUtils.GoodListPrice(allMoney - totalCashCoup - totalContractCoup - totalCoupValue);
                                MoneyUtils.showSpannedPrice(tvTotalPrice, price);
                            }
                        }
                        break;
                    }
                }
            }
        }
        //======================================================发票==================================================//
        if (requestCode == 1000 && resultCode == 1001) {
            if (data != null) {
                Ord00201Resp shop = (Ord00201Resp) data.getExtras().get("shop");
                if (shop != null) {
                    Long shopId = shop.getShopId();
                    for (int i = 0; i < groups.size(); i++) {
                        Ord00201Resp group = groups.get(i);
                        if (shopId.equals(group.getShopId())) {
                            group.setBillType(shop.getBillType());
                            group.setBillTypeText(shop.getBillTypeText());
                            group.setDetailFlag(shop.getDetailFlag());
                            group.setBillTitle(shop.getBillTitle());
                            group.setBillContent(shop.getBillContent());
                            group.setTaxpayerNo(shop.getTaxpayerNo());
                            adapter.notifyDataSetChanged();
                            break;
                        }
                    }
                }
            }
        }
    }

    /**
     * 使用资金账户
     * @param shopId
     * @param acctInfo
     * @param inputView
     * @param showView
     * @param clickView
     */
    @Override
    public void useCash(Long shopId, AcctInfoResDTO acctInfo, EditText inputView, TextView showView, TextView clickView, int position) {
        for (int i = 0; i < groups.size(); i++) {
            Ord00201Resp shop = groups.get(i);
            if (shopId.equals(shop.getShopId())) {
                AcctInfoResDTO acctInfoResDTO = shop.getAcctInfoResDTO();
                acctInfoResDTO.setAcctType(acctInfo.getAcctType());
                acctInfoResDTO.setShopId(acctInfo.getShopId());
                Long inputBalance;
                Long shopPrice;
                Long orderPrice = orderMoneyMap.get(shopId);
                Long discountPrice = discountPriceMoneyMap.get(shop.getCartId());
                Long promotionCodePrice = shop.getPromotionCodeValue();

                if (StringUtils.equals(clickView.getText().toString(),"使用"))
                {
                    if (StringUtils.isEmpty(inputView.getText().toString()))
                    {
                        ToastUtil.showCenter(this, "请输入使用金额");
                    }
                    else
                    {
                        if (discountPrice != null && promotionCodePrice != null)
                        {
                            shopPrice = orderPrice - discountPrice - promotionCodePrice;
                        }
                        else if (discountPrice != null)
                        {
                            shopPrice = orderPrice - discountPrice;
                        }
                        else
                        {
                            shopPrice = orderPrice;
                        }
                        if (StringUtils.contains(inputView.getText().toString(), "."))
                        {
                            String decimal = StringUtils.substringAfter(inputView.getText().toString(), ".");
                            if (decimal.length() == 1)
                            {
                                inputBalance = Long.parseLong(StringUtils.remove(inputView.getText().toString(), "."))*10L;
                            }
                            else
                            {
                                inputBalance = Long.parseLong(StringUtils.remove(inputView.getText().toString(), "."));
                            }
                        }
                        else
                        {
                            inputBalance = Long.parseLong(inputView.getText().toString())*100L;
                        }
                        if (inputBalance > shopPrice)
                        {
                            ToastUtil.showCenter(this, "资金账户使用金额不能大于订单金额");
                        }
                        else
                        {
                            clickView.setText("取消");
                            if (acctInfo.getDeductOrderMoney() < inputBalance)
                            {
                                hideCashInput(inputView, showView);
                                showView.setText(StringUtils.remove(MoneyUtils.GoodListPrice(acctInfo.getDeductOrderMoney()), "￥"));
                                totalCashCoup += acctInfo.getDeductOrderMoney();
                                cashBalance = totalCashCoup;
                                calculate();
                                acctInfoResDTO.setDeductOrderMoney(acctInfo.getDeductOrderMoney());
                            }
                            else
                            {
                                hideCashInput(inputView, showView);
                                showView.setText(StringUtils.remove(MoneyUtils.GoodListPrice(inputBalance), "￥"));
                                totalCashCoup += inputBalance;
                                cashBalance = totalCashCoup;
                                calculate();
                                acctInfoResDTO.setDeductOrderMoney(inputBalance);
                            }
                        }
                    }
                }
                else if (StringUtils.equals(clickView.getText().toString(),"取消"))
                {
                    clickView.setText("使用");
                    hideCashShow(inputView, showView);
                    Long showBalance;
                    if (StringUtils.contains(showView.getText().toString(), "."))
                    {
                        String decimal = StringUtils.substringAfter(showView.getText().toString(), ".");
                        if (decimal.length() == 1)
                        {
                            showBalance = Long.parseLong(StringUtils.remove(showView.getText().toString(), "."))*10L;
                        }
                        else
                        {
                            showBalance = Long.parseLong(StringUtils.remove(showView.getText().toString(), "."));
                        }
                    }
                    else
                    {
                        showBalance = Long.parseLong(showView.getText().toString())*100L;
                    }
                    inputView.setText(null);
                    totalCashCoup -= showBalance;
                    cashBalance = totalCashCoup;
                    calculate();
                    acctInfoResDTO.setDeductOrderMoney(0L);
                }
                shop.setAcctInfoResDTO(acctInfoResDTO);
            }
        }
    }

    private void hideCashInput(EditText inputView, TextView showView) {
        setGone(inputView);
        setVisible(showView);
    }

    private void hideCashShow(EditText inputView, TextView showView){
        setGone(showView);
        setVisible(inputView);
    }

    /**
     * 显示金额
     */
    private void calculate() {
        tvCashPrice.setText("-" + MoneyUtils.GoodListPrice(totalCashCoup + totalContractCoup));
        tvCouponPrice.setText("-" + MoneyUtils.GoodListPrice(totalCoupValue));
        tvPromotionCodePrice.setText("-" + MoneyUtils.GoodListPrice(totalPromotionCodeValue));
        tvTotalPayPrice.setText(MoneyUtils.GoodListPrice(allMoney - totalCashCoup - totalContractCoup - totalCoupValue - totalPromotionCodeValue));
        String price = MoneyUtils.GoodListPrice(allMoney - totalCashCoup - totalContractCoup - totalCoupValue - totalPromotionCodeValue);
        MoneyUtils.showSpannedPrice(tvTotalPrice, price);
        adapter.notifyDataSetChanged();
    }

    /**
     * 使用合约账户
     * @param shopId
     * @param acctInfo
     * @param inputView
     * @param showView
     * @param clickView
     */
    @Override
    public void useContract(Long shopId, AcctInfoResDTO acctInfo, EditText inputView, TextView showView, TextView clickView) {
        for (int i = 0; i < groups.size(); i++) {
            Ord00201Resp shop = groups.get(i);
            if (shopId.equals(shop.getShopId())) {
                AcctInfoResDTO acctInfoResDTO = shop.getAcctInfoResDTO();
                acctInfoResDTO.setAcctType(acctInfo.getAcctType());
                acctInfoResDTO.setShopId(acctInfo.getShopId());
                Long inputBalance;
                Long shopPrice;
                Long orderPrice = orderMoneyMap.get(shopId);
                Long discountPrice = discountPriceMoneyMap.get(shop.getCartId());
                Long promotionCodePrice = shop.getPromotionCodeValue();

                if (StringUtils.equals(clickView.getText().toString(),"使用"))
                {
                    if (StringUtils.isEmpty(inputView.getText().toString()))
                    {
                        ToastUtil.showCenter(this, "请输入使用金额");
                    }
                    else
                    {
                        if (discountPrice != null && promotionCodePrice != null)
                        {
                            shopPrice = orderPrice - discountPrice - promotionCodePrice;
                        }
                        else if (discountPrice != null)
                        {
                            shopPrice = orderPrice - discountPrice;
                        }
                        else
                        {
                            shopPrice = orderPrice;
                        }
                        if (StringUtils.contains(inputView.getText().toString(), "."))
                        {
                            String decimal = StringUtils.substringAfter(inputView.getText().toString(), ".");
                            if (decimal.length() == 1)
                            {
                                inputBalance = Long.parseLong(StringUtils.remove(inputView.getText().toString(), "."))*10L;
                            }
                            else
                            {
                                inputBalance = Long.parseLong(StringUtils.remove(inputView.getText().toString(), "."));
                            }
                        }
                        else
                        {
                            inputBalance = Long.parseLong(inputView.getText().toString())*100L;
                        }
                        if (inputBalance > shopPrice)
                        {
                            ToastUtil.showCenter(this, "资金账户使用金额不能大于订单金额");
                        }
                        else
                        {
                            clickView.setText("取消");
                            if (acctInfo.getDeductOrderMoney() < inputBalance)
                            {
                                hideCashInput(inputView, showView);
                                showView.setText(StringUtils.remove(MoneyUtils.GoodListPrice(acctInfo.getDeductOrderMoney()), "￥"));
                                totalContractCoup += acctInfo.getDeductOrderMoney();
                                contractBalance = totalContractCoup;
                                calculate();
                                acctInfoResDTO.setDeductOrderMoney(acctInfo.getDeductOrderMoney());
                            }
                            else
                            {
                                hideCashInput(inputView, showView);
                                showView.setText(StringUtils.remove(MoneyUtils.GoodListPrice(inputBalance), "￥"));
                                totalContractCoup += inputBalance;
                                contractBalance = totalContractCoup;
                                calculate();
                                acctInfoResDTO.setDeductOrderMoney(inputBalance);
                            }
                        }
                    }
                }
                else if (StringUtils.equals(clickView.getText().toString(),"取消"))
                {
                    clickView.setText("使用");
                    hideCashShow(inputView, showView);
                    Long showBalance;
                    if (StringUtils.contains(showView.getText().toString(), "."))
                    {
                        String decimal = StringUtils.substringAfter(showView.getText().toString(), ".");
                        if (decimal.length() == 1)
                        {
                            showBalance = Long.parseLong(StringUtils.remove(showView.getText().toString(), "."))*10L;
                        }
                        else
                        {
                            showBalance = Long.parseLong(StringUtils.remove(showView.getText().toString(), "."));
                        }
                    }
                    else
                    {
                        showBalance = Long.parseLong(showView.getText().toString())*100L;
                    }
                    inputView.setText(null);
                    totalContractCoup -= showBalance;
                    contractBalance = totalContractCoup;
                    calculate();
                    acctInfoResDTO.setDeductOrderMoney(0L);
                }
                shop.setAcctInfoResDTO(acctInfoResDTO);
            }
        }
    }

    @Override
    public void sendDeliverType(Ord00201Resp shop) {
        Long expressMoney = realExpressFeesMap.get(shop.getShopId());
        if (StringUtils.equals("1", shop.getCheckDeliverType())
                && !StringUtils.equals("1", shop.getNoExpress())) //配送方式为快递
        {
            shop.setExpressMoney(expressMoney);
        }
        else if (StringUtils.equals("1", shop.getNoExpress())) //免邮优惠券
        {
            shop.setExpressMoney(0L);
        }
        else
        {
            shop.setExpressMoney(0L);
        }
        showExpressMoney();
    }

    private void showExpressMoney() {
        Long totalExpress = 0L;
        for (int i = 0; i < groups.size(); i++) {
            Ord00201Resp shop = groups.get(i);
            totalExpress += shop.getExpressMoney();
        }
        tvExpress.setText(MoneyUtils.GoodListPrice(totalExpress));
        if (totalExpress==0) {
            String price = MoneyUtils.GoodListPrice(allMoney-totalCashCoup-totalContractCoup-totalCoupValue-totalPromotionCodeValue-realExpressFees);
            tvTotalPayPrice.setText(price);
            MoneyUtils.showSpannedPrice(tvTotalPrice, price);
        }
        else if (totalExpress>0 && totalExpress<realExpressFees) {
            String price = MoneyUtils.GoodListPrice(allMoney-totalCashCoup-totalContractCoup-totalCoupValue-totalPromotionCodeValue-(realExpressFees-totalExpress));
            tvTotalPayPrice.setText(price);
            MoneyUtils.showSpannedPrice(tvTotalPrice, price);
        }
        else {
            String price = MoneyUtils.GoodListPrice(allMoney-totalCashCoup-totalContractCoup-totalCoupValue-totalPromotionCodeValue);
            tvTotalPayPrice.setText(price);
            MoneyUtils.showSpannedPrice(tvTotalPrice, price);
        }
    }

    @Override
    @OnClick({R.id.iv_back, R.id.ll_info_layout, R.id.ll_address_layout, R.id.tv_online, R.id.tv_bank,
                R.id.tv_post_office, R.id.tv_door, R.id.tv_pay})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.ll_info_layout:
                Intent intent = new Intent(this, AddressManagerActivity.class);
                intent.putExtra("addrId", addrId);
                startActivityForResult(intent, 10000);
                break;
            case R.id.ll_address_layout:
                Intent _intent = new Intent(this, AddressManagerActivity.class);
                _intent.putExtra("addrId", addrId);
                startActivityForResult(_intent, 10000);
                break;
            case R.id.tv_online:
                payType = "0";
                clickOnline(tvOnline, tvBank, tvPostOffice, tvDoor);
                break;
            case R.id.tv_bank:
                payType = "3";
                clickBank(tvBank, tvOnline, tvPostOffice, tvDoor);
                break;
            case R.id.tv_post_office:
                payType = "2";
                clickPostOffice(tvPostOffice, tvOnline, tvBank, tvDoor);
                break;
            case R.id.tv_door:
                payType = "1";
                adapter.setTakeSelf("自提");
                clickDoor(tvDoor, tvOnline, tvBank, tvPostOffice);
                break;
            case R.id.tv_pay:
                requestToPay();
                break;
            default:
                break;
        }
    }

    private void clickOnline(TextView tvOnline, TextView tvBank, TextView tvPostOffice, TextView tvDoor){
        tvOnline.setBackgroundResource(R.drawable.shape_round_delbutton);
        if (StringUtils.equals(BuildConfig.BUILD_TYPE, Constant.BUILD_TYPE_PMPH)) {
            tvOnline.setTextColor(ContextCompat.getColor(this, R.color.orange_ff6a00));
        } else {
            tvOnline.setTextColor(ContextCompat.getColor(this, R.color.red));
        }
        tvBank.setBackgroundResource(R.drawable.shape_round_textview);
        tvBank.setTextColor(ContextCompat.getColor(this, R.color.gray_312B2D));
        tvPostOffice.setBackgroundResource(R.drawable.shape_round_textview);
        tvPostOffice.setTextColor(ContextCompat.getColor(this, R.color.gray_312B2D));
        tvDoor.setBackgroundResource(R.drawable.shape_round_textview);
        tvDoor.setTextColor(ContextCompat.getColor(this, R.color.gray_312B2D));
    }

    private void clickBank(TextView tvBank, TextView tvOnline, TextView tvPostOffice, TextView tvDoor){
        tvBank.setBackgroundResource(R.drawable.shape_round_delbutton);
        if (StringUtils.equals(BuildConfig.BUILD_TYPE, Constant.BUILD_TYPE_PMPH)) {
            tvBank.setTextColor(ContextCompat.getColor(this, R.color.orange_ff6a00));
        } else {
            tvBank.setTextColor(ContextCompat.getColor(this, R.color.red));
        }
        tvOnline.setBackgroundResource(R.drawable.shape_round_textview);
        tvOnline.setTextColor(ContextCompat.getColor(this, R.color.gray_312B2D));
        tvPostOffice.setBackgroundResource(R.drawable.shape_round_textview);
        tvPostOffice.setTextColor(ContextCompat.getColor(this, R.color.gray_312B2D));
        tvDoor.setBackgroundResource(R.drawable.shape_round_textview);
        tvDoor.setTextColor(ContextCompat.getColor(this, R.color.gray_312B2D));
    }

    private void clickPostOffice(TextView tvPostOffice, TextView tvOnline, TextView tvBank, TextView tvDoor){
        tvPostOffice.setBackgroundResource(R.drawable.shape_round_delbutton);
        if (StringUtils.equals(BuildConfig.BUILD_TYPE, Constant.BUILD_TYPE_PMPH)) {
            tvPostOffice.setTextColor(ContextCompat.getColor(this, R.color.orange_ff6a00));
        } else {
            tvPostOffice.setTextColor(ContextCompat.getColor(this, R.color.red));
        }
        tvOnline.setBackgroundResource(R.drawable.shape_round_textview);
        tvOnline.setTextColor(ContextCompat.getColor(this, R.color.gray_312B2D));
        tvBank.setBackgroundResource(R.drawable.shape_round_textview);
        tvBank.setTextColor(ContextCompat.getColor(this, R.color.gray_312B2D));
        tvDoor.setBackgroundResource(R.drawable.shape_round_textview);
        tvDoor.setTextColor(ContextCompat.getColor(this, R.color.gray_312B2D));
    }

    private void clickDoor(TextView tvDoor, TextView tvOnline, TextView tvBank, TextView tvPostOffice){
        tvDoor.setBackgroundResource(R.drawable.shape_round_delbutton);
        if (StringUtils.equals(BuildConfig.BUILD_TYPE, Constant.BUILD_TYPE_PMPH)) {
            tvDoor.setTextColor(ContextCompat.getColor(this, R.color.orange_ff6a00));
        } else {
            tvDoor.setTextColor(ContextCompat.getColor(this, R.color.red));
        }
        tvOnline.setBackgroundResource(R.drawable.shape_round_textview);
        tvOnline.setTextColor(ContextCompat.getColor(this, R.color.gray_312B2D));
        tvBank.setBackgroundResource(R.drawable.shape_round_textview);
        tvBank.setTextColor(ContextCompat.getColor(this, R.color.gray_312B2D));
        tvPostOffice.setBackgroundResource(R.drawable.shape_round_textview);
        tvPostOffice.setTextColor(ContextCompat.getColor(this, R.color.gray_312B2D));
    }

    /**
     *
     */
    private void requestToPay() {
        if (StringUtils.isEmpty(tvContactName.getText().toString()))
        {
            ToastUtil.showCenter(this, "请填写收货人");
            return;
        }
        if (StringUtils.isEmpty(tvContactPhone.getText().toString()))
        {
            ToastUtil.showCenter(this, "请填写联系方式");
            return;
        }
        if (StringUtils.isEmpty(tvAddress.getText().toString()))
        {
            ToastUtil.showCenter(this, "请填写收货地址");
            return;
        }

        tvPay.setText("正在提交...");
        tvPay.setClickable(false);
        Ord009Req request = new Ord009Req();

        List<RSumbitMainReqVO> sumbitMainList = new ArrayList<>();

        for (int i = 0; i < groups.size(); i++) {
            Ord00201Resp shop = groups.get(i);
            RSumbitMainReqVO rSumbitMainReqVO = new RSumbitMainReqVO();
            rSumbitMainReqVO.setCartId(shop.getCartId());
            Long realMoney;
            Long checkCoupValue = 0L;
            Long cashValue;
            Long promotionCodeValue;
            if (shop.getAcctInfoResDTO().getDeductOrderMoney() == null) {
                cashValue = 0L;
            } else {
                cashValue = shop.getAcctInfoResDTO().getDeductOrderMoney();
            }
            if (shop.getPromotionCodeValue() == null) {
                promotionCodeValue = 0L;
            } else {
                promotionCodeValue = shop.getPromotionCodeValue();
            }
            if (StringUtils.equals("1", shop.getCheckDeliverType())
                    && !StringUtils.equals("1", shop.getNoExpress()))//快递且不使用免邮优惠券
            {
                if (shop.getCoupValueList()!=null) {
                    for (int j = 0; j < shop.getCoupValueList().size(); j++) {
                        Long coupValue = shop.getCoupValueList().get(j);
                        checkCoupValue += coupValue;
                    }
                    rSumbitMainReqVO.setRealExpressFee(shop.getExpressMoney());
                    realMoney = orderMoneyMap.get(shop.getShopId()) + realExpressFeesMap.get(shop.getShopId())
                            - discountPriceMoneyMap.get(shop.getCartId())
                            - (checkCoupValue + cashValue + contractBalance + promotionCodeValue);
                    rSumbitMainReqVO.setRealMoney(realMoney);
                } else {
                    rSumbitMainReqVO.setRealExpressFee(shop.getExpressMoney());
                    realMoney = orderMoneyMap.get(shop.getShopId()) + realExpressFeesMap.get(shop.getShopId())
                            - discountPriceMoneyMap.get(shop.getCartId())
                            - (checkCoupValue + cashValue + contractBalance + promotionCodeValue);
                    rSumbitMainReqVO.setRealMoney(realMoney);
                }
            }
            else if (StringUtils.equals("1", shop.getCheckDeliverType())
                    && StringUtils.equals("1", shop.getNoExpress()))//快递但使用了免邮优惠券
            {
                if (shop.getCoupValueList()!=null) {
                    for (int k = 0; k < shop.getCoupValueList().size(); k++) {
                        Long coupValue = shop.getCoupValueList().get(k);
                        checkCoupValue += coupValue;
                    }

                    rSumbitMainReqVO.setRealExpressFee(0L);
                    realMoney = orderMoneyMap.get(shop.getShopId()) - discountPriceMoneyMap.get(shop.getCartId())
                            - (checkCoupValue + cashValue + contractBalance + promotionCodeValue);
                    rSumbitMainReqVO.setRealMoney(realMoney);
                } else {
                    rSumbitMainReqVO.setRealExpressFee(0L);
                    realMoney = orderMoneyMap.get(shop.getShopId()) - discountPriceMoneyMap.get(shop.getCartId())
                            - (checkCoupValue + cashValue + contractBalance + promotionCodeValue);
                    rSumbitMainReqVO.setRealMoney(realMoney);
                }
            }
            else
            {
                if (shop.getCoupValueList()!=null) {
                    for (int k = 0; k < shop.getCoupValueList().size(); k++) {
                        Long coupValue = shop.getCoupValueList().get(k);
                        checkCoupValue += coupValue;
                    }

                    rSumbitMainReqVO.setRealExpressFee(0L);
                    realMoney = orderMoneyMap.get(shop.getShopId()) - discountPriceMoneyMap.get(shop.getCartId())
                            - (checkCoupValue + cashValue + contractBalance + promotionCodeValue);
                    rSumbitMainReqVO.setRealMoney(realMoney);
                } else {
                    rSumbitMainReqVO.setRealExpressFee(0L);
                    realMoney = orderMoneyMap.get(shop.getShopId()) - discountPriceMoneyMap.get(shop.getCartId())
                            - (checkCoupValue + cashValue + contractBalance + promotionCodeValue);
                    rSumbitMainReqVO.setRealMoney(realMoney);
                }
            }
            rSumbitMainReqVO.setCoupCode(shop.getPromotionCode());
            rSumbitMainReqVO.setCoupCodeMoney(shop.getPromotionCodeValue());
            rSumbitMainReqVO.setHashKey(shop.getPromotionHashKey());
            rSumbitMainReqVO.setShopDiscountMoney(0L);
            rSumbitMainReqVO.setDiscountMoney(discountPriceMoneyMap.get(shop.getCartId()));
            rSumbitMainReqVO.setInvoiceType(shop.getBillType());
            rSumbitMainReqVO.setDeliverType(shop.getCheckDeliverType());

            //发票处理
            ROrdInvoiceCommRequest rOrdInvoiceCommRequest = new ROrdInvoiceCommRequest();
            if (StringUtils.equals("2", shop.getBillType())) //不开发票
            {
                rOrdInvoiceCommRequest.setDetailFlag("0");
                rOrdInvoiceCommRequest.setInvoiceTitle("");
                rOrdInvoiceCommRequest.setInvoiceContent("");
                rOrdInvoiceCommRequest.setInvoiceType(shop.getBillType());
                rSumbitMainReqVO.setrOrdInvoiceCommRequest(rOrdInvoiceCommRequest);
            } else {
                rOrdInvoiceCommRequest.setDetailFlag(shop.getDetailFlag());
                rOrdInvoiceCommRequest.setInvoiceTitle(shop.getBillTitle());
                rOrdInvoiceCommRequest.setInvoiceContent(shop.getBillContent());
                rOrdInvoiceCommRequest.setInvoiceType(shop.getBillType());
                //纳税人识别号
                rOrdInvoiceCommRequest.setTaxpayerNo(shop.getTaxpayerNo());
                rSumbitMainReqVO.setrOrdInvoiceCommRequest(rOrdInvoiceCommRequest);
            }

//            if (shop.getAcctInfoResDTO().getDeductOrderMoney()!=null
//                    && shop.getShopId().equals(shop.getAcctInfoResDTO().getShopId())) {
//                ordAcctInfoList.add(shop.getAcctInfoResDTO());
//                rSumbitMainReqVO.setOrdAcctInfoList(ordAcctInfoList);
//            } else {
//                rSumbitMainReqVO.setOrdAcctInfoList(ordAcctInfoList);
//            }
            List<AcctInfoResDTO> ordAcctInfoList = new ArrayList<>();
            ordAcctInfoList.add(shop.getAcctInfoResDTO());
            rSumbitMainReqVO.setOrdAcctInfoList(ordAcctInfoList);

            List<CoupCheckBeanRespVO> coupCheckBean = new ArrayList<>();
            CoupCheckBeanRespVO coupCheckBeanRespVO = new CoupCheckBeanRespVO();

            if (shop.getCoupCheckBeans().size() > 0 && shop.getCoupCheckBeans()!=null) {
                List<CoupDetailRespVO> coupDetails = new ArrayList<>();
                for (int m = 0; m < shop.getCoupCheckBeans().size(); m++) {
                    CoupCheckBeanRespDTO checkCoupBean = shop.getCoupCheckBeans().get(m);
                    coupCheckBeanRespVO.setCoupId(checkCoupBean.getCoupId());
                    coupCheckBeanRespVO.setCoupSize(checkCoupBean.getCoupSize());
                    coupCheckBeanRespVO.setVarLimit(checkCoupBean.getVarLimit());
                }
                if (shop.getCoupDetails().size() > 0 && shop.getCoupDetails()!=null) {
                    for (int n = 0; n < shop.getCoupDetails().size(); n++) {
                        CoupDetailRespDTO coupDetail = shop.getCoupDetails().get(n);
                        CoupDetailRespVO coupDetailRespVO = new CoupDetailRespVO();
                        coupDetailRespVO.setCoupId(coupDetail.getCoupId());
                        coupDetailRespVO.setCoupNo(coupDetail.getCoupNo());
                        coupDetailRespVO.setId(coupDetail.getId());
                        coupDetailRespVO.setCoupName(coupDetail.getCoupName());
                        if (StringUtils.equals("1", shop.getNoExpress())) {
                            coupDetailRespVO.setCoupValue(0L);
                        } else {
                            coupDetailRespVO.setCoupValue(coupDetail.getCoupValue());
                        }
                        coupDetails.add(coupDetailRespVO);
                    }
                    coupCheckBeanRespVO.setCoupDetails(coupDetails);
                }
                coupCheckBean.add(coupCheckBeanRespVO);
                rSumbitMainReqVO.setCoupCheckBean(coupCheckBean);
            }

            if (StringUtils.isNotEmpty(etBuyInfo.getText().toString())) {
                rSumbitMainReqVO.setBuyerMsg(etBuyInfo.getText().toString());
            }
            sumbitMainList.add(rSumbitMainReqVO);
            request.setSumbitMainList(sumbitMainList);
        }
        request.setRedisKey(redisKey);
        request.setAddrId(addrId);
        request.setPayType(payType);
        DialogUtil.showCustomerProgressDialog(this);
        NetCenter.build(this)
                .requestDefault(request,"ord009")
                .map(new Func1<AppBody, Ord009Resp>() {
                    @Override
                    public Ord009Resp call(AppBody appBody) {
                        return null == appBody ? null : (Ord009Resp) appBody;
                    }
                }).subscribe(new Action1<Ord009Resp>() {
            @Override
            public void call(Ord009Resp ord009Resp) {
                DialogUtil.dismissDialog();
                tvPay.setText("提交订单");
                tvPay.setClickable(true);
                if (ord009Resp != null) {
                    String flag = ord009Resp.getExceptionFlag();
                    String errorContent = ord009Resp.getExceptionContent();
                    if (StringUtils.isNotEmpty(flag)) {
                        if (StringUtils.equals("1", flag)) {
                            if (StringUtils.isNotEmpty(errorContent)) {
                                new AlertView("提示", errorContent, null, new String[]{"确定"},
                                        null, OrderConfirmActivity.this, AlertView.Style.Alert,
                                        new OnItemClickListener() {
                                            @Override
                                            public void onItemClick(Object o, int position) {
                                                switch (position) {
                                                    case 0:
                                                        onBackPressed();
                                                        break;
                                                }
                                            }
                                        }).show();
                            }
                        } else {
                            if (StringUtils.equals("0", payType)) {
                                Set<Long> keySet = discountPriceMoneyMap.keySet();
                                //正常支付
                                if (keySet.size()==1) {
                                    requestPay001(ord009Resp);
                                }
                                //多店铺支付
                                if (keySet.size()>1) {
                                    requestPay003(ord009Resp);
                                }
                            } else {
                                launch(AllOrdersActivity.class);
                                finish();
                            }
                            refreshCartNum();
                        }
                    }
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                DialogUtil.dismissDialog();
                ToastUtil.show(OrderConfirmActivity.this, throwable.getMessage());
            }
        });

    }

    private void refreshCartNum() {
        Ord018Req req = new Ord018Req();
        NetCenter.build(this)
                .requestDefault(req,"ord018")
                .map(new Func1<AppBody, Ord018Resp>() {
                    @Override
                    public Ord018Resp call(AppBody appBody) {
                        return null == appBody ? null : (Ord018Resp) appBody;
                    }
                }).subscribe(new Action1<Ord018Resp>() {
            @Override
            public void call(Ord018Resp ord018Resp) {
                Intent intent = new Intent(MainActivity.CART_GOODS_NUM);
                Bundle bundle = new Bundle();
                bundle.putSerializable("ord018Resp", ord018Resp);
                intent.putExtras(bundle);
                sendBroadcast(intent);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                ToastUtil.show(OrderConfirmActivity.this, throwable.getMessage());
            }
        });
    }

    private void requestPay001(final Ord009Resp ord009Resp) {
        Pay001Req pay001Req = new Pay001Req();
        pay001Req.setRedisKey(ord009Resp.getRedisKey());
        NetCenter.build(this)
                .requestDefault(pay001Req,"pay001")
                .map(new Func1<AppBody, Pay001Resp>() {
                    @Override
                    public Pay001Resp call(AppBody appBody) {
                        return null == appBody ? null : (Pay001Resp) appBody;
                    }
                }).subscribe(new Action1<Pay001Resp>() {
            @Override
            public void call(Pay001Resp pay001Resp) {
                Bundle bundle = new Bundle();
                bundle.putString("redisKey", ord009Resp.getRedisKey());
                bundle.putSerializable("pay001Resp", pay001Resp);
                launch(DealConfirmActivity.class, bundle);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                ToastUtil.show(OrderConfirmActivity.this, throwable.getMessage());
            }
        });
    }

    private void requestPay003(final Ord009Resp ord009Resp) {
        Pay003Req pay003Req = new Pay003Req();
        pay003Req.setRedisKey(ord009Resp.getRedisKey());
        NetCenter.build(this)
                .requestDefault(pay003Req,"pay003")
                .map(new Func1<AppBody, Pay003Resp>() {
                    @Override
                    public Pay003Resp call(AppBody appBody) {
                        return null == appBody ? null : (Pay003Resp) appBody;
                    }
                }).subscribe(new Action1<Pay003Resp>() {
            @Override
            public void call(Pay003Resp pay003Resp) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("pay003Resp", pay003Resp);
                launch(DealConfirmShopActivity.class, bundle);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                ToastUtil.show(OrderConfirmActivity.this, throwable.getMessage());
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
        unregisterReceiver(refreshReceiver);
    }

}
