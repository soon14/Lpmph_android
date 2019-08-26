package com.ailk.pmph.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

import com.ailk.pmph.AppManager;
import com.ailk.pmph.R;
import com.ailk.pmph.base.BaseActivity;
import com.ailk.pmph.ui.fragment.CommentCenterFragment;
import com.ailk.pmph.ui.fragment.UnCommentCenterFragment;
import com.viewpagerindicator.TabPageIndicator;

import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * 类注释:待评价
 * 项目名:pmph_android
 * 包名:com.ailk.pmph.ui.activity
 * 作者: Chrizz
 * 时间: 2016/4/12 20:26
 */
public class UnCommentActivity extends BaseActivity implements View.OnClickListener{

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tab_titles)
    TabPageIndicator tabTitles;
    @BindView(R.id.tab_viewpager)
    ViewPager tabViewPager;

    private String[] titles = {"未评价", "已评价"};

    @Override
    protected int getContentViewId() {
        return R.layout.activity_comment_center;
    }

    public void initView() {
        CommentPagerAdapter adapter = new CommentPagerAdapter(getSupportFragmentManager(), titles);
        tabViewPager.setAdapter(adapter);
        tabTitles.setViewPager(tabViewPager);
        setVisible(tabTitles);
    }

    @Override
    public void initData() {

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

    private class CommentPagerAdapter extends FragmentPagerAdapter {

        private String[] titles;

        public CommentPagerAdapter(FragmentManager fragmentManager, String[] titles) {
            super(fragmentManager);
            this.titles = titles;
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0)
            {
                return new UnCommentCenterFragment();
            }
            else if (position == 1)
            {
                return new CommentCenterFragment();
            }
            return new UnCommentCenterFragment();
        }

        @Override
        public int getCount() {
            return titles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position % titles.length];
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
