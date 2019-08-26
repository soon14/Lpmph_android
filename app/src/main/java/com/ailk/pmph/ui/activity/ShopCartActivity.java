package com.ailk.pmph.ui.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import com.ailk.pmph.R;
import com.ailk.pmph.base.BaseActivity;
import com.ailk.pmph.ui.fragment.ShopCartFragment;

/**
 * 类注释:购物车界面
 * 项目名:pmph_android
 * 包名:com.ailk.pmph.ui.activity
 * 作者: Chrizz
 * 时间: 2016/4/27 17:58
 */
public class ShopCartActivity extends BaseActivity {

    private ShopCartFragment shopCartFragment;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_shopcart;
    }

    @Override
    public void initView() {
        if (shopCartFragment == null) {
            shopCartFragment = ShopCartFragment.newInstance();
            addFragment(shopCartFragment);
            shopCartFragment.getArguments().putString("fromShopDetail", "fromShopDetail");
            showFragment(shopCartFragment);
        } else {
            showFragment(shopCartFragment);
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
        if (shopCartFragment != null) {
            ft.hide(shopCartFragment);
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
        if (null == shopCartFragment && fragment instanceof ShopCartFragment) {
            shopCartFragment = (ShopCartFragment) fragment;
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
