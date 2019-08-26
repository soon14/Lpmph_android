package com.ailk.pmph.ui.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.ai.ecp.app.req.Ord010Req;
import com.ai.ecp.app.req.Ord011Req;
import com.ai.ecp.app.req.Ord012Req;
import com.ai.ecp.app.req.Ord017Req;
import com.ai.ecp.app.req.Ord021Req;
import com.ai.ecp.app.req.Pay002Req;
import com.ai.ecp.app.resp.Ord010Resp;
import com.ai.ecp.app.resp.Ord011Resp;
import com.ai.ecp.app.resp.Ord01201Resp;
import com.ai.ecp.app.resp.Ord01202Resp;
import com.ai.ecp.app.resp.Ord012Resp;
import com.ai.ecp.app.resp.Ord017Resp;
import com.ai.ecp.app.resp.Ord021Resp;
import com.ai.ecp.app.resp.Pay002Resp;
import com.ailk.butterfly.app.model.AppHeader;
import com.ailk.butterfly.app.model.JsonService;
import com.ailk.pmph.R;
import com.ailk.pmph.base.BaseActivity;
import com.ailk.pmph.utils.Constant;
import com.ailk.pmph.utils.DialogAnotherUtil;
import com.ailk.pmph.utils.DialogUtil;
import com.ailk.pmph.utils.OrderStatus;
import com.ailk.pmph.utils.ToastUtil;
import com.ailk.pmph.ui.adapter.OrderListAdapter;
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
 * 类注释:全部订单
 * 项目名:pmph_android
 * 包名:com.ailk.pmph.ui.activity
 * 作者: Chrizz
 * 时间: 2016/4/5 10:26
 */
public class AllOrdersActivity extends BaseActivity implements View.OnClickListener,
        OrderListAdapter.ClickChildInterface,OrderListAdapter.PayMoneyInterface,
        OrderListAdapter.DeleteOrderInterface, OrderListAdapter.RefundInterface,
        OrderListAdapter.SalesReturnInterface,OrderListAdapter.TakeGoodsInterface,
        OrderListAdapter.CheckLogisticsInterface{

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.lv_all_order_list)
    PullToRefreshExpandableListView lvAllOrderList;
    @BindView(R.id.rl_empty_layout)
    RelativeLayout emptyLayout;
    @BindView(R.id.ll_unempty_layout)
    LinearLayout unEmptyLayout;

    private ExpandableListView listView;
    private List<Ord01201Resp> groups = new ArrayList<>();
    private Map<String, List<Ord01202Resp>> children = new HashMap<>();
    private OrderListAdapter adapter;
    private int pageNo = 1;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_all_orders;
    }

    @Override
    protected void onResume() {
        super.onResume();
        pageNo=1;
        requestOrderList(true);
    }

    @Override
    public void initView() {
        lvAllOrderList.setPullToRefreshOverScrollEnabled(true);
        lvAllOrderList.setMode(PullToRefreshBase.Mode.BOTH);
        listView = lvAllOrderList.getRefreshableView();
        listView.setGroupIndicator(null);
        listView.setChildIndicator(null);
        listView.setDivider(null);
    }

    private void requestOrderList(boolean isShowProgress){
        Ord012Req request = new Ord012Req();
        request.setPageNo(pageNo);
        request.setPageSize(Constant.PAGE_SIZE);
        request.setStatus(OrderStatus.ALL);
        request.setSiteId(1L);
        getJsonService().requestOrd012(this, request, isShowProgress, new JsonService.CallBack<Ord012Resp>() {
            @Override
            public void oncallback(Ord012Resp ord012Resp) {
                groups = ord012Resp.getOrd01201Resps();
                if (groups==null || groups.size() == 0) {
                    setVisible(emptyLayout);
                    setGone(unEmptyLayout);
                } else {
                    setGone(emptyLayout);
                    setVisible(unEmptyLayout);
                    for (int i = 0 ; i < groups.size(); i++) {
                        Ord01201Resp shop = groups.get(i);
                        children.put(shop.getOrderId(), shop.getOrd01102Resps());
                        adapter = new OrderListAdapter(AllOrdersActivity.this, groups, children);
                    }
                    adapter.setClickChildInterface(AllOrdersActivity.this);
                    adapter.setPayMoneyInterface(AllOrdersActivity.this);
                    adapter.setSalesReturnInterface(AllOrdersActivity.this);
                    adapter.setTakeGoodsInterface(AllOrdersActivity.this);
                    adapter.setRefundInterface(AllOrdersActivity.this);
                    adapter.setDeleteOrderInterface(AllOrdersActivity.this);
                    adapter.setCheckLogisticsInterface(AllOrdersActivity.this);
                    listView.setAdapter(adapter);
                    for (int i = 0; i < adapter.getGroupCount(); i++) {
                        listView.expandGroup(i);
                    }
                }
            }

            @Override
            public void onErro(AppHeader header) {

            }
        });
    }

    @Override
    public void initData() {
        lvAllOrderList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ExpandableListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ExpandableListView> refreshView) {
                pageNo=1;
                requestOrderList(true);
                lvAllOrderList.onRefreshComplete();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ExpandableListView> refreshView) {
                loadMoreData();
                lvAllOrderList.onRefreshComplete();
            }
        });
    }

    private void loadMoreData() {
        Ord012Req request = new Ord012Req();
        request.setPageNo(++pageNo);
        request.setPageSize(Constant.PAGE_SIZE);
        request.setStatus(Constant.ALL);
        request.setSiteId(1L);
        getJsonService().requestOrd012(this, request, true, new JsonService.CallBack<Ord012Resp>() {
            @Override
            public void oncallback(Ord012Resp ord012Resp) {
                List<Ord01201Resp> data = ord012Resp.getOrd01201Resps();
                if (data==null) {
                    ToastUtil.showCenter(AllOrdersActivity.this, R.string.toast_load_more_msg);
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
    @OnClick({R.id.iv_back})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            default:
                break;
        }
    }

    @Override
    public void clickChild(int groupPosition) {
        Ord01201Resp group = groups.get(groupPosition);
        requestOrderDetail(group.getOrderId());
    }

    @Override
    public void payMoney(Ord01201Resp shop) {
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
    public void refund(Ord01201Resp shop) {

    }

    @Override
    public void salesReturn(Ord01201Resp shop) {

    }

    @Override
    public void takeGoods(final Ord01201Resp shop) {
        DialogAnotherUtil.showCustomAlertDialogWithMessage(this, null,
                "确认收货吗？", "确定", "取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DialogUtil.dismissDialog();
                        requestOrd017(shop);
                    }
                },
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DialogUtil.dismissDialog();
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

    @Override
    public void deleteOrder(final Ord01201Resp shop, final int groupPosition) {
        DialogAnotherUtil.showCustomAlertDialogWithMessage(this, null,
                "确认要删除订单吗？", "确定", "取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        requestCancelOrder(true, shop, groupPosition);
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

    private void requestCancelOrder(boolean isShowProgress, final Ord01201Resp shop, final int groupPosition){
        Ord010Req request = new Ord010Req();
        request.setOrderId(shop.getOrderId());
        getJsonService().requestOrd010(this, request, isShowProgress, new JsonService.CallBack<Ord010Resp>() {
            @Override
            public void oncallback(Ord010Resp ord010Resp) {
                ToastUtil.showCenter(AllOrdersActivity.this, "订单取消成功");
                pageNo=1;
                requestOrderList(true);
                listView.setSelectedGroup(groupPosition);
            }

            @Override
            public void onErro(AppHeader header) {

            }
        });
    }

    private void requestOrd017(final Ord01201Resp shop) {
        Ord017Req request = new Ord017Req();
        request.setOrderId(shop.getOrderId());
        getJsonService().requestOrd017(this, request, false, new JsonService.CallBack<Ord017Resp>() {
            @Override
            public void oncallback(Ord017Resp ord017Resp) {
                adapter.deleteData(shop);
                pageNo=1;
                requestOrderList(true);
            }

            @Override
            public void onErro(AppHeader header) {

            }
        });
    }

    @Override
    public void checkLogistics(Ord01201Resp shop) {
        Ord021Req req = new Ord021Req();
        req.setOrderId(shop.getOrderId());
        getJsonService().requestOrd021(this, req, false, new JsonService.CallBack<Ord021Resp>() {
            @Override
            public void oncallback(Ord021Resp ord021Resp) {
                if (ord021Resp != null) {
                    String url = ord021Resp.getUrl();
                    Bundle bundle = new Bundle();
                    if (StringUtils.isNotEmpty(url)) {
                        bundle.putString("url", url);
                    }
                    launch(LogisticsInfoActivity.class, bundle);
                } else {
                    ToastUtil.show(AllOrdersActivity.this, "获取物流信息失败");
                }
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
        }else {
            return super.onKeyDown(keyCode, event);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
