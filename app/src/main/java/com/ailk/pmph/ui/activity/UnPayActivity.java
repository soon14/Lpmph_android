package com.ailk.pmph.ui.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ai.ecp.app.req.Ord010Req;
import com.ai.ecp.app.req.Ord011Req;
import com.ai.ecp.app.req.Ord012Req;
import com.ai.ecp.app.req.Ord022Req;
import com.ai.ecp.app.req.Pay002Req;
import com.ai.ecp.app.resp.Ord010Resp;
import com.ai.ecp.app.resp.Ord011Resp;
import com.ai.ecp.app.resp.Ord01201Resp;
import com.ai.ecp.app.resp.Ord01202Resp;
import com.ai.ecp.app.resp.Ord012Resp;
import com.ai.ecp.app.resp.Ord022Resp;
import com.ai.ecp.app.resp.Pay002Resp;
import com.ailk.butterfly.app.model.AppHeader;
import com.ailk.butterfly.app.model.JsonService;
import com.ailk.pmph.R;
import com.ailk.pmph.base.BaseActivity;
import com.ailk.pmph.utils.Constant;
import com.ailk.pmph.utils.ToastUtil;

import com.ailk.pmph.ui.adapter.UnPayOrderListAdapter;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshExpandableListView;


import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 类注释:待付款
 * 项目名:pmph_android
 * 包名:com.ailk.pmph.ui.activity
 * 作者: Chrizz
 * 时间: 2016/4/10 15:55
 */
public class UnPayActivity extends BaseActivity implements UnPayOrderListAdapter.ClickChildInterface,
        UnPayOrderListAdapter.PayMoneyInterface, UnPayOrderListAdapter.DeleteOrderInterface,
        UnPayOrderListAdapter.GroupInterface, View.OnClickListener{

    @BindView(R.id.iv_back)                   ImageView ivBack;
    @BindView(R.id.tv_merge_pay)              TextView tvMergePay;
    @BindView(R.id.lv_all_order_list)         PullToRefreshExpandableListView lvUnPayOrderList;
    @BindView(R.id.rl_empty_layout)           RelativeLayout emptyLayout;
    @BindView(R.id.ll_unempty_layout)         LinearLayout unEmptyLayout;

    private ExpandableListView listView;
    private List<Ord01201Resp> groups = new ArrayList<>();
    private Map<String, List<Ord01202Resp>> children = new HashMap<>();
    private UnPayOrderListAdapter adapter;
    private int pageNo = 1;
    private long lastRequest = 0;
    private List<Ord01201Resp> payGroups;
    private String mParaValue;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_unpay_orders;
    }

    @Override
    protected void onResume() {
        super.onResume();
        payGroups.clear();
        pageNo=1;
        requestOrderList(true);
    }

    public void initView() {
        lvUnPayOrderList.setPullToRefreshOverScrollEnabled(true);
        lvUnPayOrderList.setMode(PullToRefreshBase.Mode.BOTH);
        listView = lvUnPayOrderList.getRefreshableView();
        listView.setGroupIndicator(null);
        listView.setChildIndicator(null);
        listView.setDivider(null);
        payGroups = new ArrayList<>();
        showMergePay();
    }

    private void showMergePay() {
        Ord022Req req = new Ord022Req();
        req.setSpCode(Constant.SWITCH_PAY_MERGE);
        getJsonService().requestOrd022(this, req, false, new JsonService.CallBack<Ord022Resp>() {
            @Override
            public void oncallback(Ord022Resp ord022Resp) {
                if (ord022Resp != null) {
                    mParaValue = ord022Resp.getParaValue();
                    if (StringUtils.isNotEmpty(mParaValue)) {
                        if (StringUtils.equals("1", mParaValue)) {
                            setVisible(tvMergePay);
                        } else {
                            setGone(tvMergePay);
                        }
                    }
                }
            }

            @Override
            public void onErro(AppHeader header) {

            }
        });
    }

    private void requestOrderList(boolean isShowProgress){
        Ord012Req request = new Ord012Req();
        request.setPageNo(pageNo);
        request.setPageSize(Constant.PAGE_SIZE);
        request.setStatus(Constant.UN_PAY);
        request.setSiteId(1L);
        getJsonService().requestOrd012(this, request, isShowProgress, new JsonService.CallBack<Ord012Resp>() {
            @Override
            public void oncallback(Ord012Resp ord012Resp) {
                groups = ord012Resp.getOrd01201Resps();
                if (groups==null || groups.size() == 0)
                {
                    setGone(unEmptyLayout);
                    setVisible(emptyLayout);
                }
                else
                {
                    setGone(emptyLayout);
                    setVisible(unEmptyLayout);
                    for (int i = 0; i < groups.size(); i++) {
                        Ord01201Resp shop = groups.get(i);
                        children.put(shop.getOrderId(), shop.getOrd01102Resps());
                        adapter = new UnPayOrderListAdapter(UnPayActivity.this, groups, children);
                    }
                    adapter.setClickChildInterface(UnPayActivity.this);
                    adapter.setPayMoneyInterface(UnPayActivity.this);
                    adapter.setDeleteOrderInterface(UnPayActivity.this);
                    adapter.setGroupInterface(UnPayActivity.this);
                    adapter.setParaValue(mParaValue);
                    listView.setAdapter(adapter);
                    for (int i = 0; i < adapter.getGroupCount(); i++) {
                        listView.expandGroup(i);
                    }
                    listView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                        @Override
                        public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                            return true;
                        }
                    });
                }
            }

            @Override
            public void onErro(AppHeader header) {

            }
        });
    }

    public void initData() {
        lvUnPayOrderList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ExpandableListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ExpandableListView> refreshView) {
                pageNo=1;
                requestOrderList(true);
                lvUnPayOrderList.onRefreshComplete();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ExpandableListView> refreshView) {
                loadMoreData();
                lvUnPayOrderList.onRefreshComplete();
            }
        });
    }

    private void loadMoreData() {
        Ord012Req request = new Ord012Req();
        request.setPageNo(++pageNo);
        request.setPageSize(Constant.PAGE_SIZE);
        request.setStatus(Constant.UN_PAY);
        request.setSiteId(1L);
        getJsonService().requestOrd012(this, request, true, new JsonService.CallBack<Ord012Resp>() {
            @Override
            public void oncallback(Ord012Resp ord012Resp) {
                List<Ord01201Resp> data = ord012Resp.getOrd01201Resps();
                if (data==null) {
                    ToastUtil.showCenter(UnPayActivity.this, R.string.toast_load_more_msg);
                } else {
                    adapter.addData(data);
                }
                for (int i = 0; i < adapter.getGroupCount(); i++) {
                    listView.expandGroup(i);
                }
            }

            @Override
            public void onErro(AppHeader header) {
            }
        });
    }

    @Override
    @OnClick({R.id.iv_back, R.id.tv_merge_pay})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_merge_pay:
                requestMergePay();
                break;
        }
    }

    @Override
    public void checkGroup(int groupPosition, boolean isChecked) {
        Ord01201Resp group = groups.get(groupPosition);
        if (isChecked) {
            payGroups.add(group);
            adapter.notifyDataSetChanged();
        } else {
            payGroups.remove(group);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void clickChild(int groupPosition) {
        Ord01201Resp group = groups.get(groupPosition);
        requestOrderDetail(group.getOrderId());
    }

    @Override
    public void payMoney(Ord01201Resp shop) {
        if (System.currentTimeMillis() - lastRequest < 1000) {
            return;
        }
        lastRequest = System.currentTimeMillis();
        Pay002Req req = new Pay002Req();
        req.setOrderId(shop.getOrderId());
        getJsonService().requestPay002(this, req, true, new JsonService.CallBack<Pay002Resp>() {
            @Override
            public void oncallback(Pay002Resp pay002Resp) {
                Bundle bundle = new Bundle();
                bundle.putLong("realMoney", pay002Resp.getMergeTotalRealMoney());
                bundle.putSerializable("pay002Resp", pay002Resp);
                launch(OrderListPayActivity.class, bundle);
            }

            @Override
            public void onErro(AppHeader header) {

            }
        });
    }

    @Override
    public void deleteOrder(Ord01201Resp shop, int groupPostion) {
        requestCancelOrder(true, shop);
    }

    /**
     * 合并支付
     */
    private void requestMergePay() {
        if (payGroups.size()==0) {
            ToastUtil.showCenter(this,"亲,您还没有选择要支付的店铺哦");
            return;
        }
        if (System.currentTimeMillis() - lastRequest < 1000) {
            return;
        }
        lastRequest = System.currentTimeMillis();
        StringBuilder builder = new StringBuilder();
        Pay002Req req = new Pay002Req();
        if (payGroups.size() == 1) {
            req.setOrderId(payGroups.get(0).getOrderId());
        }
        else if (payGroups.size() > 1) {
            for (int i = 0 ; i < payGroups.size(); i++) {
                Ord01201Resp payGroup = payGroups.get(i);
                builder.append(payGroup.getOrderId()).append(":");
                req.setOrderId(StringUtils.substringBeforeLast(builder.toString(),":"));
            }
        }
        getJsonService().requestPay002(this, req, true, new JsonService.CallBack<Pay002Resp>() {
            @Override
            public void oncallback(Pay002Resp pay002Resp) {
                Bundle bundle = new Bundle();
                bundle.putLong("realMoney", pay002Resp.getMergeTotalRealMoney());
                bundle.putSerializable("pay002Resp", pay002Resp);
                launch(OrderListPayActivity.class, bundle);
            }

            @Override
            public void onErro(AppHeader header) {

            }
        });
    }

    private void requestOrderDetail(String orderId) {
        Ord011Req request = new Ord011Req();
        request.setOrderId(orderId);
        getJsonService().requestOrd011(this, request, true, new JsonService.CallBack<Ord011Resp>() {
            @Override
            public void oncallback(Ord011Resp ord011Resp) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("ord011Resp", ord011Resp);
                launch(OrderDetailActivity.class, bundle);
            }

            @Override
            public void onErro(AppHeader header) {

            }
        });
    }

    private void requestCancelOrder(boolean isShowProgress, Ord01201Resp shop){
        Ord010Req request = new Ord010Req();
        request.setOrderId(shop.getOrderId());
        getJsonService().requestOrd010(this, request, isShowProgress, new JsonService.CallBack<Ord010Resp>() {
            @Override
            public void oncallback(Ord010Resp ord010Resp) {
                ToastUtil.showCenter(UnPayActivity.this, "订单取消成功");
            }

            @Override
            public void onErro(AppHeader header) {

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

}
