package com.ailk.integral.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.ai.ecp.app.req.Ord109Req;
import com.ai.ecp.app.resp.Ord10901Resp;
import com.ai.ecp.app.resp.Ord10902Resp;
import com.ai.ecp.app.resp.Ord109Resp;
import com.ailk.butterfly.app.model.AppHeader;
import com.ailk.butterfly.app.model.InteJsonService;
import com.ailk.integral.adapter.InteExchangeListAdapter;
import com.ailk.pmph.R;
import com.ailk.pmph.utils.Constant;
import com.ailk.pmph.utils.OrderStatus;
import com.ailk.pmph.utils.ToastUtil;
import com.ailk.pmph.base.BaseActivity;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshExpandableListView;

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
 * 时间: 2016/5/16 14:10
 */
public class InteExchangeRecordActivity extends BaseActivity implements View.OnClickListener,InteExchangeListAdapter.ClickChildInterface{

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.lv_inte_exchange_list)
    PullToRefreshExpandableListView lvExchange;
    @BindView(R.id.rl_empty_layout)
    RelativeLayout emptyLayout;
    @BindView(R.id.ll_unempty_layout)
    LinearLayout unEmptyLayout;

    private ExpandableListView listView;
    private InteExchangeListAdapter adapter;
    private int pageNo = 1;
    private List<Ord10901Resp> groups = new ArrayList<>();
    private Map<String, List<Ord10902Resp>> children = new HashMap<>();

    @Override
    protected int getContentViewId() {
        return R.layout.inte_activity_exchange;
    }

    @Override
    protected void onResume() {
        super.onResume();
        requestExchangeList(true);
    }

    public void initView(){
        lvExchange.setPullToRefreshOverScrollEnabled(true);
        lvExchange.setMode(PullToRefreshBase.Mode.BOTH);
        listView = lvExchange.getRefreshableView();
        listView.setGroupIndicator(null);
        listView.setChildIndicator(null);
        listView.setDivider(null);
    }

    public void initData(){
        lvExchange.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ExpandableListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ExpandableListView> refreshView) {
                pageNo=1;
                requestExchangeList(true);
                lvExchange.onRefreshComplete();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ExpandableListView> refreshView) {
                loadMoreData();
                lvExchange.onRefreshComplete();
            }
        });
    }

    private void requestExchangeList(boolean isShowProgress){
        Ord109Req req = new Ord109Req();
        req.setPageNo(pageNo);
        req.setPageSize(Constant.PAGE_SIZE);
        req.setSiteId(2L);
        req.setStatus(OrderStatus.TAKEOVER);
        getInteJsonService().requestOrd109(this, req, isShowProgress, new InteJsonService.CallBack<Ord109Resp>() {
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
                    setVisible(unEmptyLayout);
                    setGone(emptyLayout);
                    for (int i =0; i < groups.size(); i++) {
                        Ord10901Resp shop = groups.get(i);
                        children.put(shop.getOrderId(), shop.getOrd01102Resps());
                        adapter = new InteExchangeListAdapter(InteExchangeRecordActivity.this, groups, children);
                    }
                    adapter.setClickChildInterface(InteExchangeRecordActivity.this);
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

    private void loadMoreData(){
        Ord109Req req = new Ord109Req();
        req.setPageNo(++pageNo);
        req.setPageSize(Constant.PAGE_SIZE);
        req.setSiteId(2L);
        req.setStatus(OrderStatus.TAKEOVER);
        getInteJsonService().requestOrd109(this, req, false, new InteJsonService.CallBack<Ord109Resp>() {
            @Override
            public void oncallback(Ord109Resp ord109Resp) {
                List<Ord10901Resp> data = ord109Resp.getOrd10901Resps();
                if (data==null) {
                    ToastUtil.showCenter(InteExchangeRecordActivity.this, R.string.toast_load_more_msg);
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
    public void clickChild(Ord10902Resp product) {
        Bundle bundle = new Bundle();
        bundle.putString(Constant.SHOP_GDS_ID, String.valueOf(product.getGdsId()));
        bundle.putString(Constant.SHOP_SKU_ID, String.valueOf(product.getSkuId()));
        launch(InteShopDetailActivity.class, bundle);
    }

    @Override
    @OnClick({R.id.iv_back})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
