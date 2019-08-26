package com.ailk.integral.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.ai.ecp.app.req.Ord108Req;
import com.ai.ecp.app.req.Ord109Req;
import com.ai.ecp.app.req.Ord110Req;
import com.ai.ecp.app.req.Pay102Req;
import com.ai.ecp.app.resp.Ord108Resp;
import com.ai.ecp.app.resp.Ord10901Resp;
import com.ai.ecp.app.resp.Ord10902Resp;
import com.ai.ecp.app.resp.Ord109Resp;
import com.ai.ecp.app.resp.Ord110Resp;
import com.ai.ecp.app.resp.Pay102Resp;
import com.ailk.butterfly.app.model.AppHeader;
import com.ailk.butterfly.app.model.InteJsonService;
import com.ailk.integral.adapter.InteOrderListAdapter;
import com.ailk.pmph.R;
import com.ailk.pmph.utils.Constant;
import com.ailk.pmph.utils.DialogAnotherUtil;
import com.ailk.pmph.utils.DialogUtil;
import com.ailk.pmph.utils.ToastUtil;
import com.ailk.pmph.base.BaseActivity;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * 类注释:
 * 项目名:pmph_android
 * 包名:com.ailk.integral.act
 * 作者: Chrizz
 * 时间: 2016/5/11 19:16
 */
public class InteOrdersActivity extends BaseActivity implements InteOrderListAdapter.ClickChildInterface,
        InteOrderListAdapter.PayMoneyInterface,InteOrderListAdapter.TakeGoodsInterface{

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.lv_inte_all_order_list)
    PullToRefreshExpandableListView lvOrderList;
    @BindView(R.id.rl_empty_layout)
    RelativeLayout emptyLayout;
    @BindView(R.id.ll_unempty_layout)
    LinearLayout unEmptyLayout;

    private ExpandableListView listView;
    private List<Ord10901Resp> groups = new ArrayList<>();
    private Map<String, List<Ord10902Resp>> children = new HashMap<>();
    private InteOrderListAdapter adapter;
    private int pageNo = 1;

    @Override
    protected int getContentViewId() {
        return R.layout.inte_activity_all_orders;
    }

    @Override
    protected void onResume() {
        super.onResume();
        pageNo=1;
        requestOrderList(true);
    }

    public void initView(){
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        lvOrderList.setPullToRefreshOverScrollEnabled(true);
        lvOrderList.setMode(PullToRefreshBase.Mode.BOTH);
        listView = lvOrderList.getRefreshableView();
        listView.setGroupIndicator(null);
        listView.setChildIndicator(null);
        listView.setDivider(null);
        lvOrderList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ExpandableListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ExpandableListView> refreshView) {
                pageNo=1;
                requestOrderList(true);
                lvOrderList.onRefreshComplete();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ExpandableListView> refreshView) {
                loadMoreData();
                lvOrderList.onRefreshComplete();
            }
        });
    }

    @Override
    public void initData() {

    }

    private void requestOrderList(boolean isShowProgress){
        Ord109Req request = new Ord109Req();
        request.setPageNo(pageNo);
        request.setPageSize(Constant.PAGE_SIZE);
        request.setStatus(Constant.ALL);
        request.setSiteId(2L);
        getInteJsonService().requestOrd109(this, request, isShowProgress, new InteJsonService.CallBack<Ord109Resp>() {
            @Override
            public void oncallback(Ord109Resp ord109Resp) {
                groups = ord109Resp.getOrd10901Resps();
                if (groups==null || groups.size() == 0)
                {
                    setVisible(emptyLayout);
                    setGone(unEmptyLayout);
                }
                else
                {
                    setGone(emptyLayout);
                    setVisible(unEmptyLayout);
                    for (int i = 0; i < groups.size(); i++) {
                        Ord10901Resp shop = groups.get(i);
                        children.put(shop.getOrderId(), shop.getOrd01102Resps());
                        adapter = new InteOrderListAdapter(InteOrdersActivity.this, groups, children);
                    }
                    adapter.setClickChildInterface(InteOrdersActivity.this);
                    adapter.setPayMoneyInterface(InteOrdersActivity.this);
                    adapter.setTakeGoodsInterface(InteOrdersActivity.this);
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

    private void loadMoreData() {
        Ord109Req request = new Ord109Req();
        request.setPageNo(++pageNo);
        request.setPageSize(Constant.PAGE_SIZE);
        request.setStatus(Constant.ALL);
        request.setSiteId(2L);
        getInteJsonService().requestOrd109(this, request, true, new InteJsonService.CallBack<Ord109Resp>() {
            @Override
            public void oncallback(Ord109Resp ord109Resp) {
                List<Ord10901Resp> data = ord109Resp.getOrd10901Resps();
                if (data == null) {
                    ToastUtil.showCenter(InteOrdersActivity.this, R.string.toast_load_more_msg);
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
        Ord10901Resp group = groups.get(groupPosition);
        requestOrderDetail(group.getOrderId());
    }

    private void requestOrderDetail(String orderId) {
        Ord108Req request = new Ord108Req();
        request.setOrderId(orderId);
        getInteJsonService().requestOrd108(this, request, true, new InteJsonService.CallBack<Ord108Resp>() {
            @Override
            public void oncallback(Ord108Resp ord108Resp) {
                Bundle extras = new Bundle();
                extras.putSerializable("ord108Resp", ord108Resp);
                launch(InteOrderDetailActivity.class, extras);
            }

            @Override
            public void onErro(AppHeader header) {

            }
        });
    }

    @Override
    public void payMoney(final Ord10901Resp shop) {
        Pay102Req request = new Pay102Req();
        request.setOrderId(shop.getOrderId());
        request.setShopId(shop.getShopId());
        getInteJsonService().requestPay102(this, request, true, new InteJsonService.CallBack<Pay102Resp>() {
            @Override
            public void oncallback(Pay102Resp pay102Resp) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("pay102Resp", pay102Resp);
                bundle.putLong("realMoney", shop.getRealMoney());
                launch(InteMorePayActivity.class,bundle);
            }

            @Override
            public void onErro(AppHeader header) {

            }
        });
    }

    @Override
    public void takeGoods(final Ord10901Resp shop) {
        DialogAnotherUtil.showCustomAlertDialogWithMessage(this, null,
                "确认收货吗？", "确定", "取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DialogUtil.dismissDialog();
                        requestConfirmGoods(shop);
                    }
                },
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DialogUtil.dismissDialog();
                    }
                });
    }

    private void requestConfirmGoods(Ord10901Resp shop){
        Ord110Req request = new Ord110Req();
        request.setOrderId(shop.getOrderId());
        getInteJsonService().requestOrd110(this, request, false, new InteJsonService.CallBack<Ord110Resp>() {
            @Override
            public void oncallback(Ord110Resp ord110Resp) {
                ToastUtil.showCenter(InteOrdersActivity.this, "您已确认收货");
                pageNo=1;
                requestOrderList(true);
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
