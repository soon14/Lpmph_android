package com.ailk.pmph.ui.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ai.ecp.app.req.Ord002Req;
import com.ai.ecp.app.req.Ord003Req;
import com.ai.ecp.app.req.Ord006Req;
import com.ai.ecp.app.req.Ord007Req;
import com.ai.ecp.app.req.Ord008Req;
import com.ai.ecp.app.req.Ord013Req;
import com.ai.ecp.app.req.Ord014Req;
import com.ai.ecp.app.req.Ord015Req;
import com.ai.ecp.app.req.Ord016Req;
import com.ai.ecp.app.req.Ord018Req;
import com.ai.ecp.app.req.RCrePreOrdItemReqVO;
import com.ai.ecp.app.req.RCrePreOrdReqVO;
import com.ai.ecp.app.req.ROrdCartChangeRequest;
import com.ai.ecp.app.req.ROrdCartCommRequest;
import com.ai.ecp.app.req.ROrdCartItemCommRequest;
import com.ai.ecp.app.req.ROrdCartItemRequest;
import com.ai.ecp.app.resp.Ord00201Resp;
import com.ai.ecp.app.resp.Ord00202Resp;
import com.ai.ecp.app.resp.Ord00203Resp;
import com.ai.ecp.app.resp.Ord00204Resp;
import com.ai.ecp.app.resp.Ord00205Resp;
import com.ai.ecp.app.resp.Ord002Resp;
import com.ai.ecp.app.resp.Ord003Resp;
import com.ai.ecp.app.resp.Ord00401Resp;
import com.ai.ecp.app.resp.Ord006Resp;
import com.ai.ecp.app.resp.Ord007Resp;
import com.ai.ecp.app.resp.Ord008Resp;
import com.ai.ecp.app.resp.Ord013Resp;
import com.ai.ecp.app.resp.Ord014Resp;
import com.ai.ecp.app.resp.Ord015Resp;
import com.ai.ecp.app.resp.Ord016Resp;
import com.ai.ecp.app.resp.Ord018Resp;
import com.ai.ecp.app.resp.ord.DeliverTypesMap;
import com.ai.ecp.app.resp.ord.DiscountPriceMoneyMap;
import com.ai.ecp.app.resp.ord.OrderMoneyMap;
import com.ai.ecp.app.resp.ord.PayListMap;
import com.ai.ecp.app.resp.ord.RealExpressFeesMap;
import com.ailk.butterfly.app.model.AppHeader;
import com.ailk.butterfly.app.model.JsonService;
import com.ailk.pmph.R;
import com.ailk.pmph.base.BaseFragment;
import com.ailk.pmph.ui.activity.MainActivity;
import com.ailk.pmph.utils.Constant;
import com.ailk.pmph.utils.DialogAnotherUtil;
import com.ailk.pmph.utils.DialogUtil;
import com.ailk.pmph.utils.MoneyUtils;
import com.ailk.pmph.utils.ToastUtil;
import com.ailk.pmph.ui.activity.OrderConfirmActivity;
import com.ailk.pmph.ui.activity.ShopDetailActivity;
import com.ailk.pmph.ui.adapter.ShopCartExpandableListAdapter;
import com.ailk.pmph.ui.view.ProductCouponDialog;
import com.ailk.pmph.ui.view.ShopCouponDialog;

import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 类注释:购物车页面
 * 项目名:PMPH
 * 包名:com.ailk.pmph.ui.fragment
 * 作者: Chrizz
 * 时间: 2016/3/17 14:45
 */
public class ShopCartFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener,
        AbsListView.OnScrollListener,View.OnClickListener,
        ShopCartExpandableListAdapter.CheckInterface, ShopCartExpandableListAdapter.ModifyCountInterface,
        ShopCartExpandableListAdapter.DeleteInterface,ShopCartExpandableListAdapter.ShowShopCouponDialogInterface,
        ShopCartExpandableListAdapter.ShowProductCouponDialogInterface,
        ShopCartExpandableListAdapter.RedirectToShopDetailInterface{

    @BindView(R.id.iv_back)                   ImageView ivBack;
    @BindView(R.id.tv_edit)                   TextView tvEdit;
    @BindView(R.id.sw_container)              SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.lv_shopcart)               ExpandableListView lvShopCartList;
    @BindView(R.id.cb_checkall)               CheckBox cbCheckAll;
    @BindView(R.id.cb_editcheckall)           CheckBox cbEditCheckAll;
    @BindView(R.id.tv_check_all)              TextView tvCheckAll;
    @BindView(R.id.tv_textall)                TextView tvEditCheckAll;
    @BindView(R.id.tv_totalprice)             TextView tvTotalPrice;
    @BindView(R.id.tv_total_default_price)    TextView tvTotalDefaultPrice;
    @BindView(R.id.tv_total_minus_price)      TextView tvTotalMinusPrice;
    @BindView(R.id.tv_account)                Button tvAccount;
    @BindView(R.id.btn_del)                   Button btnDel;
    @BindView(R.id.rl_bottom)                 RelativeLayout rlBottom;
    @BindView(R.id.rl_editbottom)             RelativeLayout rlEditBottom;
    @BindView(R.id.empty_layout)              RelativeLayout emptyLayout;//空购物车显示
    @BindView(R.id.un_empty_layout)           LinearLayout unEmptyLayout;

    public static final int STATE_NONE = 0;
    public static final int STATE_REFRESH = 1;
    public static int mState = STATE_NONE;
    public static final String SHOP_COUPON_ACTION = "shop_coupon_change";
    public static final String PRODUCT_COUPON_ACTION = "product_coupon_change";
    public static final String REFRESH_CART = "refresh_cart";

    private Ord002Resp data;
    private ShopCartExpandableListAdapter adapter;
    private List<Ord00201Resp> groups = new ArrayList<>();//组元素数据列表
    private Map<Long, List<Ord00202Resp>> children = new HashMap<>();//子元素数据列表
    private Ord00204Resp goods = new Ord00204Resp();
    private Long totalNum = 0L;//购买数量

    public static ShopCartFragment newInstance() {
        ShopCartFragment fragment = new ShopCartFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_shopcart;
    }

    @Override
    public void initData() {

    }

    public void initView(View view){
        if (StringUtils.isNotEmpty(getArguments().getString("fromShopDetail"))) {
            setVisible(ivBack);
        } else {
            setGone(ivBack);
        }

        swipeRefreshLayout.setColorSchemeColors(getActivity().getResources().getIntArray(R.array.swipe_colors));
        swipeRefreshLayout.setOnRefreshListener(this);
        lvShopCartList.setOnScrollListener(this);

        IntentFilter shopCoupon = new IntentFilter();
        shopCoupon.addAction(SHOP_COUPON_ACTION);
        getActivity().registerReceiver(shopCouponChangeReceiver, shopCoupon);

        IntentFilter productCoupon = new IntentFilter();
        productCoupon.addAction(PRODUCT_COUPON_ACTION);
        getActivity().registerReceiver(productCouponChangeReceiver,productCoupon);

        IntentFilter refreshCart = new IntentFilter();
        refreshCart.addAction(REFRESH_CART);
        getActivity().registerReceiver(refreshCartReceiver,refreshCart);

    }

    @Override
    public void onResume() {
        super.onResume();
        requestList(true, null);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getActivity().unregisterReceiver(shopCouponChangeReceiver);
        getActivity().unregisterReceiver(productCouponChangeReceiver);
        getActivity().unregisterReceiver(refreshCartReceiver);
    }

    @Override
    public void onRefresh() {
        if (mState==STATE_REFRESH) {
            return;
        }
        requestList(false, null);//下拉刷新时不显示进度条
        getCartGoodsNum();
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (adapter==null || adapter.getGroupCount()==0) {
            return;
        }
        if (mState == STATE_REFRESH) {
            return;
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    @Override
    public void checkGroup(int groupPosition, boolean isChecked) {
        Ord00201Resp group = groups.get(groupPosition);
        List<Ord00202Resp> childs = children.get(group.getShopId());
        for (int i = 0; i < childs.size(); i++) {
            Ord00202Resp child = childs.get(i);
            child.setIsChoosed(isChecked);
        }
        goods = group.getGroupLists();
        goods.setIsChoosed(isChecked);

        if (isAllCheck()) {
            cbCheckAll.setChecked(true);
            cbEditCheckAll.setChecked(true);
        }
        else {
            cbCheckAll.setChecked(false);
            cbEditCheckAll.setChecked(false);
        }
        adapter.notifyDataSetChanged();
        calculate();
    }

    @Override
    public void checkGroups(int groupPosition, int childPosition, boolean isChecked) {
        Ord00201Resp group = groups.get(groupPosition);
        List<Ord00202Resp> childs = group.getOrdCartItemList();
        goods = group.getGroupLists();
//        goods.setIsChoosed(isChecked);
        List<Ord00205Resp> groupLists = goods.getGroupLists();
        boolean allSameState = true;
        for (int i = 0; i < groupLists.size(); i++) {
            Ord00205Resp product = groupLists.get(i);
            product.setIsChoosed(isChecked);
            if (product.isChoosed() == isChecked) {
                goods.setIsChoosed(isChecked);
                cbCheckAll.setChecked(false);
            }
            if (product.isChoosed() != isChecked) {
                allSameState = false;
                break;
            }
            if (goods.isChoosed() == isChecked) {
                group.setIsChoosed(isChecked);
                cbCheckAll.setChecked(false);
            }
            if (goods.isChoosed() != isChecked) {
                goods.setIsChoosed(false);
                allSameState = false;
            }
        }
        if (allSameState && childs.size()==0) {
            group.setIsChoosed(isChecked);
        }
        else {
            group.setIsChoosed(false);
        }
        if (isAllCheck()) {
            cbCheckAll.setChecked(true);
            cbEditCheckAll.setChecked(true);
        }
        else {
            cbCheckAll.setChecked(false);
            cbEditCheckAll.setChecked(false);
        }
        calculate();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void checkChild(Ord00202Resp checkProduct, int groupPosition, int childPosition, boolean isChecked) {
        requestCheck(checkProduct, groupPosition, true);
        boolean allChildSameState = true;
        Ord00201Resp group = groups.get(groupPosition);
        Ord00204Resp good = group.getGroupLists();
        List<Ord00202Resp> childs = children.get(group.getShopId());
        for (int i = 0; i < childs.size(); i++) {
            Ord00202Resp child = childs.get(i);
            if (child.isChoosed() == isChecked) {
                group.setIsChoosed(isChecked);
                cbCheckAll.setChecked(false);
            }
            if (child.isChoosed() != isChecked) {
                allChildSameState = false;
                break;
            }
        }
        if (allChildSameState && good.getGroupLists().size() == 0) {
            group.setIsChoosed(isChecked);
        } else {
            group.setIsChoosed(false);
        }
        if (isAllCheck()) {
            cbCheckAll.setChecked(true);
            cbEditCheckAll.setChecked(true);
        }
        else {
            cbCheckAll.setChecked(false);
            cbEditCheckAll.setChecked(false);
        }
        adapter.notifyDataSetChanged();
        calculate();
    }

    @Override
    public void doAddGroups(int groupPosition, int childPosition, View showCountView) {
        Ord00201Resp group = groups.get(groupPosition);
        List<Ord00205Resp> groupLists = group.getGroupLists().getGroupLists();
        requestAddGroups(false, groupLists, showCountView);
    }

    @Override
    public void doReduceGrouops(int groupPosition, int childPosition, View showCountView) {
        Ord00201Resp group = groups.get(groupPosition);
        List<Ord00205Resp> groupLists = group.getGroupLists().getGroupLists();
        requestReduceGroups(false, groupLists, showCountView);
    }

    @Override
    public void doIncrease(int groupPosition, int childPosition, View showCountView, boolean isChecked) {
        Ord00201Resp group = groups.get(groupPosition);
        Ord00202Resp product = (Ord00202Resp) adapter.getChild(groupPosition, childPosition);
        if (!isChecked) {
            ((TextView) showCountView).setText(String.valueOf(product.getOrderAmount()));
            requestList(true, null);
        } else {
            requestIncrease(true, group, product, showCountView);
        }
    }

    @Override
    public void doDecrease(int groupPosition, int childPosition, View showCountView, boolean isChecked) {
        Ord00201Resp group = groups.get(groupPosition);
        Ord00202Resp product = (Ord00202Resp) adapter.getChild(groupPosition, childPosition);
        if (!isChecked) {
            ((TextView) showCountView).setText(String.valueOf(product.getOrderAmount()));
            requestList(true, null);
        } else {
            requestDecrease(true, group, product, showCountView);
        }
    }

    @Override
    public void doDeleteGroups(int groupPosition, int childPosition, boolean isChecked) {
        final Ord00201Resp group = groups.get(groupPosition);
        final List<Ord00205Resp> groupLists = group.getGroupLists().getGroupLists();
        final Ord00205Resp groupSku = groupLists.get(childPosition);
        DialogAnotherUtil.showCustomAlertDialogWithMessage(getActivity(), null,
                "确认要删除这套组合商品吗？", "确定", "取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Ord016Req request = new Ord016Req();
                        List<ROrdCartItemRequest> ordCartItems = new ArrayList<>();
                        for (int i = 0; i < groupSku.getGroupSkus().size(); i++) {
                            Ord00202Resp bean = groupSku.getGroupSkus().get(i);
                            ROrdCartItemRequest rOrdCartItemRequest = new ROrdCartItemRequest();
                            rOrdCartItemRequest.setId(bean.getId());
                            rOrdCartItemRequest.setCartId(bean.getCartId());
                            ordCartItems.add(rOrdCartItemRequest);
                        }
                        request.setOrdCartItems(ordCartItems);
                        getJsonService().requestOrd016(getActivity(), request, false, new JsonService.CallBack<Ord016Resp>() {
                            @Override
                            public void oncallback(Ord016Resp ord016Resp) {
                                requestList(true, null);
                            }

                            @Override
                            public void onErro(AppHeader header) {

                            }
                        });
                        DialogUtil.dismissDialog();
                    }
                }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DialogUtil.dismissDialog();
                    }
                });
    }

    @Override
    public void doDeleteItem(final Ord00202Resp product, final int groupPosition, final int childPosition, boolean isChecked) {
        final Ord00201Resp group = groups.get(groupPosition);
        final List<Ord00202Resp> childs = children.get(group.getShopId());
        if (!isChecked) {
            DialogAnotherUtil.showCustomAlertDialogWithMessage(getActivity(), null,
                    "确认要删除这1种商品吗？", "确定", "取消",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Ord007Req request = new Ord007Req();
                            ROrdCartItemRequest rOrdCartItemRequest = new ROrdCartItemRequest();
                            rOrdCartItemRequest.setId(product.getId());
                            rOrdCartItemRequest.setPromId(product.getPromId());
                            rOrdCartItemRequest.setStaffId(data.getStaffId());
                            rOrdCartItemRequest.setCartId(product.getCartId());
                            List<ROrdCartItemRequest> ordCartItems = new ArrayList<>();
                            ordCartItems.add(rOrdCartItemRequest);
                            request.setOrdCartItems(ordCartItems);
                            getJsonService().requestOrd007(getActivity(), request, false, new JsonService.CallBack<Ord007Resp>() {
                                @Override
                                public void oncallback(Ord007Resp ord007Resp) {
                                    requestList(true, null);
                                }

                                @Override
                                public void onErro(AppHeader header) {
                                }
                            });
                            DialogUtil.dismissDialog();
                        }
                    }, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            DialogUtil.dismissDialog();
                        }
                    });
        } else {
            DialogAnotherUtil.showCustomAlertDialogWithMessage(getActivity(), null,
                    "确认要删除这1种商品吗？", "确定", "取消",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            List<Ord00202Resp> chooseList = new ArrayList<>();
                            for (int i = 0; i < childs.size(); i++) {
                                if (childs.get(i).isChoosed()) {
                                    chooseList.add(childs.get(i));
                                }
                            }
                            if (chooseList.size() == 1) {
                                requestDelteteNoChoosed(chooseList.get(0), false);
                            } else {
                                requestDeleteChoosed(chooseList, groupPosition, childPosition, false);
                            }
                            DialogUtil.dismissDialog();
                        }
                    }, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            DialogUtil.dismissDialog();
                        }
                    });
        }
    }

    @Override
    public void showShopCouponDialog(int groupPosition) {
        ShopCouponDialog dialog = new ShopCouponDialog(getActivity(), R.style.coupon_dialog);
        dialog.setGroup(groups.get(groupPosition));
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    @Override
    public void showProductCouponDialog(int groupPosition, int childPosition) {
        Ord00201Resp group = groups.get(groupPosition);
        Ord00204Resp good = group.getGroupLists();
        List<Ord00202Resp> childs = children.get(group.getShopId());
        Ord00202Resp product;
        if (good != null) {
            product = childs.get(childPosition-good.getGroupLists().size());
        } else {
            product = childs.get(childPosition);
        }
        ProductCouponDialog dialog = new ProductCouponDialog(getActivity(), R.style.coupon_dialog);
        dialog.setGroup(group);
        dialog.setProduct(product);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    @Override
    public void redirectToShopDetail(Ord00202Resp product) {
        Bundle bundle = new Bundle();
        bundle.putString(Constant.SHOP_GDS_ID, String.valueOf(product.getGdsId()));
        bundle.putString(Constant.SHOP_SKU_ID, String.valueOf(product.getSkuId()));
        launch(ShopDetailActivity.class, bundle);
    }

    @Override
    @OnClick({R.id.iv_back,R.id.tv_edit, R.id.cb_checkall, R.id.tv_check_all, R.id.tv_account,
            R.id.cb_editcheckall, R.id.tv_textall, R.id.btn_del})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                getActivity().onBackPressed();
                break;
            case R.id.tv_edit:
                if (StringUtils.equals(tvEdit.getText().toString(), "编辑"))
                {
                    if (adapter!=null)
                    {
                        tvEdit.setText("完成");
                        adapter.setTextEdit("完成");
                        setGone(rlBottom);
                        setVisible(rlEditBottom);
                    }
                    if (cbCheckAll.isChecked())
                    {
                        cbCheckAll.setChecked(true);
                        cbEditCheckAll.setChecked(true);
                    }
                    else
                    {
                        cbCheckAll.setChecked(false);
                        cbEditCheckAll.setChecked(false);
                    }
                }
                else if (StringUtils.equals(tvEdit.getText().toString(), "完成"))
                {
                    if (adapter!=null)
                    {
                        tvEdit.setText("编辑");
                        adapter.setTextEdit("编辑");
                        setGone(rlEditBottom);
                        setVisible(rlBottom);
                    }
                    if (cbEditCheckAll.isChecked())
                    {
                        cbCheckAll.setChecked(true);
                        cbEditCheckAll.setChecked(true);
                    }
                    else
                    {
                        cbCheckAll.setChecked(false);
                        cbEditCheckAll.setChecked(false);
                    }
                }
                break;
            case R.id.cb_checkall:
                doCheckAll(cbCheckAll);
                break;
            case R.id.tv_check_all:
                if (cbCheckAll.isChecked()) {
                    cbCheckAll.setChecked(false);
                } else {
                    cbCheckAll.setChecked(true);
                }
                doCheckAll(cbCheckAll);
                break;
            case R.id.tv_account:
                if (totalNum == 0) {
                    ToastUtil.showCenter(getActivity(),"您还没有选择商品哦");
                    return;
                }
                if (isAllCheck())
                {
                    for (int i = 0; i < groups.size(); i++) {
                        Ord00201Resp shop = groups.get(i);
                        if (shop.getGroupLists() != null) {
                            for (int j = 0; j < shop.getGroupLists().getGroupLists().size(); j++) {
                                Ord00205Resp good = shop.getGroupLists().getGroupLists().get(j);
                                for (int k = 0; k < good.getGroupSkus().size(); k++) {
                                    Ord00202Resp sku = good.getGroupSkus().get(k);
                                    if (!sku.isGdsStatus()) {
                                        ToastUtil.showCenter(getActivity(), "请先删除已失效的商品");
                                        return;
                                    }
                                }
                            }
                        }
                        for (int a = 0; a < shop.getOrdCartItemList().size(); a++) {
                            Ord00202Resp product = shop.getOrdCartItemList().get(a);
                            if (!product.isGdsStatus()) {
                                ToastUtil.showCenter(getActivity(), "请先删除已失效的商品");
                                return;
                            }
                        }
                    }
                    requestAccount(groups, true);
                }
                else
                {
                    List<Ord00201Resp> accountGroups = new ArrayList<>();
                    for (int i = 0; i < groups.size(); i++) {
                        Ord00201Resp group = groups.get(i);
                        //待结算的组合商品
                        Ord00204Resp accountGoods = new Ord00204Resp();
                        List<Ord00205Resp> accountGroupLists = new ArrayList<>();
                        List<Ord00202Resp> accountProducts = new ArrayList<>();

                        goods = group.getGroupLists();
                        if (goods != null) {
                            List<Ord00205Resp> groupLists = goods.getGroupLists();

                            for (int j = 0; j < groupLists.size(); j++) {
                                Ord00205Resp groupList = groupLists.get(j);
                                if (groupList.isChoosed()) {
                                    accountGroupLists.add(groupList);
                                }
                            }

                        }

                        if (accountGroupLists.size() > 0) {
                            accountGoods.setGroupLists(accountGroupLists);
                            group.setGroupLists(accountGoods);
                            group.setOrdCartItemList(null);//设置单品的信息为空，方便读取组合套餐的信息
                            accountGroups.add(group);

                            for (int k = 0; k < accountGroupLists.size(); k++) {
                                Ord00205Resp entity = accountGroupLists.get(k);
                                for (int a = 0; a < entity.getGroupSkus().size();a++) {
                                    Ord00202Resp sku = entity.getGroupSkus().get(a);
                                    if (!sku.isGdsStatus()) {
                                        ToastUtil.showCenter(getActivity(), "请先删除已失效的商品");
                                        return;
                                    }
                                }
                            }
                        } else {
                            accountGoods.setGroupLists(accountGroupLists);
                            group.setGroupLists(accountGoods);
                        }

                        //待结算的单品
                        List<Ord00202Resp> products = children.get(group.getShopId());
                        if (products!=null && products.size()>0) {
                            for (int b = 0; b < products.size(); b++) {
                                Ord00202Resp product = products.get(b);
                                if (product.isChoosed()) {
                                    accountProducts.add(product);
                                }
                                if (!product.isGdsStatus()) {
                                    ToastUtil.showCenter(getActivity(), "请先删除已失效的商品");
                                    return;
                                }
                            }
                            group.setOrdCartItemList(accountProducts);//重新加入单品，在结算时accountGroups.add(group)保证group有单品的信息
                        }

                        if (accountProducts.size() > 0) {
                            if (accountGroupLists.size() > 0) {
                                //当选有单品和组合套餐时不用执行accountGroups.add(group);（）

                            } else {
                                group.setOrdCartItemList(accountProducts);
                                accountGroups.add(group);
                            }

                        }
//                        else {
//                            g01.setOrdCartItemList(accountProducts);
//                            accountGroups.add(g01);
//                        }
                    }
                    requestAccount(accountGroups,true);
                }
                break;
            case R.id.cb_editcheckall:
                doCheckAll(cbEditCheckAll);
                break;
            case R.id.tv_textall:
                if (cbEditCheckAll.isChecked()) {
                    cbEditCheckAll.setChecked(false);
                } else {
                    cbEditCheckAll.setChecked(true);
                }
                doCheckAll(cbEditCheckAll);
                break;
            case R.id.btn_del:
                if (totalNum == 0) {
                    ToastUtil.showCenter(getActivity(),"您还没有选择商品哦");
                    return;
                }
                if (isAllCheck())//全部删除
                {
                    DialogAnotherUtil.showCustomAlertDialogWithMessage(getActivity(), null,
                            "确认要删除这" + totalNum + "种商品吗？", "确定", "取消",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    requestBatchDelete(groups, true);
                                    DialogUtil.dismissDialog();
                                }
                            },
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    DialogUtil.dismissDialog();
                                }
                            });
                }
                else
                {
                    final List<Ord00201Resp> deleteGroups = new ArrayList<>();// 待删除的店铺列表
                    for (int i = 0; i < groups.size(); i++) {
                        Ord00201Resp group = groups.get(i);
                        List<Ord00202Resp> deleteProducts = new ArrayList<>();// 待删除的单品列表
                        List<Ord00202Resp> products = children.get(group.getShopId());
                        for (int j = 0; j < products.size(); j++) {
                            Ord00202Resp product = products.get(j);
                            if (product.isChoosed()) {
                                deleteProducts.add(product);
                            }
                        }
                        group.setOrdCartItemList(deleteProducts);
                        deleteGroups.add(group);

                        List<Ord00205Resp> deleteGroupLists = new ArrayList<>();// 待删除的组合商品列表
                        goods = group.getGroupLists();
                        List<Ord00205Resp> groupLists = goods.getGroupLists();
                        if (groupLists.size() > 0) {
                            for (int k = 0; k < groupLists.size(); k++) {
                                Ord00205Resp entity = groupLists.get(k);
                                if (entity.isChoosed()) {
                                    deleteGroupLists.add(entity);
                                }
                            }
                            goods.setGroupLists(deleteGroupLists);
                            group.setGroupLists(goods);
                            deleteGroups.add(group);
                        }
                    }
                    DialogAnotherUtil.showCustomAlertDialogWithMessage(getActivity(), null,
                            "确认要删除这" + totalNum + "种商品吗？", "确定", "取消",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    requestBatchDelete(deleteGroups, true);
                                    DialogUtil.dismissDialog();
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
//
    /**
     * 全选操作
     */
    private void doCheckAll(CheckBox checkAll){
        for (int i = 0; i < groups.size(); i++) {
            Ord00201Resp group = groups.get(i);
            group.setIsChoosed(checkAll.isChecked());

            List<Ord00202Resp> childs = children.get(group.getShopId());
            for (int j = 0; j < childs.size(); j++) {
                Ord00202Resp child = childs.get(j);
                child.setIsChoosed(checkAll.isChecked());
            }

            goods = group.getGroupLists();
            goods.setIsChoosed(checkAll.isChecked());
            List<Ord00205Resp> groupLists = goods.getGroupLists();
            for (int k = 0; k < groupLists.size(); k++) {
                Ord00205Resp entity = groupLists.get(k);
                entity.setIsChoosed(checkAll.isChecked());
            }
        }
        adapter.notifyDataSetChanged();
        calculate();
    }

    /**
     * 判断是否全选
     * @return
     */
    private boolean isAllCheck() {
        for (int i = 0; i < groups.size(); i++) {
            Ord00201Resp group = groups.get(i);
            if (!group.isChoosed())
                return false;
        }
        return true;
    }

    /**
     * 删除操作
     */
    private void doDelete() {
        List<Ord00201Resp> deleteGroups = new ArrayList<>();// 待删除的店铺列表
        for (int i = 0; i < groups.size(); i++) {
            Ord00201Resp group = groups.get(i);
            if (group.isChoosed()) {
                deleteGroups.add(group);
            }

            List<Ord00202Resp> deleteProducts = new ArrayList<>();// 待删除的单品列表
            List<Ord00202Resp> products = children.get(group.getShopId());
            for (int j = 0; j < products.size(); j++) {
                Ord00202Resp product = products.get(j);
                if (product.isChoosed()) {
                    deleteProducts.add(product);
                }
            }
            products.removeAll(deleteProducts);

            List<Ord00205Resp> deleteGroupLists = new ArrayList<>();// 待删除的组合商品列表
            goods = group.getGroupLists();
            List<Ord00205Resp> groupLists = goods.getGroupLists();
            for (int k = 0; k < groupLists.size(); k++) {
                Ord00205Resp entity = groupLists.get(k);
                if (entity.isChoosed()) {
                    deleteGroupLists.add(entity);
                }
            }
            groupLists.removeAll(deleteGroupLists);
        }
        groups.removeAll(deleteGroups);
        if (deleteGroups.size()==groups.size()) {
            setGone(tvEdit);
        } else {
            setVisible(tvEdit);
        }
        adapter.notifyDataSetChanged();
        calculate();
    }

    /**
     * 统计操作
     */
    private void calculate() {
        totalNum = 0L;
        Long totalPrice = 0L;
        Long _totalPrice = 0L;
        Long totalFalsePrice = 0L;
        Long totalProductDiscount = 0L;
        Long totalGroupDiscount1 = 0L;
        Long totalGroupDiscount2 = 0L;
        for (int i = 0; i < groups.size(); i++) {
            Ord00201Resp group = groups.get(i);
            if (group.isIfFulfillProm())
            {
                totalGroupDiscount1 += group.getDiscountMoney();
            }
            else
            {
                totalGroupDiscount2 = 0L;
            }

            List<Ord00202Resp> childs = children.get(group.getShopId());
            for (int j = 0; j < childs.size(); j++) {
                Ord00202Resp product = childs.get(j);
                if (product.isGdsStatus())
                {
                    if (product.isChoosed())
                    {
                        totalNum++;
                        if (!product.isIfFulfillProm())
                        {
                            totalPrice += product.getBuyPrice()*product.getOrderAmount();
                        }
                        else
                        {
                            totalProductDiscount += product.getDiscountMoney();
                            totalPrice += (product.getDiscountCaclPrice()*product.getOrderAmount());
                        }
                    }
                }
//                else
//                {
//                    totalPrice = 0L;
//                }
            }

            goods = group.getGroupLists();
            if (goods != null) {
                List<Ord00205Resp> groupLists = goods.getGroupLists();
                for (int k = 0; k < groupLists.size(); k++) {
                    Ord00205Resp bean = groupLists.get(k);
                    if (goods.isChoosed())
                    {
                        totalNum++;
                        boolean isFlag = false;
                        for (int m = 0; m < bean.getGroupSkus().size(); m++) {
                            Ord00202Resp sku = bean.getGroupSkus().get(m);
                            if (!sku.isGdsStatus()) {
                                isFlag =true;
                                break;
                            }
                        }
                        if (isFlag) {
                            for (int n = 0; n < bean.getGroupSkus().size(); n++) {
                                Ord00202Resp sku = bean.getGroupSkus().get(n);
                                sku.setGdsStatus(false);
                            }
                            totalFalsePrice = 0L;
                        } else {
                            for (int x = 0; x < bean.getGroupSkus().size(); x++) {
                                Ord00202Resp entity = bean.getGroupSkus().get(x);
                                if (entity.isGdsStatus())
                                {
                                    if (entity.isIfFulfillProm())
                                    {
                                        _totalPrice += (entity.getDiscountCaclPrice()*entity.getOrderAmount()-entity.getDiscountMoney());
                                    }
                                    else
                                    {
                                        _totalPrice += entity.getBuyPrice()*entity.getOrderAmount();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        if (totalPrice!=0 || _totalPrice!=0)
        {
            String price = MoneyUtils.GoodListPrice(totalPrice+totalFalsePrice+_totalPrice-(totalGroupDiscount1 + totalGroupDiscount2)-totalProductDiscount);
            tvTotalPrice.setText(price);
            tvTotalDefaultPrice.setText(MoneyUtils.GoodListPrice(totalPrice+totalFalsePrice+_totalPrice));
            tvTotalMinusPrice.setText(MoneyUtils.GoodListPrice(totalProductDiscount+(totalGroupDiscount1 + totalGroupDiscount2)));
        }
//        else
//        {
//            String price = MoneyUtils.GoodListPrice(totalPrice+totalFalsePrice+_totalPrice-totalGroupDiscount);
//            tvTotalPrice.setText(price);
//            tvTotalDefaultPrice.setText(MoneyUtils.GoodListPrice(totalPrice+totalFalsePrice+_totalPrice+totalProductDiscount-totalGroupDiscount));
//            tvTotalMinusPrice.setText(MoneyUtils.GoodListPrice(totalProductDiscount+totalGroupDiscount));
//        }

        if (totalPrice==0 && _totalPrice==0)
        {
            tvTotalPrice.setText("￥0.00");
            tvTotalDefaultPrice.setText("￥0.00");
            tvTotalMinusPrice.setText("￥0.00");
        }
    }

    public BroadcastReceiver shopCouponChangeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (StringUtils.equals(intent.getAction(), SHOP_COUPON_ACTION)) {
                requestList(true, "ORDER-SUBMIT");
            }
        }
    };

    public BroadcastReceiver productCouponChangeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (StringUtils.equals(intent.getAction(), PRODUCT_COUPON_ACTION)) {
                requestList(true, "ORDER-SUBMIT");
            }
        }
    };

    public BroadcastReceiver refreshCartReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (StringUtils.equals(intent.getAction(), REFRESH_CART)) {
                setVisible(emptyLayout);
                setGone(unEmptyLayout,tvEdit);
            }
        }
    };

    //=====================================数据请求==========================================//

    /**
     * 请求购物车列表
     */
    private void requestList(boolean isShowProgress, String promSubmitType){
        Ord002Req req = new Ord002Req();
        req.setPromSubmitType(promSubmitType);
        getJsonService().requestOrd002(getActivity(), req, isShowProgress, new JsonService.CallBack<Ord002Resp>() {
            @Override
            public void oncallback(Ord002Resp ord002Resp) {
                swipeRefreshLayout.setRefreshing(false);
                data = ord002Resp;
                groups = data.getOrdCartList();
                if (groups.size()==0)
                {
                    setGone(unEmptyLayout, rlEditBottom, tvEdit);
                    setVisible(emptyLayout);
                }
                else
                {
                    setVisible(tvEdit, unEmptyLayout);
                    setGone(emptyLayout);
                    if (StringUtils.equals(tvEdit.getText().toString(),"编辑"))
                    {
                        setGone(rlEditBottom);
                        setVisible(rlBottom);
                    }
                    else
                    {
                        setGone(rlBottom);
                        setVisible(rlEditBottom);
                    }
                    for (int i = 0; i < groups.size(); i++) {
                        final Ord00201Resp bean = groups.get(i);
                        children.put(bean.getShopId(), bean.getOrdCartItemList());
                        if (bean.getPromInfoDTOList() !=null && bean.getPromInfoDTOList().size()>0) {
                            Ord00203Resp prom = new Ord00203Resp();
                            prom.setPromTheme("不使用活动优惠");
                            prom.setId(-1L);
                            prom.setNameShort("");
                            bean.getPromInfoDTOList().add(prom);
                        }
                        for (int j = 0; j < bean.getOrdCartItemList().size(); j++) {
                            Ord00202Resp product = bean.getOrdCartItemList().get(j);
                            if (product.isGdsStatus()) {
                                List<Ord00203Resp> proms = product.getPromInfoDTOList();
                                if (proms!=null && proms.size() > 0) {
                                    Ord00203Resp prom = new Ord00203Resp();
                                    prom.setPromTheme("不使用活动优惠");
                                    prom.setId(-1L);
                                    prom.setNameShort("");
                                    proms.add(prom);
                                }
                            }
                        }
                        adapter = new ShopCartExpandableListAdapter(groups, children, getActivity(), tvEdit.getText().toString());
                        if (bean.getGroupLists() != null) //有组合商品
                        {
                            goods = bean.getGroupLists();
                            goods.setIsChoosed(true);
                            List<Ord00205Resp> groupLists = goods.getGroupLists();
                            for (int k = 0; k < groupLists.size(); k++) {
                                Ord00205Resp ord00205Resp = groupLists.get(k);
                                ord00205Resp.setIsChoosed(true);
//                                for (Ord00202Resp ord00202Resp : ord00205Resp.getGroupSkus()) {
//                                    ord00202Resp.setIsChoosed(true);
//                                }
                            }
                            adapter.setGoods(goods);
                        }
                    }
                    for (int i = 0; i < groups.size(); i++) {
                        groups.get(i).setIsChoosed(true);
                        Ord00201Resp group = groups.get(i);
                        List<Ord00202Resp> childs = children.get(group.getShopId());
                        for (int j = 0; j < childs.size(); j++) {
                            childs.get(j).setIsChoosed(true);
                        }
                    }
                    if (isAllCheck()) {
                        cbCheckAll.setChecked(true);
                        cbEditCheckAll.setChecked(true);
                    }
                    calculate();
                    adapter.setCheckInterface(ShopCartFragment.this);
                    adapter.setModifyCountInterface(ShopCartFragment.this);
                    adapter.setDeleteInterface(ShopCartFragment.this);
                    adapter.setShowShopCouponDialogInterface(ShopCartFragment.this);
                    adapter.setShowProductCouponDialogInterface(ShopCartFragment.this);
                    adapter.setRedirectToShopDetailInterface(ShopCartFragment.this);
                    lvShopCartList.setAdapter(adapter);
                    for (int i = 0; i < adapter.getGroupCount(); i++) {
                        lvShopCartList.expandGroup(i);
                    }
                }
            }

            @Override
            public void onErro(AppHeader header) {
            }
        });
    }

    /**
     * 获取购物车商品总数
     */
    private void getCartGoodsNum() {
        Ord018Req req = new Ord018Req();
        getJsonService().requestOrd018(getActivity(), req, false, new JsonService.CallBack<Ord018Resp>() {
            @Override
            public void oncallback(Ord018Resp ord018Resp) {
                if (ord018Resp != null) {
                    Intent intent = new Intent(MainActivity.CART_GOODS_NUM);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("ord018Resp", ord018Resp);
                    intent.putExtras(bundle);
                    getActivity().sendBroadcast(intent);
                }
            }

            @Override
            public void onErro(AppHeader header) {

            }
        });
    }

    /**
     * 增加商品数量
     * @param isShowProgress
     * @param product
     * @param showCountView
     */
    private void requestIncrease(boolean isShowProgress, final Ord00201Resp group, final Ord00202Resp product, View showCountView){
        long currentCount = product.getOrderAmount();
        currentCount++;
        product.setOrderAmount(currentCount);
        ((TextView) showCountView).setText(String.valueOf(currentCount));
        adapter.notifyDataSetChanged();

        Ord013Req request = new Ord013Req();
        List<Ord00202Resp> products = group.getOrdCartItemList();

        ROrdCartItemRequest ordCartItem = new ROrdCartItemRequest();
        ROrdCartChangeRequest ordCartChg = new ROrdCartChangeRequest();
        List<ROrdCartItemCommRequest> ordCartItemCommList = new ArrayList<>();

        ordCartItem.setId(product.getId());
        ordCartItem.setOrderAmount(currentCount);
        ordCartItem.setStaffId(group.getStaffId());
        ordCartItem.setPromId(product.getPromId());

        if (products.size() > 1)
        {
            for (int i = 0; i < products.size(); i++) {
                Ord00202Resp bean = products.get(i);
                ROrdCartCommRequest rOrdCartCommRequest = new ROrdCartCommRequest();
                rOrdCartCommRequest.setId(group.getCartId());
                rOrdCartCommRequest.setPromId(group.getPromId());

                ROrdCartItemCommRequest rOrdCartItemCommRequest = new ROrdCartItemCommRequest();
                rOrdCartItemCommRequest.setId(bean.getId());
                rOrdCartItemCommRequest.setPromId(bean.getPromId());
                ordCartItemCommList.add(rOrdCartItemCommRequest);
                rOrdCartCommRequest.setOrdCartItemCommList(ordCartItemCommList);
                ordCartChg.setrOrdCartCommRequest(rOrdCartCommRequest);
            }
        }
        else
        {
            ROrdCartCommRequest rOrdCartCommRequest = new ROrdCartCommRequest();
            rOrdCartCommRequest.setId(group.getCartId());
            rOrdCartCommRequest.setPromId(group.getPromId());
            ROrdCartItemCommRequest rOrdCartItemCommRequest = new ROrdCartItemCommRequest();
            rOrdCartItemCommRequest.setId(product.getId());
            rOrdCartItemCommRequest.setPromId(product.getPromId());
            ordCartItemCommList.add(rOrdCartItemCommRequest);
            rOrdCartCommRequest.setOrdCartItemCommList(ordCartItemCommList);
            ordCartChg.setrOrdCartCommRequest(rOrdCartCommRequest);
        }

        request.setOrdCartItem(ordCartItem);
        request.setOrdCartChg(ordCartChg);

        getJsonService().requestOrd013(getActivity(), request, isShowProgress, new JsonService.CallBack<Ord013Resp>() {
            @Override
            public void oncallback(Ord013Resp ord013Resp) {
                resetProm(group, product, ord013Resp);
//                requestList(false, null);
                getCartGoodsNum();
            }

            @Override
            public void onErro(AppHeader header) {
            }
        });
    }

    /**
     * 设置优惠相关
     * @param group
     * @param product
     * @param response
     */
    private void resetProm(Ord00201Resp group, Ord00202Resp product, Ord013Resp response) {
        Ord00401Resp cartProm = response.getCartPromDTO();
        Ord00401Resp cartItemProm = response.getCartPromItemDTO();
        for (int i = 0; i < groups.size(); i++) {
            Ord00201Resp shop = groups.get(i);
            if (shop.getShopId().equals(group.getShopId())) {
                if (cartProm.getOrdPromId()!=null) {
                    shop.setPromId(cartProm.getOrdPromId());
                }
                shop.setIfFulfillProm(cartProm.isIfFulfillProm());
                if (cartProm.getFulfilMsg()!=null) {
                    shop.setFulfilMsg(cartProm.getFulfilMsg());
                }
                if (cartProm.getNoFulfilMsg()!=null) {
                    shop.setNoFulfilMsg(cartProm.getNoFulfilMsg());
                }
                if (cartProm.getDiscountPrice()!=null) {
                    shop.setDiscountPrice(cartProm.getDiscountPrice().longValue());
                }
                if (cartProm.getDiscountMoney()!=null) {
                    shop.setDiscountMoney(cartProm.getDiscountMoney().longValue());
                }
                if (cartProm.getDiscountAmount()!=null) {
                    shop.setDiscountAmount(cartProm.getDiscountAmount().longValue());
                }

                List<Ord00202Resp> products = children.get(shop.getShopId());
                for (int j = 0; j < products.size(); j++) {
                    Ord00202Resp bean = products.get(j);
                    if (bean.getId().equals(product.getId())) {
                        if (cartItemProm.getPromId()!=null) {
                            bean.setPromId(cartItemProm.getPromId());
                        }
                        bean.setIfFulfillProm(cartItemProm.isIfFulfillProm());
                        if (cartItemProm.getFulfilMsg()!=null) {
                            bean.setFulfilMsg(cartItemProm.getFulfilMsg());
                        }
                        if (cartItemProm.getNoFulfilMsg()!=null) {
                            bean.setNoFulfilMsg(cartItemProm.getNoFulfilMsg());
                        }
                        if (cartItemProm.getDiscountPrice()!=null) {
                            bean.setDiscountPrice(cartItemProm.getDiscountPrice().longValue());
                        }
                        if (cartItemProm.getDiscountMoney()!=null) {
                            bean.setDiscountMoney(cartItemProm.getDiscountMoney().longValue());
                        }
                        if (cartItemProm.getDiscountAmount()!=null) {
                            bean.setDiscountAmount(cartItemProm.getDiscountAmount().longValue());
                        }
                    }
                }
                shop.setOrdCartItemList(products);
            }
        }
        calculate();
        adapter.notifyDataSetChanged();
    }

    /**
     * 减少商品数量
     * @param isShowProgress
     * @param product
     * @param showCountView
     */
    private void requestDecrease(boolean isShowProgress, final Ord00201Resp group, final Ord00202Resp product, View showCountView){
        long currentCount = product.getOrderAmount();
        if (currentCount==1)
            return;
        currentCount--;
        product.setOrderAmount(currentCount);
        ((TextView) showCountView).setText(String.valueOf(currentCount));
        adapter.notifyDataSetChanged();

        Ord013Req request = new Ord013Req();
        List<Ord00202Resp> products = group.getOrdCartItemList();

        ROrdCartItemRequest ordCartItem = new ROrdCartItemRequest();
        ROrdCartChangeRequest ordCartChg = new ROrdCartChangeRequest();
        List<ROrdCartItemCommRequest> ordCartItemCommList = new ArrayList<>();

        ordCartItem.setId(product.getId());
        ordCartItem.setOrderAmount(currentCount);
        ordCartItem.setStaffId(group.getStaffId());
        ordCartItem.setPromId(product.getPromId());

        if (products.size() > 1)
        {
            for (int i = 0; i < products.size(); i++) {
                Ord00202Resp bean = products.get(i);
                ROrdCartCommRequest rOrdCartCommRequest = new ROrdCartCommRequest();
                rOrdCartCommRequest.setId(group.getCartId());
                rOrdCartCommRequest.setPromId(group.getPromId());

                ROrdCartItemCommRequest rOrdCartItemCommRequest = new ROrdCartItemCommRequest();
                rOrdCartItemCommRequest.setId(bean.getId());
                rOrdCartItemCommRequest.setPromId(bean.getPromId());
                ordCartItemCommList.add(rOrdCartItemCommRequest);
                rOrdCartCommRequest.setOrdCartItemCommList(ordCartItemCommList);
                ordCartChg.setrOrdCartCommRequest(rOrdCartCommRequest);
            }
        }
        else
        {
            ROrdCartCommRequest rOrdCartCommRequest = new ROrdCartCommRequest();
            rOrdCartCommRequest.setId(group.getCartId());
            rOrdCartCommRequest.setPromId(group.getPromId());
            ROrdCartItemCommRequest rOrdCartItemCommRequest = new ROrdCartItemCommRequest();
            rOrdCartItemCommRequest.setId(product.getId());
            rOrdCartItemCommRequest.setPromId(product.getPromId());
            ordCartItemCommList.add(rOrdCartItemCommRequest);
            rOrdCartCommRequest.setOrdCartItemCommList(ordCartItemCommList);
            ordCartChg.setrOrdCartCommRequest(rOrdCartCommRequest);
        }

        request.setOrdCartItem(ordCartItem);
        request.setOrdCartChg(ordCartChg);
        getJsonService().requestOrd013(getActivity(), request, isShowProgress, new JsonService.CallBack<Ord013Resp>() {
            @Override
            public void oncallback(Ord013Resp ord013Resp) {
                resetProm(group, product, ord013Resp);
//                requestList(false, null);
                getCartGoodsNum();
            }

            @Override
            public void onErro(AppHeader header) {
            }
        });
    }

    /**
     * 增加组合商品数量
     * @param isShowProgress
     * @param groupLists
     * @param showCountView
     */
    private void requestAddGroups(boolean isShowProgress, List<Ord00205Resp> groupLists, View showCountView){
        Ord015Req request = new Ord015Req();
        List<ROrdCartItemRequest> ordCartItems = new ArrayList<>();
        for (int i = 0; i < groupLists.size(); i++) {
            Ord00205Resp entity = groupLists.get(i);
            for (int j = 0; j < entity.getGroupSkus().size(); j++) {
                Ord00202Resp bean = entity.getGroupSkus().get(j);
                Long currentCount = bean.getOrderAmount();
                currentCount++;
                bean.setOrderAmount(currentCount);
                ((TextView) showCountView).setText(String.valueOf(currentCount));

                ROrdCartItemRequest rOrdCartItemRequest = new ROrdCartItemRequest();
                rOrdCartItemRequest.setId(bean.getId());
                rOrdCartItemRequest.setOrderAmount(bean.getOrderAmount());
                ordCartItems.add(rOrdCartItemRequest);
            }
        }
        request.setOrdCartItems(ordCartItems);
        getJsonService().requestOrd015(getActivity(), request, isShowProgress, new JsonService.CallBack<Ord015Resp>() {
            @Override
            public void oncallback(Ord015Resp ord015Resp) {
                requestList(true, null);
                getCartGoodsNum();
            }

            @Override
            public void onErro(AppHeader header) {

            }
        });
    }

    /**
     * 减少组合商品数量
     * @param isShowProgress
     * @param groupLists
     * @param showCountView
     */
    private void requestReduceGroups(boolean isShowProgress,List<Ord00205Resp> groupLists,View showCountView){
        Ord015Req request = new Ord015Req();
        List<ROrdCartItemRequest> ordCartItems = new ArrayList<>();
        for (int i = 0; i < groupLists.size(); i++) {
            Ord00205Resp entity = groupLists.get(i);
            for (int j = 0; j < entity.getGroupSkus().size(); j++) {
                Ord00202Resp bean = entity.getGroupSkus().get(j);
                long currentCount = bean.getOrderAmount();
                if (currentCount==1)
                    return;
                currentCount--;
                bean.setOrderAmount(currentCount);
                ((TextView) showCountView).setText(String.valueOf(currentCount));

                ROrdCartItemRequest rOrdCartItemRequest = new ROrdCartItemRequest();
                rOrdCartItemRequest.setId(bean.getId());
                rOrdCartItemRequest.setOrderAmount(bean.getOrderAmount());
                ordCartItems.add(rOrdCartItemRequest);
            }
        }
        request.setOrdCartItems(ordCartItems);
        getJsonService().requestOrd015(getActivity(), request, isShowProgress, new JsonService.CallBack<Ord015Resp>() {
            @Override
            public void oncallback(Ord015Resp ord015Resp) {
                requestList(true, null);
                getCartGoodsNum();
            }

            @Override
            public void onErro(AppHeader header) {

            }
        });
    }

    /**
     * 删除产品信息（无勾选）
     * @param isShowProgress
     * @param product
     */
    private void requestDelteteNoChoosed(Ord00202Resp product, boolean isShowProgress){
        Ord003Req request = new Ord003Req();
        ROrdCartItemRequest ordCartItem = new ROrdCartItemRequest();
        ordCartItem.setId(product.getId());
        ordCartItem.setCartId(product.getCartId());
        ordCartItem.setStaffId(data.getStaffId());
        request.setOrdCartItem(ordCartItem);
        getJsonService().requestOrd003(getActivity(), request, isShowProgress, new JsonService.CallBack<Ord003Resp>() {
            @Override
            public void oncallback(Ord003Resp ord003Resp) {
                requestList(true, null);
                getCartGoodsNum();
            }

            @Override
            public void onErro(AppHeader header) {
            }
        });
    }

    /**
     * 删除购物车产品信息（勾选）
     * @param choosedList
     * @param groupPosition
     * @param childPosition
     * @param isShowProgress
     */
    private void requestDeleteChoosed(List<Ord00202Resp> choosedList, final int groupPosition, final int childPosition,
                                      boolean isShowProgress){
        Ord00201Resp group = groups.get(groupPosition);
        Ord00204Resp ord00204Resp = group.getGroupLists();
        List<Ord00205Resp> groupLists = ord00204Resp.getGroupLists();

        List<Ord00202Resp> products = group.getOrdCartItemList();
        Ord00202Resp product;
        if (groupLists != null && groupLists.size() > 0) {
            product = products.get(childPosition-groupLists.size());
        } else {
            product = products.get(childPosition);
        }

        ROrdCartItemRequest ordCartItem = new ROrdCartItemRequest();
        ROrdCartChangeRequest ordCartChg = new ROrdCartChangeRequest();
        List<ROrdCartItemCommRequest> ordCartItemCommList = new ArrayList<>();

        for (int i = 0; i < choosedList.size(); i++) {
            Ord00202Resp bean = choosedList.get(i);
            if (bean.getId().longValue()==product.getId().longValue())
            {
                ordCartItem.setId(bean.getId());
                ordCartItem.setCartId(bean.getCartId());
                ordCartItem.setPromId(product.getPromId());
                ordCartItem.setStaffId(group.getStaffId());
            }
            else
            {
                ROrdCartCommRequest rOrdCartCommRequest = new ROrdCartCommRequest();
                rOrdCartCommRequest.setId(group.getCartId());
                rOrdCartCommRequest.setPromId(group.getPromId());

                ROrdCartItemCommRequest rOrdCartItemCommRequest = new ROrdCartItemCommRequest();
                rOrdCartItemCommRequest.setId(bean.getId());
                rOrdCartItemCommRequest.setPromId(bean.getPromId());
                ordCartItemCommList.add(rOrdCartItemCommRequest);
                rOrdCartCommRequest.setOrdCartItemCommList(ordCartItemCommList);
                ordCartChg.setrOrdCartCommRequest(rOrdCartCommRequest);
            }
        }
        Ord006Req request = new Ord006Req();
        request.setOrdCartItem(ordCartItem);
        request.setOrdCartChg(ordCartChg);
        getJsonService().requestOrd006(getActivity(), request, isShowProgress, new JsonService.CallBack<Ord006Resp>() {
            @Override
            public void oncallback(Ord006Resp ord006Resp) {
                requestList(true, null);
                getCartGoodsNum();
            }

            @Override
            public void onErro(AppHeader header) {

            }
        });
    }

    /**
     * 批量删除购物车明细
     * @param groups
     * @param isShowProgress
     */
    private void requestBatchDelete(List<Ord00201Resp> groups, boolean isShowProgress){
        Ord007Req request = new Ord007Req();
        List<ROrdCartItemRequest> allOrdCartItems = new ArrayList<>();
        List<ROrdCartItemRequest> ordCartItems = new ArrayList<>();
        List<ROrdCartItemRequest> _ordCartItems = new ArrayList<>();

        for (int i = 0; i < groups.size(); i++) {
            Ord00201Resp group = groups.get(i);
            List<Ord00202Resp> products = group.getOrdCartItemList();
            for (int j = 0; j < products.size(); j++) {
                Ord00202Resp product = products.get(j);
                ROrdCartItemRequest rOrdCartItemRequest = new ROrdCartItemRequest();
                rOrdCartItemRequest.setId(product.getId());
                rOrdCartItemRequest.setPromId(product.getPromId());
                rOrdCartItemRequest.setStaffId(data.getStaffId());
                rOrdCartItemRequest.setCartId(product.getCartId());
                ordCartItems.add(rOrdCartItemRequest);
            }

            List<Ord00205Resp> groupLists = group.getGroupLists().getGroupLists();
            for (int k = 0 ;k < groupLists.size(); k++) {
                Ord00205Resp entity = groupLists.get(k);
                for (int m = 0; m < entity.getGroupSkus().size(); m++) {
                    Ord00202Resp bean = entity.getGroupSkus().get(m);
                    ROrdCartItemRequest rOrdCartItemRequest = new ROrdCartItemRequest();
                    rOrdCartItemRequest.setId(bean.getId());
                    rOrdCartItemRequest.setCartId(bean.getCartId());
                    _ordCartItems.add(rOrdCartItemRequest);
                }
            }

            allOrdCartItems.addAll(ordCartItems);
            allOrdCartItems.addAll(_ordCartItems);
        }
        request.setOrdCartItems(allOrdCartItems);
        getJsonService().requestOrd007(getActivity(), request, isShowProgress, new JsonService.CallBack<Ord007Resp>() {
            @Override
            public void oncallback(Ord007Resp ord007Resp) {
                doDelete();
                requestList(true, null);
                getCartGoodsNum();
            }

            @Override
            public void onErro(AppHeader header) {
            }
        });
    }

    /**
     * 勾选、不选择商品
     * @param groupPosition
     * @param isShowProgress
     */
    private void requestCheck(Ord00202Resp checkProduct, final int groupPosition, boolean isShowProgress) {
        Ord00201Resp group = groups.get(groupPosition);

        Ord014Req request = new Ord014Req();
        ROrdCartChangeRequest ordCartChg = new ROrdCartChangeRequest();
        ROrdCartCommRequest rOrdCartCommRequest = new ROrdCartCommRequest();
        List<ROrdCartItemCommRequest> ordCartItemList = new ArrayList<>();

        rOrdCartCommRequest.setId(checkProduct.getCartId());
        rOrdCartCommRequest.setPromId(checkProduct.getPromId());

        List<Ord00202Resp> products = children.get(group.getShopId());
        for (int i = 0; i < products.size(); i++) {
            Ord00202Resp product = products.get(i);
            if (product.getId().equals(checkProduct.getId())) {
                ROrdCartItemCommRequest rOrdCartItemCommRequest = new ROrdCartItemCommRequest();
                rOrdCartItemCommRequest.setId(product.getId());
                rOrdCartItemCommRequest.setPromId(product.getPromId());
                ordCartItemList.add(rOrdCartItemCommRequest);
                rOrdCartCommRequest.setOrdCartItemCommList(ordCartItemList);
                ordCartChg.setrOrdCartCommRequest(rOrdCartCommRequest);
            }
        }
        request.setrOrdCartChangeRequest(ordCartChg);

        getJsonService().requestOrd014(getActivity(), request, isShowProgress, new JsonService.CallBack<Ord014Resp>() {
            @Override
            public void oncallback(Ord014Resp ord014Resp) {
                requestList(false, null);
            }

            @Override
            public void onErro(AppHeader header) {

            }
        });
    }

    /**
     * 购物车去结算
     * @param isShowProgress
     */
    private void requestAccount(final List<Ord00201Resp> groups, boolean isShowProgress){
        Ord008Req request = new Ord008Req();
        List<RCrePreOrdReqVO> carList = new ArrayList<>();

        for (int i = 0; i < groups.size(); i++) {
            Ord00201Resp group = groups.get(i);
            RCrePreOrdReqVO rCrePreOrdReqVO = new RCrePreOrdReqVO();
            rCrePreOrdReqVO.setCartId(group.getCartId());
            rCrePreOrdReqVO.setPromId(group.getPromId());

            List<Ord00202Resp> childs = group.getOrdCartItemList();
            List<RCrePreOrdItemReqVO> cartItemIdList = new ArrayList<>();

            if (childs!=null && childs.size() >0) {
                for (int j = 0; j < childs.size(); j++) {
                    Ord00202Resp child = childs.get(j);
                    if (child.isGdsStatus()) {
                        RCrePreOrdItemReqVO rCrePreOrdItemReqVO = new RCrePreOrdItemReqVO();
                        rCrePreOrdItemReqVO.setCartItemId(child.getId());
                        rCrePreOrdItemReqVO.setPromId(child.getPromId());
                        cartItemIdList.add(rCrePreOrdItemReqVO);
                    }
                }
            }

            Ord00204Resp goods = group.getGroupLists();
            if (goods != null) {
                List<Ord00205Resp> groupList = goods.getGroupLists();
                if (groupList.size() > 0) {
                    for (int a = 0; a < groupList.size(); a++) {
                        Ord00205Resp bean = groupList.get(a);
                        for (int b = 0; b < bean.getGroupSkus().size(); b++) {
                            Ord00202Resp detail = bean.getGroupSkus().get(b);
                            if (detail.isGdsStatus()) {
                                RCrePreOrdItemReqVO rCrePreOrdItemReqVO = new RCrePreOrdItemReqVO();
                                rCrePreOrdItemReqVO.setCartItemId(detail.getId());
                                rCrePreOrdItemReqVO.setPromId(detail.getPromId());
                                cartItemIdList.add(rCrePreOrdItemReqVO);
                            }
                        }
                    }
                }
            }
            rCrePreOrdReqVO.setCartItemIdList(cartItemIdList);
            carList.add(rCrePreOrdReqVO);
        }
        request.setCarList(carList);
        getJsonService().requestOrd008(getActivity(), request, isShowProgress, new JsonService.CallBack<Ord008Resp>() {
            @Override
            public void oncallback(Ord008Resp ord008Resp) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("groups", (Serializable) groups);
                bundle.putString("redisKey", ord008Resp.getRedisKey());
                bundle.putLong("orderMoneys", ord008Resp.getOrderMoneys());
                bundle.putLong("realExpressFees", ord008Resp.getRealExpressFees());
                bundle.putLong("allMoney", ord008Resp.getAllMoney());
                bundle.putLong("orderAmounts", ord008Resp.getOrderAmounts());
                bundle.putLong("discountMoneys", ord008Resp.getDiscountMoneys());
                bundle.putSerializable("addrs", (Serializable) ord008Resp.getAddrs());
                bundle.putString("ifcoupCodeOpen", ord008Resp.getIfcoupCodeOpen());

                OrderMoneyMap orderMoneyMap = new OrderMoneyMap();
                orderMoneyMap.setOrderMoneyMap(ord008Resp.getOrderMoneyMap());
                bundle.putSerializable("orderMoneyMap", orderMoneyMap);

                RealExpressFeesMap realExpressFeesMap = new RealExpressFeesMap();
                realExpressFeesMap.setRealExpressFeesMap(ord008Resp.getRealExpressFeesMap());
                bundle.putSerializable("realExpressFeesMap", realExpressFeesMap);

                DiscountPriceMoneyMap discountPriceMoneyMap = new DiscountPriceMoneyMap();
                discountPriceMoneyMap.setDiscountPriceMoneyMap(ord008Resp.getDiscountPriceMoneyMap());
                bundle.putSerializable("discountPriceMoneyMap", discountPriceMoneyMap);

                DeliverTypesMap deliverTypesMap = new DeliverTypesMap();
                deliverTypesMap.setDeliverTypes(ord008Resp.getDeliverTypes());
                bundle.putSerializable("deliverTypes", deliverTypesMap);

                PayListMap payListMap = new PayListMap();
                payListMap.setPayList(ord008Resp.getPayList());
                bundle.putSerializable("payList", payListMap);

                bundle.putSerializable("Ord00801Resps", (Serializable) ord008Resp.getOrd00801Resps());
                bundle.putSerializable("invoiceConList", (Serializable) ord008Resp.getInvoiceConList());
                launch(OrderConfirmActivity.class, bundle);
            }

            @Override
            public void onErro(AppHeader header) {
            }
        });
    }

}
