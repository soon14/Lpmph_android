package com.ailk.pmph.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.ai.ecp.app.req.Cms009Req;
import com.ai.ecp.app.resp.Cms009Resp;
import com.ai.ecp.app.resp.cms.InfoRespVO;
import com.ailk.butterfly.app.model.AppHeader;
import com.ailk.butterfly.app.model.JsonService;
import com.ailk.pmph.R;
import com.ailk.pmph.base.BaseActivity;
import com.ailk.pmph.ui.adapter.BulletinListAdapter;
import com.ailk.pmph.utils.Constant;
import com.ailk.pmph.utils.ToastUtil;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.apache.commons.lang.StringUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 类注释:人卫快报
 * 项目名:pmph_android
 * 包名:com.ailk.pmph.ui.activity
 * 作者: Chrizz
 * 时间: 2016/9/30 9:58
 */

public class BulletinActivity extends BaseActivity implements View.OnClickListener, BulletinListAdapter.MyOnItemListener{

    @BindView(R.id.iv_back)
    ImageView mBackIv;
    @BindView(R.id.lv_bulletin_list)
    PullToRefreshListView mBulletinListLv;

    private BulletinListAdapter mAdapter;

    private int mPageNo = 1;
    private String mStatus = "1";
    private Long mSiteId = 1L;
    private int mPageSize = 20;
    private Cms009Req mRequest;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_bulletin;
    }

    @Override
    protected void onResume() {
        super.onResume();
        getBulletinList(mPageNo, mPageSize, mSiteId, mStatus);
    }

    @Override
    public void initView() {
        mRequest = new Cms009Req();
        mBulletinListLv.setMode(PullToRefreshBase.Mode.BOTH);
        mBulletinListLv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                mPageNo = 1;
                getBulletinList(mPageNo, mPageSize, mSiteId, mStatus);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                loadMoreData(mPageSize, mSiteId, mStatus);
            }
        });


    }

    @Override
    public void initData() {

    }

    private void getBulletinList(int pageNo, int pageSize, Long siteId, String status) {
        mRequest.setPageNo(pageNo);
        mRequest.setPageSize(pageSize);
        mRequest.setSiteId(siteId);
        mRequest.setStatus(status);
        getJsonService().requestCms009(this, mRequest, true, new JsonService.CallBack<Cms009Resp>() {
            @Override
            public void oncallback(Cms009Resp cms009Resp) {
                if (cms009Resp.getInfoList() != null && cms009Resp.getInfoList().size() != 0) {
                    mAdapter = new BulletinListAdapter(BulletinActivity.this, cms009Resp.getInfoList());
                    mAdapter.setOnItemListener(BulletinActivity.this);
                    mBulletinListLv.setAdapter(mAdapter);
                    mBulletinListLv.onRefreshComplete();
                }
            }

            @Override
            public void onErro(AppHeader header) {

            }
        });
    }

    private void loadMoreData(int pageSize, Long siteId, String status) {
        mRequest.setPageNo(++mPageNo);
        mRequest.setPageSize(pageSize);
        mRequest.setSiteId(siteId);
        mRequest.setStatus(status);
        getJsonService().requestCms009(this, mRequest, true, new JsonService.CallBack<Cms009Resp>() {
            @Override
            public void oncallback(Cms009Resp cms009Resp) {
                List<InfoRespVO> data = cms009Resp.getInfoList();
                if (data != null) {
                    if (data.size() == 0) {
                        ToastUtil.showCenter(BulletinActivity.this, R.string.toast_load_more_msg);
                        mBulletinListLv.onRefreshComplete();
                    } else {
                        mAdapter.addData(data);
                        mBulletinListLv.onRefreshComplete();
                    }
                }
            }

            @Override
            public void onErro(AppHeader header) {

            }
        });
    }

    @Override
    public void goToDetail(InfoRespVO infoRespVO) {
        Bundle bundle = new Bundle();
        if (StringUtils.isNotEmpty(infoRespVO.getClickUrl())) {
            bundle.putString("url", StringUtils.substringAfterLast(infoRespVO.getClickUrl(),"url="));
        }
        launch(PromotionActivity.class, bundle);
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

}
