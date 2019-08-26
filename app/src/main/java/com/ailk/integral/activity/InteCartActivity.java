package com.ailk.integral.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;

import com.ailk.integral.fragment.InteCartFragment;
import com.ailk.pmph.R;
import com.ailk.pmph.base.BaseActivity;

/**
 * 类注释:
 * 项目名:pmph_android
 * 包名:com.ailk.integral.act
 * 作者: Chrizz
 * 时间: 2016/5/19 21:10
 */
public class InteCartActivity extends BaseActivity {

    private InteCartFragment cartFragment;

    @Override
    protected int getContentViewId() {
        return R.layout.inte_activity_cart;
    }

    @Override
    public void initView() {
        if (cartFragment == null) {
            cartFragment = InteCartFragment.newInstance();
            addFragment(cartFragment);
            cartFragment.getArguments().putString("fromInteShopDetail", "fromInteShopDetail");
            showFragment(cartFragment);
        } else {
            showFragment(cartFragment);
        }
    }

    @Override
    public void initData() {

    }

    /**
     * 添加Fragment
     **/
    public void addFragment(Fragment fragment) {
        if (!fragment.isAdded()) {
            FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
            ft.add(R.id.fl_content, fragment);
            ft.commit();
        }
    }

    /**
     * 显示Fragment
     **/
    public void showFragment(Fragment fragment) {
        FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
        if (cartFragment != null) {
            ft.hide(cartFragment);
        }
        ft.show(fragment);
        ft.commitAllowingStateLoss();
    }

    @Override
    public void onAttachFragment(android.app.Fragment fragment) {
        super.onAttachFragment(fragment);

    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        if (null == cartFragment && fragment instanceof InteCartFragment) {
            cartFragment = (InteCartFragment) fragment;
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

}
