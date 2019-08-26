package com.ailk.pmph.ui.activity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ai.ecp.app.req.Gds013Req;
import com.ai.ecp.app.resp.Gds013Resp;
import com.ai.ecp.app.resp.gds.GdsEvalBaseInfo;
import com.ailk.butterfly.app.model.AppHeader;
import com.ailk.butterfly.app.model.JsonService;
import com.ailk.pmph.AppContext;
import com.ailk.pmph.BuildConfig;
import com.ailk.pmph.R;
import com.ailk.pmph.base.BaseActivity;
import com.ailk.pmph.utils.Constant;
import com.ailk.pmph.utils.ToastUtil;
import com.ailk.pmph.ui.view.RatingBarView;
import com.ailk.tool.GlideUtil;

import org.apache.commons.lang.StringUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 类注释:提交评价
 * 项目名:pmph_android
 * 包名:com.ailk.pmph.ui.activity
 * 作者: Chrizz
 * 时间: 2016/4/15 22:35
 */
public class CommitCommentActivity extends BaseActivity implements View.OnClickListener{

    @BindView(R.id.iv_back)           ImageView ivBack;
    @BindView(R.id.iv_gds_img)        ImageView ivGdsImg;
    @BindView(R.id.tv_gds_name)       TextView tvGdsName;
    @BindView(R.id.rb_score)          RatingBarView ratingBarView;
    @BindView(R.id.et_content)        EditText etContent;
    @BindView(R.id.tv_commit_comment) TextView tvCommit;

    private GdsEvalBaseInfo bean;
    private Integer ratingScore = 0;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_comment_commit;
    }

    @Override
    public void initView(){
        if (StringUtils.equals(BuildConfig.BUILD_TYPE, Constant.BUILD_TYPE_PMPH)) {
            tvCommit.setBackgroundColor(ContextCompat.getColor(this, R.color.orange_ff6a00));
        } else {
            tvCommit.setBackgroundColor(ContextCompat.getColor(this, R.color.red));
        }
        Bundle bundle = getIntent().getExtras();
        bean = (GdsEvalBaseInfo) bundle.getSerializable("gdsEvalBaseInfo");
        if (bean != null) {
            if (bean.getUrl() != null) {
                GlideUtil.loadImg(this, bean.getUrl(), ivGdsImg);
            }
            if (bean.getGdsName() != null) {
                tvGdsName.setText(bean.getGdsName());
            }
        }
    }

    @Override
    public void initData() {
        ratingBarView.setOnRatingListener(new RatingBarView.OnRatingListener() {
            @Override
            public void onRating(Object bindObject, Integer RatingScore) {
                ratingScore = RatingScore;
            }
        });
    }

    @Override
    @OnClick({R.id.iv_back, R.id.tv_commit_comment})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                launch(UnCommentActivity.class);
                onBackPressed();
                break;
            case R.id.tv_commit_comment:
                if (checkComment()) {
                    return;
                }
                requestGds013(bean);
                break;
        }
    }

    private boolean checkComment() {
        if (StringUtils.isEmpty(etContent.getText().toString())) {
            ToastUtil.showCenter(this, "请输入1-100字的评论");
            return true;
        }
        if (ratingScore.shortValue() == 0) {
            ToastUtil.showCenter(this, "请选择评分");
            return true;
        }
        return false;
    }

    private void requestGds013(GdsEvalBaseInfo bean){
        Gds013Req request = new Gds013Req();
        request.setGdsId(bean.getGdsId());
        request.setOrderId(bean.getOrderId());
        request.setOrderSubId(bean.getOrderSubId());
        request.setShopId(bean.getShopId());
        request.setSkuId(bean.getSkuId());
        request.setGdsName(bean.getGdsName());
        request.setScore(ratingScore.shortValue());
        request.setDetail(etContent.getText().toString());
        tvCommit.setText("正在评价...");
        tvCommit.setClickable(false);
        getJsonService().requestGds013(this, request, true, new JsonService.CallBack<Gds013Resp>() {
            @Override
            public void oncallback(Gds013Resp gds013Resp) {
                ToastUtil.showCenter(CommitCommentActivity.this, "评价成功");
                launch(UnCommentActivity.class);
                onBackPressed();
            }

            @Override
            public void onErro(AppHeader header) {

            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            launch(UnCommentActivity.class);
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
