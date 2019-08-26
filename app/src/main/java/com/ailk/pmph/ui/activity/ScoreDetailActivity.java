package com.ailk.pmph.ui.activity;

import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.ai.ecp.app.req.Staff102Req;
import com.ai.ecp.app.resp.ScoreTrade;
import com.ai.ecp.app.resp.Staff102Resp;
import com.ailk.butterfly.app.model.AppHeader;
import com.ailk.butterfly.app.model.JsonService;
import com.ailk.pmph.R;
import com.ailk.pmph.base.BaseActivity;
import com.ailk.pmph.utils.Constant;
import com.ailk.pmph.utils.ToastUtil;
import com.ailk.pmph.ui.adapter.ScoreDetailListAdapter;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.List;

import butterknife.BindView;

/**
 * 类注释:我的积分明细
 * 项目名:pmph_android
 * 包名:com.ailk.pmph.ui.activity
 * 作者: Chrizz
 * 时间: 2016/4/8 16:24
 */
public class ScoreDetailActivity extends BaseActivity {

    @BindView(R.id.iv_back)                   ImageView ivBack;
    @BindView(R.id.lv_score_detail)           PullToRefreshListView lvScoreDetailList;
    @BindView(R.id.rl_empty_score_detail)     RelativeLayout rlEmpty;
    @BindView(R.id.ll_unempty_layout)         LinearLayout llUnEmpty;

    private ScoreDetailListAdapter adapter;
    private int pageNo=1;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_score_detail;
    }

    @Override
    protected void onResume() {
        super.onResume();
        requestScoreDetails(true);
    }

    public void initView(){
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        lvScoreDetailList.setMode(PullToRefreshBase.Mode.BOTH);
        lvScoreDetailList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                pageNo=1;
                requestScoreDetails(true);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                loadMoreData();
            }
        });
    }

    @Override
    public void initData() {

    }

    private void requestScoreDetails(boolean isShowProgress){
        Staff102Req request = new Staff102Req();
        request.setPageNo(pageNo);
        request.setPageSize(Constant.PAGE_SIZE);
        getJsonService().requestStaff102(this, request, isShowProgress, new JsonService.CallBack<Staff102Resp>() {
            @Override
            public void oncallback(Staff102Resp staff102Resp) {
                List<ScoreTrade> resList = staff102Resp.getResList();
                if (resList.size()==0) {
                    setVisible(rlEmpty);
                    setGone(llUnEmpty);
                } else {
                    setGone(rlEmpty);
                    setVisible(llUnEmpty);
                    adapter = new ScoreDetailListAdapter(ScoreDetailActivity.this, resList);
                    lvScoreDetailList.setAdapter(adapter);
                    lvScoreDetailList.onRefreshComplete();
                }
            }

            @Override
            public void onErro(AppHeader header) {

            }
        });
    }

    private void loadMoreData(){
        Staff102Req request = new Staff102Req();
        request.setPageNo(++pageNo);
        request.setPageSize(Constant.PAGE_SIZE);
        getJsonService().requestStaff102(this, request, false, new JsonService.CallBack<Staff102Resp>() {
            @Override
            public void oncallback(Staff102Resp staff102Resp) {
                List<ScoreTrade> data = staff102Resp.getResList();
                if (data.size() == 0) {
                    ToastUtil.showCenter(ScoreDetailActivity.this, R.string.toast_load_more_msg);
                    lvScoreDetailList.onRefreshComplete();
                } else {
                    adapter.addData(data);
                    lvScoreDetailList.onRefreshComplete();
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
