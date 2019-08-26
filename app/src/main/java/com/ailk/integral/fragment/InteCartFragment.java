package com.ailk.integral.fragment;

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

import com.ai.ecp.app.req.Ord102Req;
import com.ai.ecp.app.req.Ord103Req;
import com.ai.ecp.app.req.Ord104Req;
import com.ai.ecp.app.req.Ord105Req;
import com.ai.ecp.app.req.Ord111Req;
import com.ai.ecp.app.req.RCrePreOrdItemReqVO;
import com.ai.ecp.app.req.RCrePreOrdReqVO;
import com.ai.ecp.app.req.ROrdCartItemRequest;
import com.ai.ecp.app.resp.Ord10201Resp;
import com.ai.ecp.app.resp.Ord10202Resp;
import com.ai.ecp.app.resp.Ord102Resp;
import com.ai.ecp.app.resp.Ord103Resp;
import com.ai.ecp.app.resp.Ord104Resp;
import com.ai.ecp.app.resp.Ord105Resp;
import com.ai.ecp.app.resp.Ord111Resp;
import com.ailk.butterfly.app.model.AppHeader;
import com.ailk.butterfly.app.model.InteJsonService;
import com.ailk.integral.activity.InteMainActivity;
import com.ailk.integral.activity.InteOrderConfirmActivity;
import com.ailk.integral.activity.InteShopDetailActivity;
import com.ailk.integral.adapter.InteCartAdapter;
import com.ailk.pmph.R;
import com.ailk.pmph.utils.Constant;
import com.ailk.pmph.utils.DialogAnotherUtil;
import com.ailk.pmph.utils.DialogUtil;
import com.ailk.pmph.utils.MoneyUtils;
import com.ailk.pmph.utils.ToastUtil;
import com.ailk.pmph.base.BaseFragment;

import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 类注释:
 * 项目名:pmph_android
 * 包名:com.ailk.integral.frag
 * 作者: Chrizz
 * 时间: 2016/5/11 15:19
 */
public class InteCartFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener,
        AbsListView.OnScrollListener,View.OnClickListener,InteCartAdapter.CheckInterface,
        InteCartAdapter.ModifyCountInterface,InteCartAdapter.RedirectToShopDetailInterface,
        InteCartAdapter.DeleteInterface{

    @BindView(R.id.iv_back)               ImageView ivBack;
    @BindView(R.id.tv_edit)               TextView tvEdit;
    @BindView(R.id.sw_container)          SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.lv_shopcart)           ExpandableListView lvShopCartList;
    @BindView(R.id.cb_checkall)           CheckBox cbCheckAll;
    @BindView(R.id.cb_editcheckall)       CheckBox cbEditCheckAll;
    @BindView(R.id.tv_check_all)          TextView tvCheckAll;
    @BindView(R.id.tv_textall)            TextView tvEditCheckAll;
    @BindView(R.id.tv_totalprice)         TextView tvTotalPrice;
    @BindView(R.id.tv_account)            Button tvAccount;
    @BindView(R.id.btn_del)               Button btnDel;
    @BindView(R.id.rl_bottom)             RelativeLayout rlBottom;
    @BindView(R.id.rl_editbottom)         RelativeLayout rlEditBottom;
    @BindView(R.id.empty_layout)          RelativeLayout emptyLayout;//空购物车显示
    @BindView(R.id.un_empty_layout)       LinearLayout unEmptyLayout;

    private Ord102Resp data;
    private List<Ord10201Resp> groups = new ArrayList<>();//组元素数据列表
    private Map<Long, List<Ord10202Resp>> productsMap = new HashMap<>();//子元素数据列表

    public static final int STATE_NONE = 0;
    public static final int STATE_REFRESH = 1;
    public static int mState = STATE_NONE;
    private InteCartAdapter adapter;
    private Long totalNum = 0L;
    public static final String REFRESH_INTE_CART = "refresh_inte_cart";

    public static InteCartFragment newInstance() {
        InteCartFragment fragment = new InteCartFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.inte_fragment_cart;
    }

    @Override
    public void initData() {

    }

    @Override
    public void onResume() {
        super.onResume();
        requestList(true);
    }

    public void initView(View view){
        if (StringUtils.isNotEmpty(getArguments().getString("fromInteShopDetail"))) {
            setVisible(ivBack);
        } else {
            setGone(ivBack);
        }

        swipeRefreshLayout.setColorSchemeColors(getActivity().getResources().getIntArray(R.array.swipe_colors));
        swipeRefreshLayout.setOnRefreshListener(this);
        cbCheckAll.setOnClickListener(this);
        lvShopCartList.setOnScrollListener(this);
        tvEdit.setOnClickListener(this);
        tvAccount.setOnClickListener(this);
        cbEditCheckAll.setOnClickListener(this);
        tvCheckAll.setOnClickListener(this);
        tvEditCheckAll.setOnClickListener(this);
        btnDel.setOnClickListener(this);

        IntentFilter refreshCart = new IntentFilter();
        refreshCart.addAction(REFRESH_INTE_CART);
        getActivity().registerReceiver(refreshCartReceiver,refreshCart);
    }

    public BroadcastReceiver refreshCartReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (StringUtils.equals(intent.getAction(), REFRESH_INTE_CART)) {
                setVisible(emptyLayout);
                setGone(unEmptyLayout,tvEdit);
            }
        }
    };

    @Override
    public void onRefresh() {
        if (mState==STATE_REFRESH) {
            return;
        }
        requestList(true);//下拉刷新时不显示进度条
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
        Ord10201Resp group = groups.get(groupPosition);
        List<Ord10202Resp> products = productsMap.get(group.getShopId());
        for (int i = 0; i < products.size(); i++) {
            Ord10202Resp product = products.get(i);
            product.setIsChoosed(isChecked);
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
    public void checkChild(int groupPosition, int childPosition, boolean isChecked) {
        boolean allChildSameState = true;
        Ord10201Resp group = groups.get(groupPosition);
        List<Ord10202Resp> products = productsMap.get(group.getShopId());
        for (int i = 0; i < products.size(); i++) {
            Ord10202Resp product = products.get(i);
            if (product.isChoosed() == isChecked) {
                group.setIsChoosed(isChecked);
                cbCheckAll.setChecked(false);
            }
            if (product.isChoosed() != isChecked) {
                allChildSameState = false;
                break;
            }
        }
        if (allChildSameState) {
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
    public void doIncrease(int groupPosition, int childPosition, View showCountView, boolean isChecked) {
        if (!isChecked) {
            ToastUtil.showCenter(getActivity(),"您还没有选择商品哦");
            return;
        }
        Ord10202Resp product = adapter.getChild(groupPosition, childPosition);
        requestIncrease(true, product, showCountView);
    }

    @Override
    public void doDecrease(int groupPosition, int childPosition, View showCountView, boolean isChecked) {
        if (!isChecked) {
            ToastUtil.showCenter(getActivity(),"您还没有选择商品哦");
            return;
        }
        Ord10202Resp product = adapter.getChild(groupPosition, childPosition);
        requestDecrease(true, product, showCountView);
    }

    @Override
    public void redirectToShopDetail(Ord10202Resp product) {
        Bundle bundle = new Bundle();
        bundle.putString(Constant.SHOP_GDS_ID, String.valueOf(product.getGdsId()));
        bundle.putString(Constant.SHOP_SKU_ID, String.valueOf(product.getSkuId()));
        launch(InteShopDetailActivity.class, bundle);
    }

    @Override
    public void doDeleteItem(Ord10201Resp shop, final Ord10202Resp product, int groupPosition, int childPosition) {
        DialogAnotherUtil.showCustomAlertDialogWithMessage(getActivity(), null,
                "确认要删除这1种商品吗？", "确定", "取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Ord104Req request = new Ord104Req();
                        List<ROrdCartItemRequest> ordCartItems = new ArrayList<>();
                        ROrdCartItemRequest rOrdCartItemRequest = new ROrdCartItemRequest();
                        rOrdCartItemRequest.setId(product.getId());
                        rOrdCartItemRequest.setPromId(product.getPromId());
                        rOrdCartItemRequest.setStaffId(data.getStaffId());
                        rOrdCartItemRequest.setCartId(product.getCartId());
                        ordCartItems.add(rOrdCartItemRequest);
                        request.setOrdCartItems(ordCartItems);
                        getInteJsonService().requestOrd104(getActivity(), request, true, new InteJsonService.CallBack<Ord104Resp>() {
                            @Override
                            public void oncallback(Ord104Resp ord104Resp) {
                                requestList(true);
                            }

                            @Override
                            public void onErro(AppHeader header) {
                            }
                        });
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

    @Override
    @OnClick({R.id.iv_back, R.id.tv_edit, R.id.cb_checkall, R.id.tv_check_all, R.id.cb_editcheckall,
            R.id.tv_textall, R.id.btn_del, R.id.tv_account})
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
                        setVisible(rlBottom);
                        setGone(rlEditBottom);
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
                if (isAllCheck())
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
                    final List<Ord10201Resp> deleteGroups = new ArrayList<>();// 待删除的店铺列表
                    for (int i = 0; i < groups.size(); i++) {
                        Ord10201Resp group = groups.get(i);
                        List<Ord10202Resp> deleteProducts = new ArrayList<>();// 待删除的单品列表
                        List<Ord10202Resp> products = productsMap.get(group.getShopId());
                        for (int j = 0; j < products.size(); j++) {
                            Ord10202Resp product = products.get(j);
                            if (product.isChoosed()) {
                                deleteProducts.add(product);
                            }
                        }
                        group.setOrdCartItemList(deleteProducts);
                        deleteGroups.add(group);
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
            case R.id.tv_account:
                if (totalNum == 0) {
                    ToastUtil.showCenter(getActivity(),"您还没有选择商品哦");
                    return;
                }
                if (isAllCheck()) {
                    for (int i = 0; i < groups.size(); i++) {
                        Ord10201Resp shop = groups.get(i);
                        for (int j = 0; j < shop.getOrdCartItemList().size(); j++) {
                            Ord10202Resp product = shop.getOrdCartItemList().get(j);
                            if (!product.isGdsStatus()) {
                                ToastUtil.showCenter(getActivity(), "请先删除已失效的商品");
                                return;
                            }
                        }
                    }
                    requestAccount(groups, true);
                }
                else {
                    List<Ord10201Resp> accountGroups = new ArrayList<>();
                    for (int i = 0; i < groups.size(); i++) {
                        Ord10201Resp group = groups.get(i);
                        List<Ord10202Resp> accountProducts = new ArrayList<>();
                        List<Ord10202Resp> products = productsMap.get(group.getShopId());
                        for (int j = 0; j < products.size(); j++) {
                            Ord10202Resp product = products.get(j);
                            if (product.isChoosed()) {
                                accountProducts.add(product);
                            }
                            if (!product.isGdsStatus()) {
                                ToastUtil.showCenter(getActivity(), "请先删除已失效的商品");
                                return;
                            }
                        }
                        if (accountProducts.size() > 0) {
                            group.setOrdCartItemList(accountProducts);
                            accountGroups.add(group);
                        }
                    }
                    requestAccount(accountGroups, true);
                }
                break;
        }
    }

    private void requestList(boolean isShowProgress){
        Ord102Req req = new Ord102Req();
        getInteJsonService().requestOrd102(getActivity(), req, isShowProgress, new InteJsonService.CallBack<Ord102Resp>() {
            @Override
            public void oncallback(Ord102Resp ord102Resp) {
                swipeRefreshLayout.setRefreshing(false);
                data = ord102Resp;
                groups = data.getOrdCartList();
                if (groups.size()==0)
                {
                    setGone(tvEdit, unEmptyLayout, rlEditBottom);
                    setVisible(emptyLayout);
                }
                else
                {
                    setVisible(tvEdit, unEmptyLayout);
                    setGone(emptyLayout);
                    if (StringUtils.equals(tvEdit.getText().toString(),"编辑"))
                    {
                        setVisible(rlBottom);
                        setGone(rlEditBottom);
                    }
                    else
                    {
                        setGone(rlBottom);
                        setVisible(rlEditBottom);
                    }
                    for (int i = 0; i < ord102Resp.getOrdCartList().size(); i++) {
                        Ord10201Resp bean = ord102Resp.getOrdCartList().get(i);
                        productsMap.put(bean.getShopId(), bean.getOrdCartItemList());
                        adapter = new InteCartAdapter(getActivity(), groups, productsMap, tvEdit.getText().toString());
                    }
                    for (int i = 0; i < groups.size(); i++) {
                        Ord10201Resp group = groups.get(i);
                        groups.get(i).setIsChoosed(true);
                        List<Ord10202Resp> products = productsMap.get(group.getShopId());
                        for (int j = 0; j < products.size(); j++) {
                            products.get(j).setIsChoosed(true);
                        }
                    }
                    if (isAllCheck()) {
                        cbCheckAll.setChecked(true);
                        cbEditCheckAll.setChecked(true);
                    }
                    calculate();
                    adapter.setCheckInterface(InteCartFragment.this);
                    adapter.setModifyCountInterface(InteCartFragment.this);
                    adapter.setRedirectInterface(InteCartFragment.this);
                    adapter.setDeleteInterface(InteCartFragment.this);
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

    private void getCartGoodsNum() {
        Ord111Req req = new Ord111Req();
        getInteJsonService().requestOrd111(getActivity(), req, false, new InteJsonService.CallBack<Ord111Resp>() {
            @Override
            public void oncallback(Ord111Resp ord111Resp) {
                if (ord111Resp != null) {
                    Intent intent = new Intent(InteMainActivity.INTE_CART_GOODS_NUM);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("ord111Resp", ord111Resp);
                    intent.putExtras(bundle);
                    getActivity().sendBroadcast(intent);
                }
            }

            @Override
            public void onErro(AppHeader header) {

            }
        });
    }

    private void requestIncrease(boolean isShowProgress, Ord10202Resp product, View showCountView) {
        long currentCount = product.getOrderAmount();
        currentCount++;
        product.setOrderAmount(currentCount);
        ((TextView) showCountView).setText(String.valueOf(currentCount));
        adapter.notifyDataSetChanged();
        calculate();
        Ord103Req req = new Ord103Req();
        ROrdCartItemRequest ordCartItem = new ROrdCartItemRequest();
        ordCartItem.setId(product.getId());
        ordCartItem.setOrderAmount(currentCount);
        req.setOrdCartItem(ordCartItem);
        getInteJsonService().requestOrd103(getActivity(), req, isShowProgress, new InteJsonService.CallBack<Ord103Resp>() {
            @Override
            public void oncallback(Ord103Resp ord103Resp) {
                getCartGoodsNum();
            }

            @Override
            public void onErro(AppHeader header) {

            }
        });
    }

    private void requestDecrease(boolean isShowProgress, Ord10202Resp product, View showCountView) {
        long currentCount = product.getOrderAmount();
        if (currentCount==1)
            return;
        currentCount--;
        product.setOrderAmount(currentCount);
        ((TextView) showCountView).setText(String.valueOf(currentCount));
        adapter.notifyDataSetChanged();
        calculate();
        Ord103Req req = new Ord103Req();
        ROrdCartItemRequest ordCartItem = new ROrdCartItemRequest();
        ordCartItem.setId(product.getId());
        ordCartItem.setOrderAmount(currentCount);
        req.setOrdCartItem(ordCartItem);
        getInteJsonService().requestOrd103(getActivity(), req, isShowProgress, new InteJsonService.CallBack<Ord103Resp>() {
            @Override
            public void oncallback(Ord103Resp ord103Resp) {
                getCartGoodsNum();
            }

            @Override
            public void onErro(AppHeader header) {

            }
        });
    }

    private void requestBatchDelete(List<Ord10201Resp> groups, boolean isShowProgress){
        Ord104Req request = new Ord104Req();
        List<ROrdCartItemRequest> ordCartItems = new ArrayList<>();

        for (int i = 0; i < groups.size(); i++) {
            Ord10201Resp group = groups.get(i);
            List<Ord10202Resp> products = group.getOrdCartItemList();
            for (int j = 0; j < products.size(); j++) {
                Ord10202Resp product = products.get(j);
                ROrdCartItemRequest rOrdCartItemRequest = new ROrdCartItemRequest();
                rOrdCartItemRequest.setId(product.getId());
                rOrdCartItemRequest.setPromId(product.getPromId());
                rOrdCartItemRequest.setStaffId(data.getStaffId());
                rOrdCartItemRequest.setCartId(product.getCartId());
                ordCartItems.add(rOrdCartItemRequest);
            }
        }
        request.setOrdCartItems(ordCartItems);
        getInteJsonService().requestOrd104(getActivity(), request, isShowProgress, new InteJsonService.CallBack<Ord104Resp>() {
            @Override
            public void oncallback(Ord104Resp ord104Resp) {
                doDelete();
                requestList(true);
                getCartGoodsNum();
            }

            @Override
            public void onErro(AppHeader header) {
            }
        });
    }

    private void requestAccount(final List<Ord10201Resp> groups, boolean isShowProgress){
        Ord105Req req = new Ord105Req();
        List<RCrePreOrdReqVO> carList = new ArrayList<>();
        for (int i = 0 ; i < groups.size(); i++) {
            Ord10201Resp shop = groups.get(i);
            RCrePreOrdReqVO rCrePreOrdReqVO = new RCrePreOrdReqVO();
            rCrePreOrdReqVO.setCartId(shop.getCartId());

            List<RCrePreOrdItemReqVO> cartItemIdList = new ArrayList<>();
            List<Ord10202Resp> products = shop.getOrdCartItemList();
            for (int j = 0; j < products.size(); j++) {
                Ord10202Resp product = products.get(j);
                if (product.isGdsStatus()) {
                    RCrePreOrdItemReqVO rCrePreOrdItemReqVO = new RCrePreOrdItemReqVO();
                    rCrePreOrdItemReqVO.setCartItemId(product.getId());
                    cartItemIdList.add(rCrePreOrdItemReqVO);
                }
            }
            rCrePreOrdReqVO.setCartItemIdList(cartItemIdList);
            carList.add(rCrePreOrdReqVO);
        }
        req.setCarList(carList);
        getInteJsonService().requestOrd105(getActivity(), req, isShowProgress, new InteJsonService.CallBack<Ord105Resp>() {
            @Override
            public void oncallback(Ord105Resp ord105Resp) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("groups", (Serializable) groups);
                bundle.putString("redisKey", ord105Resp.getRedisKey());
                bundle.putLong("orderMoneys", ord105Resp.getOrderMoneys());
                bundle.putLong("orderScores", ord105Resp.getOrderScores());
                bundle.putLong("orderAmounts", ord105Resp.getOrderAmounts());
                bundle.putSerializable("orderMoneyMap", (Serializable) ord105Resp.getOrderMoneyMap());
                bundle.putSerializable("orderScoreMap", (Serializable) ord105Resp.getOrderScoreMap());
                bundle.putSerializable("addrs", (Serializable) ord105Resp.getAddrs());
                launch(InteOrderConfirmActivity.class, bundle);
            }

            @Override
            public void onErro(AppHeader header) {

            }
        });
    }

    /**
     * 全选操作
     */
    private void doCheckAll(CheckBox checkAll){
        for (int i = 0; i < groups.size(); i++) {
            Ord10201Resp group = groups.get(i);
            group.setIsChoosed(checkAll.isChecked());
            List<Ord10202Resp> products = productsMap.get(group.getShopId());
            for (int j = 0; j < products.size(); j++) {
                Ord10202Resp product = products.get(j);
                product.setIsChoosed(checkAll.isChecked());
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
            Ord10201Resp group = groups.get(i);
            if (!group.isChoosed())
                return false;
        }
        return true;
    }

    /**
     * 删除操作
     */
    private void doDelete() {
        List<Ord10201Resp> deleteGroups = new ArrayList<>();// 待删除的店铺列表
        for (int i = 0 ; i < groups.size(); i++) {
            Ord10201Resp group = groups.get(i);
            if (group.isChoosed()) {
                deleteGroups.add(group);
            }
            List<Ord10202Resp> deleteProducts = new ArrayList<>();// 待删除的单品列表
            List<Ord10202Resp> products = productsMap.get(group.getShopId());
            for (int j = 0; j < products.size(); j++) {
                Ord10202Resp product = products.get(j);
                if (product.isChoosed()) {
                    deleteProducts.add(product);
                }
            }
            products.removeAll(deleteProducts);
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
        Long totalScore = 0L;
        for (int i = 0; i < groups.size(); i++) {
            Ord10201Resp group = groups.get(i);
            List<Ord10202Resp> products = productsMap.get(group.getShopId());
            for (int j = 0; j < products.size(); j++) {
                Ord10202Resp product = products.get(j);
                if (product.isGdsStatus())
                {
                    if (product.isChoosed())
                    {
                        totalNum++;
                        totalPrice += product.getBuyPrice()*product.getOrderAmount();
                        totalScore += product.getScore()*product.getOrderAmount();
                    }
                }
            }
            tvTotalPrice.setText(totalScore + "积分 + " + MoneyUtils.GoodListPrice(totalPrice));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getActivity().unregisterReceiver(refreshCartReceiver);
    }

}
