package com.ailk.pmph.ui.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ai.ecp.app.req.Staff117Req;
import com.ai.ecp.app.req.Staff118Req;
import com.ai.ecp.app.resp.Staff117Resp;
import com.ai.ecp.app.resp.Staff118Resp;
import com.ai.ecp.sys.dubbo.dto.MsgInsiteResDTO;
import com.ailk.butterfly.app.model.AppHeader;
import com.ailk.butterfly.app.model.JsonService;
import com.ailk.pmph.R;
import com.ailk.pmph.base.BaseActivity;
import com.ailk.pmph.utils.Constant;


import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 类注释:消息中心
 * 项目名:pmph_android
 * 包名:com.ailk.pmph.ui.activity
 * 作者: Chrizz
 * 时间: 2016/6/13 12:59
 */
public class MessageActivity extends BaseActivity implements View.OnClickListener{

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.rl_empty_layout)
    RelativeLayout emptyLayout;
    @BindView(R.id.ll_unempty_layout)
    LinearLayout unEmptyLayout;
    @BindView(R.id.tv_msg_num)
    TextView tvMsgNum;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.tv_content)
    TextView tvContent;

    private String readFlag = "";

    @Override
    protected int getContentViewId() {
        return R.layout.activity_message;
    }

    @Override
    public void initView() {
        getData();
    }

    @Override
    public void initData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void getData() {
        //查询消息数量
        Staff118Req staff118Req = new Staff118Req();
        readFlag = "00";
        staff118Req.setReadFlag(readFlag);
        getJsonService().requestStaff118(this, staff118Req, false, new JsonService.CallBack<Staff118Resp>() {
            @Override
            public void oncallback(Staff118Resp staff118Resp) {
                long msgNum = staff118Resp.getMsgCnt();
                if (msgNum > 0) {
                    readFlag = "00";
                    tvMsgNum.setText(String.valueOf(msgNum));
                } else {
                    readFlag = "10";
                    setGone(tvMsgNum);
                }
            }

            @Override
            public void onErro(AppHeader header) {

            }
        });
        //查询消息列表
        Staff117Req staff117Req = new Staff117Req();
        staff117Req.setReadFlag(readFlag);
        staff117Req.setPageNo(1);
        staff117Req.setPageSize(Constant.PAGE_SIZE);
        getJsonService().requestStaff117(this, staff117Req, true, new JsonService.CallBack<Staff117Resp>() {
            @Override
            public void oncallback(Staff117Resp staff117Resp) {
                List<MsgInsiteResDTO> resList = staff117Resp.getResList();
                if (resList.size() > 0) {
                    setVisible(unEmptyLayout);
                    setGone(emptyLayout);
                    MsgInsiteResDTO msg = resList.get(0);
                    SimpleDateFormat ft = new SimpleDateFormat("MM月dd日");
                    Timestamp ts = new Timestamp(msg.getRecTime().getTime());
                    tvDate.setText(ft.format(ts));
                    tvContent.setText(msg.getMsgContext());
                } else {
                    setVisible(emptyLayout);
                    setGone(unEmptyLayout);
                }
            }

            @Override
            public void onErro(AppHeader header) {

            }
        });
    }

    @Override
    @OnClick({R.id.iv_back,R.id.ll_unempty_layout})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.ll_unempty_layout:
                tvMsgNum.setVisibility(View.GONE);
                Bundle bundle = new Bundle();
                bundle.putString("readFlag", readFlag);
                launch(SystemMessageActivity.class, bundle);
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
