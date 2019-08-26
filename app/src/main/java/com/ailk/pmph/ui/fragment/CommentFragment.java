package com.ailk.pmph.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ailk.pmph.R;
import com.ailk.pmph.base.BaseFragment;
import com.ailk.pmph.utils.Constant;
import com.ailk.pmph.utils.StringUtil;
import com.viewpagerindicator.TabPageIndicator;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Project : PMPH
 * Created by 王可 on 16/3/31.
 */
public class CommentFragment extends BaseFragment {

    @BindView(R.id.comment_viewpager)
    ViewPager viewPager;
    @BindView(R.id.comment_titles)
    TabPageIndicator tabPageIndicator;

    private static String[] TITLE = {"全部评论", "好评", "中评", "差评"};
    private String skuId;
    private String gdsId;
    public CommentFragment() {

    }

    public static CommentFragment newInstance() {
        CommentFragment fragment = new CommentFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_comment;
    }

    @Override
    public void initView(View view) {
        CommentViewPagerAdapter adapter = new CommentViewPagerAdapter(getFragmentManager());
        viewPager.setAdapter(adapter);
        tabPageIndicator.setViewPager(viewPager);
        setVisible(tabPageIndicator);
    }

    @Override
    public void initData() {
        if (getArguments() != null) {
            if (StringUtil.isNotEmpty(getArguments().getString(Constant.SHOP_SKU_ID))){
                skuId = getArguments().getString(Constant.SHOP_SKU_ID);
            }
            if (StringUtil.isNotEmpty(getArguments().getString(Constant.SHOP_GDS_ID))){
                gdsId = getArguments().getString(Constant.SHOP_GDS_ID);
            }
        }
    }

    private class CommentViewPagerAdapter extends FragmentPagerAdapter {

        public CommentViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            CommentListFragment commentListFragment = CommentListFragment.newInstance();
            commentListFragment.getArguments().putString(Constant.SHOP_GDS_ID,gdsId);
            commentListFragment.getArguments().putString(Constant.SHOP_SKU_ID,skuId);
            switch (position){
                case 0:
                    //全部
                    commentListFragment.getArguments().putString("type", "");
                    break;
                case 1:
                    //好评
                    commentListFragment.getArguments().putString("type", "00");
                    break;
                case 2:
                    //中评
                    commentListFragment.getArguments().putString("type", "01");
                    break;
                case 3:
                    //差评
                    commentListFragment.getArguments().putString("type", "02");
                    break;
            }
            return commentListFragment;


        }

        @Override
        public int getCount() {
            return TITLE.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return  TITLE[position % TITLE.length];
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            return super.instantiateItem(container, position);
        }

    }

}
