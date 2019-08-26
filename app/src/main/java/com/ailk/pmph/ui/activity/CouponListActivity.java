package com.ailk.pmph.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ai.ecp.app.req.Coup001Req;
import com.ai.ecp.app.resp.Coup001Resp;
import com.ailk.butterfly.app.model.AppHeader;
import com.ailk.butterfly.app.model.JsonService;
import com.ailk.pmph.BuildConfig;
import com.ailk.pmph.R;
import com.ailk.pmph.base.BaseActivity;
import com.ailk.pmph.utils.Constant;
import com.ailk.pmph.ui.fragment.OldCouponFragment;
import com.ailk.pmph.ui.fragment.UnUseCouponFragment;
import com.ailk.pmph.ui.fragment.UseCouponFragment;


import org.apache.commons.lang.StringUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 类注释:优惠券列表界面
 * 项目名:pmph_android
 * 包名:com.ailk.pmph.ui.activity
 * 作者: Chrizz
 * 时间: 2016/4/8 00:07
 */
public class CouponListActivity extends BaseActivity implements View.OnClickListener{

    @BindView(R.id.iv_back)           ImageView ivBack;
    @BindView(R.id.tv_un_use)         TextView tvUnUse;
    @BindView(R.id.un_use_line)       View unUseLine;
    @BindView(R.id.tv_old)            TextView tvOld;
    @BindView(R.id.old_line)          View oldLine;
    @BindView(R.id.tv_use)            TextView tvUse;
    @BindView(R.id.use_line)          View useLine;

    private UnUseCouponFragment unuseCouponFragment;
    private OldCouponFragment oldCouponFragment;
    private UseCouponFragment useCouponFragment;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_coupon_list;
    }

    @Override
    public void initView(){
        if (unuseCouponFragment == null) {
            unuseCouponFragment = new UnUseCouponFragment();
            if (!unuseCouponFragment.isAdded()) {
                addFragment(unuseCouponFragment);
            }
            showFragment(unuseCouponFragment);
        } else {
            if (unuseCouponFragment.isHidden()) {
                showFragment(unuseCouponFragment);
            }
        }
    }

    @Override
    public void initData(){
        requestCoup001(false);
    }

    @Override
    @OnClick({R.id.iv_back, R.id.tv_un_use, R.id.tv_old, R.id.tv_use})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_un_use:
                if (StringUtils.equals(BuildConfig.BUILD_TYPE, Constant.BUILD_TYPE_PMPH)) {
                    tvUnUse.setTextColor(ContextCompat.getColor(this, R.color.orange_ff6a00));
                } else {
                    tvUnUse.setTextColor(ContextCompat.getColor(this, R.color.red));
                }
                tvOld.setTextColor(ContextCompat.getColor(this, R.color.black));
                tvUse.setTextColor(ContextCompat.getColor(this, R.color.black));
                setVisible(unUseLine);
                setGone(oldLine,useLine);
                if (unuseCouponFragment == null) {
                    unuseCouponFragment = new UnUseCouponFragment();
                    if (!unuseCouponFragment.isAdded()) {
                        addFragment(unuseCouponFragment);
                    }
                    showFragment(unuseCouponFragment);
                } else {
                    if (unuseCouponFragment.isHidden()) {
                        showFragment(unuseCouponFragment);
                    }
                }
                break;
            case R.id.tv_old:
                tvUnUse.setTextColor(ContextCompat.getColor(this, R.color.black));
                if (StringUtils.equals(BuildConfig.BUILD_TYPE, Constant.BUILD_TYPE_PMPH)) {
                    tvOld.setTextColor(ContextCompat.getColor(this, R.color.orange_ff6a00));
                } else {
                    tvOld.setTextColor(ContextCompat.getColor(this, R.color.red));
                }
                tvUse.setTextColor(ContextCompat.getColor(this, R.color.black));
                setVisible(oldLine);
                setGone(unUseLine,useLine);
                if (oldCouponFragment == null) {
                    oldCouponFragment = new OldCouponFragment();
                    if (!oldCouponFragment.isAdded()) {
                        addFragment(oldCouponFragment);
                    }
                    showFragment(oldCouponFragment);
                } else {
                    if (oldCouponFragment.isHidden()) {
                        showFragment(oldCouponFragment);
                    }
                }
                break;
            case R.id.tv_use:
                tvUnUse.setTextColor(ContextCompat.getColor(this, R.color.black));
                tvOld.setTextColor(ContextCompat.getColor(this, R.color.black));
                if (StringUtils.equals(BuildConfig.BUILD_TYPE, Constant.BUILD_TYPE_PMPH)) {
                    tvUse.setTextColor(ContextCompat.getColor(this, R.color.orange_ff6a00));
                } else {
                    tvUse.setTextColor(ContextCompat.getColor(this, R.color.red));
                }
                setVisible(useLine);
                setGone(unUseLine,oldLine);
                if (useCouponFragment == null) {
                    useCouponFragment = new UseCouponFragment();
                    if (!useCouponFragment.isAdded()) {
                        addFragment(useCouponFragment);
                    }
                    showFragment(useCouponFragment);
                } else {
                    if (useCouponFragment.isHidden()) {
                        showFragment(useCouponFragment);
                    }
                }
                break;
        }
    }

    private void requestCoup001(boolean isShowProgress){
        Coup001Req request = new Coup001Req();
        request.setOpeType(Constant.OPE_TYPE_UN_USE);
        request.setPageNo(1);
        request.setPageSize(Constant.PAGE_SIZE);
        getJsonService().requestCoup001(this, request, isShowProgress, new JsonService.CallBack<Coup001Resp>() {
            @Override
            public void oncallback(Coup001Resp coup001Resp) {
                showText(coup001Resp);
            }

            @Override
            public void onErro(AppHeader header) {

            }
        });
    }

    private void showText(Coup001Resp coup001Resp){
        if (coup001Resp.getCoupCount_1() > 99) {
            tvUnUse.setText("未使用(99+)");
        } else {
            tvUnUse.setText("未使用("+coup001Resp.getCoupCount_1()+")");
        }

        if (coup001Resp.getCoupCount_0() > 99) {
            tvOld.setText("已过期(99+)");
        } else {
            tvOld.setText("已过期("+coup001Resp.getCoupCount_0()+")");
        }

        if (coup001Resp.getCoupCount_2() > 99) {
            tvUse.setText("已使用(99+)");
        } else {
            tvUse.setText("已使用("+coup001Resp.getCoupCount_2()+")");
        }
    }

    /**
     * 添加Fragment
     **/
    public void addFragment(Fragment fragment) {
        if (!fragment.isAdded()) {
            FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
            ft.add(R.id.main_detail, fragment);
            ft.commit();
        }
    }

    /**
     * 显示Fragment
     **/
    public void showFragment(Fragment fragment) {
        if (null == fragment.getArguments()) {
            Bundle args = new Bundle();
            fragment.setArguments(args);
        }
        FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
        if (unuseCouponFragment != null) {
            ft.hide(unuseCouponFragment);
        }
        if (oldCouponFragment != null) {
            ft.hide(oldCouponFragment);
        }
        if (useCouponFragment != null) {
            ft.hide(useCouponFragment);
        }
        ft.show(fragment);
        ft.commitAllowingStateLoss();
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
