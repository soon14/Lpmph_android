package com.ailk.pmph.ui.fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.ai.ecp.app.req.Staff113Req;
import com.ai.ecp.app.resp.Staff113Resp;
import com.ai.ecp.app.resp.staff.CollectionShopInfo;
import com.ailk.butterfly.app.model.AppHeader;
import com.ailk.butterfly.app.model.JsonService;
import com.ailk.pmph.R;
import com.ailk.pmph.base.BaseFragment;
import com.ailk.pmph.utils.ToastUtil;
import com.ailk.pmph.thirdstore.activity.StoreActivity;
import com.ailk.pmph.thirdstore.adapter.StoreCollectAdapter;
import com.ailk.pmph.utils.Constant;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.BindView;

/**
 * 店铺收藏
 */
public class StoreCollectFragment extends BaseFragment {

    @BindView(R.id.listView)
    PullToRefreshListView listView;
    @BindView(R.id.no_collect)
    ImageView noCollect;

    private StoreCollectAdapter adapter;
    private int pageNumber = 1;
    private Staff113Req staff113Req;
    public static final String REFRESH = "refresh";

    public StoreCollectFragment() {
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_store_collect;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView(View view) {
        adapter = new StoreCollectAdapter(getActivity(),new ArrayList<CollectionShopInfo>());
        listView.setAdapter(adapter);
        staff113Req = new Staff113Req();
        loadData();
        event();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(REFRESH);
        getActivity().registerReceiver(receiver, intentFilter);
    }

    public BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(REFRESH)) {
                adapter.clear();
                pageNumber=1;
                loadData();
            }
        }
    };

    private void event() {
        //监听收藏的店铺还剩多少
        adapter.setListener(new StoreCollectAdapter.GetCollectNumListener() {
            @Override
            public void onCollectNum(int num) {
                if (0 == num) {
                    setGone(listView);
                    setVisible(noCollect);
                }
            }
        });

        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                adapter.clear();
                pageNumber=1;
                loadData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                loadData();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CollectionShopInfo collectionShopInfo = adapter.getItem(position-1);
                Intent intent = new Intent(getActivity(), StoreActivity.class);
                intent.putExtra(Constant.STORE_ID, String.valueOf(collectionShopInfo.getId()));//统一传入String
                launch(intent);
            }
        });
    }

    private void loadData() {
        staff113Req.setPageNo(pageNumber++);
        staff113Req.setPageSize(Constant.PAGE_SIZE);
        getJsonService().requestStaff113(getActivity(), staff113Req, false, new JsonService.CallBack<Staff113Resp>() {
            @Override
            public void oncallback(Staff113Resp staff113Resp) {
                if (null != staff113Resp && 0 != staff113Resp.getResList().size()) {
                    adapter.addAll(staff113Resp.getResList());
                    setVisible(listView);
                    setGone(noCollect);
                    listView.onRefreshComplete();
                }else {
                    if (2 == pageNumber) {
                        setGone(listView);
                        setVisible(noCollect);
                    }else {
                        ToastUtil.show(getActivity(),R.string.toast_load_more_msg);
                    }
                }
                listView.onRefreshComplete();
            }

            @Override
            public void onErro(AppHeader header) {
                listView.onRefreshComplete();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getActivity().unregisterReceiver(receiver);
    }
}
