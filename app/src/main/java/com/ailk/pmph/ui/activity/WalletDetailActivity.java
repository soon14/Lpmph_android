package com.ailk.pmph.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.ai.ecp.app.req.Staff104Req;
import com.ai.ecp.app.resp.AcctInfo;
import com.ai.ecp.app.resp.AcctTrade;
import com.ai.ecp.app.resp.Staff104Resp;
import com.ailk.butterfly.app.model.AppHeader;
import com.ailk.butterfly.app.model.JsonService;
import com.ailk.pmph.AppManager;
import com.ailk.pmph.R;
import com.ailk.pmph.base.BaseActivity;
import com.ailk.pmph.utils.Constant;
import com.ailk.pmph.utils.ToastUtil;
import com.ailk.pmph.ui.adapter.WalletDetailListAdapter;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.BindView;

/**
 * 类注释:我的钱包明细
 * 项目名:pmph_android
 * 包名:com.ailk.pmph.ui.activity
 * 作者: Chrizz
 * 时间: 2016/4/9 10:09
 */
public class WalletDetailActivity extends BaseActivity {

    @BindView(R.id.rl_empty_wallet)       RelativeLayout rlEmpty;
    @BindView(R.id.iv_back)               ImageView ivBack;
    @BindView(R.id.lv_wallet_detail)      PullToRefreshListView lvWalletList;
    @BindView(R.id.ll_unempty_layout)     LinearLayout llUnEmpty;

    private WalletDetailListAdapter adapter;
    private int pageNo=1;
    private AcctInfo acctInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
        setContentView(R.layout.activity_wallet_detail);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_wallet_detail;
    }

    @Override
    protected void onResume() {
        super.onResume();
        requestWalletDetails(true);
    }

    public void initView(){
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        Bundle bundle = getIntent().getExtras();
        acctInfo = (AcctInfo) bundle.get("acctInfo");
        lvWalletList.setMode(PullToRefreshBase.Mode.BOTH);
        lvWalletList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                pageNo=1;
                requestWalletDetails(true);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                loadMoreData();
            }
        });
    }

    private void requestWalletDetails(boolean isShowProgress){
        final Staff104Req request = new Staff104Req();
        request.setAcctType(acctInfo.getAcctType());
        request.setAdaptType(acctInfo.getAdaptType());
        request.setShopId(acctInfo.getShopId());
        request.setPageNo(pageNo);
        request.setPageSize(Constant.PAGE_SIZE);
        getJsonService().requestStaff104(this, request, isShowProgress, new JsonService.CallBack<Staff104Resp>() {
            @Override
            public void oncallback(Staff104Resp staff104Resp) {
                List<AcctTrade> resList = staff104Resp.getResList();
                if (resList.size()==0) {
                    setVisible(rlEmpty);
                    setGone(llUnEmpty);
                } else {
                    setVisible(llUnEmpty);
                    setGone(rlEmpty);
                    adapter = new WalletDetailListAdapter(WalletDetailActivity.this, resList);
                    lvWalletList.setAdapter(adapter);
                    lvWalletList.onRefreshComplete();
                }
            }

            @Override
            public void onErro(AppHeader header) {

            }
        });
    }

    private void loadMoreData(){
        Staff104Req request = new Staff104Req();
        request.setAcctType(acctInfo.getAcctType());
        request.setAdaptType(acctInfo.getAdaptType());
        request.setShopId(acctInfo.getShopId());
        request.setPageNo(++pageNo);
        request.setPageSize(Constant.PAGE_SIZE);
        getJsonService().requestStaff104(this, request, false, new JsonService.CallBack<Staff104Resp>() {
            @Override
            public void oncallback(Staff104Resp staff104Resp) {
                List<AcctTrade> data = staff104Resp.getResList();
                if (data.size() == 0) {
                    ToastUtil.showCenter(WalletDetailActivity.this, R.string.toast_load_more_msg);
                    lvWalletList.onRefreshComplete();
                } else {
                    adapter.addData(data);
                    lvWalletList.onRefreshComplete();
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
