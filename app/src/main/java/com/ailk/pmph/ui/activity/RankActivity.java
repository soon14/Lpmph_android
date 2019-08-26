package com.ailk.pmph.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.ai.ecp.app.req.Cms004Req;
import com.ai.ecp.app.req.Cms005Req;
import com.ai.ecp.app.resp.Cms004Resp;
import com.ai.ecp.app.resp.Cms005Resp;
import com.ai.ecp.app.resp.cms.FloorTabRespVO;
import com.ai.ecp.app.resp.gds.GdsBaseInfo;
import com.ailk.butterfly.app.model.AppHeader;
import com.ailk.butterfly.app.model.JsonService;
import com.ailk.pmph.AppContext;
import com.ailk.pmph.R;
import com.ailk.pmph.base.BaseActivity;
import com.ailk.pmph.ui.adapter.RankingRecyclerAdapter;
import com.ailk.pmph.utils.Constant;
import com.ailk.pmph.utils.ToastUtil;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshRecyclerView;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 类注释:
 * 项目名:pmph_android
 * 包名:com.ailk.pmph.ui.activity
 * 作者: Chrizz
 * 时间: 2016/9/29 18:57
 */

public class RankActivity extends BaseActivity implements View.OnClickListener{

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.iv_message)
    ImageView ivMessage;
    @BindView(R.id.tablayout)
    TabLayout mTabLayout;
    @BindView(R.id.rv_rank_list)
    PullToRefreshRecyclerView mRecyclerView;

    private RankingRecyclerAdapter mAdapter;
    private List<FloorTabRespVO> mTabList;
    private Long mTabId;
    private int mPageNo = 1;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_rank;
    }

    @Override
    public void initView() {
        mTabList = new ArrayList<>();
        mAdapter = new RankingRecyclerAdapter(this, new ArrayList<GdsBaseInfo>());
        mAdapter.clear();
        mRecyclerView.setMode(PullToRefreshBase.Mode.BOTH);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        ivMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppContext.isLogin) {
                    launch(MessageActivity.class);
                } else {
                    showLoginDialog(RankActivity.this);
                }
            }
        });
        requestCms004();
    }

    @Override
    public void initData() {
        mRecyclerView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<RecyclerView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                mPageNo = 1;
                mAdapter.clear();
                requsetCms005(mTabId, mPageNo);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                requsetCms005(mTabId, ++mPageNo);
            }
        });
        mAdapter.setOnItemClickListener(new RankingRecyclerAdapter.MyItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                GdsBaseInfo gdsBaseInfo = mAdapter.getItem(position - 1);
                Long gdsId = gdsBaseInfo.getGdsId();
                Long skuId = gdsBaseInfo.getSkuId();
                Bundle bundle = new Bundle();
                if (gdsId != null) {
                    bundle.putString(Constant.SHOP_GDS_ID, String.valueOf(gdsId));
                }
                if (skuId != null) {
                    bundle.putString(Constant.SHOP_SKU_ID, String.valueOf(skuId));
                }
                launch(ShopDetailActivity.class, bundle);
            }
        });
    }

    private void requestCms004() {
        Cms004Req req = new Cms004Req();
        getJsonService().requestCms004(this, req, false, new JsonService.CallBack<Cms004Resp>() {
            @Override
            public void oncallback(final Cms004Resp cms004Resp) {
                if (cms004Resp != null) {
                    mTabList.clear();
                    mTabList = cms004Resp.getTabList();
                    if (mTabList != null && mTabList.size() != 0) {
                        for (FloorTabRespVO respVO : mTabList) {
                            mTabLayout.addTab(mTabLayout.newTab().setText(respVO.getTabName()));
                            mTabLayout.setTabMode(TabLayout.MODE_FIXED);
                        }
                        mTabId = mTabList.get(0).getId();
                        mPageNo = 1;
                        mAdapter.clear();
                        requsetCms005(mTabId, mPageNo);
                        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                            @Override
                            public void onTabSelected(TabLayout.Tab tab) {
                                mTabId = mTabList.get(tab.getPosition()).getId();
                                mPageNo = 1;
                                mAdapter.clear();
                                requsetCms005(mTabId, mPageNo);
                            }

                            @Override
                            public void onTabUnselected(TabLayout.Tab tab) {

                            }

                            @Override
                            public void onTabReselected(TabLayout.Tab tab) {

                            }
                        });
                    }
                }

            }

            @Override
            public void onErro(AppHeader header) {

            }
        });
    }

    private void requsetCms005(Long tabId, int pageNo) {
        Cms005Req req = new Cms005Req();
        req.setTabId(tabId);
        req.setPageNo(pageNo);
        getJsonService().requestCms005(this, req, true, new JsonService.CallBack<Cms005Resp>() {
            @Override
            public void oncallback(Cms005Resp cms005Resp) {
                if (cms005Resp != null) {
                    List<GdsBaseInfo> list = cms005Resp.getGdsList();
                    if (list.size() != 0) {
                        mAdapter.addAll(list);
                    } else {
                        ToastUtil.show(RankActivity.this, R.string.toast_load_more_msg);
                    }
                    mRecyclerView.onRefreshComplete();
                }
            }

            @Override
            public void onErro(AppHeader header) {

            }
        });
    }

    @Override
    @OnClick({R.id.iv_back,R.id.iv_message})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.iv_message:
                if (AppContext.isLogin) {
                    launch(MessageActivity.class);
                } else {
                    showLoginDialog(this);
                }
                break;
        }
    }

}
