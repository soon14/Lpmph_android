package com.ailk.pmph.ui.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;

import com.ailk.pmph.R;
import com.ailk.pmph.base.BaseActivity;
import com.ailk.pmph.ui.fragment.SortFragment;

/**
 * 类注释:
 * 项目名:pmph_android
 * 包名:com.ailk.pmph.ui.activity
 * 作者: Chrizz
 * 时间: 2016/8/17 13:00
 */
public class SortActivity extends BaseActivity {

    private SortFragment sortFragment;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_sort;
    }

    @Override
    public void initView() {
        if (sortFragment == null) {
            sortFragment = SortFragment.newInstance();
            addFragment(sortFragment);
            showFragment(sortFragment);
        } else {
            showFragment(sortFragment);
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
        if (sortFragment != null) {
            ft.hide(sortFragment);
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
        if (null == sortFragment && fragment instanceof SortFragment) {
            sortFragment = (SortFragment) fragment;
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
