package com.ailk.pmph.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.ailk.pmph.AppManager;
import com.ailk.pmph.R;
import com.ailk.pmph.base.BaseActivity;
import com.ailk.pmph.ui.fragment.ShopCollectFragment;
import com.ailk.pmph.ui.fragment.StoreCollectFragment;

import butterknife.ButterKnife;
import butterknife.BindView;

/**
 * Project : PMPH
 * Created by 王可 on 16/4/8.
 */
public class MyCollectionActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title_layout)
    TabLayout titleLayout;
    @BindView(R.id.content_layout)
    ViewPager contentLayout;

    private TextView tabContent1;
    private TextView tabContent2;

    private String[] titles = {"商品", "店铺"};

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        AppManager.getAppManager().addActivity(this);
//        setContentView(R.layout.activity_collect);
//        ButterKnife.bind(this);
//        initToolBar();
//        initViewPager();
//    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_collect;
    }

    @Override
    public void initView() {
        initToolBar();
    }

    @Override
    public void initData() {
        initViewPager();
    }

    private void initToolBar() {
        setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.arrow_left_black);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        titleLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(this, R.color.black));
        titleLayout.setTabTextColors(ContextCompat.getColor(this, R.color.black),ContextCompat.getColor(this, R.color.black));

        titleLayout.addTab(titleLayout.newTab().setCustomView(R.layout.item_title1));
        tabContent1 = (TextView) titleLayout.findViewById(R.id.tab_content1);
        tabContent1.setText("商品");
        tabContent1.setTextColor(ContextCompat.getColor(this, R.color.black));
        tabContent1.setTextSize(15);

        titleLayout.addTab(titleLayout.newTab().setCustomView(R.layout.item_title2));
        tabContent2 = (TextView) titleLayout.findViewById(R.id.tab_content2);
        tabContent2.setText("店铺");
        tabContent2.setTextColor(ContextCompat.getColor(this, R.color.black));
        tabContent2.setTextSize(14);

        titleLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                contentLayout.setCurrentItem(tab.getPosition());
                switch (tab.getPosition()) {
                    case 0:
                        tabContent1.setTextColor(ContextCompat.getColor(MyCollectionActivity.this, R.color.black));
                        tabContent1.setTextSize(15);
                        break;
                    case 1:
                        tabContent2.setTextColor(ContextCompat.getColor(MyCollectionActivity.this, R.color.black));
                        tabContent2.setTextSize(15);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tabContent1.setTextColor(ContextCompat.getColor(MyCollectionActivity.this, R.color.black));
                tabContent1.setTextSize(14);
                tabContent2.setTextColor(ContextCompat.getColor(MyCollectionActivity.this, R.color.black));
                tabContent2.setTextSize(14);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void initViewPager() {
        contentLayout.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), titles));
        contentLayout.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                titleLayout.getTabAt(position).select();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        private String[] titles;

        public MyFragmentPagerAdapter(FragmentManager fragmentManager, String[] titles) {
            super(fragmentManager);
            this.titles = titles;
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return new ShopCollectFragment();
            } else if (position == 1) {
                return new StoreCollectFragment();
            }
            return new ShopCollectFragment();
        }

        @Override
        public int getCount() {
            return titles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
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
