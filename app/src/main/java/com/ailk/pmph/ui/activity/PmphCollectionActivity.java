package com.ailk.pmph.ui.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

import com.ailk.pmph.R;
import com.ailk.pmph.base.BaseActivity;
import com.ailk.pmph.ui.fragment.ShopCollectFragment;

import butterknife.BindView;

/**
 * 类注释:
 * 项目名:pmph_android
 * 包名:com.ailk.pmph.ui.activity
 * 作者: Chrizz
 * 时间: 2016/10/19 21:16
 */

public class PmphCollectionActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;

    ShopCollectFragment shopCollectFragment;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_pmph_collection;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        if (shopCollectFragment == null) {
            shopCollectFragment = new ShopCollectFragment();
            addFragment(shopCollectFragment);
            showFragment(shopCollectFragment);
        } else {
            showFragment(shopCollectFragment);
        }
    }

    /**
     * 添加Fragment
     **/
    public void addFragment(Fragment fragment) {
        if (!fragment.isAdded()) {
            FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
            ft.add(R.id.collect_content, fragment);
            ft.commit();
        }
    }

    /**
     * 显示Fragment
     **/
    public void showFragment(Fragment fragment) {
        FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
        if (shopCollectFragment != null) {
            ft.hide(shopCollectFragment);
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
        if (null == shopCollectFragment && fragment instanceof ShopCollectFragment) {
            shopCollectFragment = (ShopCollectFragment) fragment;
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
