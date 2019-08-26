package com.ailk.pmph.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.ai.ecp.app.req.Staff103Req;
import com.ai.ecp.app.resp.AcctInfo;
import com.ai.ecp.app.resp.Staff103Resp;
import com.ailk.butterfly.app.model.AppHeader;
import com.ailk.butterfly.app.model.JsonService;
import com.ailk.pmph.AppManager;
import com.ailk.pmph.R;
import com.ailk.pmph.base.BaseActivity;
import com.ailk.pmph.ui.adapter.WalletListAdapter;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.BindView;

/**
 * 类注释:我的钱包
 * 项目名:pmph_android
 * 包名:com.ailk.pmph.ui.activity
 * 作者: Chrizz
 * 时间: 2016/4/9 9:45
 */
public class WalletActivity extends BaseActivity {

    @BindView(R.id.rl_empty_wallet)       RelativeLayout rlEmpty;
    @BindView(R.id.iv_back)               ImageView ivBack;
    @BindView(R.id.lv_wallet_list)        ListView lvWalletList;
    @BindView(R.id.ll_unempty_layout)     LinearLayout llUnEmpty;

    private WalletListAdapter adapter;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_wallet;
    }

    public void initView(){
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        requestWalletList(true);
    }

    private void requestWalletList(boolean isShowProgress){
        Staff103Req request = new Staff103Req();
        getJsonService().requestStaff103(this, request, isShowProgress, new JsonService.CallBack<Staff103Resp>() {
            @Override
            public void oncallback(Staff103Resp staff103Resp) {
                List<AcctInfo> resList = staff103Resp.getResList();
                if (resList.size()==0) {
                    setVisible(rlEmpty);
                    setGone(llUnEmpty);
                } else {
                    setVisible(llUnEmpty);
                    setGone(rlEmpty);
                    adapter = new WalletListAdapter(WalletActivity.this, resList);
                    lvWalletList.setAdapter(adapter);
                }
            }

            @Override
            public void onErro(AppHeader header) {

            }
        });
    }

    @Override
    public void initData() {

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
