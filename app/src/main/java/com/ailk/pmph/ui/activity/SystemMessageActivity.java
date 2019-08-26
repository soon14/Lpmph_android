package com.ailk.pmph.ui.activity;


import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.ai.ecp.app.req.Staff117Req;
import com.ai.ecp.app.req.Staff119Req;
import com.ai.ecp.app.resp.Staff117Resp;
import com.ai.ecp.app.resp.Staff119Resp;
import com.ai.ecp.sys.dubbo.dto.MsgInsiteResDTO;
import com.ailk.butterfly.app.model.AppHeader;
import com.ailk.butterfly.app.model.JsonService;
import com.ailk.pmph.R;
import com.ailk.pmph.base.BaseActivity;
import com.ailk.pmph.utils.Constant;
import com.ailk.pmph.utils.DialogAnotherUtil;
import com.ailk.pmph.utils.DialogUtil;
import com.ailk.pmph.utils.ToastUtil;
import com.ailk.pmph.ui.adapter.SystemMsgListAdapter;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 类注释:
 * 项目名:pmph_android
 * 包名:com.ailk.pmph.ui.activity
 * 作者: Chrizz
 * 时间: 2016/6/13 13:16
 */
public class SystemMessageActivity extends BaseActivity implements View.OnClickListener{

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.lv_system_msg)
    PullToRefreshListView lvSystemMsg;

    private SystemMsgListAdapter adapter;
    private int pageNo = 1;
    private String readFlag;
    private List<MsgInsiteResDTO> resList;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_system_message;
    }

    @Override
    protected void onResume() {
        super.onResume();
        requestMgsList();
    }

    public void initView(){
        Bundle bundle = getIntent().getExtras();
        readFlag = bundle.getString("readFlag");
        lvSystemMsg.setMode(PullToRefreshBase.Mode.BOTH);
        lvSystemMsg.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                pageNo=1;
                requestMgsList();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                loadMoreData();
            }
        });
        lvSystemMsg.getRefreshableView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final int curPosition = position - 1;
                final MsgInsiteResDTO msg = adapter.getItem(curPosition);
                DialogAnotherUtil.showCustomAlertDialogWithMessage(SystemMessageActivity.this, null,
                        "确认删除这条消息吗？", "删除", "取消",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                deleteMsg(msg, curPosition);
                                DialogUtil.dismissDialog();
                            }
                        }, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DialogUtil.dismissDialog();
                            }
                        });
                return true;
            }
        });
    }

    @Override
    public void initData() {

    }

    private void requestMgsList(){
        Staff117Req req = new Staff117Req();
        req.setReadFlag(readFlag);
        req.setPageNo(pageNo);
        req.setPageSize(Constant.PAGE_SIZE);
        getJsonService().requestStaff117(this, req, true, new JsonService.CallBack<Staff117Resp>() {
            @Override
            public void oncallback(Staff117Resp staff117Resp) {
                resList = staff117Resp.getResList();
                adapter = new SystemMsgListAdapter(SystemMessageActivity.this, resList);
                lvSystemMsg.setAdapter(adapter);
                lvSystemMsg.onRefreshComplete();
            }

            @Override
            public void onErro(AppHeader header) {

            }
        });
    }

    private void loadMoreData(){
        Staff117Req req = new Staff117Req();
        req.setReadFlag(readFlag);
        req.setPageNo(++pageNo);
        req.setPageSize(Constant.PAGE_SIZE);
        getJsonService().requestStaff117(this, req, true, new JsonService.CallBack<Staff117Resp>() {
            @Override
            public void oncallback(Staff117Resp staff117Resp) {
                List<MsgInsiteResDTO> data = staff117Resp.getResList();
                if (data.size() == 0) {
                    ToastUtil.showCenter(SystemMessageActivity.this, R.string.toast_load_more_msg);
                    lvSystemMsg.onRefreshComplete();
                } else {
                    adapter.addData(data);
                    lvSystemMsg.onRefreshComplete();
                }
            }

            @Override
            public void onErro(AppHeader header) {

            }
        });
    }

    private void deleteMsg(final MsgInsiteResDTO msg, final int curPosition){
        Staff119Req req = new Staff119Req();
        req.setMsgInfoIds(String.valueOf(msg.getMsgInfoId()));
        getJsonService().requestStaff119(this, req, true, new JsonService.CallBack<Staff119Resp>() {
            @Override
            public void oncallback(Staff119Resp staff119Resp) {
                if (staff119Resp.isFlag()) {
                    adapter.removeData(curPosition);
                    ToastUtil.showIconToast(SystemMessageActivity.this, "删除成功");
                } else {
                    ToastUtil.showCenter(SystemMessageActivity.this,"删除失败");
                }
            }

            @Override
            public void onErro(AppHeader header) {

            }
        });
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
