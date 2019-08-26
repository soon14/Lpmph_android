package com.ailk.pmph.ui.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ai.ecp.app.req.Staff102Req;
import com.ai.ecp.app.resp.ScoreTrade;
import com.ai.ecp.app.resp.Staff102Resp;
import com.ailk.butterfly.app.model.AppHeader;
import com.ailk.butterfly.app.model.JsonService;
import com.ailk.pmph.AppManager;
import com.ailk.pmph.R;
import com.ailk.pmph.base.BaseActivity;
import com.ailk.pmph.ui.adapter.ScoreDetailListAdapter;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * 类注释:我的积分
 * 项目名:pmph_android
 * 包名:com.ailk.pmph.ui.activity
 * 作者: Chrizz
 * 时间: 2016/4/8 15:50
 */
public class ScoreActivity extends BaseActivity implements View.OnClickListener{

    @BindView(R.id.iv_back)                   ImageView ivBack;
    @BindView(R.id.tv_total_score)            TextView tvScore;
    @BindView(R.id.ll_more_score_layout)      LinearLayout llMoreScoreLayout;
    @BindView(R.id.lv_score)                  ListView lvScore;
    @BindView(R.id.rl_empty_score)            RelativeLayout rlEmpty;
    @BindView(R.id.ll_unempty_score)          LinearLayout llUnEmpty;

    private ScoreDetailListAdapter adapter;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_score;
    }

    public void initView(){
        requestScore(true);
    }

    @Override
    public void initData() {

    }

    private void requestScore(boolean isShowProgress){
        Staff102Req request = new Staff102Req();
        request.setPageNo(1);
        request.setPageSize(4);
        getJsonService().requestStaff102(this, request, isShowProgress, new JsonService.CallBack<Staff102Resp>() {
            @Override
            public void oncallback(Staff102Resp staff102Resp) {
                List<ScoreTrade> resList = staff102Resp.getResList();
                if (resList.size()==0) {
                    tvScore.setText("0");
                    setVisible(rlEmpty);
                    setGone(llUnEmpty);
                } else {
                    tvScore.setText(String.valueOf(staff102Resp.getTotalScore()));
                    setGone(rlEmpty);
                    setVisible(llUnEmpty);
                    adapter = new ScoreDetailListAdapter(ScoreActivity.this, staff102Resp.getResList());
                    lvScore.setAdapter(adapter);
                }
            }

            @Override
            public void onErro(AppHeader header) {

            }
        });
    }

    @Override
    @OnClick({R.id.iv_back, R.id.ll_more_score_layout})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.ll_more_score_layout:
                launch(ScoreDetailActivity.class);
                break;
        }
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
