package com.ailk.pmph.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ai.ecp.app.req.Gds015Req;
import com.ai.ecp.app.req.Gds016Req;
import com.ai.ecp.app.resp.Gds015Resp;
import com.ai.ecp.app.resp.Gds016Resp;
import com.ai.ecp.app.resp.gds.GdsEvalReplyRespBaseInfo;
import com.ai.ecp.app.resp.gds.GdsEvalRespBaseInfo;
import com.ailk.butterfly.app.model.AppHeader;
import com.ailk.butterfly.app.model.JsonService;
import com.ailk.pmph.R;
import com.ailk.pmph.base.BaseActivity;
import com.ailk.pmph.utils.Constant;
import com.ailk.pmph.utils.PrefUtility;
import com.ailk.pmph.utils.ToastUtil;
import com.ailk.pmph.ui.adapter.ReplyListAdapter;
import com.ailk.pmph.ui.fragment.CommentListFragment;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 类注释:回复
 * 项目名:pmph_android
 * 包名:com.ailk.pmph.ui.activity
 * 作者: Chrizz
 * 时间: 2016/5/3 22:36
 */
public class ReplyActivity extends BaseActivity implements View.OnClickListener, ReplyListAdapter.ReplyInterface{

    @BindView(R.id.iv_back)               ImageView ivBack;
    @BindView(R.id.lv_reply)              PullToRefreshListView lvReply;
    @BindView(R.id.et_reply)              EditText etReply;
    @BindView(R.id.tv_send)               TextView tvSend;
    @BindView(R.id.ll_empty_layout)       RelativeLayout llEmptyLayout;
    @BindView(R.id.ll_unempty_layout)     LinearLayout llUnEmptyLayout;

    private int pageNumber = 1;
    private GdsEvalRespBaseInfo gdsEvalRespBaseInfo;
    private GdsEvalReplyRespBaseInfo gdsEvalReplyRespBaseInfo = new GdsEvalReplyRespBaseInfo();
    private List<GdsEvalReplyRespBaseInfo> evalReplyInfoList = new ArrayList<>();
    private ReplyListAdapter adapter;
    private long lastRequest = 0;
    private static final int CLICK_INTERVAL = 1000;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_reply;
    }

    @Override
    protected void onResume() {
        super.onResume();
        requestReplyList(true);
    }

    public void initView() {
        Bundle bundle = getIntent().getExtras();
        gdsEvalRespBaseInfo = (GdsEvalRespBaseInfo) bundle.get("gdsEvalRespBaseInfo");
        lvReply.setMode(PullToRefreshBase.Mode.BOTH);
        lvReply.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                pageNumber=1;
                requestReplyList(true);
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

    private void requestReplyList(boolean isShowProgress) {
        Gds015Req request = new Gds015Req();
        request.setEvalId(gdsEvalRespBaseInfo.getId());
        request.setPageNumber(pageNumber);
        request.setPageSize(Constant.PAGE_SIZE);
        getJsonService().requestGds015(this, request, isShowProgress, new JsonService.CallBack<Gds015Resp>() {
            @Override
            public void oncallback(Gds015Resp gds015Resp) {
                evalReplyInfoList = gds015Resp.getEvalReplyInfoList();
                if (evalReplyInfoList.size()==0) {
                    setVisible(llEmptyLayout);
                    setGone(llUnEmptyLayout);
                } else {
                    setGone(llEmptyLayout);
                    setVisible(llUnEmptyLayout);
                    adapter = new ReplyListAdapter(ReplyActivity.this, evalReplyInfoList);
                    adapter.setReplyInterface(ReplyActivity.this);
                    lvReply.setAdapter(adapter);
                    lvReply.onRefreshComplete();
                }
            }

            @Override
            public void onErro(AppHeader header) {

            }
        });
    }

    private void loadMoreData() {
        Gds015Req request = new Gds015Req();
        request.setEvalId(gdsEvalRespBaseInfo.getId());
        request.setPageNumber(++pageNumber);
        request.setPageSize(Constant.PAGE_SIZE);
        getJsonService().requestGds015(this, request, false, new JsonService.CallBack<Gds015Resp>() {
            @Override
            public void oncallback(Gds015Resp gds015Resp) {
                List<GdsEvalReplyRespBaseInfo> data = gds015Resp.getEvalReplyInfoList();
                if (data.size() == 0) {
                    ToastUtil.showCenter(ReplyActivity.this, R.string.toast_load_more_msg);
                    lvReply.onRefreshComplete();
                } else {
                    adapter.addData(data);
                    lvReply.onRefreshComplete();
                }
            }

            @Override
            public void onErro(AppHeader header) {

            }
        });
    }

    private void replyContent(GdsEvalReplyRespBaseInfo info) {
        Gds016Req req = new Gds016Req();
        req.setEvalId(info.getEvalId());
        req.setReplyId(info.getReplyId());
        String staffName = info.getStaffName();
        String staffCode = PrefUtility.get("staffCode", "");
        if (StringUtils.equals(staffName, staffCode)) {
            req.setContent("回复"+info.getStaffName()+":"+etReply.getText().toString());
        } else {
            req.setContent("回复"+StringUtils.replace(staffName, StringUtils.substring(staffName, 1, staffName.length() - 1), "***")+":"+etReply.getText().toString());
        }
        tvSend.setClickable(false);
        tvSend.setBackground(ContextCompat.getDrawable(this, R.drawable.shape_round_gray));
        getJsonService().requestGds016(this, req, true, new JsonService.CallBack<Gds016Resp>() {
            @Override
            public void oncallback(Gds016Resp gds016Resp) {
                etReply.setText("");
                etReply.setHint("");
                requestReplyList(true);
                tvSend.setClickable(true);
                tvSend.setBackground(ContextCompat.getDrawable(ReplyActivity.this, R.drawable.shape_round_button));
                Intent intent = new Intent(CommentListFragment.REFRESH_LIST);
                sendBroadcast(intent);
            }

            @Override
            public void onErro(AppHeader header) {

            }
        });
    }

    private void commitReply() {
        Gds016Req req = new Gds016Req();
        req.setEvalId(gdsEvalRespBaseInfo.getId());
        req.setReplyId(-1L);
        req.setContent(etReply.getText().toString());
        tvSend.setClickable(false);
        tvSend.setBackground(ContextCompat.getDrawable(this, R.drawable.shape_round_gray));
        getJsonService().requestGds016(this, req, true, new JsonService.CallBack<Gds016Resp>() {
            @Override
            public void oncallback(Gds016Resp gds016Resp) {
                etReply.setText("");
                etReply.setHint("");
                requestReplyList(true);
                tvSend.setClickable(true);
                tvSend.setBackground(ContextCompat.getDrawable(ReplyActivity.this, R.drawable.shape_round_button));
                Intent intent = new Intent(CommentListFragment.REFRESH_LIST);
                sendBroadcast(intent);
            }

            @Override
            public void onErro(AppHeader header) {

            }
        });
    }

    @Override
    public void reply(GdsEvalReplyRespBaseInfo info) {
        gdsEvalReplyRespBaseInfo = info;
        String staffName = info.getStaffName();
        String staffCode = PrefUtility.get("staffCode", "");
        if (StringUtils.equals(staffName, staffCode)) {
            etReply.setHint("回复" + info.getStaffName() +":");
        } else {
            etReply.setHint("回复" + StringUtils.replace(staffName, StringUtils.substring(staffName, 1, staffName.length() - 1), "***") +":");
        }
    }

    @Override
    @OnClick({R.id.iv_back, R.id.tv_send})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_send:
                if (System.currentTimeMillis() - lastRequest < CLICK_INTERVAL) {
                    return;
                }
                lastRequest = System.currentTimeMillis();
                if (StringUtils.isEmpty(etReply.getText().toString())) {
                    ToastUtil.showCenter(this, "请输入回复内容");
                    return;
                } else {
                    if (StringUtils.contains(etReply.getHint().toString(), "回复")) {
                        replyContent(gdsEvalReplyRespBaseInfo);
                    } else {
                        commitReply();
                    }
                }
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onBackPressed();
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
