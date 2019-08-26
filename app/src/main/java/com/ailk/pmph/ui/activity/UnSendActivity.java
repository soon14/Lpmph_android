package com.ailk.pmph.ui.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ai.ecp.app.req.Ord011Req;
import com.ai.ecp.app.req.Ord012Req;
import com.ai.ecp.app.resp.Ord011Resp;
import com.ai.ecp.app.resp.Ord01201Resp;
import com.ai.ecp.app.resp.Ord01202Resp;
import com.ai.ecp.app.resp.Ord012Resp;
import com.ailk.butterfly.app.model.AppHeader;
import com.ailk.butterfly.app.model.JsonService;
import com.ailk.pmph.R;
import com.ailk.pmph.base.BaseActivity;
import com.ailk.pmph.utils.Constant;
import com.ailk.pmph.utils.ToastUtil;
import com.ailk.pmph.ui.adapter.OrderListAdapter;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * 类注释:待发货
 * 项目名:pmph_android
 * 包名:com.ailk.pmph.ui.activity
 * 作者: Chrizz
 * 时间: 2016/4/10 15:56
 */
public class UnSendActivity extends BaseActivity implements OrderListAdapter.ClickChildInterface,
        OrderListAdapter.RefundInterface{

    @BindView(R.id.iv_back)                   ImageView ivBack;
    @BindView(R.id.tv_title)                  TextView tvTitle;
    @BindView(R.id.lv_all_order_list)         PullToRefreshExpandableListView lvUnSendOrderList;
    @BindView(R.id.rl_empty_layout)           RelativeLayout emptyLayout;
    @BindView(R.id.ll_unempty_layout)         LinearLayout unEmptyLayout;

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

    public void initView() {
        tvTitle.setText("待发货");
        lvUnSendOrderList.setPullToRefreshOverScrollEnabled(true);
        lvUnSendOrderList.setMode(PullToRefreshBase.Mode.BOTH);
        listView = lvUnSendOrderList.getRefreshableView();
        listView.setGroupIndicator(null);
        listView.setChildIndicator(null);
        listView.setDivider(null);
    }

    private void requestOrderList(boolean isShowProgress){
        Ord012Req request = new Ord012Req();
        request.setPageNo(pageNo);
        request.setPageSize(Constant.PAGE_SIZE);
        request.setStatus(Constant.UN_SEND);
        request.setSiteId(1L);
        getJsonService().requestOrd012(this, request, isShowProgress, new JsonService.CallBack<Ord012Resp>() {
            @Override
            public void oncallback(Ord012Resp ord012Resp) {
                groups = ord012Resp.getOrd01201Resps();
                if (groups==null || groups.size() == 0) {
                    setVisible(emptyLayout);
                    setGone(unEmptyLayout);
                } else {
                    setVisible(unEmptyLayout);
                    setGone(emptyLayout);
                    for (int i = 0; i < groups.size(); i++) {
                        Ord01201Resp shop = groups.get(i);
                        children.put(shop.getOrderId(), shop.getOrd01102Resps());
                        adapter = new OrderListAdapter(UnSendActivity.this, groups, children);
                    }
                    adapter.setClickChildInterface(UnSendActivity.this);
                    adapter.setRefundInterface(UnSendActivity.this);
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

    public void initData() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        lvUnSendOrderList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ExpandableListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ExpandableListView> refreshView) {
                pageNo=1;
                requestOrderList(true);
                lvUnSendOrderList.onRefreshComplete();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ExpandableListView> refreshView) {
                loadMoreData();
                lvUnSendOrderList.onRefreshComplete();
            }
        });
    }

    private void loadMoreData() {
        Ord012Req request = new Ord012Req();
        request.setPageNo(++pageNo);
        request.setPageSize(Constant.PAGE_SIZE);
        request.setStatus(Constant.UN_SEND);
        request.setSiteId(1L);
        getJsonService().requestOrd012(this, request, true, new JsonService.CallBack<Ord012Resp>() {
            @Override
            public void oncallback(Ord012Resp ord012Resp) {
                List<Ord01201Resp> data = ord012Resp.getOrd01201Resps();
                if (data==null) {
                    ToastUtil.showCenter(UnSendActivity.this, R.string.toast_load_more_msg);
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
    public void clickChild(int groupPosition) {
        Ord01201Resp group = groups.get(groupPosition);
        requestOrderDetail(group.getOrderId());
    }

    @Override
    public void refund(Ord01201Resp shop) {

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
