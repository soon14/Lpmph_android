package com.ailk.pmph.ui.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.ailk.pmph.R;
import com.ailk.pmph.base.BaseActivity;
import com.ailk.pmph.ui.fragment.PersonalCenterFragment;

/**
 * 类注释:
 * 项目名:pmph_android
 * 包名:com.ailk.pmph.ui.activity
 * 作者: Chrizz
 * 时间: 2016/9/29 19:54
 */

public class PersonalCenterActivity extends BaseActivity {

    private PersonalCenterFragment fragment;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_personal_center;
    }

    @Override
    public void initView() {
        if (fragment != null) {
            removeFragment(fragment);
            fragment = null;
        }
        fragment = new PersonalCenterFragment();
        addFragment(fragment);
        showFragment(fragment);
    }

    @Override
    public void initData() {

    }

    /**
     * 显示Fragment
     **/
    public void showFragment(Fragment fragment) {
        FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
        // 判断页面是否已经创建，如果已经创建，那么就隐藏掉
        if (fragment != null) {
            ft.hide(fragment);
        }
        ft.show(fragment);
        ft.commitAllowingStateLoss();
    }

    /**
     * 添加Fragment
     **/
    public void addFragment(Fragment fragment) {
        if (!fragment.isAdded()) {
            FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
            ft.add(R.id.main_framelayout, fragment);
            ft.commit();
        }
    }

    /**
     * 删除Fragment
     **/
    public void removeFragment(Fragment fragment) {
        FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
        ft.remove(fragment);
        ft.commit();
    }

}
